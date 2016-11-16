package com.reborn.controller.base;

import org.springframework.web.servlet.ModelAndView;

import com.reborn.utils.Logger;


public class BaseController {
	
	private static final long serialVersionUID = 6357869213649815390L;
	
	protected Logger logger=Logger.getLogger(getClass());
	
	/**得到ModelAndView
	 * @return
	 */
	public ModelAndView getModeAndView(){
		return new ModelAndView();
	}
}
