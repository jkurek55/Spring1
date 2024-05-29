package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import com.capgemini.wsb.fitnesstracker.user.internal.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingMapper {
    private final UserMapper userMapper;
    private final UserServiceImpl userService;
    TrainingTO toTrainingTO(Training training){
        return new TrainingTO(
                training.getId(),
                userMapper.toDto(training.getUser()),
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed());

    }

    public Training toTrainingEntity(TrainingTO trainingTO){
        return new Training(
                userService.getUser(trainingTO.getUserDto().Id()).get(), trainingTO.getStartTime(), trainingTO.getEndTime(), trainingTO.getActivityType(), trainingTO.getDistance(), trainingTO.getAverageSpeed()
        );
    }




}
