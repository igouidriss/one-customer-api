package com.sbm.rcu.service.mapper;

import com.sbm.rcu.domain.Payload;
import com.sbm.rcu.domain.Phone;
import com.sbm.rcu.service.dto.PayloadDTO;
import com.sbm.rcu.service.dto.PhoneDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Phone} and its DTO {@link PhoneDTO}.
 */
@Mapper(componentModel = "spring")
public interface PhoneMapper extends EntityMapper<PhoneDTO, Phone> {
    @Mapping(target = "payload", source = "payload", qualifiedByName = "payloadId")
    PhoneDTO toDto(Phone s);

    @Named("payloadId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PayloadDTO toDtoPayloadId(Payload payload);
}
