package com.sbm.rcu.service.mapper;

import com.sbm.rcu.domain.Expense;
import com.sbm.rcu.domain.Expenses;
import com.sbm.rcu.domain.Metadata;
import com.sbm.rcu.service.dto.ExpenseDTO;
import com.sbm.rcu.service.dto.ExpensesDTO;
import com.sbm.rcu.service.dto.MetadataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Expense} and its DTO {@link ExpenseDTO}.
 */
@Mapper(componentModel = "spring")
public interface ExpenseMapper extends EntityMapper<ExpenseDTO, Expense> {
    @Mapping(target = "metadata", source = "metadata", qualifiedByName = "metadataId")
    @Mapping(target = "expenses", source = "expenses", qualifiedByName = "expensesId")
    ExpenseDTO toDto(Expense s);

    @Named("metadataId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MetadataDTO toDtoMetadataId(Metadata metadata);

    @Named("expensesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ExpensesDTO toDtoExpensesId(Expenses expenses);
}
