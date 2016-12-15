package com.reborn.dao;

import com.sun.tools.javac.util.List;


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
	 * 查找单条数据
	 * @param sqlName   配置文件的sql
	 * @param paramObj   参数
	 * @return
	 */
	public <T> T findForObject(String sqlName, Object paramObj) throws Exception;
	
	
	/**
	 * 
	 * @Title: update   
	 * @Description:  修改对象 
	 * @param: @param sqlName
	 * @param: @param paramObj
	 * @param: @return      
	 * @return: int   影响条数
	 */
	public int update(String sqlName, Object paramObj);
	
	/**
	 * 
	 * @Title: findForList   
	 * @Description: 查找多个数据   
	 * @param: @param sqlName
	 * @param: @param paramObj
	 * @param: @return      
	 * @return: List<E>
	 */
	public  <E> List<E>  findForList(String sqlName,Object paramObj);
	
}
