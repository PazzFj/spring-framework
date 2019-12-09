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

package org.springframework.messaging;

/**
 * 具有标题和正文的通用消息表示
 */
public interface Message<T> {

	/**
	 * 返回消息有效负载
	 */
	T getPayload();

	/**
	 * 返回消息头(决不是{@code null}，但可能是空的)
	 */
	MessageHeaders getHeaders();

}
