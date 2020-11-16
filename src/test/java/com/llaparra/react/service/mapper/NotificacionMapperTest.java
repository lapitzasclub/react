package com.llaparra.react.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class NotificacionMapperTest {

    private NotificacionMapper notificacionMapper;

    @BeforeEach
    public void setUp() {
        notificacionMapper = new NotificacionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(notificacionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(notificacionMapper.fromId(null)).isNull();
    }
}
