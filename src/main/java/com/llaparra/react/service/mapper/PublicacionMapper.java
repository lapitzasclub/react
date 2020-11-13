package com.llaparra.react.service.mapper;


import com.llaparra.react.domain.*;
import com.llaparra.react.service.dto.PublicacionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Publicacion} and its DTO {@link PublicacionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PublicacionMapper extends EntityMapper<PublicacionDTO, Publicacion> {



    default Publicacion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Publicacion publicacion = new Publicacion();
        publicacion.setId(id);
        return publicacion;
    }
}
