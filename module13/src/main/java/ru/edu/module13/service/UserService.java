package ru.edu.module13.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edu.module13.entity.User;
import ru.edu.module13.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Юзер с id = " + id + " не найден!"));
    }

    public User save(User user) {
        return repository.save(user);
    }

    public User update(User user) {
        findById(user.getId());
        return repository.save(user);
    }

    public void deleteById(Long id) {
        findById(id);
        repository.deleteById(id);
    }
}