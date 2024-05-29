package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

// TODO: Provide Impl
@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingProvider {


    private final TrainingRepository trainingRepository;
    private final UserMapper userMapper;
    @Override
    public List<Training> getTrainingsByUserId(final Long userId) {
        return trainingRepository.getTrainingByUserId(userId);
    }

    @Override
    public List<Training> getTrainings(){
        return trainingRepository.findAll();
    }

    @Override
    public List<Training> getTrainingsAfterDate(Date date) {
        return trainingRepository.getTrainingsAfterDate(date);
    }


    @Override
    public List<Training> getTrainingsByAcitityType(ActivityType activityType){
        return trainingRepository.getTrainingsByAcitityType(activityType);
    }

    @Override
    public Training addTraining(Training training){
        return trainingRepository.save(training);
    }

    @Override
    public Training editTraining(Long trainingId, TrainingTO trainingTO){
        Training training = trainingRepository.findById(trainingId).get();

        if (trainingTO.getUserDto() != null)
        {
            training.setUser(userMapper.toEntity(trainingTO.getUserDto()));
        }
        if (trainingTO.getStartTime() != null)
        {
            training.setStartTime(trainingTO.getStartTime());
        }
        if (trainingTO.getEndTime() != null)
        {
            training.setEndTime(trainingTO.getEndTime());
        }
        if (trainingTO.getActivityType() != null)
        {
            training.setActivityType(trainingTO.getActivityType());
        }
        if (trainingTO.getDistance() != null)
        {
            training.setDistance(trainingTO.getDistance());
        }
        if (trainingTO.getAverageSpeed() != null)
        {
            training.setAverageSpeed(trainingTO.getAverageSpeed());
        }



        return training;

    }

}
