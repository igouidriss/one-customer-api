package com.sbm.rcu.service.mapper;

import com.sbm.rcu.domain.Metadata;
import com.sbm.rcu.service.dto.MetadataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Metadata} and its DTO {@link MetadataDTO}.
 */
@Mapper(componentModel = "spring")
public interface MetadataMapper extends EntityMapper<MetadataDTO, Metadata> {}
