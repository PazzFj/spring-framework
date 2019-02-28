/*
 * Copyright 2002-2014 the original author or authors.
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

package org.springframework.context;

import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.core.env.EnvironmentCapable;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.lang.Nullable;

/**
 * 应用上下文
 */
public interface ApplicationContext extends EnvironmentCapable, ListableBeanFactory, HierarchicalBeanFactory,
		MessageSource, ApplicationEventPublisher, ResourcePatternResolver {

	/**
	 * 获取应用id
	 */
	@Nullable
	String getId();

	/**
	 * 获取应用名称
	 */
	String getApplicationName();

	/**
	 * 获取展示名称(跟id一样)
	 */
	String getDisplayName();

	/**
	 * 返回启动时间
	 */
	long getStartupDate();

	/**
	 * 返回父的上下文
	 */
	@Nullable
	ApplicationContext getParent();

	/**
	 * 返回BeanFactory
	 */
	AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException;

}
