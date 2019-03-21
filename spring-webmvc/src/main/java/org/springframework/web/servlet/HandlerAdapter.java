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

package org.springframework.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.lang.Nullable;

/**
 * MVC框架SPI，允许核心MVC工作流的参数化
 */
public interface HandlerAdapter {

	/**
	 * 根据HandlerAdapter是否支持HandlerMethod
	 */
	boolean supports(Object handler);

	/**
	 * 使用给定的处理程序来处理此请求。所需的工作流可能有很大的不同
	 */
	@Nullable
	ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;

	/**
	 * 与HttpServlet的{@code getLastModified}方法相同。如果处理程序类中不支持，可以简单地返回-1
	 */
	long getLastModified(HttpServletRequest request, Object handler);

}
