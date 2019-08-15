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

package org.springframework.beans.factory;

/**
 * 接口将由bean实现,该接口需要响应一旦所有属性被一个{ @link BeanFactory }设置:
 * 例如,执行自定义初始化,或者仅仅检查所有强制属性已经设置
 */
public interface InitializingBean {

	/**
	 * 当包含的{@code BeanFactory}设置了所有bean属性并满足{@link BeanFactoryAware}、{@code ApplicationContextAware}等之后，在调用
	 */
	void afterPropertiesSet() throws Exception;

}
