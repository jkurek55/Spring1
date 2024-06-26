package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.List;


public record IDEmailUserDto(
        @Nullable Long Id,
        String email) {
}

