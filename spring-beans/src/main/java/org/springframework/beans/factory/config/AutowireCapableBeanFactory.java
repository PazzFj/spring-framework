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

package org.springframework.beans.factory.config;

import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.lang.Nullable;

/**
 * 扩展{@link org.springframework.beans.factory.BeanFactory}接口将由能够自动装配的bean工厂实现
 * <p>
 * 自动装配的bean工厂
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

	/**
	 * 常量，表示没有外部定义的自动装配。注意，BeanFactoryAware等和注解驱动的注入仍然会被应用
	 */
	int AUTOWIRE_NO = 0;
	/**
	 * 常量，该常量通过名称指示自动装配bean属性(应用于所有bean属性设置程序)
	 */
	int AUTOWIRE_BY_NAME = 1;
	/**
	 * 常量，该常量按类型指示自动装配bean属性(应用于所有bean属性设置程序)
	 */
	int AUTOWIRE_BY_TYPE = 2;
	/**
	 * 常量，指示自动装配可以满足的最贪婪的构造函数(涉及解析适当的构造函数)
	 */
	int AUTOWIRE_CONSTRUCTOR = 3;
	/**
	 * 常量，指示通过bean类的内省确定适当的自动装配策略
	 */
	@Deprecated
	int AUTOWIRE_AUTODETECT = 4;
	/**
	 * 初始化现有bean实例时“原始实例”约定的后缀:附加到完全限定的bean类名，例如。"com.mypackage.MyClass.ORIGINAL"，以强制返回给定的实例，即没有代理等
	 */
	String ORIGINAL_INSTANCE_SUFFIX = ".ORIGINAL";


	//-------------------------------------------------------------------------
	// Typical methods for creating and populating external bean instances
	//-------------------------------------------------------------------------

	/**
	 * 完全创建给定类的新bean实例
	 */
	<T> T createBean(Class<T> beanClass) throws BeansException;

	/**
	 * 通过应用实例化后回调和bean属性后处理(例如，对于注释驱动的注入)来填充给定的bean实例
	 */
	void autowireBean(Object existingBean) throws BeansException;

	/**
	 * 配置给定的原始bean:自动装配bean属性、应用bean属性值、应用工厂回调(如{@code setBeanName}和{@code setBeanFactory})，还应用所有bean后处理器(包括可能包装给定原始bean的处理器)
	 */
	Object configureBean(Object existingBean, String beanName) throws BeansException;


	//-------------------------------------------------------------------------
	// Specialized methods for fine-grained control over the bean lifecycle
	// 用于对bean生命周期进行详细的控制的专用方法
	//-------------------------------------------------------------------------

	/**
	 * 用指定的自动装配策略完全创建给定类的新bean实例。这里支持此接口中定义的所有常量
	 */
	Object createBean(Class<?> beanClass, int autowireMode, boolean dependencyCheck)
			throws BeansException;

	/**
	 * 自动装配
	 */
	Object autowire(Class<?> beanClass, int autowireMode, boolean dependencyCheck)
			throws BeansException;

	/**
	 * 按名称或类型自动装配给定bean实例的bean属性
	 */
	void autowireBeanProperties(Object existingBean, int autowireMode, boolean dependencyCheck)
			throws BeansException;

	/**
	 * 请求bean属性
	 */
	void applyBeanPropertyValues(Object existingBean, String beanName)
			throws BeansException;

	/**
	 * 初始化给定的bean
	 */
	Object initializeBean(Object existingBean, String beanName)
			throws BeansException;

	/**
	 * 请求bean实例化之前调用处理器
	 */
	Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
			throws BeansException;

	/**
	 * 将{@link BeanPostProcessor BeanPostProcessor}应用于给定的现有bean实例, 调用它们的{@code postProcessAfterInitialization}方法
	 * 返回的bean实例可能是原始bean的包装器
	 */
	Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
			throws BeansException;

	/**
	 * 销毁给定的实例对象
	 */
	void destroyBean(Object existingBean);


	//-------------------------------------------------------------------------
	// Delegate methods for resolving injection points
	//-------------------------------------------------------------------------

	/**
	 * 解析唯一匹配给定对象类型(如果有的话)的bean实例，包括它的bean名称
	 */
	<T> NamedBeanHolder<T> resolveNamedBean(Class<T> requiredType) throws BeansException;

	/**
	 * 针对此工厂中定义的bean解析指定的依赖项
	 */
	@Nullable
	Object resolveDependency(DependencyDescriptor descriptor, @Nullable String requestingBeanName) throws BeansException;

	/**
	 * 针对此工厂中定义的bean解析指定的依赖项
	 */
	@Nullable
	Object resolveDependency(DependencyDescriptor descriptor, @Nullable String requestingBeanName, @Nullable Set<String> autowiredBeanNames, @Nullable TypeConverter typeConverter) throws BeansException;

}
