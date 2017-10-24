package com.future.yingyue.config;

import com.future.yingyue.base.AliyunNewOss;
import com.future.yingyue.base.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:yingyue-core.properties")
public class AliyunOssConfig {
	@Autowired
	private Environment env;

	@Bean
	public StorageService aliyunNewOss() {
		return new AliyunNewOss(env.getProperty("oss.access.id"), env.getProperty("oss.access.key"),
				env.getProperty("oss.endpoint"), env.getProperty("oss.bucket.name"),
				env.getProperty("cdn.domain.name"));
	}
}
