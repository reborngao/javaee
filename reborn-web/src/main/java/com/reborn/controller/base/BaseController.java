package com.reborn.controller.base;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.reborn.common.util.Logger;
import com.reborn.util.PageData;


public class BaseController {
	
	private static final long serialVersionUID = 6357869213649815390L;
	
	protected Logger logger=Logger.getLogger(getClass());
	
	/**得到ModelAndView
	 * @return
	 */
	public ModelAndView getModeAndView(){
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
}
