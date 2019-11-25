/*
 * Copyright 2002-2016 the original author or authors.
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

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.lang.Nullable;

/**
 * web应用上下文
 */
public interface WebApplicationContext extends ApplicationContext {

	/**
	 * org.springframework.web.context.WebApplicationContext.ROOT
	 */
	String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebApplicationContext.class.getName() + ".ROOT";

	/**
	 * scope:request
	 */
	String SCOPE_REQUEST = "request";

	/**
	 * scope:session
	 */
	String SCOPE_SESSION = "session";

	/**
	 * scope:application
	 */
	String SCOPE_APPLICATION = "application";

	/**
	 * 服务上下文
	 */
	String SERVLET_CONTEXT_BEAN_NAME = "servletContext";

	/**
	 * 上下文参数
	 */
	String CONTEXT_PARAMETERS_BEAN_NAME = "contextParameters";

	/**
	 * 上下文属性
	 */
	String CONTEXT_ATTRIBUTES_BEAN_NAME = "contextAttributes";


	/**
	 * 获取服务上下文
	 */
	@Nullable
	ServletContext getServletContext();

}
