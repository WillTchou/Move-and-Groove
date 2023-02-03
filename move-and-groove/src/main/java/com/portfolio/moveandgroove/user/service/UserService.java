package com.portfolio.moveandgroove.user.service;

import com.portfolio.moveandgroove.user.model.User;
import com.portfolio.moveandgroove.user.model.UserDTO;
import com.portfolio.moveandgroove.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(final UserRepository userRepository,
                       final UserDTOMapper userDTOMapper,
                       final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO getUserById(final Long userId) {
        final String message = String.format("%s doesn't exist", userId);
        final Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.map(userDTOMapper)
                .orElseThrow(() -> new IllegalStateException(message));
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());
    }

    public void createUser(final User user) {
        final String userEmail = user.getEmail();
        final String username = user.getUsername();
        final Optional<User> alreadyFoundEmail = userRepository.findUserByEmail(userEmail);
        final Optional<User> alreadyFoundUserName = userRepository.findUserByUsername(username);
        assertBodyParamIsUnique.accept(alreadyFoundEmail);
        assertBodyParamIsUnique.accept(alreadyFoundUserName);
        userRepository.save(user);
    }

    public void deleteUser(final Long userId) {
        assertUserExists(userId);
        userRepository.deleteById(userId);
    }

    @Transactional
    public void updateUser(final Long userId, final User updatedUser) {
        userRepository.findById(userId)
                .ifPresent(userToUpdate -> {
                    setUserFields(userToUpdate, updatedUser);
                    userRepository.save(userToUpdate);
                });
    }

    public User getUserByUsername(final String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private final Consumer<Optional<User>> assertBodyParamIsUnique = bodyParam -> {
        if (bodyParam.isPresent()) {
            final String message = String.format("%s already exist from another user account", bodyParam);
            throw new IllegalStateException(message);
        }
    };

    private void assertUserExists(final Long id) {
        final boolean doesIdExist = userRepository.existsById(id);
        if (!doesIdExist) {
            final String message = String.format("%s doesn't exist", id);
            throw new IllegalStateException(message);
        }
    }

    private void setUserFields(final User userToUpdate, final User updatedUser) {
        final String updatedUserPassword = updatedUser.getPassword();
        userToUpdate.setEmail(updatedUser.getEmail());
        userToUpdate.setPassword(passwordEncoder.encode(updatedUserPassword));
        userToUpdate.setUsername(updatedUser.getUsername());
        userToUpdate.setFirstname(updatedUser.getFirstname());
        userToUpdate.setLastname(updatedUser.getLastname());
    }
}
