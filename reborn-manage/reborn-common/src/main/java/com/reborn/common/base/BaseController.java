package com.reborn.common.base;


import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.reborn.common.util.Logger;
import com.reborn.common.util.PageData;


public class BaseController {
	
	
	private static final long serialVersionUID = 6357869213649815390L;
	
	protected Logger logger=Logger.getLogger(getClass());
	
	
	/**得到ModelAndView
	 * @return
	 */
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
	
	/**
	 * new PageData对象
	 */
	public PageData  getPageData(){
		return new PageData(this.getRequest());
	}
	/**
	 * 得到request对象
	 * @return
	 */
	public HttpServletRequest getRequest() {
		 HttpServletRequest request= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		 return request;
	}
	protected void logBefore(Logger logger,String interfaceName){
		logger.info("start");
		logger.info(interfaceName);
	}
	protected void logAfter(Logger logger){
		logger.info("end");
	}
	
}
