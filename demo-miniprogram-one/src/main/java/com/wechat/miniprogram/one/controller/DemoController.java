package com.standard.demo.webapp.one.controller;

import java.util.concurrent.Semaphore;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.standard.demo.web.core.entity.ResponseEntity;
import com.standard.demo.web.core.utils.CommonUtil;
import com.standard.demo.webapp.one.service.DemoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author zhangjw
 * @Date 2020/3/27 12:29
 */
@RestController
@RequestMapping("/demo")
@Api(tags = "Demo")
@Slf4j
public class DemoController {

	Semaphore semaphore = new Semaphore(1);

	@Resource
	private DemoService demoService;

	@GetMapping("/sayHello")
	public ResponseEntity sayHello(String name) {
		return new ResponseEntity(demoService.sayHello(name));
	}

	/**
	 * semaphore测试
	 * @return
	 */
	@GetMapping("/getOne")
	@ApiOperation("测试多并发")
	public String getOne() {
		//可用资源数
		int availablePermits = semaphore.availablePermits();
		if (availablePermits > 0) {
			System.out.println("抢到资源");
		} else {
			System.out.println("资源已被占用，稍后再试");
			return "Resource is busy!";
		}

		try {
			semaphore.acquire(1);
			System.out.println("资源正在被使用");
			Thread.sleep(30000);
		} catch (Exception e) {
			log.error("报错：", e);
		} finally {
			semaphore.release(1);
		}

		return "Success";
	}

	/**
	 * synchronized测试
	 * @return
	 */
	@GetMapping("/getTwo")
	@ApiOperation("测试多并发")
	public String getTwo() {
		return demoService.demoSynchronized("123");
	}

	/**
	 * 异常测试
	 * @return
	 */
	@GetMapping("/demoException")
	public ResponseEntity demoException() {
		int a = 0;
		int b = 1 / a;
		return CommonUtil.successJson();
	}

	/**
	 * 测试缓存
	 * @param name
	 * @return
	 */
	@GetMapping("/demoCache")
	public ResponseEntity demoCache(String name) {
		return new ResponseEntity(demoService.demoOneCache(name) + ":" + demoService.demoCache(name));
	}
}
