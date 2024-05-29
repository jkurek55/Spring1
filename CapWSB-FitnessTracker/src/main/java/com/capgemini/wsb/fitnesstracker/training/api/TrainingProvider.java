package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingTO;
import com.capgemini.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface TrainingProvider {

    /**
     * Retrieves a training based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param trainingId id of the training to be searched
     * @return An {@link Optional} containing the located Training, or {@link Optional#empty()} if not found
     */
    List<Training> getTrainingsByUserId(Long userId);

    List<Training> getTrainings();

    public List<Training> getTrainingsAfterDate(Date date);

    public List<Training> getTrainingsByAcitityType(ActivityType activityType);

    public Training addTraining(Training training);

    public Training editTraining(Long trainingId, TrainingTO trainingTO);
}
