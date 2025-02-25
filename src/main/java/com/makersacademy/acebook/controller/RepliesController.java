package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.Reply;
import com.makersacademy.acebook.repository.ReplyRepository;
import com.makersacademy.acebook.repository.UserRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import com.makersacademy.acebook.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.HtmlUtils;



@Controller
public class RepliesController {
    @Autowired
    ReplyRepository repository;
    @Autowired
    UserRepository urepository;

    @PostMapping("/posts/reply")
    public RedirectView createReply(@ModelAttribute Reply reply, Principal principal) {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter new_date = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String new_date_format = date.format(new_date);
        String userName = principal.getName();
        Optional<User> currentUser = urepository.findByUsername(userName);
        User user = currentUser.get();
        Long userIdLong = user.getId();
        Integer userId = userIdLong.intValue();

        reply.setContent(HtmlUtils.htmlEscape(reply.getContent()));
        reply.setTime_posted(new_date_format);
        reply.setUser_id(userId);
        // Is Username meant to be commented out?
        reply.setUsername(userName);

        repository.save(reply);

        return new RedirectView("/posts/" + reply.getPost_id());
    }
    
}
