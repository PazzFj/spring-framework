
package org.springframework.transaction.interceptor;

import org.springframework.lang.Nullable;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.StringUtils;

/**
 * Spring的公共事务属性实现。默认情况下回滚运行时异常，但未检查异常
 */
@SuppressWarnings("serial")
public class DefaultTransactionAttribute extends DefaultTransactionDefinition implements TransactionAttribute {

	@Nullable
	private String qualifier;   //修饰符

	@Nullable
	private String descriptor;  //描述符


	/**
	 * 使用默认设置创建一个新的DefaultTransactionAttribute
	 */
	public DefaultTransactionAttribute() {
		super();
	}

	/**
	 * 拷贝构造函数
	 */
	public DefaultTransactionAttribute(TransactionAttribute other) {
		super(other);
	}

	/**
	 *
	 */
	public DefaultTransactionAttribute(int propagationBehavior) {
		super(propagationBehavior);
	}


	/**
	 * 将限定符值与此事务属性关联
	 */
	public void setQualifier(@Nullable String qualifier) {
		this.qualifier = qualifier;
	}

	/**
	 * Return a qualifier value associated with this transaction attribute.
	 * @since 3.0
	 */
	@Override
	@Nullable
	public String getQualifier() {
		return this.qualifier;
	}

	/**
	 * Set a descriptor for this transaction attribute,
	 * e.g. indicating where the attribute is applying.
	 * @since 4.3.4
	 */
	public void setDescriptor(@Nullable String descriptor) {
		this.descriptor = descriptor;
	}

	/**
	 * 返回此事务属性的描述符，如果没有，则返回{@code null}
	 * @since 4.3.4
	 */
	@Nullable
	public String getDescriptor() {
		return this.descriptor;
	}

	/**
	 * The default behavior is as with EJB: rollback on unchecked exception
	 * ({@link RuntimeException}), assuming an unexpected outcome outside of any
	 * business rules. Additionally, we also attempt to rollback on {@link Error} which
	 * is clearly an unexpected outcome as well. By contrast, a checked exception is
	 * considered a business exception and therefore a regular expected outcome of the
	 * transactional business method, i.e. a kind of alternative return value which
	 * still allows for regular completion of resource operations.
	 * <p>This is largely consistent with TransactionTemplate's default behavior,
	 * except that TransactionTemplate also rolls back on undeclared checked exceptions
	 * (a corner case). For declarative transactions, we expect checked exceptions to be
	 * intentionally declared as business exceptions, leading to a commit by default.
	 * @see org.springframework.transaction.support.TransactionTemplate#execute
	 */
	@Override
	public boolean rollbackOn(Throwable ex) {
		return (ex instanceof RuntimeException || ex instanceof Error);
	}


	/**
	 * Return an identifying description for this transaction attribute.
	 * <p>Available to subclasses, for inclusion in their {@code toString()} result.
	 */
	protected final StringBuilder getAttributeDescription() {
		StringBuilder result = getDefinitionDescription();
		if (StringUtils.hasText(this.qualifier)) {
			result.append("; '").append(this.qualifier).append("'");
		}
		return result;
	}

}
