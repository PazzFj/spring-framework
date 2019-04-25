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
 * Configuration interface to be implemented by most listable bean factories.
 * In addition to {@link ConfigurableBeanFactory}, it provides facilities to
 * analyze and modify bean definitions, and to pre-instantiate singletons.
 *
 * <p>This subinterface of {@link org.springframework.beans.factory.BeanFactory}
 * is not meant to be used in normal application code: Stick to
 * {@link org.springframework.beans.factory.BeanFactory} or
 * {@link org.springframework.beans.factory.ListableBeanFactory} for typical
 * use cases. This interface is just meant to allow for framework-internal
 * plug'n'play even when needing access to bean factory configuration methods.
 *
 * @author Juergen Hoeller
 * @since 03.11.2003
 * @see org.springframework.context.support.AbstractApplicationContext#getBeanFactory()
 */
public interface ConfigurableListableBeanFactory
		extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

	/**
	 * Ignore the given dependency type for autowiring:
	 * for example, String. Default is none.
	 * @param type the dependency type to ignore
	 */
	void ignoreDependencyType(Class<?> type);

	/**
	 * 忽略用于自动连接的给定依赖项接口。
	 * @see org.springframework.beans.factory.BeanFactoryAware
	 */
	void ignoreDependencyInterface(Class<?> ifc);

	/**
	 * 注册具有相应自动获取值的特殊依赖项类型。
	 */
	void registerResolvableDependency(Class<?> dependencyType, @Nullable Object autowiredValue);

	/**
	 * 确定指定的bean是否具有自动装配候选资格，以便被注入到声明匹配类型依赖关系的其他bean中。
	 */
	boolean isAutowireCandidate(String beanName, DependencyDescriptor descriptor)
			throws NoSuchBeanDefinitionException;

	/**
	 * 根据名称获取BeanDefinition
	 * @see org.springframework.beans.factory.support.BeanDefinitionRegistry#getBeanDefinition(String)
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
	 * Return whether this factory's bean definitions are frozen,
	 * i.e. are not supposed to be modified or post-processed any further.
	 * @return {@code true} if the factory's configuration is considered frozen
	 */
	boolean isConfigurationFrozen();

	/**
	 * 确保所有非延迟初始化的单例都被实例化
	 * @throws BeansException 如果无法创建一个单例bean。
	 * Note: 这可能使工厂中已经初始化了一些bean !
	 * Call {@link #destroySingletons()} 对于本例中的完全清理。
	 * @see #destroySingletons()
	 */
	void preInstantiateSingletons() throws BeansException;

}
