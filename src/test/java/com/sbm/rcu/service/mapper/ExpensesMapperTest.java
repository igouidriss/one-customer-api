package com.sbm.rcu.service.mapper;

import static com.sbm.rcu.domain.ExpensesAsserts.*;
import static com.sbm.rcu.domain.ExpensesTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExpensesMapperTest {

    private ExpensesMapper expensesMapper;

    @BeforeEach
    void setUp() {
        expensesMapper = new ExpensesMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getExpensesSample1();
        var actual = expensesMapper.toEntity(expensesMapper.toDto(expected));
        assertExpensesAllPropertiesEquals(expected, actual);
    }
}
