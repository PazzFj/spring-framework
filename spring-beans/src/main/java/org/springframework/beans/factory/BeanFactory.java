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

package org.springframework.beans.factory;

import org.springframework.beans.BeansException;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;

/**
 * BeanFactory 工厂
 */
public interface BeanFactory {

	/**
	 * 用于解除对{@link FactoryBean}实例的引用，并将其与FactoryBean创建的</i>的bean <i>区别开来。
	 * 例如，如果名为{@code myJndiObject}的bean是FactoryBean，那么获取{@code &myJndiObject}将返回工厂，而不是工厂返回的实例
	 */
	String FACTORY_BEAN_PREFIX = "&";


	/**
	 *
	 */
	Object getBean(String name) throws BeansException;

	/**
	 *
	 */
	<T> T getBean(String name, Class<T> requiredType) throws BeansException;

	/**
	 *
	 */
	Object getBean(String name, Object... args) throws BeansException;

	/**
	 *
	 */
	<T> T getBean(Class<T> requiredType) throws BeansException;

	/**
	 *
	 */
	<T> T getBean(Class<T> requiredType, Object... args) throws BeansException;

	/**
	 *
	 */
	<T> ObjectProvider<T> getBeanProvider(Class<T> requiredType);

	/**
	 *
	 */
	<T> ObjectProvider<T> getBeanProvider(ResolvableType requiredType);

	/**
	 *
	 */
	boolean containsBean(String name);

	/**
	 *
	 */
	boolean isSingleton(String name) throws NoSuchBeanDefinitionException;

	/**
	 *
	 */
	boolean isPrototype(String name) throws NoSuchBeanDefinitionException;

	/**
	 * 检查具有给定名称的bean是否与指定类型匹配。
	 */
	boolean isTypeMatch(String name, ResolvableType typeToMatch) throws NoSuchBeanDefinitionException;

	/**
	 * 检查具有给定名称的bean是否与指定类型匹配。
	 */
	boolean isTypeMatch(String name, Class<?> typeToMatch) throws NoSuchBeanDefinitionException;

	/**
	 *
	 */
	@Nullable
	Class<?> getType(String name) throws NoSuchBeanDefinitionException;

	/**
	 *
	 */
	String[] getAliases(String name);

}
