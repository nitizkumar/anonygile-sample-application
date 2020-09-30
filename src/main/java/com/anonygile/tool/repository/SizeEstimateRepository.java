package com.anonygile.tool.repository;

import com.anonygile.tool.domain.SizeEstimate;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the SizeEstimate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SizeEstimateRepository extends JpaRepository<SizeEstimate, Long> {

    @Query("select sizeEstimate from SizeEstimate sizeEstimate where sizeEstimate.user.login = ?#{principal.username}")
    List<SizeEstimate> findByUserIsCurrentUser();
}
