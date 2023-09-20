package com.metaphorse.diagnostic.diagnostic_taskmanagement.Repository;

import com.metaphorse.diagnostic.diagnostic_taskmanagement.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUser(String user);
}
