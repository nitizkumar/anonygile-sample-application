package com.anonygile.tool.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.anonygile.tool.web.rest.TestUtil;

public class TaskBucketTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskBucket.class);
        TaskBucket taskBucket1 = new TaskBucket();
        taskBucket1.setId(1L);
        TaskBucket taskBucket2 = new TaskBucket();
        taskBucket2.setId(taskBucket1.getId());
        assertThat(taskBucket1).isEqualTo(taskBucket2);
        taskBucket2.setId(2L);
        assertThat(taskBucket1).isNotEqualTo(taskBucket2);
        taskBucket1.setId(null);
        assertThat(taskBucket1).isNotEqualTo(taskBucket2);
    }
}
