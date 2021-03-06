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

package org.springframework.aop.config;

import org.w3c.dom.Element;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.lang.Nullable;

/**
 * 用于处理Aop名称空间标记内部使用的自动代理创建者注册的实用程序类
 * @see AopConfigUtils
 */
public abstract class AopNamespaceUtils {

	public static final String PROXY_TARGET_CLASS_ATTRIBUTE = "proxy-target-class";

	private static final String EXPOSE_PROXY_ATTRIBUTE = "expose-proxy";

	//通过XML解析<tx:annotation-driven>是注册BeanDefinition, 注册自动代理
	public static void registerAutoProxyCreatorIfNecessary(ParserContext parserContext, Element sourceElement) {
		// 公共顾问自动代理
		// 创建 InfrastructureAdvisorAutoProxyCreator 对象, 在创建对象调用构造器时处理需要处理的bean (创建代理)
		BeanDefinition beanDefinition = AopConfigUtils.registerAutoProxyCreatorIfNecessary(parserContext.getRegistry(), parserContext.extractSource(sourceElement));//<tx:annotation-driven>
		// 配置BeanDefinition 对应的属性的值, 默认为false及不配置
		useClassProxyingIfNecessary(parserContext.getRegistry(), sourceElement);
		// 往事件监听器注册组件 BeanDefinition, 如果不为空
		registerComponentIfNecessary(beanDefinition, parserContext);
	}

	//通过XML解析<aop:config/>时注册BeanDefinition, 注册切面自动代理
	public static void registerAspectJAutoProxyCreatorIfNecessary(ParserContext parserContext, Element sourceElement) {
		// 切面顾问
		// 创建 AspectJAwareAdvisorAutoProxyCreator 对象, 在创建对象调用构造器时处理需要处理的bean (创建代理)
		BeanDefinition beanDefinition = AopConfigUtils.registerAspectJAutoProxyCreatorIfNecessary(parserContext.getRegistry(), parserContext.extractSource(sourceElement));//<aop:config/>
		// 配置BeanDefinition 对应的属性的值, 默认为false及不配置
		useClassProxyingIfNecessary(parserContext.getRegistry(), sourceElement);
		// 往事件监听器注册组件 BeanDefinition, 如果不为空
		registerComponentIfNecessary(beanDefinition, parserContext);
	}

	//通过XML解析<aop:aspectj-autoproxy>元素时注册BeanDefinition, 注册切面注解自动代理
	public static void registerAspectJAnnotationAutoProxyCreatorIfNecessary(ParserContext parserContext, Element sourceElement) {
		// 注册或更新 AutoProxyCreator 定义 beanName 为 org.Springframework.aop.config.internalAutoProxyCreator的BeanDefinition
		// 如果内在的 internalAutoProxyCreator 的 BeanDefinition 已经存在，而根据优先级更新BeanDefinition
		// 注册 AnnotationAwareAspectJAutoProxyCreator 对象的BeanDefinition
		BeanDefinition beanDefinition = AopConfigUtils.registerAspectJAnnotationAutoProxyCreatorIfNecessary(parserContext.getRegistry(), parserContext.extractSource(sourceElement));//<aop:aspectj-autoproxy>
		// 处理 proxy-target-class 以及 expose-proxy 属性
		useClassProxyingIfNecessary(parserContext.getRegistry(), sourceElement);
		// 注册组件并通知，便于监听器作进一步处理
		// 其中 beanDefinition 的 className 为 AnnotationAwareAspectJAutoProxyCreator
		registerComponentIfNecessary(beanDefinition, parserContext);
	}

	//使用代理
	private static void useClassProxyingIfNecessary(BeanDefinitionRegistry registry, @Nullable Element sourceElement) {
		if (sourceElement != null) {
			// 设置代理属性值proxy-target-class
			boolean proxyTargetClass = Boolean.parseBoolean(sourceElement.getAttribute(PROXY_TARGET_CLASS_ATTRIBUTE));
			if (proxyTargetClass) { //默认false
				AopConfigUtils.forceAutoProxyCreatorToUseClassProxying(registry);
			}
			// 设置代理属性值expose-proxy
			boolean exposeProxy = Boolean.parseBoolean(sourceElement.getAttribute(EXPOSE_PROXY_ATTRIBUTE));
			if (exposeProxy) { //默认false
				AopConfigUtils.forceAutoProxyCreatorToExposeProxy(registry);
			}
		}
	}

	//注册组件
	private static void registerComponentIfNecessary(@Nullable BeanDefinition beanDefinition, ParserContext parserContext) {
		if (beanDefinition != null) {
			parserContext.registerComponent(new BeanComponentDefinition(beanDefinition, AopConfigUtils.AUTO_PROXY_CREATOR_BEAN_NAME));
		}
	}

}
