package com.anonygile.tool.repository;

import com.anonygile.tool.domain.Board;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Board entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select board from Board board where board.owner.login = ?#{principal.username}")
    List<Board> findByOwnerIsCurrentUser();
}
