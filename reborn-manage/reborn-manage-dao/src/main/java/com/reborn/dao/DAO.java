package com.reborn.dao;


/**
 * 
* @Title 

* @Description: TODO

* @author reborngao  Email 460600117@qq.com

* @CreateTime：2016年12月13日

* @UpdateTime：下午10:06:34

* @date 2016年12月13日 
 
* @version V1.0
 */
public interface DAO {
	/**
	 * 查找对象
	 * @param sqlName   配置文件的sql
	 * @param paramObj   参数
	 * @return
	 * @throws Exception
	 */
	public <T> T findForObject(String sqlName, Object paramObj) throws Exception;
	
	
	/**
	 * 
	 * @Title: update   
	 * @Description: 修改对象 
	 * @param: @param sqlName 		
	 * @param: @param paramObj     
	 * @return: void       
	 * @throws
	 */
	public void update(String sqlName, Object paramObj);
}
