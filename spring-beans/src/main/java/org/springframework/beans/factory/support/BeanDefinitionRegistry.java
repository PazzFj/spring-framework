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

package org.springframework.beans.factory.support;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.AliasRegistry;

/**
 * BeanDefinition 注册器 继承 AliasRegistry
 */
public interface BeanDefinitionRegistry extends AliasRegistry {

	/**
	 * 注册Bean定义
	 */
	void registerBeanDefinition(String beanName, BeanDefinition beanDefinition)
			throws BeanDefinitionStoreException;

	/**
	 * 清除Bean定义
	 */
	void removeBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;

	/**
	 * 获取Bean定义
	 */
	BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;

	/**
	 * 是否包含Bean定义
	 */
	boolean containsBeanDefinition(String beanName);

	/**
	 * 获取所有Bean定义名称返回一个数组
	 */
	String[] getBeanDefinitionNames();

	/**
	 * 返回注册的Bean定义数量
	 */
	int getBeanDefinitionCount();

	/**
	 * 是否bean在使用
	 */
	boolean isBeanNameInUse(String beanName);

}
