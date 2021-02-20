package com.ming.dictionary.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * ORM框架配置
 * @author ming
 */
@Configuration
@MapperScan("com.ming.**.*dao")
@EnableTransactionManagement
public class OrmConfiguration {
}
