package com.reborn.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


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
		//sessionTemplate.selectOne(sqlName, paramObj)
		System.out.println(sessionTemplate+"===============================");
		return null;
	}

}
