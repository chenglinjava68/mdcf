package com.mindai.cf.scheduler.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.mindai.cf.scheduler.center" },excludeFilters = { @Filter(type = FilterType.REGEX,pattern={"com.mindai.cf.scheduler.center.rabbit.*"})})
public class Bootstrap {
	
    public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class, args);
    }
}
