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

import javax.servlet.ServletContext;

import org.springframework.beans.factory.Aware;

/**
 * 接口将由任何对象实现，该对象希望得到它运行的{@link ServletContext}(通常由{@link WebApplicationContext}决定)的通知
 */
public interface ServletContextAware extends Aware {

	/**
	 * 设置此对象运行的{@link ServletContext}
	 */
	void setServletContext(ServletContext servletContext);

}
