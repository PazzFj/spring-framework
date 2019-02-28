/*
 * Copyright 2002-2017 the original author or authors.
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

package org.springframework.context.annotation;

import java.lang.annotation.Annotation;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionCustomizer;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AutowireCandidateQualifier;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.core.env.Environment;
import org.springframework.core.env.EnvironmentCapable;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Convenient adapter for programmatic registration of annotated bean classes.
 * This is an alternative to {@link ClassPathBeanDefinitionScanner}, applying
 * the same resolution of annotations but for explicitly registered classes only.
 *
 * @author Juergen Hoeller
 * @author Chris Beams
 * @author Sam Brannen
 * @author Phillip Webb
 * @since 3.0
 * @see AnnotationConfigApplicationContext#register
 */
public class AnnotatedBeanDefinitionReader {

	private final BeanDefinitionRegistry registry;

	private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

	private ScopeMetadataResolver scopeMetadataResolver = new AnnotationScopeMetadataResolver();

	private ConditionEvaluator conditionEvaluator;


	/**
	 * Create a new {@code AnnotatedBeanDefinitionReader} for the given registry.
	 * If the registry is {@link EnvironmentCapable}, e.g. is an {@code ApplicationContext},
	 * the {@link Environment} will be inherited, otherwise a new
	 * {@link StandardEnvironment} will be created and used.
	 * @param registry the {@code BeanFactory} to load bean definitions into,
	 * in the form of a {@code BeanDefinitionRegistry}
	 * @see #AnnotatedBeanDefinitionReader(BeanDefinitionRegistry, Environment)
	 * @see #setEnvironment(Environment)
	 */
	public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry registry) {
		this(registry, getOrCreateEnvironment(registry));
	}

	/**
	 * Create a new {@code AnnotatedBeanDefinitionReader} for the given registry and using
	 * the given {@link Environment}.
	 * @param registry the {@code BeanFactory} to load bean definitions into,
	 * in the form of a {@code BeanDefinitionRegistry}
	 * @param environment the {@code Environment} to use when evaluating bean definition
	 * profiles.
	 * @since 3.1
	 */
	public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry registry, Environment environment) {
		Assert.notNull(registry, "BeanDefinitionRegistry must not be null");
		Assert.notNull(environment, "Environment must not be null");
		this.registry = registry;
		this.conditionEvaluator = new ConditionEvaluator(registry, environment, null);
		AnnotationConfigUtils.registerAnnotationConfigProcessors(this.registry);
	}


	/**
	 * Return the BeanDefinitionRegistry that this scanner operates on.
	 */
	public final BeanDefinitionRegistry getRegistry() {
		return this.registry;
	}

	/**
	 * Set the Environment to use when evaluating whether
	 * {@link Conditional @Conditional}-annotated component classes should be registered.
	 * <p>The default is a {@link StandardEnvironment}.
	 * @see #registerBean(Class, String, Class...)
	 */
	public void setEnvironment(Environment environment) {
		this.conditionEvaluator = new ConditionEvaluator(this.registry, environment, null);
	}

	/**
	 * Set the BeanNameGenerator to use for detected bean classes.
	 * <p>The default is a {@link AnnotationBeanNameGenerator}.
	 */
	public void setBeanNameGenerator(@Nullable BeanNameGenerator beanNameGenerator) {
		this.beanNameGenerator = (beanNameGenerator != null ? beanNameGenerator : new AnnotationBeanNameGenerator());
	}

	/**
	 * Set the ScopeMetadataResolver to use for detected bean classes.
	 * <p>The default is an {@link AnnotationScopeMetadataResolver}.
	 */
	public void setScopeMetadataResolver(@Nullable ScopeMetadataResolver scopeMetadataResolver) {
		this.scopeMetadataResolver =
				(scopeMetadataResolver != null ? scopeMetadataResolver : new AnnotationScopeMetadataResolver());
	}


	/**
	 * 注册一个或者多个配置类
	 */
	public void register(Class<?>... annotatedClasses) {
		for (Class<?> annotatedClass : annotatedClasses) {
			registerBean(annotatedClass);
		}
	}

	/**
	 * 注册一个配置类
	 */
	public void registerBean(Class<?> annotatedClass) {
		doRegisterBean(annotatedClass, null, null, null);
	}

	/**
	 * 从给定的bean类注册一个bean，从类声明的注释派生其元数据
	 */
	public <T> void registerBean(Class<T> annotatedClass, @Nullable Supplier<T> instanceSupplier) {
		doRegisterBean(annotatedClass, instanceSupplier, null, null);
	}

	/**
	 * 从给定的bean类注册一个bean，从类声明的注释派生其元数据
	 */
	public <T> void registerBean(Class<T> annotatedClass, String name, @Nullable Supplier<T> instanceSupplier) {
		doRegisterBean(annotatedClass, instanceSupplier, name, null);
	}

	/**
	 * 从给定的bean类注册一个bean，从类声明的注释派生其元数据
	 */
	@SuppressWarnings("unchecked")
	public void registerBean(Class<?> annotatedClass, Class<? extends Annotation>... qualifiers) {
		doRegisterBean(annotatedClass, null, null, qualifiers);
	}

	/**
	 * 从给定的bean类注册一个bean，从类声明的注释派生其元数据
	 */
	@SuppressWarnings("unchecked")
	public void registerBean(Class<?> annotatedClass, String name, Class<? extends Annotation>... qualifiers) {
		doRegisterBean(annotatedClass, null, name, qualifiers);
	}

	/**
	 * 从给定的bean类注册一个bean，从类声明的注释派生其元数据
	 * @since 5.0
	 */
	<T> void doRegisterBean(Class<T> annotatedClass, @Nullable Supplier<T> instanceSupplier, @Nullable String name,
			@Nullable Class<? extends Annotation>[] qualifiers, BeanDefinitionCustomizer... definitionCustomizers) {

		// 创建AnnotatedGenericBeanDefinition  bean定义
		AnnotatedGenericBeanDefinition abd = new AnnotatedGenericBeanDefinition(annotatedClass);
		// 是否跳过@Conditional注解
		if (this.conditionEvaluator.shouldSkip(abd.getMetadata())) {
			return;
		}

		// 设置实例提供者Supplier<?>对象
		abd.setInstanceSupplier(instanceSupplier);
		// bean作用域分解器, 设置作用域
		ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(abd);
		abd.setScope(scopeMetadata.getScopeName());
		// 使用beanName生成器, 生成beanName
		String beanName = (name != null ? name : this.beanNameGenerator.generateBeanName(abd, this.registry));

		// 处理公共定义的注解, @DependsOn  @Lazy  @Primary...
		AnnotationConfigUtils.processCommonDefinitionAnnotations(abd);
		if (qualifiers != null) {
			for (Class<? extends Annotation> qualifier : qualifiers) {
				if (Primary.class == qualifier) {
					abd.setPrimary(true);
				}
				else if (Lazy.class == qualifier) {
					abd.setLazyInit(true);
				}
				else {
					abd.addQualifier(new AutowireCandidateQualifier(qualifier));
				}
			}
		}
		// Bean定义定制
		for (BeanDefinitionCustomizer customizer : definitionCustomizers) {
			customizer.customize(abd);
		}

		// 封装Bean定义持有者
		BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(abd, beanName);
		// 是否申请作用域代理模式 (默认false)
		definitionHolder = AnnotationConfigUtils.applyScopedProxyMode(scopeMetadata, definitionHolder, this.registry);
		// 注册Bean定义, 通过Holder持有者, 及注册工厂(BeanDefinitionRegistry)
		BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, this.registry);
	}


	/**
	 * Get the Environment from the given registry if possible, otherwise return a new
	 * StandardEnvironment.
	 */
	private static Environment getOrCreateEnvironment(BeanDefinitionRegistry registry) {
		Assert.notNull(registry, "BeanDefinitionRegistry must not be null");
		if (registry instanceof EnvironmentCapable) {
			return ((EnvironmentCapable) registry).getEnvironment();
		}
		return new StandardEnvironment();
	}

}
