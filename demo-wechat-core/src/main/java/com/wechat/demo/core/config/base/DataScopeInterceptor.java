package com.standard.demo.core.config.base;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.handlers.AbstractSqlParserHandler;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;

/**
 *
 * 数据权限拦截器
 * @Author zhangjw
 * @Date 2020/3/15 16:33
 */
@Slf4j
@Intercepts({
		@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class DataScopeInterceptor extends AbstractSqlParserHandler implements Interceptor {

	@Override
	@SneakyThrows
	public Object intercept(Invocation invocation) {
		StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
		MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
		this.sqlParser(metaObject);

		// 先判断是不是SELECT操作
		MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
		if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
			return invocation.proceed();
		}

		BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
		String originalSql = boundSql.getSql();
		Object parameterObject = boundSql.getParameterObject();

		//查找参数中包含DataScope类型的参数
		DataScope dataScope = findDataScopeObject(parameterObject);

		if (dataScope == null || Boolean.TRUE.equals(dataScope.getIsAllData())) {
			return invocation.proceed();
		} else {
			String scopeName = dataScope.getScopeName();
			List ids = dataScope.getScopeList();
			if (StringUtils.isNotBlank(scopeName) && !ids.isEmpty()) {
				originalSql = addDataScopeAddition(originalSql, scopeName, ids);
				metaObject.setValue("delegate.boundSql.sql", originalSql);
			} else {
				originalSql = addEmptyResultDataScopeAddition(originalSql);
				metaObject.setValue("delegate.boundSql.sql", originalSql);
			}
		}
		return invocation.proceed();
	}

	/**
	 * 生成拦截对象的代理
	 *
	 * @param target 目标对象
	 * @return 代理对象
	 */
	@Override
	public Object plugin(Object target) {
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		}
		return target;
	}

	/**
	 * mybatis配置的属性
	 *
	 * @param properties mybatis配置的属性
	 */
	@Override
	public void setProperties(Properties properties) {

	}

	/**
	 * 查找参数是否包括DataScope对象
	 *
	 * @param parameterObj 参数列表
	 * @return DataScope
	 */
	private DataScope findDataScopeObject(Object parameterObj) {
		if (parameterObj instanceof DataScope) {
			return (DataScope) parameterObj;
		} else if (parameterObj instanceof Map) {
			for (Object val : ((Map<?, ?>) parameterObj).values()) {
				if (val instanceof DataScope) {
					return (DataScope) val;
				}
			}
		}
		return null;
	}

	private String addDataScopeAddition(String originalSql, String scopeName, List ids) {
		try {
			Select select = (Select) CCJSqlParserUtil.parse(originalSql);
			PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
			ExpressionList expressionList = new ExpressionList();
			List<Expression> expressions = new ArrayList<>();

			for (Object id : ids) {
				if (id instanceof Long) {
					expressions.add(new LongValue((Long) id));
				}
				if (id instanceof String) {
					expressions.add(new StringValue((String) id));
				}
			}

			expressionList.setExpressions(expressions);
			InExpression in = new InExpression(new Column(scopeName), expressionList);
			if (plainSelect.getWhere() == null) {
				plainSelect.setWhere(in);
			} else {
				Expression where = plainSelect.getWhere();
				AndExpression and = new AndExpression(where, in);
				plainSelect.setWhere(and);
			}
			return select.toString();
		} catch (JSQLParserException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String addEmptyResultDataScopeAddition(String originalSql) {
		try {
			Select select = (Select) CCJSqlParserUtil.parse(originalSql);
			PlainSelect plainSelect = (PlainSelect) select.getSelectBody();

			EqualsTo equalsTo = new EqualsTo();
			equalsTo.setLeftExpression(new LongValue(0L));
			equalsTo.setRightExpression(new LongValue(1L));
			if (plainSelect.getWhere() == null) {
				plainSelect.setWhere(equalsTo);
			} else {
				Expression where = plainSelect.getWhere();
				AndExpression and = new AndExpression(where, equalsTo);
				plainSelect.setWhere(and);
			}
			return select.toString();
		} catch (JSQLParserException e) {
			e.printStackTrace();
		}
		return null;
	}
}
