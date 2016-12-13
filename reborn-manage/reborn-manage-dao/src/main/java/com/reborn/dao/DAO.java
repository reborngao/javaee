package com.reborn.dao;

public interface DAO {
	/**
	 * 查找对象
	 * @param sqlName   配置文件的sql
	 * @param paramObj   参数
	 * @return
	 * @throws Exception
	 */
	public Object findForObject(String sqlName, Object paramObj) throws Exception;
}
