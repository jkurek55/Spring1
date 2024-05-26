package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.BasicUserDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query searching users by email address. It matches by exact match.
     *
     * @param email email of the user to search
     * @return {@link Optional} containing found user or {@link Optional#empty()} if none matched
     */
    default Optional<User> findByEmail(String email) {
        return findAll().stream()
                        .filter(user -> Objects.equals(user.getEmail(), email))
                        .findFirst();
    }
    default List<BasicUserDto> getAllBasicUsers(){
        return findAll().stream()
                .map(user -> new BasicUserDto(user.getId(), user.getFirstName(), user.getLastName()))
                .collect(Collectors.toList());
    }
/*
    default List<IDEmailUserDto> searchUserByEmail(String email){
        return findAll().stream()
                .map(user -> new IDEmailUserDto(user.getId(), user.getEmail()))
                .filter(user -> user.getEmail().contains(email))
                .collect(Collectors.toList());
    }
*/

    default List<User> searchUserByEmail(String email){
        return findAll().stream()
                //.map(user -> new IDEmailUserDto(user.getId(), user.getEmail()))
                //.filter(user -> user.getEmail().toLowercase().contains(email.toLowerCase()))
                .filter(user -> user.getEmail().matches("(?i).*" + email + ".*"))
                .collect(Collectors.toList());
    }

    default List<User> getOlderThan(int age){
        LocalDate currentDate = LocalDate.now();
        return findAll().stream()
                .filter(user -> (age < Period.between(user.getBirthdate(), currentDate).getYears()))
                .collect(Collectors.toList());
    }




}
