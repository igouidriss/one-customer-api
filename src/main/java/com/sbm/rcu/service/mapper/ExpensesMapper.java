package com.sbm.rcu.service.mapper;

import com.sbm.rcu.domain.Expenses;
import com.sbm.rcu.service.dto.ExpensesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Expenses} and its DTO {@link ExpensesDTO}.
 */
@Mapper(componentModel = "spring")
public interface ExpensesMapper extends EntityMapper<ExpensesDTO, Expenses> {}
