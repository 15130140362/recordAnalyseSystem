package edu.xidian.sselab.cloudcourse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@SpringBootApplication            //开启组件扫描和自动配置
@EnableAutoConfiguration          //自动配置
@ComponentScan                    //开启自动配置
public class CloudcourseApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudcourseApplication.class, args);
	}//负责启动引导程序
    
}
