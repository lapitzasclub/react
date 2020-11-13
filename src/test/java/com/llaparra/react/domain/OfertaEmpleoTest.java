package com.llaparra.react.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.llaparra.react.web.rest.TestUtil;

public class OfertaEmpleoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OfertaEmpleo.class);
        OfertaEmpleo ofertaEmpleo1 = new OfertaEmpleo();
        ofertaEmpleo1.setId(1L);
        OfertaEmpleo ofertaEmpleo2 = new OfertaEmpleo();
        ofertaEmpleo2.setId(ofertaEmpleo1.getId());
        assertThat(ofertaEmpleo1).isEqualTo(ofertaEmpleo2);
        ofertaEmpleo2.setId(2L);
        assertThat(ofertaEmpleo1).isNotEqualTo(ofertaEmpleo2);
        ofertaEmpleo1.setId(null);
        assertThat(ofertaEmpleo1).isNotEqualTo(ofertaEmpleo2);
    }
}
