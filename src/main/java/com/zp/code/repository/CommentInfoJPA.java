package com.zp.code.repository;


import com.zp.code.model.CommentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentInfoJPA extends JpaRepository<CommentInfo, Long> {

    List<CommentInfo> findByUserId(Long userId);

    List<CommentInfo> findByProjectId(String projectId);

    List<CommentInfo> findByProjectIdOrderByCreateTimeAsc(String projectId);

    List<CommentInfo> findByUserIdOrderByCreateTimeAsc(Long userId);

}
