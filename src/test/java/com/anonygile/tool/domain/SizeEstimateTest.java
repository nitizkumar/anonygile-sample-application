package com.anonygile.tool.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.anonygile.tool.web.rest.TestUtil;

public class SizeEstimateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SizeEstimate.class);
        SizeEstimate sizeEstimate1 = new SizeEstimate();
        sizeEstimate1.setId(1L);
        SizeEstimate sizeEstimate2 = new SizeEstimate();
        sizeEstimate2.setId(sizeEstimate1.getId());
        assertThat(sizeEstimate1).isEqualTo(sizeEstimate2);
        sizeEstimate2.setId(2L);
        assertThat(sizeEstimate1).isNotEqualTo(sizeEstimate2);
        sizeEstimate1.setId(null);
        assertThat(sizeEstimate1).isNotEqualTo(sizeEstimate2);
    }
}
