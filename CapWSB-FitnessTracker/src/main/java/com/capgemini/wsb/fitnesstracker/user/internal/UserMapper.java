package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.stereotype.Component;

@Component
class UserMapper {

    UserDto toDto(User user) {
        return new UserDto(user.getId(),
                           user.getFirstName(),
                           user.getLastName(),
                           user.getBirthdate(),
                           user.getEmail());
    }

    public BasicUserDto toBasicDto(User user) {
        return new BasicUserDto(user.getId(), user.getFirstName(), user.getLastName());
    }
    public IDEmailUserDto toIDEmailUserDto(User user){
        return new IDEmailUserDto(user.getId(), user.getEmail());
    }


    User toEntity(UserDto userDto) {
        return new User(
                        userDto.firstName(),
                        userDto.lastName(),
                        userDto.birthdate(),
                        userDto.email());
    }

}
