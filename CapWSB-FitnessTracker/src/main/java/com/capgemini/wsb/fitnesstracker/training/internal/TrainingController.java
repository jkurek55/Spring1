package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingProvider trainingService;
    private final UserServiceImpl userService;
    private final TrainingMapper trainingMapper;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    @GetMapping()
    public List<TrainingTO> getTrainings(){
        return trainingService.getTrainings()
                .stream()
                .map(training -> trainingMapper.toTrainingTO(training))
                .collect(Collectors.toList());
    }
    @GetMapping("/{userId}")
    public List<TrainingTO> getTrainingByUserId(@PathVariable Long userId){
        return trainingService.getTrainingsByUserId(userId).stream().map(trainingMapper::toTrainingTO).toList();
    }

    @GetMapping("/finished/{date}")
    public List<TrainingTO> getTrainingsAfterDate(@PathVariable String date) throws ParseException {
        Date parsedDate = sdf.parse(date);
        return trainingService.getTrainingsAfterDate(parsedDate)
                .stream()
                .map(trainingMapper::toTrainingTO)
                .collect(Collectors.toList());
    }

    //before tests
    @GetMapping("/activity/{activityString}")
    public List<TrainingTO> getTrainingsByAcitityType(@PathVariable String activityString){
        ActivityType activityType = ActivityType.valueOf(activityString.toUpperCase());
        return trainingService.getTrainingsByAcitityType(activityType)
                .stream()
                .map(trainingMapper::toTrainingTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/activityType")
    public List<TrainingTO> getTrainingsByAcitityType(@RequestParam ActivityType activityType){
        //ActivityType activityType = ActivityType.valueOf(activityString.toUpperCase());
        return trainingService.getTrainingsByAcitityType(activityType)
                .stream()
                .map(trainingMapper::toTrainingTO)
                .collect(Collectors.toList());
    }



    //Before tests
    @PostMapping("/add/{userId}")
    public TrainingTO createTraining(@RequestBody TrainingTO trainingTO, @PathVariable Long userId){

        User user = userService.getUser(userId).get();

        trainingTO.setUser(user);

        Training training = trainingMapper.toTrainingEntity(trainingTO);
        //training.setUser(user);

        TrainingTO newTraining = trainingMapper.toTrainingTO(trainingService.addTraining(training));
        return newTraining;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TrainingTO createTraining(@RequestBody TrainingUserIdTO trainingUserIdTO){

        //Long userId = trainingUserIdTO.getUserId();


        TrainingTO trainingTO = trainingMapper.toTrainingToFromTrainingUserIdTO(trainingUserIdTO);
        User user = userService.getUser(trainingUserIdTO.getUserId()).get();
        trainingTO.setUser(user);

        Training training = trainingMapper.toTrainingEntity(trainingTO);
        //training.setUser(user);

        TrainingTO newTraining = trainingMapper.toTrainingTO(trainingService.addTraining(training));
        return newTraining;
    }

    @PutMapping("/{trainingId}")
    public TrainingTO editTraining(@RequestBody TrainingTO trainingTO, @PathVariable Long trainingId){
        trainingService.editTraining(trainingId, trainingTO);
        TrainingTO newTrainingTO = trainingMapper.toTrainingTO(trainingService.editTraining(trainingId, trainingTO));
        return newTrainingTO;
    }


}
