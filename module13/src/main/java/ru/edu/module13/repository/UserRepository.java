package ru.edu.module13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.edu.module13.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByName(String name);
}