package com.llaparra.react.service.mapper;


import com.llaparra.react.domain.*;
import com.llaparra.react.service.dto.NotificacionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Notificacion} and its DTO {@link NotificacionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NotificacionMapper extends EntityMapper<NotificacionDTO, Notificacion> {



    default Notificacion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Notificacion notificacion = new Notificacion();
        notificacion.setId(id);
        return notificacion;
    }
}
