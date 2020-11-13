package com.llaparra.react.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.llaparra.react.web.rest.TestUtil;

public class PublicacionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublicacionDTO.class);
        PublicacionDTO publicacionDTO1 = new PublicacionDTO();
        publicacionDTO1.setId(1L);
        PublicacionDTO publicacionDTO2 = new PublicacionDTO();
        assertThat(publicacionDTO1).isNotEqualTo(publicacionDTO2);
        publicacionDTO2.setId(publicacionDTO1.getId());
        assertThat(publicacionDTO1).isEqualTo(publicacionDTO2);
        publicacionDTO2.setId(2L);
        assertThat(publicacionDTO1).isNotEqualTo(publicacionDTO2);
        publicacionDTO1.setId(null);
        assertThat(publicacionDTO1).isNotEqualTo(publicacionDTO2);
    }
}
