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

package org.springframework.aop.framework;

import org.springframework.lang.Nullable;

/**
 * 已配置AOP代理的委托接口，允许创建实际的代理对象
 *
 * <p>JDK动态代理和CGLIB代理都可以使用开箱即用的实现，如{@link DefaultAopProxyFactory}所应用的那样
 *
 */
public interface AopProxy {

	/**
	 * 创建代理
	 */
	Object getProxy();

	/**
	 * 根据类加载器创建代理 Proxy.newProxyInstance();
	 */
	Object getProxy(@Nullable ClassLoader classLoader);

}
