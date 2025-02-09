package com.sbm.rcu.service.mapper;

import com.sbm.rcu.domain.Address;
import com.sbm.rcu.domain.Payload;
import com.sbm.rcu.service.dto.AddressDTO;
import com.sbm.rcu.service.dto.PayloadDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring")
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {
    @Mapping(target = "payload", source = "payload", qualifiedByName = "payloadId")
    AddressDTO toDto(Address s);

    @Named("payloadId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PayloadDTO toDtoPayloadId(Payload payload);
}
