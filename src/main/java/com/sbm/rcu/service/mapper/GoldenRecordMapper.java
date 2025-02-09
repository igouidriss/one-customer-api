package com.sbm.rcu.service.mapper;

import com.sbm.rcu.domain.Cancelled;
import com.sbm.rcu.domain.GoldenRecord;
import com.sbm.rcu.domain.Payload;
import com.sbm.rcu.service.dto.CancelledDTO;
import com.sbm.rcu.service.dto.GoldenRecordDTO;
import com.sbm.rcu.service.dto.PayloadDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GoldenRecord} and its DTO {@link GoldenRecordDTO}.
 */
@Mapper(componentModel = "spring")
public interface GoldenRecordMapper extends EntityMapper<GoldenRecordDTO, GoldenRecord> {
    @Mapping(target = "cancelled", source = "cancelled", qualifiedByName = "cancelledId")
    @Mapping(target = "payload", source = "payload", qualifiedByName = "payloadId")
    GoldenRecordDTO toDto(GoldenRecord s);

    @Named("cancelledId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CancelledDTO toDtoCancelledId(Cancelled cancelled);

    @Named("payloadId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PayloadDTO toDtoPayloadId(Payload payload);
}
