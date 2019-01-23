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

package org.springframework.beans.factory.support;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.Nullable;

/**
 * BeanDefinition 阅读器的简单接口
 *
 * @author Juergen Hoeller
 * @since 1.1
 * @see org.springframework.core.io.Resource
 */
public interface BeanDefinitionReader {

	/**
	 * 返回bean工厂以注册bean定义
	 */
	BeanDefinitionRegistry getRegistry();

	/**
	 * 返回用于资源位置的资源加载器
	 */
	@Nullable
	ResourceLoader getResourceLoader();

	/**
	 * 返回用于bean类的类装入器
	 */
	@Nullable
	ClassLoader getBeanClassLoader();

	/**
	 * 返回Bean名称生成器 BeanNameGenerator
	 */
	BeanNameGenerator getBeanNameGenerator();

	/**
	 * 从指定的资源加载 BeanDefinition
	 */
	int loadBeanDefinitions(Resource resource) throws BeanDefinitionStoreException;

	/**
	 * 从指定的资源加载 BeanDefinition
	 */
	int loadBeanDefinitions(Resource... resources) throws BeanDefinitionStoreException;

	/**
	 * 从指定的资源位置加载 BeanDefinition
	 */
	int loadBeanDefinitions(String location) throws BeanDefinitionStoreException;

	/**
	 * 从指定的资源位置加载bean定义
	 */
	int loadBeanDefinitions(String... locations) throws BeanDefinitionStoreException;

}
