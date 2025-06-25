package com.jhipster.demo.expenses.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.demo.expenses.service.mapper.ExpensesMapper;
import com.jhipster.demo.expenses.service.mapper.ExpensesMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExpensesMapperTest {

    private ExpensesMapper expensesMapper;

    @BeforeEach
    public void setUp() {
        expensesMapper = new ExpensesMapperImpl();
    }
}
