package com.llaparra.react.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OfertaEmpleoMapperTest {

    private OfertaEmpleoMapper ofertaEmpleoMapper;

    @BeforeEach
    public void setUp() {
        ofertaEmpleoMapper = new OfertaEmpleoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(ofertaEmpleoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(ofertaEmpleoMapper.fromId(null)).isNull();
    }
}
