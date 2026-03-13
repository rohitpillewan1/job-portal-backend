package com.rohit.job_protal.service;

import com.rohit.job_protal.entity.JobApplication;
import com.rohit.job_protal.entity.User;
import com.rohit.job_protal.enums.ApplicationStatus;
import com.rohit.job_protal.exception.NotFoundException;
import com.rohit.job_protal.repository.JobApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImp implements EmailService{

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Override
    public void sendEmailVerification(User user, String token) {
        String verificationUrl="http://localhost:8080/api/auth/verify?token=" + token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("verify your email - job portal");
        message.setText(
                "Hi " + user.getName() + ",\n\n" +
                        "Please click below link to verify your account:\n\n" +
                           verificationUrl +
                        "\n\nThis link will expire soon."
        );
        javaMailSender.send(message);
    }

    @Override
    public void sendEmailPasswordReset(User user, String token) {
        String verificationUrl="http://localhost:5173/contact?token="+token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("reset your password - job portal");
        message.setText(
                "Hi " + user.getName() + ",\n\n" +
                        "Please click below link to reset your password:\n\n" +
                        verificationUrl +
                        "\n\nThis link will expire soon."
        );
        javaMailSender.send(message);
    }

    @Override
    public void sendApplicactionStatusChange(Long applicationId) {
        JobApplication jobApplication = jobApplicationRepository.findById(applicationId).orElseThrow(()->new NotFoundException("Application not found"));
        String subject="";
        String message="";
        if(jobApplication.getApplicationStatus()== ApplicationStatus.REJECTED){
            subject="Update on Your Job Application";
            message="Dear ,\n"+jobApplication.getUser().getName() +
                    "\n" +
                    "Thank you for applying for the position of "+jobApplication.getJob().getTitle()+"at"+ jobApplication.getJob().getCompany().getName()+".\n" +
                    "\n" +
                    "After careful consideration, we regret to inform you that we will not be moving forward with your application at this time.\n" +
                    "\n" +
                    "We appreciate your interest in our company and encourage you to apply for future opportunities that match your skills and experience.\n" +
                    "\n" +
                    "Best wishes for your career ahead.\n" +
                    "\n" +
                    "Best regards,\n" +
                    jobApplication.getJob().getCompany().getName()+" Hiring Team";
        }
        if(jobApplication.getApplicationStatus() == ApplicationStatus.SHORTLISTED){
            subject = "Your Application Has Been Shortlisted";

            message = "Dear " + jobApplication.getUser().getName() + ",\n\n" +
                    "Congratulations!\n\n" +
                    "Your application for the position of " + jobApplication.getJob().getTitle() +
                    " at " + jobApplication.getJob().getCompany().getName() + " has been shortlisted.\n\n" +
                    "Our hiring team will review your profile further and may contact you soon regarding the next steps in the hiring process.\n\n" +
                    "Thank you for your interest in joining our company.\n\n" +
                    "Best regards,\n" +
                    jobApplication.getJob().getCompany().getName() + " Hiring Team";
        }
        if(jobApplication.getApplicationStatus() == ApplicationStatus.HIRED){
            subject = "Congratulations! You Have Been Selected";

            message = "Dear " + jobApplication.getUser().getName() + ",\n\n" +
                    "Congratulations!\n\n" +
                    "We are pleased to inform you that you have been selected for the position of " +
                    jobApplication.getJob().getTitle() + " at " +
                    jobApplication.getJob().getCompany().getName() + ".\n\n" +
                    "Our team will contact you soon with further details regarding the onboarding process.\n\n" +
                    "We look forward to having you as part of our team.\n\n" +
                    "Best regards,\n" +
                    jobApplication.getJob().getCompany().getName() + " Hiring Team";
        }
        SimpleMailMessage sendMessage = new SimpleMailMessage();
        sendMessage.setTo(jobApplication.getUser().getEmail());
        sendMessage.setSubject(subject);
        sendMessage.setText(message);
        javaMailSender.send(sendMessage);
    }
}
