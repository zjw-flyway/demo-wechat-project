package com.standard.demo.webapp.one.config.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.standard.demo.web.core.constant.ErrorEnum;
import com.standard.demo.web.core.entity.ResponseEntity;
import com.standard.demo.web.core.utils.CommonUtil;

/**
 * @author: zjw
 * @description: 系统错误拦截,系统404报错拦截
 * @date: 2020/03/20 10:31
 */
@Controller
public class CustomErrorController implements ErrorController {

	private static final String ERROR_PATH = "/error";

	/**
	 * 主要是登陆后的各种错误路径 404页面改为返回此json
	 *
	 */
	@RequestMapping(value = ERROR_PATH)
	@ResponseBody
	public ResponseEntity handleError() {
		return CommonUtil.errorJson(ErrorEnum.E_404);
	}

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}
}
