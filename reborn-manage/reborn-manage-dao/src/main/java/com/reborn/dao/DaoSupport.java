package com.reborn.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



/**
 * 
* @Title DAO 实现类

* @Description: TODO

* @author reborngao  

* @date 2016年12月13日 
 
* @version V1.0
 */

@Repository
public class DaoSupport  implements DAO{
	
	@Autowired
	SqlSessionTemplate sessionTemplate;
	/**
	 * 查找对象
	 * @param sqlName   配置文件的sql
	 * @param paramObj   参数
	 * @return
	 * @throws Exception
	 */
	public Object findForObject(String sqlName, Object paramObj)
			throws Exception {
		return sessionTemplate.selectOne(sqlName, paramObj);
	}

}
