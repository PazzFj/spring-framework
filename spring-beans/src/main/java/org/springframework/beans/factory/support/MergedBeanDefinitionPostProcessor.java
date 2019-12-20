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

import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 合并 BeanDefinitionPostProcessor
 */
public interface MergedBeanDefinitionPostProcessor extends BeanPostProcessor {

	/**
	 * 对指定bean的给定合并bean定义进行后处理
	 */
	void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName);

	/**
	 * 通知指定名称的bean定义已被重置，此后处理器应清除受影响bean的任何元数据
	 */
	default void resetBeanDefinition(String beanName) {
	}

}
