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

package org.springframework.aop;

/**
 * 所有由切入点驱动的建议器的超接口。这几乎涵盖了除介绍顾问之外的所有顾问，方法级匹配不适用于介绍顾问
 *
 * @author Rod Johnson
 */
public interface PointcutAdvisor extends Advisor {

	/**
	 * 获取驱动这个advisor工具的切入点
	 * Get the Pointcut that drives this advisor.
	 */
	Pointcut getPointcut();

}
