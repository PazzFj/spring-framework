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

package org.springframework.beans.factory.config;

import java.util.Iterator;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.lang.Nullable;

/**
 * 配置接口将由大多数可列bean工厂实现。除了{@link ConfigurableBeanFactory}之外，它还提供了分析和修改bean定义以及预实例化单例的工具。
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

	/**
	 * 忽视用于自动连接的依赖项Class类型
	 */
	void ignoreDependencyType(Class<?> type);

	/**
	 * 忽略用于自动连接的给定依赖项接口
	 */
	void ignoreDependencyInterface(Class<?> ifc);

	/**
	 * 注册具有相应自动获取值的特殊依赖项类型
	 */
	void registerResolvableDependency(Class<?> dependencyType, @Nullable Object autowiredValue);

	/**
	 * 确定指定的bean是否具有自动装配候选资格，以便被注入到声明匹配类型依赖关系的其他bean中。
	 */
	boolean isAutowireCandidate(String beanName, DependencyDescriptor descriptor)
			throws NoSuchBeanDefinitionException;

	/**
	 * 根据 beanName 获取 BeanDefinition
	 */
	BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;

	/**
	 * 获取所有bean名称, 迭代模式
	 */
	Iterator<String> getBeanNamesIterator();

	/**
	 * 清除合并的bean定义缓存，删除那些被认为没有资格进行完全元数据缓存的bean的条目
	 */
	void clearMetadataCache();

	/**
	 * 冻结所有bean定义，表明注册的bean定义将不再被修改或后处理。
	 */
	void freezeConfiguration();

	/**
	 * 是否配置 BeanDefinition 冻结
	 */
	boolean isConfigurationFrozen();

	/**
	 * 确保所有非延迟初始化的单例都被实例化
	 */
	void preInstantiateSingletons() throws BeansException;

}
