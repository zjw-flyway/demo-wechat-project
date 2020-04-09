package com.wechat.miniprogram.one;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "com.wechat")
@MapperScan("com.wechat.miniprogram.one.dao")
public class DemoMiniprogramOneApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(DemoMiniprogramOneApplication.class);
		//不显示springboot的banner
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// 注意这里要指向原先用main方法执行的Application启动类
		return builder.sources(DemoMiniprogramOneApplication.class);
	}

}
