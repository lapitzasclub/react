package com.llaparra.react.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.llaparra.react.web.rest.TestUtil;

public class OfertaEmpleoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OfertaEmpleoDTO.class);
        OfertaEmpleoDTO ofertaEmpleoDTO1 = new OfertaEmpleoDTO();
        ofertaEmpleoDTO1.setId(1L);
        OfertaEmpleoDTO ofertaEmpleoDTO2 = new OfertaEmpleoDTO();
        assertThat(ofertaEmpleoDTO1).isNotEqualTo(ofertaEmpleoDTO2);
        ofertaEmpleoDTO2.setId(ofertaEmpleoDTO1.getId());
        assertThat(ofertaEmpleoDTO1).isEqualTo(ofertaEmpleoDTO2);
        ofertaEmpleoDTO2.setId(2L);
        assertThat(ofertaEmpleoDTO1).isNotEqualTo(ofertaEmpleoDTO2);
        ofertaEmpleoDTO1.setId(null);
        assertThat(ofertaEmpleoDTO1).isNotEqualTo(ofertaEmpleoDTO2);
    }
}
