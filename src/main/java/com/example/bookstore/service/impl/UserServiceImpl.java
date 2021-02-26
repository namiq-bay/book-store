package com.example.bookstore.service.impl;

import com.example.bookstore.model.User;
import com.example.bookstore.repo.UserRepository;
import com.example.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public User findByUsername(String username) {
        User user = repository.findByUsername(username);

        if (user == null)
            throw new UsernameNotFoundException(username);
        else
            return user;
    }
}
