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

package org.springframework.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.lang.Nullable;

/**
 * 接口，用于基于web的主题解析策略，该策略允许通过请求解析主题和通过请求和响应修改主题接口，
 * 用于基于web的主题解析策略，该策略允许通过请求解析主题和通过请求和响应修改主题
 */
public interface ThemeResolver {

	/**
	 * 通过给定的请求解析当前主题名称。在任何情况下都应该返回一个默认主题作为回退
	 */
	String resolveThemeName(HttpServletRequest request);

	/**
	 * 将当前主题名称设置为给定的主题名称
	 */
	void setThemeName(HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable String themeName);

}
