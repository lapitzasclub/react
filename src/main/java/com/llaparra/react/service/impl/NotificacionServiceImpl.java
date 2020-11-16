package com.llaparra.react.service.impl;

import com.llaparra.react.service.NotificacionService;
import com.llaparra.react.domain.Notificacion;
import com.llaparra.react.repository.NotificacionRepository;
import com.llaparra.react.service.dto.NotificacionDTO;
import com.llaparra.react.service.mapper.NotificacionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Notificacion}.
 */
@Service
@Transactional
public class NotificacionServiceImpl implements NotificacionService {

    private final Logger log = LoggerFactory.getLogger(NotificacionServiceImpl.class);

    private final NotificacionRepository notificacionRepository;

    private final NotificacionMapper notificacionMapper;

    public NotificacionServiceImpl(NotificacionRepository notificacionRepository, NotificacionMapper notificacionMapper) {
        this.notificacionRepository = notificacionRepository;
        this.notificacionMapper = notificacionMapper;
    }

    @Override
    public NotificacionDTO save(NotificacionDTO notificacionDTO) {
        log.debug("Request to save Notificacion : {}", notificacionDTO);
        Notificacion notificacion = notificacionMapper.toEntity(notificacionDTO);
        notificacion = notificacionRepository.save(notificacion);
        return notificacionMapper.toDto(notificacion);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificacionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Notificacions");
        return notificacionRepository.findAll(pageable)
            .map(notificacionMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<NotificacionDTO> findOne(Long id) {
        log.debug("Request to get Notificacion : {}", id);
        return notificacionRepository.findById(id)
            .map(notificacionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Notificacion : {}", id);
        notificacionRepository.deleteById(id);
    }
}
