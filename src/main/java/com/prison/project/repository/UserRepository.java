package com.prison.project.repository;

import com.prison.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("Select u FROM User u WHERE u.userName = :userName")
    User findByUserName(@Param("userName") String userName);

}
