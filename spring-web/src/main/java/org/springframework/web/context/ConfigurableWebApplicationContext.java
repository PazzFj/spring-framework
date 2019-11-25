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

package org.springframework.web.context;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.Nullable;

/**
 * 由可配置的web应用程序上下文实现的接口
 */
public interface ConfigurableWebApplicationContext extends WebApplicationContext, ConfigurableApplicationContext {

	/**
	 * 引用上下文路径和/或servlet名称的ApplicationContext id的前缀
	 */
	String APPLICATION_CONTEXT_ID_PREFIX = WebApplicationContext.class.getName() + ":";

	/**
	 * 工厂中ServletConfig环境bean的名称
	 * @see javax.servlet.ServletConfig
	 */
	String SERVLET_CONFIG_BEAN_NAME = "servletConfig";

	/**
	 * 为这个web应用程序上下文设置 ServletContext
	 */
	void setServletContext(@Nullable ServletContext servletContext);

	/**
	 * 设置此web应用程序上下文的 ServletConfig
	 */
	void setServletConfig(@Nullable ServletConfig servletConfig);

	/**
	 * 返回此web应用程序上下文的ServletConfig(如果有的话)
	 */
	@Nullable
	ServletConfig getServletConfig();

	/**
	 * 设置此web应用程序上下文的名称空间，用于构建默认上下文配置位置。根web应用程序上下文没有名称空间
	 */
	void setNamespace(@Nullable String namespace);

	/**
	 * 返回此web应用程序上下文的名称空间(如果有的话)
	 */
	@Nullable
	String getNamespace();

	/**
	 * 以init-param样式设置此web应用程序上下文的配置位置，即使用逗号、分号或空格分隔不同的位置
	 */
	void setConfigLocation(String configLocation);

	/**
	 * 设置此web应用程序上下文的配置位置
	 */
	void setConfigLocations(String... configLocations);

	/**
	 * 返回此web应用程序上下文的配置位置，如果没有指定配置位置，则返回{@code null}
	 */
	@Nullable
	String[] getConfigLocations();

}
