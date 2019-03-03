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

package org.springframework.aop;

import org.springframework.lang.Nullable;

/**
 * 目标对象接口
 */
public interface TargetSource extends TargetClassAware {

	/**
	 * 获取到目标对象的class
	 */
	@Override
	@Nullable
	Class<?> getTargetClass();

	/**
	 * 是否静态对象
	 */
	boolean isStatic();

	/**
	 *获取到目标对象
	 */
	@Nullable
	Object getTarget() throws Exception;

	/**
	 * 发布给定刀得目标对象
	 */
	void releaseTarget(Object target) throws Exception;

}
