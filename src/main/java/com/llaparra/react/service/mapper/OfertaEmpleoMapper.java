package com.llaparra.react.service.mapper;


import com.llaparra.react.domain.*;
import com.llaparra.react.service.dto.OfertaEmpleoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OfertaEmpleo} and its DTO {@link OfertaEmpleoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OfertaEmpleoMapper extends EntityMapper<OfertaEmpleoDTO, OfertaEmpleo> {



    default OfertaEmpleo fromId(Long id) {
        if (id == null) {
            return null;
        }
        OfertaEmpleo ofertaEmpleo = new OfertaEmpleo();
        ofertaEmpleo.setId(id);
        return ofertaEmpleo;
    }
}
