package com.anonygile.tool.repository;

import com.anonygile.tool.domain.TaskBucket;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TaskBucket entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskBucketRepository extends JpaRepository<TaskBucket, Long> {
}
