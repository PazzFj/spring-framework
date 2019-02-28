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
	 * 注册别名, key为别名, value为beanId
	 */
	void registerAlias(String name, String alias);

	/**
	 * 清除该别名
	 */
	void removeAlias(String alias);

	/**
	 * 确定是否别名
	 */
	boolean isAlias(String name);

	/**
	 * 返回bean对应的所有别名数组
	 */
	String[] getAliases(String name);

}
