package com.capgemini.wsb.fitnesstracker.notification;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.mail.SimpleMailMessage;

import java.util.List;

public interface ReportService {

    public String generateReport(List<Training> trainingList);

    public SimpleMailMessage constructEmail(User user);

    public void SendEmails();

}
