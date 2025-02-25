package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Like;
import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.Reply;
import com.makersacademy.acebook.repository.ReplyRepository;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.LikeRepository;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class PostsController {

    @Autowired
    ReplyRepository reply_repo;
    @Autowired
    PostRepository prepository;
    @Autowired
    UserRepository urepository;
    @Autowired
    LikeRepository lrepository;

    @GetMapping("/posts")
    public String index(Model model, Principal principal) {
        // user id
        String userName = principal.getName();
        Optional<User> currentUser = urepository.findByUsername(userName);
        User user = currentUser.get();
        Long userIdLong = user.getId();

        Iterable<Post> posts = prepository.findAll();
        
        List<Post> postsToList = new ArrayList<>();

        // get all likes for each post
        HashMap<Long, Integer> allLikes = new HashMap<Long, Integer>();
        // save which ones the user has liked
        HashMap<Long, Boolean> postsUserHasLiked = new HashMap<Long, Boolean>();
        for(Post p: posts) {
            allLikes.put(
                p.getId(),
                lrepository.findAllByPost(p.getId()).size()
            );
            postsUserHasLiked.put(
                p.getId(),
                lrepository.hasLiked(p.getId(), userIdLong)
            );
            postsToList.add(p);
        }

        //reversing posts to get newest first
        int sizeOfList = postsToList.size();
        List<Post> reversedPosts = new ArrayList<>();
        for (int i = 1; i<=sizeOfList;i++) {
            reversedPosts.add(postsToList.get(sizeOfList-i));
        }


        //Replies stuff here
        Iterable<Reply> replies = reply_repo.findAll();

        model.addAttribute("posts", reversedPosts);
        model.addAttribute("post", new Post());
        model.addAttribute("replies", replies);
        model.addAttribute("reply", new Reply());
        model.addAttribute("like", new Like());
        model.addAttribute("allLikes", allLikes);
        model.addAttribute("postsUserHasLiked", postsUserHasLiked);
        return "posts/index";
    }

    @RequestMapping(value="/posts/{id}")
    public String postView(Model model, @PathVariable("id") String id, Principal principal){
        Optional<Post> postOptional = prepository.findById(Long.parseLong(id));
        if(postOptional.isPresent()){
            String userName = principal.getName();
            Optional<User> currentUser = urepository.findByUsername(userName);
            User user = currentUser.get();
            Long userIdLong = user.getId();

            Post postBeingViewed = postOptional.get();
            Iterable<Reply> replies = reply_repo.findAll();
            Iterable<Post> posts = prepository.findAll();

            // get all likes for each post
            HashMap<Long, Integer> allLikes = new HashMap<Long, Integer>();
            // save which ones the user has liked
            HashMap<Long, Boolean> postsUserHasLiked = new HashMap<Long, Boolean>();
            for(Post p: posts) {
                allLikes.put(
                    p.getId(),
                    lrepository.findAllByPost(p.getId()).size()
                );
                postsUserHasLiked.put(
                    p.getId(),
                    lrepository.hasLiked(p.getId(), userIdLong)
                );
            }

            model.addAttribute("replies", replies);
            model.addAttribute("reply", new Reply());
            model.addAttribute("like", new Like());
            model.addAttribute("allLikes", allLikes);
            model.addAttribute("postsUserHasLiked", postsUserHasLiked);
            model.addAttribute("post", postBeingViewed);
            model.addAttribute("user_repository", urepository);

            return "/posts/view";
        } else{
            return "/posts/index";
        }
    }

    @PostMapping("/posts")
    public RedirectView create(@ModelAttribute Post post, Principal principal) {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter new_date = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String new_date_format = date.format(new_date);
        String userName = principal.getName();
        Optional<User> currentUser = urepository.findByUsername(userName);
        User user = currentUser.get();
        Long userIdLong = user.getId();
        Integer userId = userIdLong.intValue();
        String image = user.getImage();
        post.setContent(HtmlUtils.htmlEscape(post.getContent()));
        post.setTime_posted(new_date_format);
        post.setUser_id(userId);
        post.setUsername(userName);
        post.setImage(image);
        prepository.save(post);
        return new RedirectView("/posts");
    }

    @PostMapping("/posts/delete")
    public RedirectView delete(@ModelAttribute Post post, Principal principal) {
        String userName = principal.getName();
        Optional<User> currentUser = urepository.findByUsername(userName);
        User user = currentUser.get();
        Long userIdLong = user.getId();
        Integer userId = userIdLong.intValue();

        Optional<Post> PostOptional = prepository.findById(post.getId());
        Post thePost = PostOptional.get();
        if(thePost.getUser_id() == userId){
            prepository.deleteById(post.getId());
        }
        return new RedirectView("/users/me");
    }

    
}
