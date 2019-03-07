/*
 * Copyright 2002-2015 the original author or authors.
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

package org.springframework.web.multipart;

import javax.servlet.http.HttpServletRequest;

/**
 * 一个多部分文件上传解析策略接口，符合< A href="http://www.ietf.org/rfc/rfc1867.txt">RFC 1867</ A >。实现通常在应用程序上下文中和独立环境中都是可用的
 */
public interface MultipartResolver {

	/**
	 * 确定给定的请求是否包含多部分内容
	 */
	boolean isMultipart(HttpServletRequest request);

	/**
	 * 将给定的HTTP请求解析为多部分文件和参数，并将请求包装在{@link org.springframework.web.multipart.MultipartHttpServletRequest}。对象，
	 * 该对象提供对文件描述符的访问，并使包含的参数可以通过标准的ServletRequest方法进行访问。
	 */
	MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) throws MultipartException;

	/**
	 * 清除用于多部分处理的所有资源，如上传文件的存储
	 */
	void cleanupMultipart(MultipartHttpServletRequest request);

}
