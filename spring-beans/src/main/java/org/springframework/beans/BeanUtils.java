/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.beans;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import kotlin.jvm.JvmClassMappingKt;
import kotlin.reflect.KFunction;
import kotlin.reflect.KParameter;
import kotlin.reflect.full.KClasses;
import kotlin.reflect.jvm.ReflectJvmMapping;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.core.KotlinDetector;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

/**
 * JavaBeans的静态便利方法：用于实例化Bean、检查Bean属性类型、复制Bean属性等
 */
public abstract class BeanUtils {

	private static final Log logger = LogFactory.getLog(BeanUtils.class);

	private static final Set<Class<?>> unknownEditorTypes =
			Collections.newSetFromMap(new ConcurrentReferenceHashMap<>(64));


	/**
	 * 使用无参数构造函数实例化类的便利方法
	 */
	@Deprecated
	public static <T> T instantiate(Class<T> clazz) throws BeanInstantiationException {
		Assert.notNull(clazz, "Class must not be null");
		if (clazz.isInterface()) {
			throw new BeanInstantiationException(clazz, "Specified class is an interface");
		}
		try {
			return clazz.newInstance();
		}
		catch (InstantiationException ex) {
			throw new BeanInstantiationException(clazz, "Is it an abstract class?", ex);
		}
		catch (IllegalAccessException ex) {
			throw new BeanInstantiationException(clazz, "Is the constructor accessible?", ex);
		}
	}

	/**
	 * 使用其“primary”构造函数（对于kotlin类，可能声明了默认参数）或其默认构造函数（对于常规java类，需要标准的无参数设置）实例化类
	 */
	public static <T> T instantiateClass(Class<T> clazz) throws BeanInstantiationException {
		Assert.notNull(clazz, "Class must not be null");
		if (clazz.isInterface()) {
			throw new BeanInstantiationException(clazz, "Specified class is an interface");
		}
		try {
			return instantiateClass(clazz.getDeclaredConstructor());
		}
		catch (NoSuchMethodException ex) {
			Constructor<T> ctor = findPrimaryConstructor(clazz);
			if (ctor != null) {
				return instantiateClass(ctor);
			}
			throw new BeanInstantiationException(clazz, "No default constructor found", ex);
		}
		catch (LinkageError err) {
			throw new BeanInstantiationException(clazz, "Unresolvable class definition", err);
		}
	}

	/**
	 * 使用类的无参数构造函数实例化该类，并将新实例作为指定的可分配类型返回
	 */
	@SuppressWarnings("unchecked")
	public static <T> T instantiateClass(Class<?> clazz, Class<T> assignableTo) throws BeanInstantiationException {
		Assert.isAssignable(assignableTo, clazz);
		return (T) instantiateClass(clazz);
	}

	/**
	 * 使用给定构造函数实例化类的便利方法
	 */
	public static <T> T instantiateClass(Constructor<T> ctor, Object... args) throws BeanInstantiationException {
		Assert.notNull(ctor, "Constructor must not be null");
		try {
			ReflectionUtils.makeAccessible(ctor);
			return (KotlinDetector.isKotlinReflectPresent() && KotlinDetector.isKotlinType(ctor.getDeclaringClass()) ?
					KotlinDelegate.instantiateClass(ctor, args) : ctor.newInstance(args));
		}
		catch (InstantiationException ex) {
			throw new BeanInstantiationException(ctor, "Is it an abstract class?", ex);
		}
		catch (IllegalAccessException ex) {
			throw new BeanInstantiationException(ctor, "Is the constructor accessible?", ex);
		}
		catch (IllegalArgumentException ex) {
			throw new BeanInstantiationException(ctor, "Illegal arguments for constructor", ex);
		}
		catch (InvocationTargetException ex) {
			throw new BeanInstantiationException(ctor, "Constructor threw exception", ex.getTargetException());
		}
	}

	/**
	 * 返回所提供类的主构造函数对于Kotlin类，这将返回与Kotlin主构造函数相对应的Java构造函数（如Kotlin规范中所定义）否则，
	 * 特别是对于非Kotlin类，这只返回{@code null}
	 */
	@SuppressWarnings("unchecked")
	@Nullable
	public static <T> Constructor<T> findPrimaryConstructor(Class<T> clazz) {
		Assert.notNull(clazz, "Class must not be null");
		if (KotlinDetector.isKotlinReflectPresent() && KotlinDetector.isKotlinType(clazz)) {
			Constructor<T> kotlinPrimaryConstructor = KotlinDelegate.findPrimaryConstructor(clazz);
			if (kotlinPrimaryConstructor != null) {
				return kotlinPrimaryConstructor;
			}
		}
		return null;
	}

	/**
	 * 查找具有给定方法名和给定参数类型的方法，该方法在给定类或其超类之一上声明。首选公共方法，但也将返回受保护的包访问或私有方法
	 */
	@Nullable
	public static Method findMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
		try {
			return clazz.getMethod(methodName, paramTypes);
		}
		catch (NoSuchMethodException ex) {
			return findDeclaredMethod(clazz, methodName, paramTypes);
		}
	}

	/**
	 * 查找具有给定方法名和给定参数类型的方法，该方法在给定类或其超类之一上声明。将返回公共、受保护、包访问或私有方法
	 */
	@Nullable
	public static Method findDeclaredMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
		try {
			return clazz.getDeclaredMethod(methodName, paramTypes);
		}
		catch (NoSuchMethodException ex) {
			if (clazz.getSuperclass() != null) {
				return findDeclaredMethod(clazz.getSuperclass(), methodName, paramTypes);
			}
			return null;
		}
	}

	/**
	 * 查找在给定类或其超类之一上声明的具有给定方法名和最小参数（最佳情况：无）的方法。首选公共方法，但也将返回受保护的包访问或私有方法
	 */
	@Nullable
	public static Method findMethodWithMinimalParameters(Class<?> clazz, String methodName)
			throws IllegalArgumentException {

		Method targetMethod = findMethodWithMinimalParameters(clazz.getMethods(), methodName);
		if (targetMethod == null) {
			targetMethod = findDeclaredMethodWithMinimalParameters(clazz, methodName);
		}
		return targetMethod;
	}

	/**
	 * 查找在给定类或其超类之一上声明的具有给定方法名和最小参数（最佳情况：无）的方法。将返回公共、受保护、包访问或私有方法
	 */
	@Nullable
	public static Method findDeclaredMethodWithMinimalParameters(Class<?> clazz, String methodName)
			throws IllegalArgumentException {

		Method targetMethod = findMethodWithMinimalParameters(clazz.getDeclaredMethods(), methodName);
		if (targetMethod == null && clazz.getSuperclass() != null) {
			targetMethod = findDeclaredMethodWithMinimalParameters(clazz.getSuperclass(), methodName);
		}
		return targetMethod;
	}

	/**
	 * 在给定的方法列表中查找具有给定方法名和最小参数（最佳情况：无）的方法
	 */
	@Nullable
	public static Method findMethodWithMinimalParameters(Method[] methods, String methodName)
			throws IllegalArgumentException {

		Method targetMethod = null;
		int numMethodsFoundWithCurrentMinimumArgs = 0;
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				int numParams = method.getParameterCount();
				if (targetMethod == null || numParams < targetMethod.getParameterCount()) {
					targetMethod = method;
					numMethodsFoundWithCurrentMinimumArgs = 1;
				}
				else if (!method.isBridge() && targetMethod.getParameterCount() == numParams) {
					if (targetMethod.isBridge()) {
						// Prefer regular method over bridge...
						targetMethod = method;
					}
					else {
						// Additional candidate with same length
						numMethodsFoundWithCurrentMinimumArgs++;
					}
				}
			}
		}
		if (numMethodsFoundWithCurrentMinimumArgs > 1) {
			throw new IllegalArgumentException("Cannot resolve method '" + methodName +
					"' to a unique method. Attempted to resolve to overloaded method with " +
					"the least number of parameters but there were " +
					numMethodsFoundWithCurrentMinimumArgs + " candidates.");
		}
		return targetMethod;
	}

	/**
	 * 解析格式为{@code methodName[（[arg_list]）]}的方法签名，
	 * 其中{@code arg_list}是可选的、逗号分隔的完全限定类型名列表，并尝试根据提供的{@code class}解析该签名
	 */
	@Nullable
	public static Method resolveSignature(String signature, Class<?> clazz) {
		Assert.hasText(signature, "'signature' must not be empty");
		Assert.notNull(clazz, "Class must not be null");
		int startParen = signature.indexOf('(');
		int endParen = signature.indexOf(')');
		if (startParen > -1 && endParen == -1) {
			throw new IllegalArgumentException("Invalid method signature '" + signature +
					"': expected closing ')' for args list");
		}
		else if (startParen == -1 && endParen > -1) {
			throw new IllegalArgumentException("Invalid method signature '" + signature +
					"': expected opening '(' for args list");
		}
		else if (startParen == -1) {
			return findMethodWithMinimalParameters(clazz, signature);
		}
		else {
			String methodName = signature.substring(0, startParen);
			String[] parameterTypeNames =
					StringUtils.commaDelimitedListToStringArray(signature.substring(startParen + 1, endParen));
			Class<?>[] parameterTypes = new Class<?>[parameterTypeNames.length];
			for (int i = 0; i < parameterTypeNames.length; i++) {
				String parameterTypeName = parameterTypeNames[i].trim();
				try {
					parameterTypes[i] = ClassUtils.forName(parameterTypeName, clazz.getClassLoader());
				}
				catch (Throwable ex) {
					throw new IllegalArgumentException("Invalid method signature: unable to resolve type [" +
							parameterTypeName + "] for argument " + i + ". Root cause: " + ex);
				}
			}
			return findMethod(clazz, methodName, parameterTypes);
		}
	}


	/**
	 * 检索给定类的javabeans{@code PropertyDescriptors}
	 */
	public static PropertyDescriptor[] getPropertyDescriptors(Class<?> clazz) throws BeansException {
		CachedIntrospectionResults cr = CachedIntrospectionResults.forClass(clazz);
		return cr.getPropertyDescriptors();
	}

	/**
	 * 检索给定属性的JavaBeans{@code PropertyDescriptors}
	 */
	@Nullable
	public static PropertyDescriptor getPropertyDescriptor(Class<?> clazz, String propertyName)
			throws BeansException {

		CachedIntrospectionResults cr = CachedIntrospectionResults.forClass(clazz);
		return cr.getPropertyDescriptor(propertyName);
	}

	/**
	 * 找到给定方法的javabeans{@code PropertyDescriptor}，方法可以是该bean属性的read方法，也可以是write方法
	 */
	@Nullable
	public static PropertyDescriptor findPropertyForMethod(Method method) throws BeansException {
		return findPropertyForMethod(method, method.getDeclaringClass());
	}

	/**
	 * 找到给定方法的javabeans{@code PropertyDescriptor}，方法可以是该bean属性的read方法，也可以是write方法
	 */
	@Nullable
	public static PropertyDescriptor findPropertyForMethod(Method method, Class<?> clazz) throws BeansException {
		Assert.notNull(method, "Method must not be null");
		PropertyDescriptor[] pds = getPropertyDescriptors(clazz);
		for (PropertyDescriptor pd : pds) {
			if (method.equals(pd.getReadMethod()) || method.equals(pd.getWriteMethod())) {
				return pd;
			}
		}
		return null;
	}

	/**
	 * 按照“editor”后缀约定（例如“mypackage.mydomainclass”->“mypackage.mydomainclasseditor”）查找JavaBeans属性编辑器
	 */
	@Nullable
	public static PropertyEditor findEditorByConvention(@Nullable Class<?> targetType) {
		if (targetType == null || targetType.isArray() || unknownEditorTypes.contains(targetType)) {
			return null;
		}
		ClassLoader cl = targetType.getClassLoader();
		if (cl == null) {
			try {
				cl = ClassLoader.getSystemClassLoader();
				if (cl == null) {
					return null;
				}
			}
			catch (Throwable ex) {
				// e.g. AccessControlException on Google App Engine
				if (logger.isDebugEnabled()) {
					logger.debug("Could not access system ClassLoader: " + ex);
				}
				return null;
			}
		}
		String editorName = targetType.getName() + "Editor";
		try {
			Class<?> editorClass = cl.loadClass(editorName);
			if (!PropertyEditor.class.isAssignableFrom(editorClass)) {
				if (logger.isInfoEnabled()) {
					logger.info("Editor class [" + editorName +
							"] does not implement [java.beans.PropertyEditor] interface");
				}
				unknownEditorTypes.add(targetType);
				return null;
			}
			return (PropertyEditor) instantiateClass(editorClass);
		}
		catch (ClassNotFoundException ex) {
			if (logger.isTraceEnabled()) {
				logger.trace("No property editor [" + editorName + "] found for type " +
						targetType.getName() + " according to 'Editor' suffix convention");
			}
			unknownEditorTypes.add(targetType);
			return null;
		}
	}

	/**
	 * 如果可能，从给定的类/接口确定给定属性的bean属性类型
	 */
	public static Class<?> findPropertyType(String propertyName, @Nullable Class<?>... beanClasses) {
		if (beanClasses != null) {
			for (Class<?> beanClass : beanClasses) {
				PropertyDescriptor pd = getPropertyDescriptor(beanClass, propertyName);
				if (pd != null) {
					return pd.getPropertyType();
				}
			}
		}
		return Object.class;
	}

	/**
	 * 为指定属性的write方法获取新的MethodParameter对象
	 */
	public static MethodParameter getWriteMethodParameter(PropertyDescriptor pd) {
		if (pd instanceof GenericTypeAwarePropertyDescriptor) {
			return new MethodParameter(((GenericTypeAwarePropertyDescriptor) pd).getWriteMethodParameter());
		}
		else {
			Method writeMethod = pd.getWriteMethod();
			Assert.state(writeMethod != null, "No write method available");
			return new MethodParameter(writeMethod, 0);
		}
	}

	/**
	 * 检查给定类型是否表示“简单”属性：原语、字符串或其他字符序列、数字、日期、uri、url、区域设置、类或相应的数组
	 */
	public static boolean isSimpleProperty(Class<?> clazz) {
		Assert.notNull(clazz, "Class must not be null");
		return isSimpleValueType(clazz) || (clazz.isArray() && isSimpleValueType(clazz.getComponentType()));
	}

	/**
	 * 检查给定类型是否表示“简单”值类型：原语、枚举、字符串或其他字符序列、数字、日期、uri、url、区域设置或类
	 */
	public static boolean isSimpleValueType(Class<?> clazz) {
		return (ClassUtils.isPrimitiveOrWrapper(clazz) ||
				Enum.class.isAssignableFrom(clazz) ||
				CharSequence.class.isAssignableFrom(clazz) ||
				Number.class.isAssignableFrom(clazz) ||
				Date.class.isAssignableFrom(clazz) ||
				URI.class == clazz || URL.class == clazz ||
				Locale.class == clazz || Class.class == clazz);
	}


	/**
	 * 将给定源bean的属性值复制到目标bean中
	 */
	public static void copyProperties(Object source, Object target) throws BeansException {
		copyProperties(source, target, null, (String[]) null);
	}

	/**
	 * 将给定源bean的属性值复制到给定的目标bean中，只设置在给定的“可编辑”类（或接口）中定义的属性
	 */
	public static void copyProperties(Object source, Object target, Class<?> editable) throws BeansException {
		copyProperties(source, target, editable, (String[]) null);
	}

	/**
	 * 将给定源bean的属性值复制到给定目标bean中，忽略给定的“ignoreProperties”
	 */
	public static void copyProperties(Object source, Object target, String... ignoreProperties) throws BeansException {
		copyProperties(source, target, null, ignoreProperties);
	}

	/**
	 * 将给定源bean的属性值复制到给定目标bean中
	 */
	private static void copyProperties(Object source, Object target, @Nullable Class<?> editable,
			@Nullable String... ignoreProperties) throws BeansException {

		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		Class<?> actualEditable = target.getClass();
		if (editable != null) {
			if (!editable.isInstance(target)) {
				throw new IllegalArgumentException("Target class [" + target.getClass().getName() +
						"] not assignable to Editable class [" + editable.getName() + "]");
			}
			actualEditable = editable;
		}
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

		for (PropertyDescriptor targetPd : targetPds) {
			Method writeMethod = targetPd.getWriteMethod();
			if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null) {
					Method readMethod = sourcePd.getReadMethod();
					if (readMethod != null &&
							ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
						try {
							if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
								readMethod.setAccessible(true);
							}
							Object value = readMethod.invoke(source);
							if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(target, value);
						}
						catch (Throwable ex) {
							throw new FatalBeanException(
									"Could not copy property '" + targetPd.getName() + "' from source to target", ex);
						}
					}
				}
			}
		}
	}


	/**
	 * 内部类以避免运行时对kotlin的硬依赖
	 */
	private static class KotlinDelegate {

		/**
		 * Retrieve the Java constructor corresponding to the Kotlin primary constructor, if any.
		 * @param clazz the {@link Class} of the Kotlin class
		 * @see <a href="http://kotlinlang.org/docs/reference/classes.html#constructors">
		 * http://kotlinlang.org/docs/reference/classes.html#constructors</a>
		 */
		@Nullable
		public static <T> Constructor<T> findPrimaryConstructor(Class<T> clazz) {
			try {
				KFunction<T> primaryCtor = KClasses.getPrimaryConstructor(JvmClassMappingKt.getKotlinClass(clazz));
				if (primaryCtor == null) {
					return null;
				}
				Constructor<T> constructor = ReflectJvmMapping.getJavaConstructor(primaryCtor);
				if (constructor == null) {
					throw new IllegalStateException(
							"Failed to find Java constructor for Kotlin primary constructor: " + clazz.getName());
				}
				return constructor;
			}
			catch (UnsupportedOperationException ex) {
				return null;
			}
		}

		/**
		 * Instantiate a Kotlin class using the provided constructor.
		 * @param ctor the constructor of the Kotlin class to instantiate
		 * @param args the constructor arguments to apply
		 * (use {@code null} for unspecified parameter if needed)
		 */
		public static <T> T instantiateClass(Constructor<T> ctor, Object... args)
				throws IllegalAccessException, InvocationTargetException, InstantiationException {

			KFunction<T> kotlinConstructor = ReflectJvmMapping.getKotlinFunction(ctor);
			if (kotlinConstructor == null) {
				return ctor.newInstance(args);
			}
			List<KParameter> parameters = kotlinConstructor.getParameters();
			Map<KParameter, Object> argParameters = new HashMap<>(parameters.size());
			Assert.isTrue(args.length <= parameters.size(),
					"Number of provided arguments should be less of equals than number of constructor parameters");
			for (int i = 0 ; i < args.length ; i++) {
				if (!(parameters.get(i).isOptional() && args[i] == null)) {
					argParameters.put(parameters.get(i), args[i]);
				}
			}
			return kotlinConstructor.callBy(argParameters);
		}

	}

}
