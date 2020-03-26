package com.zp.code.repository;


import com.zp.code.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoJPA extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findByEmlAddr(String emlAddr);

    Optional<UserInfo> findByToken(String token);
}
