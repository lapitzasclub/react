package com.llaparra.react.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PublicacionMapperTest {

    private PublicacionMapper publicacionMapper;

    @BeforeEach
    public void setUp() {
        publicacionMapper = new PublicacionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(publicacionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(publicacionMapper.fromId(null)).isNull();
    }
}
