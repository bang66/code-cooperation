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

    @Query(nativeQuery = true, value = "select CommentInfo from CommentInfo c group by c.projectId order by c.createTime desc ")
    List<CommentInfo> searchHotProjects();

    Optional<CommentInfo> findByUserIdAndMessageNotNull(Long userId);

}
