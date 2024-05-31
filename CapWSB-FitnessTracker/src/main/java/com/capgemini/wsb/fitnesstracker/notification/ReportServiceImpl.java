package com.capgemini.wsb.fitnesstracker.notification;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingServiceImpl;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.internal.UserServiceImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
//@Slf4j
@EnableScheduling
@Data
public class ReportServiceImpl implements ReportService{

    private final UserServiceImpl userService;
    private final TrainingServiceImpl trainingService;
    private final EmailSender emailSender;


    @Override
    public String generateReport(List<Training> trainingList) {
        Date now = new Date();
        Date oneWeekAgo = new Date(now.getTime() - TimeUnit.DAYS.toMillis(7));

        List<Training> lastWeekTrainings = trainingList.stream()
                .filter(training -> training.getStartTime().after(oneWeekAgo))
                .collect(Collectors.toList());

        double totalDistance = lastWeekTrainings.stream()
                .mapToDouble(Training::getDistance)
                .sum();

        double totalAverageSpeed = lastWeekTrainings.stream()
                .mapToDouble(Training::getAverageSpeed)
                .average()
                .orElse(0);

        int numberOfTrainings = lastWeekTrainings.size();


        StringBuilder report = new StringBuilder();
        report.append("Training Report for the Last Week\n");
        report.append("-------------------------------\n");
        report.append("Number of Trainings: ").append(numberOfTrainings).append("\n");
        report.append("Total Distance: ").append(totalDistance).append(" km\n");
        report.append("Average Speed: ").append(totalAverageSpeed).append(" km/h\n");
        report.append("\nDetailed Trainings:\n");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Training training : lastWeekTrainings) {
            report.append("Training ID: ").append(training.getId()).append("\n");
            if (training.getUser() != null) {
                report.append("User: ").append(training.getUser().getFirstName()).append(" ").append(training.getUser().getLastName()).append("\n");
            }
            report.append("Start Time: ").append(sdf.format(training.getStartTime())).append("\n");
            report.append("End Time: ").append(sdf.format(training.getEndTime())).append("\n");
            report.append("Activity Type: ").append(training.getActivityType()).append("\n");
            report.append("Distance: ").append(training.getDistance()).append(" km\n");
            report.append("Average Speed: ").append(training.getAverageSpeed()).append(" km/h\n");
            report.append("-------------------------------\n");
        }

        return report.toString();

    }

    @Override
    public SimpleMailMessage constructEmail(User user) {
        String reportForUser = generateReport(trainingService.getTrainingsByUserId(user.getId()));
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(user.getEmail());
        mailMessage.setSubject("Weekly report");
        mailMessage.setText(reportForUser);
        return mailMessage;

    }

    @Override
    //@Scheduled(cron = "0 0 8 * * MON")
    @Scheduled(cron = "0/10 * * * * *")
    public void SendEmails() {
        List<User> userList = userService.findAllUsers();
        SimpleMailMessage userMailMessage;

        for (User user : userList){
             userMailMessage = constructEmail(user);
             emailSender.send(userMailMessage);
             System.out.println(userMailMessage);
        }

    }
}
