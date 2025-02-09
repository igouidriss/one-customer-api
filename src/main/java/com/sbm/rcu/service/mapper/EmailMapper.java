package com.sbm.rcu.service.mapper;

import com.sbm.rcu.domain.Email;
import com.sbm.rcu.domain.Payload;
import com.sbm.rcu.service.dto.EmailDTO;
import com.sbm.rcu.service.dto.PayloadDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Email} and its DTO {@link EmailDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmailMapper extends EntityMapper<EmailDTO, Email> {
    @Mapping(target = "payload", source = "payload", qualifiedByName = "payloadId")
    EmailDTO toDto(Email s);

    @Named("payloadId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PayloadDTO toDtoPayloadId(Payload payload);
}
