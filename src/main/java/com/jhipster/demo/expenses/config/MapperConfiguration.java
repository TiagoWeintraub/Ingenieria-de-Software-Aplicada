package com.jhipster.demo.expenses.config;

import com.jhipster.demo.expenses.service.mapper.ExpensesMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

    @Bean
    public ExpensesMapper expensesMapper() {
        return Mappers.getMapper(ExpensesMapper.class);
    }
}
