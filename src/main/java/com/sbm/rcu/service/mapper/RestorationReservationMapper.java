package com.sbm.rcu.service.mapper;

import com.sbm.rcu.domain.Cancelled;
import com.sbm.rcu.domain.Expenses;
import com.sbm.rcu.domain.Metadata;
import com.sbm.rcu.domain.OneCustomer;
import com.sbm.rcu.domain.RestorationReservation;
import com.sbm.rcu.service.dto.CancelledDTO;
import com.sbm.rcu.service.dto.ExpensesDTO;
import com.sbm.rcu.service.dto.MetadataDTO;
import com.sbm.rcu.service.dto.OneCustomerDTO;
import com.sbm.rcu.service.dto.RestorationReservationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RestorationReservation} and its DTO {@link RestorationReservationDTO}.
 */
@Mapper(componentModel = "spring")
public interface RestorationReservationMapper extends EntityMapper<RestorationReservationDTO, RestorationReservation> {
    @Mapping(target = "cancelled", source = "cancelled", qualifiedByName = "cancelledId")
    @Mapping(target = "expenses", source = "expenses", qualifiedByName = "expensesId")
    @Mapping(target = "metadata", source = "metadata", qualifiedByName = "metadataId")
    @Mapping(target = "oneCustomer", source = "oneCustomer", qualifiedByName = "oneCustomerId")
    RestorationReservationDTO toDto(RestorationReservation s);

    @Named("cancelledId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CancelledDTO toDtoCancelledId(Cancelled cancelled);

    @Named("expensesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ExpensesDTO toDtoExpensesId(Expenses expenses);

    @Named("metadataId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MetadataDTO toDtoMetadataId(Metadata metadata);

    @Named("oneCustomerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OneCustomerDTO toDtoOneCustomerId(OneCustomer oneCustomer);
}
