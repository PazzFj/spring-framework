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

package org.springframework.web.context;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 引导监听器启动和关闭Spring的root {@link WebApplicationContext}。
 * 简单地委托给{@link ContextLoader}和{@link ContextCleanupListener}
 *
 * <p>从Spring 3.1开始，{@code ContextLoaderListener}支持通过{@link #ContextLoaderListener(WebApplicationContext)}构造函数注入根web应用程序上下文，
 * 允许在Servlet 3.0+环境中进行编程配置。看到{@link org.springframework.web.WebApplicationInitializer}
 *
 * @author Juergen Hoeller
 * @author Chris Beams
 * @since 17.02.2003
 * @see #setContextInitializers
 * @see org.springframework.web.WebApplicationInitializer
 */
public class ContextLoaderListener extends ContextLoader implements ServletContextListener {

	/**
	 * 创建一个新的{@code ContextLoaderListener}，
	 * 它将基于“contextClass”和“contextConfigLocation”servlet上下文-params创建一个web应用程序上下文。
	 * 参见{@link ContextLoader}超类文档了解每个类的默认值的详细信息
	 */
	public ContextLoaderListener() {
	}

	/**
	 * 使用给定的应用程序上下文创建一个新的{@code ContextLoaderListener}。
	 * 这个构造函数在Servlet 3.0+环境中非常有用，在这种环境中，
	 * 可以通过{@link javax.servlet.ServletContext#addListener} API
	 */
	public ContextLoaderListener(WebApplicationContext context) {
		super(context);
	}


	/**
	 * 初始化root web应用程序上下文
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		initWebApplicationContext(event.getServletContext());
	}


	/**
	 * 关闭root web应用程序上下文
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		closeWebApplicationContext(event.getServletContext());
		ContextCleanupListener.cleanupAttributes(event.getServletContext());
	}

}
