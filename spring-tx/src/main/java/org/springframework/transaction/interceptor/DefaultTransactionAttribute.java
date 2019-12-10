
package org.springframework.transaction.interceptor;

import org.springframework.lang.Nullable;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.StringUtils;

/**
 * Spring的公共事务属性实现。默认情况下回滚运行时异常，但未检查异常
 *
 * 默认 (事务属性 --> 事务定义)
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
	 * 返回与此事务属性关联的限定符值
	 */
	@Override
	@Nullable
	public String getQualifier() {
		return this.qualifier;
	}

	/**
	 * 设置此事务属性的描述符，例如指示属性应用于何处。
	 */
	public void setDescriptor(@Nullable String descriptor) {
		this.descriptor = descriptor;
	}

	/**
	 * 返回此事务属性的描述符，如果没有，则返回{@code null}
	 */
	@Nullable
	public String getDescriptor() {
		return this.descriptor;
	}

	/**
	 * 默认行为与EJB一样:回滚未检查的异常({@link RuntimeException})，假设在任何业务规则之外出现意外结果。
	 * 此外，我们还尝试回滚{@link Error}，这显然也是一个意外结果。
	 * 相反，检查异常被认为是业务异常，因此是事务性业务方法的常规预期结果，
	 * 即一种可选的返回值，它仍然允许常规完成资源操作
	 */
	@Override
	public boolean rollbackOn(Throwable ex) {
		return (ex instanceof RuntimeException || ex instanceof Error);
	}


	/**
	 * 返回此事务属性的标识说明
	 */
	protected final StringBuilder getAttributeDescription() {
		StringBuilder result = getDefinitionDescription();
		if (StringUtils.hasText(this.qualifier)) {
			result.append("; '").append(this.qualifier).append("'");
		}
		return result;
	}

}
