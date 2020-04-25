package com.zp.code.repository;


import com.zp.code.model.ProjectInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectInfoJPA extends JpaRepository<ProjectInfo, Long> {

    Optional<ProjectInfo> findByProjectId(String projectId);

    Optional<ProjectInfo> findByProjectName(String projectName);

    List<ProjectInfo> findByProjectDescLike(String desc);

}
