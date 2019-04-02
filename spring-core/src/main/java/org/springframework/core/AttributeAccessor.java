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

package org.springframework.core;

import org.springframework.lang.Nullable;

/**
 * 接口定义用于将元数据附加到/从任意对象访问元数据的通用契约。
 */
public interface AttributeAccessor {

	/**
	 * 将{@code name}定义的属性设置为提供的{@code value}
	 */
	void setAttribute(String name, @Nullable Object value);

	/**
	 * 获取由{@code name}标识的属性的值。如果属性不存在，返回{@code null}
	 */
	@Nullable
	Object getAttribute(String name);

	/**
	 * 删除由{@code name}标识的属性并返回其值。
	 * 如果没有找到{@code name}下的属性，则返回{@code null}
	 */
	@Nullable
	Object removeAttribute(String name);

	/**
	 * 如果由{@code name}标识的属性存在，则返回{@code true}。
	 * 否则返回{@code false}。
	 */
	boolean hasAttribute(String name);

	/**
	 * 返回所有属性的名称
	 */
	String[] attributeNames();

}
