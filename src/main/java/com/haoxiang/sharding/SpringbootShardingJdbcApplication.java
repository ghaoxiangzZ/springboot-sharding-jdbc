package com.haoxiang.sharding;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.haoxiang.sharding.mapper")
@SpringBootApplication
public class SpringbootShardingJdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootShardingJdbcApplication.class, args);
	}

}
