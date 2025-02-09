package com.sbm.rcu.service.mapper;

import com.sbm.rcu.domain.GoldenRecord;
import com.sbm.rcu.domain.OneCustomer;
import com.sbm.rcu.service.dto.GoldenRecordDTO;
import com.sbm.rcu.service.dto.OneCustomerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OneCustomer} and its DTO {@link OneCustomerDTO}.
 */
@Mapper(componentModel = "spring")
public interface OneCustomerMapper extends EntityMapper<OneCustomerDTO, OneCustomer> {
    @Mapping(target = "goldenRecord", source = "goldenRecord", qualifiedByName = "goldenRecordId")
    OneCustomerDTO toDto(OneCustomer s);

    @Named("goldenRecordId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GoldenRecordDTO toDtoGoldenRecordId(GoldenRecord goldenRecord);
}
