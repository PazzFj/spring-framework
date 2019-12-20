/*
 * Copyright 2002-2012 the original author or authors.
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

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.lang.Nullable;

/**
 * 允许初始化servlet相关的{@link org.springframework.core.env。PropertySource}
 * 在{@link ServletContext}和(可选){@link ServletConfig}可用的最早时刻对象
 */
public interface ConfigurableWebEnvironment extends ConfigurableEnvironment {

	/**
	 * 使用给定的参数用实际的servlet上下文/配置属性源替换任何充当占位符的实例
	 */
	void initPropertySources(@Nullable ServletContext servletContext, @Nullable ServletConfig servletConfig);

}
