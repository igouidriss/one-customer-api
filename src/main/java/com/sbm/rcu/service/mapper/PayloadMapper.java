package com.sbm.rcu.service.mapper;

import com.sbm.rcu.domain.Metadata;
import com.sbm.rcu.domain.Payload;
import com.sbm.rcu.service.dto.MetadataDTO;
import com.sbm.rcu.service.dto.PayloadDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Payload} and its DTO {@link PayloadDTO}.
 */
@Mapper(componentModel = "spring")
public interface PayloadMapper extends EntityMapper<PayloadDTO, Payload> {
    @Mapping(target = "metadata", source = "metadata", qualifiedByName = "metadataId")
    PayloadDTO toDto(Payload s);

    @Named("metadataId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MetadataDTO toDtoMetadataId(Metadata metadata);
}
