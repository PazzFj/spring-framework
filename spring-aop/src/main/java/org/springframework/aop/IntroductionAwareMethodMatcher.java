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

import java.lang.reflect.Method;

/**
 * 一种特殊类型的{@link MethodMatcher}，在匹配方法时考虑到引入。
 * 例如，如果没有目标类的介绍，方法匹配器可能能够更有效地优化匹配
 *
 * Introduction 介绍,引进,采用
 */
public interface IntroductionAwareMethodMatcher extends MethodMatcher {

	/**
	 * 执行静态检查, 看看给定的方法是否匹配。
	 * 可以调用它, 而不是调用2-arg {@link #matches(java.lang.reflect.Method, Class)},
	 * 如果调用方支持扩展的 IntroductionAwareMethodMatcher 接口
	 */
	boolean matches(Method method, Class<?> targetClass, boolean hasIntroductions);

}
