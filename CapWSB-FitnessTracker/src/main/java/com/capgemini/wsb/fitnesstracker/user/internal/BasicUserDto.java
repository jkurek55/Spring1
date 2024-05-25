package com.capgemini.wsb.fitnesstracker.user.internal;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;

import java.time.LocalDate;

public record BasicUserDto(
        @Nullable Long Id,
        String firstName,
        String lastName) {
}
