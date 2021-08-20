package com.ebube.resume.controller;

import com.ebube.resume.dao.RespondentRepository;
import com.ebube.resume.dto.EmailServiceImpl;
import com.ebube.resume.entity.Respondent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class ResumeController {
    private String myEmail = "chukwuma258@gmail.com";
    private StringBuilder body = new StringBuilder();

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private StringBuilder response = new StringBuilder();


    @Autowired
    private RespondentRepository respondentRepository;

    @Autowired
    private EmailServiceImpl emailService;

    @GetMapping("/")
    public String homePage(Model model) {
        Respondent respondent = new Respondent();
        model.addAttribute("respondent", respondent);
        return "index";
    }

    @PostMapping("/received")
    public String sendMessage(@ModelAttribute("respondent") Respondent respondent) {
        respondent.trim();
        if (!respondent.getBody().isEmpty() && !respondent.getName().isEmpty() && !respondent.getSubject().isEmpty()) {
            respondentRepository.save(respondent);
            body.append("From: ").append(respondent.getName()).append("\nEmail: ").append(respondent.getEmail()).append("\n").append(respondent.getBody());
            new Thread(() -> emailService.sendSimpleMessage(myEmail, respondent.getSubject(),
                    body.toString())
            ).start();
            if (validate(respondent.getEmail()) && respondent.isWantFile()) {
                response.append("Dear: ").append(respondent.getName()).append("\n\tThank you for contacting me, you" +
                        " will find attached the PDF file of my resume.");
                new Thread(() -> emailService.sendMessageWithAttachment(
                        respondent.getEmail(),
                        "Miracle Ebube Chukwuma",
                        response.toString(),
                        "src/main/resources/ChukwumaEbube.pdf"
                ) ).start();
            }
        }
        System.out.println("Register");
        System.out.println(respondent.isWantFile());

        return "received";
    }

    private static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}
