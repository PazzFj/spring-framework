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

package org.springframework.core.type;

/**
 * 接口，该接口定义对特定类的注释的抽象访问，其形式不需要加载该类
 */
public interface MethodMetadata extends AnnotatedTypeMetadata {

	/**
	 * 返回方法的名称
	 */
	String getMethodName();

	/**
	 * 返回声明此方法的类的全限定名
	 */
	String getDeclaringClassName();

	/**
	 * 返回此方法声明的返回类型的全限定名称
	 * @since 4.2
	 */
	String getReturnTypeName();

	/**
	 *
	 * @since 4.2
	 */
	boolean isAbstract();

	/**
	 *
	 */
	boolean isStatic();

	/**
	 *
	 */
	boolean isFinal();

	/**
	 *
	 */
	boolean isOverridable();

}
