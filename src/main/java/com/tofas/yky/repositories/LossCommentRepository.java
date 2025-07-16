package com.tofas.yky.repositories;

import com.tofas.yky.model.losses.LossComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


public interface LossCommentRepository extends JpaRepository<LossComment, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO YKY_LOSS_COMMENTS(LOSS_ID, LOSS_COMMENT, INSDATE, INSBY) VALUES(:lossId, :lossComment, :insdate, :insby)", nativeQuery = true)
    void insertComment(@Param("lossId") Long lossId, @Param("lossComment") String lossComment, @Param("insdate")Date insDate, @Param("insby") String insby);

}
