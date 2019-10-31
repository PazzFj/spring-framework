/*
 * Copyright 2002-2016 the original author or authors.
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

package org.aopalliance.intercept;

import java.lang.reflect.AccessibleObject;

/**
 * 这个接口表示一个通用的运行时连接点(在AOP术语中)
 */
public interface Joinpoint {

	/**
	 * 继续进入链中的下一个拦截器
	 */
	Object proceed() throws Throwable;

	/**
	 * 返回包含当前连接点静态部分的对象
	 */
	Object getThis();

	/**
	 * 返回此连接点的静态部分
	 */
	AccessibleObject getStaticPart();

}
