/*
 * Copyright 2002-2011 the original author or authors.
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

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;

/**
 * 提供处理servlet请求中的文件上传方法，允许访问上载的文件
 */
public interface MultipartHttpServletRequest extends HttpServletRequest, MultipartRequest {

	/**
	 * 将此请求的方法作为一个方便的HttpMethod实例返回
	 */
	@Nullable
	HttpMethod getRequestMethod();

	/**
	 * 将此请求的头作为一个方便的HttpHeaders实例返回
	 */
	HttpHeaders getRequestHeaders();

	/**
	 * 返回与多部分请求的指定部分相关联的头
	 */
	@Nullable
	HttpHeaders getMultipartHeaders(String paramOrFileName);

}
