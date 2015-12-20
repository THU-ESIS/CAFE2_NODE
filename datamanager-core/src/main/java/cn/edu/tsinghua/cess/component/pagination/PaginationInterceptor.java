package cn.edu.tsinghua.cess.component.pagination;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

@Intercepts( @Signature( type = StatementHandler.class, method = "prepare", args = { Connection.class } ))
public class PaginationInterceptor implements Interceptor {
	
	@SuppressWarnings("unused")
	private Boolean enableCounting = Boolean.TRUE;
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		PaginationContext context = PaginationContext.current();
		
		if (!context.isPaged()) {
			return invocation.proceed();
		}
		
		StatementHandler handler = (StatementHandler) invocation.getTarget();
		Connection connection = (Connection) invocation.getArgs()[0];
		BoundSql boundSql = handler.getBoundSql();
		String originSql = boundSql.getSql();
		
		Integer count = this.count(handler, connection, originSql);
		context.setRowCount(count);
		
		this.replaceWithLimitedSql(handler, originSql, context);
		
		return invocation.proceed();
	}
	
	private void replaceWithLimitedSql(StatementHandler handler, String originSql, PaginationContext context) {
		final Integer offset = context.getPage() * context.getPageSize();
		final Integer rowCount = context.getPageSize();
		final ParameterHandler parameterHandler = handler.getParameterHandler();
		final String limitedSql = originSql + " limit ?, ?";
		ParameterHandler limitedParameterHandler = new ParameterHandler() {
			
			@Override
			public void setParameters(PreparedStatement ps) throws SQLException {
				parameterHandler.setParameters(ps);
				
				int count = countParameter(limitedSql);
				ps.setInt(count - 1, offset);
				ps.setInt(count, rowCount);
			}
			
			@Override
			public Object getParameterObject() {
				return parameterHandler.getParameterObject();
			}
		};

		MetaObject metaObject = SystemMetaObject.forObject(handler);
		metaObject.setValue("boundSql.sql", limitedSql);
		metaObject.setValue("delegate.parameterHandler", limitedParameterHandler);
	}
	
	private int countParameter(String sql) {
		int count = 0;
		for (int i = 0; i < sql.length(); i++) {
			if (sql.charAt(i) == '?') {
				count++;
			}
		}
		return count;
	}
	
	private Integer count(StatementHandler statementHandler, Connection connection, String originSql) throws SQLException {
		String countingSql = "select count(*) from (" + originSql + ") t";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer count = null;
		try {
			pstmt = connection.prepareStatement(countingSql);
			statementHandler.parameterize(pstmt);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (Exception e) {
			}
		}
		
		return count;
	}
	

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		
	}

}
