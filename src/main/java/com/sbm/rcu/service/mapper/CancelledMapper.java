package com.sbm.rcu.service.mapper;

import com.sbm.rcu.domain.Cancelled;
import com.sbm.rcu.service.dto.CancelledDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cancelled} and its DTO {@link CancelledDTO}.
 */
@Mapper(componentModel = "spring")
public interface CancelledMapper extends EntityMapper<CancelledDTO, Cancelled> {}
