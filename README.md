# demo-project
项目脚手架  
包括spring boot，shiro，dubbo，mybatis-plus，swagger2，springboot admin等   
访问http://localhost:18530/demo-webapp-one/swagger-ui.html 进入swagger2页面  
访问http://localhost:18770/ 进入springboot admin页面

启动步骤：  
1.在数据库中执行sql：mysql.sql文件  
2.启动DemoProviderTwoApplication  
3.启动DemoProviderOneApplication  
4.启动DemoWebappOneApplication，到这里项目就已经启动完成，可以正常访问了  
5.如果要启动springBootAdmin的话，启动DemoMonitorApplication