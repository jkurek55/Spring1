package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import com.capgemini.wsb.fitnesstracker.user.internal.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/training")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingProvider trainingService;
    private final UserServiceImpl userService;
    private final TrainingMapper trainingMapper;
    private final UserMapper userMapper;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping
    public List<TrainingTO> getTrainings(){
        return trainingService.getTrainings()
                .stream()
                .map(training -> trainingMapper.toTrainingTO(training))
                .collect(Collectors.toList());
    }

    @GetMapping("/id/{userId}")
    public List<TrainingTO> getTrainingByUserId(@PathVariable Long userId){
        return trainingService.getTrainingsByUserId(userId).stream().map(trainingMapper::toTrainingTO).toList();
    }

    @GetMapping("/date/{date}")
    public List<TrainingTO> getTrainingsAfterDate(@PathVariable String date) throws ParseException {
        Date parsedDate = sdf.parse(date);
        return trainingService.getTrainingsAfterDate(parsedDate)
                .stream()
                .map(trainingMapper::toTrainingTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/activity/{activityString}")
    public List<TrainingTO> getTrainingsByAcitityType(@PathVariable String activityString){
        ActivityType activityType = ActivityType.valueOf(activityString.toUpperCase());
        return trainingService.getTrainingsByAcitityType(activityType)
                .stream()
                .map(trainingMapper::toTrainingTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/add/{userId}")
    public TrainingTO createTraining(@RequestBody TrainingTO trainingTO, @PathVariable Long userId){

        User user = userService.getUser(userId).get();

        trainingTO.setUserDto(userMapper.toDto(user));

        Training training = trainingMapper.toTrainingEntity(trainingTO);
        //training.setUser(user);

        TrainingTO newTraining = trainingMapper.toTrainingTO(trainingService.addTraining(training));
        return newTraining;
    }

    @PutMapping("/edit/{trainingId}")
    public Training editTraining(@RequestBody TrainingTO trainingTO, @PathVariable Long trainingId){
        return trainingService.editTraining(trainingId, trainingTO);

    }


}
