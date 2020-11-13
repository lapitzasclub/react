package com.llaparra.react.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.llaparra.react.web.rest.TestUtil;

public class PublicacionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Publicacion.class);
        Publicacion publicacion1 = new Publicacion();
        publicacion1.setId(1L);
        Publicacion publicacion2 = new Publicacion();
        publicacion2.setId(publicacion1.getId());
        assertThat(publicacion1).isEqualTo(publicacion2);
        publicacion2.setId(2L);
        assertThat(publicacion1).isNotEqualTo(publicacion2);
        publicacion1.setId(null);
        assertThat(publicacion1).isNotEqualTo(publicacion2);
    }
}
