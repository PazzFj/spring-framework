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

import java.lang.annotation.Annotation;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;

/**
 * 由bean工厂实现的{@link BeanFactory}接口的扩展，bean工厂可以枚举它们的所有bean实例，
 * 而不是根据客户机的请求逐个尝试按名称查找bean。预加载所有bean定义(如基于xml的工厂)的BeanFactory实现可以实现此接口
 */
public interface ListableBeanFactory extends BeanFactory {

	/**
	 * 检查此bean工厂是否包含具有给定名称的bean定义
	 */
	boolean containsBeanDefinition(String beanName);

	/**
	 * 返回工厂中定义的bean的数量
	 */
	int getBeanDefinitionCount();

	/**
	 * 返回在这个工厂中定义的所有bean的名称.
	 */
	String[] getBeanDefinitionNames();

	/**
	 * 根据bean定义或FactoryBeans中的{@code getObjectType}值，返回与给定类型(包括子类)匹配的bean的名称
	 */
	String[] getBeanNamesForType(ResolvableType type);

	/**
	 * 根据bean定义或FactoryBeans中的{@code getObjectType}值，返回与给定类型(包括子类)匹配的bean的名称
	 */
	String[] getBeanNamesForType(@Nullable Class<?> type);

	/**
	 * 根据bean定义或FactoryBeans中的{@code getObjectType}值，返回与给定类型(包括子类)匹配的bean的名称
	 */
	String[] getBeanNamesForType(@Nullable Class<?> type, boolean includeNonSingletons, boolean allowEagerInit);

	/**
	 * 根据bean定义或FactoryBeans中{@code getObjectType}的值判断，返回与给定对象类型(包括子类)匹配的bean实例
	 */
	<T> Map<String, T> getBeansOfType(@Nullable Class<T> type) throws BeansException;

	/**
	 * 根据bean定义或FactoryBeans中{@code getObjectType}的值判断，返回与给定对象类型(包括子类)匹配的bean实例
	 */
	<T> Map<String, T> getBeansOfType(@Nullable Class<T> type, boolean includeNonSingletons, boolean allowEagerInit)
			throws BeansException;

	/**
	 * 查找其{@code Class}具有提供的{@link Annotation}类型的bean的所有名称，但还没有创建相应的bean实例
	 */
	String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType);

	/**
	 * 查找所有具有提供的{@link Annotation}类型的{@code Class}的bean，返回具有相应bean实例的bean名称映射
	 */
	Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException;

	/**
	 * 在指定的bean上找到{@code annotationType}的{@link Annotation}，如果在给定的类本身上找不到注释，则遍历其接口和超类
	 */
	@Nullable
	<A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType)
			throws NoSuchBeanDefinitionException;

}
