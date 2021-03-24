package com.grlife.newscrawler;

import com.grlife.newscrawler.repository.JdbcNewsRepository;
import com.grlife.newscrawler.repository.NewsRepository;
import com.grlife.newscrawler.service.NewsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final DataSource dataSource;
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public NewsService newsService() {
        return new NewsService(newsRepository());
    }

    @Bean
    public NewsRepository newsRepository() {
        //return new MemoryMemberRepository();
        return new JdbcNewsRepository(dataSource);
    }
}