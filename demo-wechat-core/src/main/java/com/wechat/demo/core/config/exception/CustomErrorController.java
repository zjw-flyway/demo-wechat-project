package com.wechat.demo.core.config.exception;

import com.wechat.demo.core.constant.ErrorEnum;
import com.wechat.demo.core.entity.ResponseEntity;
import com.wechat.demo.core.utils.CommonUtil;
import io.swagger.annotations.Api;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: zjw
 * @description: 系统错误拦截,系统404报错拦截
 * @date: 2020/03/20 10:31
 */
@Controller
@Api(tags = "默认错误拦截")
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
