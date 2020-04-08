package com.standard.demo.webapp.one.service.impl;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.standard.demo.provider.one.api.api.DemoOneApi;
import com.standard.demo.webapp.one.service.DemoService;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author zhangjw
 * @Date 2020/3/27 12:25
 */
@Service
@Slf4j
public class DemoServiceImpl implements DemoService {

	@Reference(version = "${dubbo.version}")
	//@Reference(version = "${dubbo.version}",url = "dubbo://192.168.2.64:18532")
	private DemoOneApi demoApi;

	@Override
	public String sayHello(String name) {
		return demoApi.sayHello(name);
	}

	@Override
	public synchronized String demoSynchronized(String name) {
		log.info("进入demoSynchronized");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			log.error("demoSynchronized报错了:", e);
		}
		log.info("结束demoSynchronized");
		return name;
	}

	@Override
	@Cacheable(value = "demoCache")
	public String demoCache(String name) {
		return name;
	}

	@Override
	public String demoOneCache(String name) {
		return demoApi.demoCache(name);
	}
}
