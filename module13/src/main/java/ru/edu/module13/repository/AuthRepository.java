package ru.edu.module13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edu.module13.entity.Auth;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {
    Auth findByUsername(String username);
}
