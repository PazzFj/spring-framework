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

package org.springframework.transaction.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.transaction.TransactionDefinition;

/**
 * 描述单个方法或类上的事务属性
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Transactional {

	/**
	 * {@link #transactionManager}的别名
	 */
	@AliasFor("transactionManager")
	String value() default "";

	/**
	 * 指定事务的<em>限定符</em>值
	 */
	@AliasFor("value")
	String transactionManager() default "";

	/**
	 * 事务传播类型
	 */
	Propagation propagation() default Propagation.REQUIRED;

	/**
	 * 事务隔离级别
	 */
	Isolation isolation() default Isolation.DEFAULT;

	/**
	 * 设置事务的超时时间(以秒为单位)
	 */
	int timeout() default TransactionDefinition.TIMEOUT_DEFAULT;

	/**
	 * 设置当前事务是否只读
	 */
	boolean readOnly() default false;

	/**
	 * 内容为异常名，表示当抛出这些异常时事务回滚，可以用逗号分隔配置多个
	 */
	Class<? extends Throwable>[] rollbackFor() default {};

	/**
	 */
	String[] rollbackForClassName() default {};

	/**
	 * 内容为异常名，表示当抛出这些异常时继续提交事务，可以用逗号分隔配置多个
	 */
	Class<? extends Throwable>[] noRollbackFor() default {};

	/**
	 * 定义零个(0)或多个异常名称(对于必须是{@link Throwable}子类的异常)，指示哪些异常类型必须<b>而不是</b>导致事务回滚
	 */
	String[] noRollbackForClassName() default {};

}
