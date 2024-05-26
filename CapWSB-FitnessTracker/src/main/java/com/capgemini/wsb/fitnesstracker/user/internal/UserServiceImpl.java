package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

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
    public List<User> getOlderThan(int age){
        return userRepository.getOlderThan(age);
    }

    @Override
    public boolean updateUser(Long id, UserDto userDto){
        User user = userRepository.findById(id).get();

        if (userDto.firstName() != null)
        {
            user.setFirstName(userDto.firstName());
        }

        userRepository.save(user);
        return true;
    }

}
























