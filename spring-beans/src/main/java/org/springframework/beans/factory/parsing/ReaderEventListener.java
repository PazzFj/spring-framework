/*
 * Copyright 2002-2007 the original author or authors.
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

package org.springframework.beans.factory.parsing;

import java.util.EventListener;

/**
 * 接口，在bean定义读取过程中接收组件、别名和导入注册的回调。
 */
public interface ReaderEventListener extends EventListener {

	/**
	 * 给定已注册默认值的通知
	 */
	void defaultsRegistered(DefaultsDefinition defaultsDefinition);

	/**
	 * 给定已注册组件的通知
	 */
	void componentRegistered(ComponentDefinition componentDefinition);

	/**
	 * 已注册给定别名的通知
	 */
	void aliasRegistered(AliasDefinition aliasDefinition);

	/**
	 * 已处理给定导入的通知
	 */
	void importProcessed(ImportDefinition importDefinition);

}
