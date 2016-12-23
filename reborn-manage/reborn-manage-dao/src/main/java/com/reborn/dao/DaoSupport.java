package com.reborn.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reborn.pojo.Menu;






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
	public <T> T findForObject(String sqlName, Object paramObj)
			 {
		return sessionTemplate.selectOne(sqlName,paramObj);
	}
	/**
	 * 
	 * <p>Title: update</p>   
	 * <p>Description: 修改对象 </p>   
	 * @param sqlName   配置文件的sql
	 * @param paramObj   参数
	 * @see com.reborn.dao.DAO#update(java.lang.String, java.lang.Object)
	 */
	public int update(String sqlName, Object paramObj) {
		return sessionTemplate.update(sqlName,paramObj);
	}
	/**
	 * 
	 * <p>Title: findForList</p>   
	 * <p>Description:查找多个数据   </p>   
	 * @param sqlName
	 * @param paramObj
	 * @return   
	 * @see com.reborn.dao.DAO#findForList(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public <E> List<E> findForList(String sqlName, Object paramObj) {
		return (List<E>) sessionTemplate.selectList(sqlName, paramObj);
	}
	
	/**
	 *  保存对象
	 * <p>Title: save</p>   
	 * <p>Description: </p>   
	 * @param sqlName
	 * @param paramObj   
	 * @see com.reborn.dao.DAO#save(java.lang.String, java.lang.Object)
	 */
	public void save(String sqlName, Object paramObj) {
		// TODO Auto-generated method stub
		sessionTemplate.insert(sqlName,paramObj);
	}
}
