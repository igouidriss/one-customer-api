package com.sbm.rcu.service.mapper;

import com.sbm.rcu.domain.GoldenRecord;
import com.sbm.rcu.domain.SourceReference;
import com.sbm.rcu.service.dto.GoldenRecordDTO;
import com.sbm.rcu.service.dto.SourceReferenceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SourceReference} and its DTO {@link SourceReferenceDTO}.
 */
@Mapper(componentModel = "spring")
public interface SourceReferenceMapper extends EntityMapper<SourceReferenceDTO, SourceReference> {
    @Mapping(target = "goldenRecord", source = "goldenRecord", qualifiedByName = "goldenRecordId")
    SourceReferenceDTO toDto(SourceReference s);

    @Named("goldenRecordId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GoldenRecordDTO toDtoGoldenRecordId(GoldenRecord goldenRecord);
}
