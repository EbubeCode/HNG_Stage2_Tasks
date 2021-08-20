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

@Controller
public class ResumeController {
    String myEmail = "chukwuma258@gmail.com";

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

    @PostMapping("/")
    public String sendMessage(@ModelAttribute("respondent") Respondent respondent) {
        System.out.println("Register");
        respondentRepository.save(respondent);
        emailService.sendSimpleMessage(respondent.getName(), myEmail, respondent.getSubject(),
                respondent.getBody());
        return "index";
    }
}
