/*
 * Copyright 2002-2017 the original author or authors.
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

import org.aopalliance.aop.Advice;

/**
 * 顾问：在通知的基础之上，在细化我们的aop切面！
 *
 * 通知和顾问都是切面的实现方式！
 * 通知是顾问的一个属性！
 *
 * 顾问会通过我们的设置，将不同的通知，在不通过的时间点，把切面
 * 织入到不同的切入点！
 *
 * PointCutAdvisor接口！
 * 比较常用的两个实现类：
 * NameMatchMethodPointcutAdvisor :根据切入点（主业务方法）名称织入切面！
 * RegexpMethodPointcutAdvisor :根据自定义的正则表达式织入切面！
 *
 */
public interface Advisor {

	/**
	 * 如果没有配置正确的通知(还没有)，则从{@link #getAdvice()}返回空的{@code Advice}的通用占位符
	 */
	Advice EMPTY_ADVICE = new Advice() {};


	/**
	 * 返回这方面的建议部分。一个通知可以是一个拦截器，一个before通知，一个throw通知，等等
	 */
	Advice getAdvice();

	/**
	 * 返回此通知是与特定实例关联，还是与从相同Spring bean工厂获得的建议类的所有实例共享
	 */
	boolean isPerInstance();

}
