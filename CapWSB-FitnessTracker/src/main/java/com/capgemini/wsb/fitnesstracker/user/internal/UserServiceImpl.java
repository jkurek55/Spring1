package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    public void deleteUser(final Long userid){
        userRepository.deleteById(userid);

    }

    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }



    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public List<BasicUserDto> getAllBasicUsers() {
        return userRepository.getAllBasicUsers();
    }

    @Override
    public List<User> searchUserByEmail(String email){
        return userRepository.searchUserByEmail(email);
    }

    @Override
    public List<User> getOlderThan(LocalDate date){
        return userRepository.getOlderThan(date);
    }

    @Override
    public boolean updateUser(Long id, UserDto userDto){
        User user = userRepository.findById(id).get();

        if (userDto.firstName() != null)
        {
            user.setFirstName(userDto.firstName());
        }
        if (userDto.lastName() != null)
        {
            user.setLastName(userDto.lastName());
        }
        if (userDto.firstName() != null)
        {
            user.setBirthdate(userDto.birthdate());
        }
        if (userDto.firstName() != null)
        {
            user.setEmail(userDto.email());
        }

        userRepository.save(user);
        return true;
    }

}
























