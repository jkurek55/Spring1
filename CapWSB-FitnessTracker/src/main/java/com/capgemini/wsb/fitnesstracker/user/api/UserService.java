package com.capgemini.wsb.fitnesstracker.user.api;
import com.capgemini.wsb.fitnesstracker.user.internal.BasicUserDto;
import com.capgemini.wsb.fitnesstracker.user.internal.IDEmailUserDto;
import com.capgemini.wsb.fitnesstracker.user.internal.UserDto;
import java.util.List;


/**
 * Interface (API) for modifying operations on {@link User} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface UserService {

    User createUser(User user);

    public List<BasicUserDto> getAllBasicUsers();

    public List<User> searchUserByEmail(String email);

    public List<User> getOlderThan(int age);

    public boolean updateUser(Long id, UserDto userDto);
    }


