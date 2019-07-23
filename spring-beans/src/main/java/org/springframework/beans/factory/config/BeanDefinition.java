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

import org.springframework.beans.BeanMetadataElement;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.core.AttributeAccessor;
import org.springframework.lang.Nullable;

/**
 * bean定义描述一个bean实例，它具有属性值、构造函数参数值和具体实现提供的进一步信息
 * bean定义 + 属性存取 + bean元数据节点
 *
 * @see ConfigurableListableBeanFactory#getBeanDefinition
 * @see org.springframework.beans.factory.support.RootBeanDefinition
 * @see org.springframework.beans.factory.support.ChildBeanDefinition
 */
public interface BeanDefinition extends AttributeAccessor, BeanMetadataElement {

	/**
	 * 标准单例范围的范围标识符:“单例”
	 * @see #setScope
	 */
	String SCOPE_SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETON;

	/**
	 *标准原型作用域的作用域标识符:"prototype"
	 * @see #setScope
	 */
	String SCOPE_PROTOTYPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;


	/**
	 * 角色提示，表明{@code BeanDefinition}是应用程序的主要部分。通常对应于用户定义的bean
	 */
	int ROLE_APPLICATION = 0;

	/**
	 * 角色提示，指示{@code BeanDefinition}是某些较大配置(通常是外部配置)的支持部分
	 */
	int ROLE_SUPPORT = 1;

	/**
	 * 角色提示，表明{@code BeanDefinition}提供了一个完全的后台角色，与最终用户无关。
	 * 这个提示用于注册bean时，这些bean完全是{@link org.springframework.beans.factory.parsing.ComponentDefinition}内部工作的一部分。
	 */
	int ROLE_INFRASTRUCTURE = 2;


	// Modifiable attributes

	/**
	 * 设置此bean定义的父定义的名称(如果有的话)
	 */
	void setParentName(@Nullable String parentName);

	/**
	 * 返回此bean定义的父定义的名称(如果有的话)
	 */
	@Nullable
	String getParentName();

	/**
	 * 指定此BeanDefinition的bean类名
	 *
	 * @see #setParentName
	 * @see #setFactoryBeanName
	 * @see #setFactoryMethodName
	 */
	void setBeanClassName(@Nullable String beanClassName);

	/**
	 * 返回此bean定义的当前bean类名
	 *
	 * @see #getParentName()
	 * @see #getFactoryBeanName()
	 * @see #getFactoryMethodName()
	 */
	@Nullable
	String getBeanClassName();

	/**
	 * 重写此bean的目标范围，指定一个新的范围名称
	 *
	 * @see #SCOPE_SINGLETON
	 * @see #SCOPE_PROTOTYPE
	 */
	void setScope(@Nullable String scope);

	/**
	 * 返回此bean的当前目标范围的名称，如果还不知道，则返回{@code null}
	 */
	@Nullable
	String getScope();

	/**
	 * 设置这个bean是否应该延迟初始化
	 */
	void setLazyInit(boolean lazyInit);

	/**
	 * 返回是否应该延迟初始化此bean，即在启动时不急于实例化。仅适用于单例bean
	 */
	boolean isLazyInit();

	/**
	 * 设置此bean依赖于初始化的bean的名称
	 * bean工厂将确保首先初始化这些bean
	 */
	void setDependsOn(@Nullable String... dependsOn);

	/**
	 * 返回此bean所依赖的bean名称
	 */
	@Nullable
	String[] getDependsOn();

	/**
	 * 设置此bean是否是自动连接到其他bean的候选bean
	 */
	void setAutowireCandidate(boolean autowireCandidate);

	/**
	 * 返回此bean是否是自动连接到其他bean的候选bean
	 */
	boolean isAutowireCandidate();

	/**
	 * 设置此bean是否是主要自动连接候选对象
	 */
	void setPrimary(boolean primary);

	/**
	 * 返回此bean是否是主要自动连接候选对象
	 */
	boolean isPrimary();

	/**
	 * 指定要使用的工厂bean(如果有的话)
	 * 这是要调用指定工厂方法的bean的名称
	 * @see #setFactoryMethodName
	 */
	void setFactoryBeanName(@Nullable String factoryBeanName);

	/**
	 * 返回工厂bean名称(如果有的话)
	 */
	@Nullable
	String getFactoryBeanName();

	/**
	 * 指定工厂方法(如果有的话)。此方法将使用构造函数参数调用，如果没有指定参数，则不使用参数调用。
	 * 方法将在指定的工厂bean上调用(如果有的话)，或者作为本地bean类上的静态方法调用
	 * @see #setFactoryBeanName
	 * @see #setBeanClassName
	 */
	void setFactoryMethodName(@Nullable String factoryMethodName);

	/**
	 * 返回工厂方法(如果有的话)
	 */
	@Nullable
	String getFactoryMethodName();

	/**
	 * 返回此bean的构造函数参数值
	 */
	ConstructorArgumentValues getConstructorArgumentValues();

	/**
	 * 如果此bean定义了构造函数参数值，则返回
	 * @since 5.0.2
	 */
	default boolean hasConstructorArgumentValues() {
		return !getConstructorArgumentValues().isEmpty();
	}

	/**
	 * 返回BeanDefinition存储属性值的一个对象, 该对象存储Bean所有的属性
	 */
	MutablePropertyValues getPropertyValues();

	/**
	 * 如果存在为此bean定义的属性值，则返回
	 * @since 5.0.2
	 */
	default boolean hasPropertyValues() {
		return !getPropertyValues().isEmpty();
	}

	/**
	 * 设置初始化器方法的名称
	 * @since 5.1
	 */
	void setInitMethodName(@Nullable String initMethodName);

	/**
	 * 返回初始化器方法的名称
	 * @since 5.1
	 */
	@Nullable
	String getInitMethodName();

	/**
	 * 设置销毁方法的名称.
	 * @since 5.1
	 */
	void setDestroyMethodName(@Nullable String destroyMethodName);

	/**
	 * 返回销毁方法的名称
	 * @since 5.1
	 */
	@Nullable
	String getDestroyMethodName();

	/**
	 * 为这个{@code BeanDefinition}设置角色提示。角色提示为框架和工具提供了特定{@code BeanDefinition}的角色和重要性的指示
	 * @since 5.1
	 * @see #ROLE_APPLICATION
	 * @see #ROLE_SUPPORT
	 * @see #ROLE_INFRASTRUCTURE
	 */
	void setRole(int role);

	/**
	 * 获取这个{@code BeanDefinition}的角色提示。角色提示为框架和工具提供了特定{@code BeanDefinition}的角色和重要性的指示
	 * @see #ROLE_APPLICATION
	 * @see #ROLE_SUPPORT
	 * @see #ROLE_INFRASTRUCTURE
	 */
	int getRole();

	/**
	 * 设置这个bean定义的可读描述
	 * @since 5.1
	 */
	void setDescription(@Nullable String description);

	/**
	 * 返回此bean定义的可读描述
	 */
	@Nullable
	String getDescription();


	// Read-only attributes

	/**
	 * 返回是否这是一个 <b>单例</b>，用一个单一的，共享的实例返回所有的调用。
	 * @see #SCOPE_SINGLETON
	 */
	boolean isSingleton();

	/**
	 * 返回是否这是一个 <b>Prototype</b>，并为每个调用返回一个独立的实例.
	 * @since 3.0
	 * @see #SCOPE_PROTOTYPE
	 */
	boolean isPrototype();

	/**
	 * 返回此bean是否是“抽象的”，即不打算实例化
	 */
	boolean isAbstract();

	/**
	 * 返回此bean定义来自的资源的描述(以便在出现错误时显示上下文)
	 */
	@Nullable
	String getResourceDescription();

	/**
	 * 返回原始bean定义，如果没有，则返回{@code null}。
	 * 允许检索修饰后的bean定义(如果有的话)
	 * <p>请注意，此方法返回直接发起者。遍历发起者链以找到用户定义的原始bean定义。
	 */
	@Nullable
	BeanDefinition getOriginatingBeanDefinition();

}
