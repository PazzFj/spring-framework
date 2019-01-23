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

package org.springframework.core;

/**
 * 管理别名的通用接口。作为超级接口
 */
public interface AliasRegistry {

	/**
	 * 给定名称，为其注册别名
	 */
	void registerAlias(String name, String alias);

	/**
	 * 从这个注册表中删除指定的别名
	 */
	void removeAlias(String alias);

	/**
	 * 确定此给定名称是否定义为别名
	 */
	boolean isAlias(String name);

	/**
	 * 如果已定义，则返回给定名称的别名
	 */
	String[] getAliases(String name);

}
