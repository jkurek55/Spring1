package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        //przekazanie do serwisu prosby
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }
    @GetMapping("/basic")
    public List<BasicUserDto> getBasicUserInfo() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toBasicDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/get/{id}")
    public Optional<User> getUserInfo(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping("/add")
    public User addUser(@RequestBody UserDto userDto) {

        // Demonstracja how to use @RequestBody
        //System.out.println("User with e-mail: " + userDto.email() + "passed to the request");
        User newUser = userService.createUser(userMapper.toEntity(userDto));
        // TODO: saveUser with Service and return User
        return newUser;
    }

    @PostMapping("/delete/{id}")
    public boolean deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return true;
    }

}