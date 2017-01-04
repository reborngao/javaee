package com.reborn.plugin;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Properties;

import javax.xml.bind.PropertyException;

import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.mysql.jdbc.PreparedStatement;
import com.reborn.common.util.Logger;
import com.reborn.common.util.ReflectHelper;
import com.reborn.common.util.StringUtil;


/**
 * 分页插件
* @Title  

* @Description: @Signature  拦截器签名
* 
* Cause: org.apache.ibatis.plugin.PluginException: Could not find method on interface org.apache.ibatis.executor.statement.StatementHandler named prepare
* 解决办法   加个  Integer.class
* 

* @author reborngao  Email 460600117@qq.com

* @CreateTime：2017年1月3日 下午3:45:38
 
* @version V1.0
 */

@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class,Integer.class})})
public class PagePlugin implements Interceptor  {
	private 	Logger logger=Logger.getLogger(getClass());
	private static String dialect = "";	//数据库方言
	private static String pageSqlId = ""; //mapper.xml中需要拦截的ID(正则匹配)

	@Override
	public Object intercept(Invocation ivk) throws Throwable {
		logger.info("intercept"+ivk.getClass().getName());
	/*	StatementHandler的默认实现类是RoutingStatementHandler，因此拦截的实际对象是它。RoutingStatementHandler的主要功能是分发，
		它根据配置Statement类型创建真正执行数据库操作的StatementHandler，并将其保存到delegate属性里。
		由于delegate是一个私有属性并且没有提供访问它的方法，因此需要借助MetaObject的帮忙。通过MetaObject的封装后我们可以轻易的获得想要的属性*/
		/*if(ivk.getTarget() instanceof RoutingStatementHandler){
			RoutingStatementHandler statementHandler = (RoutingStatementHandler)ivk.getTarget();
			BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper.getValueByFieldName(statementHandler, "delegate");
			MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(delegate, "mappedStatement");
			
		//	if(mappedStatement.getId().matches(pageSqlId)){ //拦截需要分页的SQL
				BoundSql boundSql = delegate.getBoundSql();
				Object parameterObject = boundSql.getParameterObject();//分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
				logger.info("intercept"+parameterObject);
				if(parameterObject==null){
					throw new NullPointerException("parameterObject尚未实例化！");
				}else{
					Connection connection = (Connection) ivk.getArgs()[0];
					String sql = boundSql.getSql();
					//String countSql = "select count(0) from (" + sql+ ") as tmp_count"; //记录统计
					String fhsql = sql;
					String countSql = "select count(0) from (" + fhsql+ ")  tmp_count"; //记录统计 == oracle 加 as 报错(SQL command not properly ended)
					PreparedStatement countStmt = connection.prepareStatement(countSql);
					BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(),countSql,boundSql.getParameterMappings(),parameterObject);
					setParameters(countStmt,mappedStatement,countBS,parameterObject);
					ResultSet rs = countStmt.executeQuery();
					int count = 0;
					if (rs.next()) {
						count = rs.getInt(1);
					}
					rs.close();
					countStmt.close();
					//System.out.println(count);
					Page page = null;
					if(parameterObject instanceof Page){	//参数就是Page实体
						 page = (Page) parameterObject;
						 page.setEntityOrField(true);	 
						 page.setTotalResult(count);
					}else{	//参数为某个实体，该实体拥有Page属性
						Field pageField = ReflectHelper.getFieldByFieldName(parameterObject,"page");
						if(pageField!=null){
							page = (Page) ReflectHelper.getValueByFieldName(parameterObject,"page");
							if(page==null)
								page = new Page();
							page.setEntityOrField(false); 
							page.setTotalResult(count);
							ReflectHelper.setValueByFieldName(parameterObject,"page", page); //通过反射，对实体对象设置分页对象
						}else{
							throw new NoSuchFieldException(parameterObject.getClass().getName()+"不存在 page 属性！");
						}
					}
					String pageSql = generatePageSql(sql,page);
					System.out.println(sql);
					//ReflectHelper.setValueByFieldName(boundSql, "sql", sql); //将分页sql语句反射回BoundSql.
				}
			//}
		}*/
		
		return ivk.proceed();
	}

	/**
	 * 
	 * <p>Title: plugin</p>   
	 * <p>Description: 就是用当前这个拦截器生成对目标target的代理，实际是通过Plugin.wrap(target,this) 来完成的，把目标target和拦截器this传给了包装函数
	 * 	实现plugin方法时判断一下目标类型，是本插件要拦截的对象才执行Plugin.wrap方法，否者直接返回目标本省，这样可以减少目标被代理的次数
	 *  </p>   
	 * @param target
	 * @return   
	 * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object)
	 */
	@Override
	public Object plugin(Object target) {
		logger.info("plugin");
		// 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的
        // 次数
         return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		logger.info("setProperties");
		dialect =properties.getProperty("dialect");
		if (StringUtil.isEmpty(dialect)) {
			try {
				throw new PropertyException("dialect property is not found!");
			} catch (PropertyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		pageSqlId = properties.getProperty("pageSqlId");
		if (StringUtil.isEmpty(pageSqlId)) {
			try {
				throw new PropertyException("pageSqlId property is not found!");
			} catch (PropertyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
