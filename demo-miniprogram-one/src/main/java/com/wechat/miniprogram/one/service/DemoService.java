package com.standard.demo.webapp.one.service;

/**
 * @Description
 * @Author zhangjw
 * @Date 2020/3/27 12:25
 */
public interface DemoService {

	String sayHello(String name);

	String demoSynchronized(String name);

	String demoCache(String name);

	String demoOneCache(String name);
}
