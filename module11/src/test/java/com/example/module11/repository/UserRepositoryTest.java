package com.example.module11.repository;

import com.example.module11.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    static User user;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    TestEntityManager testEntityManager;
    @BeforeAll
    static void initUser() {
        user = new User(1L, "Djiga", 22);
    }

    @Test
    @DisplayName("Сохранение юзера")
    void saveUserTest() {
        User user1 = userRepository.save(user);
        assertThat(testEntityManager.find(User.class, user1.getId())).isEqualTo(user1);
    }

    @Test
    @DisplayName("Поиск Юзера по имени")
    void findAllByNameTest() {
        userRepository.save(user);
        User user1 = userRepository.findUserByName(user.getName());
        assertThat(testEntityManager.find(User.class, user1.getId())).isEqualTo(user1);
    }

}