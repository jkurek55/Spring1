package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }
    @GetMapping("/simple")
    public List<BasicUserDto> getBasicUserInfo() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toBasicDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDto getUserInfo(@PathVariable Long id) {
        return userMapper.toDto(userService.getUser(id).get());
    }

    //before tests
    @GetMapping("/get/idbyemail/{email}")
    public List<IDEmailUserDto> searchUserByEmail2(@PathVariable String email)
    {
        return userService.searchUserByEmail(email)
                .stream()
                .map(userMapper::toIDEmailUserDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/email")
    public List<UserDto> searchUserByEmail(@RequestParam String email)
    {
        return userService.searchUserByEmail(email)
                .stream()
                //.map(userMapper::toIDEmailUserDto)
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }


    @GetMapping("/older/{date}")
    public List<User> getOlderThan(@PathVariable LocalDate date){
        return userService.getOlderThan(date);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody UserDto userDto) {

        // Demonstracja how to use @RequestBody
        //System.out.println("User with e-mail: " + userDto.email() + "passed to the request");
        User newUser = userService.createUser(userMapper.toEntity(userDto));
        // TODO: saveUser with Service and return User
        return newUser;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return true;
    }

    @PutMapping("/{id}")
    public boolean updateUser(@PathVariable Long id, @RequestBody UserDto userDto){
        return userService.updateUser(id, userDto);
    }

}



