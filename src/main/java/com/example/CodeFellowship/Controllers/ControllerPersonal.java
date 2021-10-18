package com.example.CodeFellowship.Controllers;


import com.example.CodeFellowship.model.ApplicationUser;
import com.example.CodeFellowship.model.Post;
import com.example.CodeFellowship.repositery.RepositeryData;
import com.example.CodeFellowship.repositery.RepositeryPost;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class ControllerPersonal {

    @Autowired
    RepositeryData repositeryData;

    @Autowired
    RepositeryPost repositeryPost;

    @Autowired
    BCryptPasswordEncoder encoder;

    @GetMapping("/signup")
    public String signUpInformation() {
      return "signup";
    }
    @GetMapping("/login")
    public String loginInformation() {
        return "login";
    }
    @GetMapping("/profile")
    public String profileInformation(Model model , Principal principal) {
                ApplicationUser applicationUser = repositeryData.findApplicationUserByUsername(principal.getName());
                model.addAttribute("username" , applicationUser);

        return "profile";
    }



    @PostMapping("/signup")
    public RedirectView attemptSignUp(
                        @RequestParam String username,
                        @RequestParam String password,
                        @RequestParam String firstName,
                        @RequestParam String lastName,
                        @RequestParam String dateOfBirth,
                        @RequestParam String bio,
                        @RequestParam String imageUrl){
        ApplicationUser applicationUser = new ApplicationUser(username , encoder.encode(password) , firstName , lastName , dateOfBirth , bio , imageUrl);
        applicationUser = repositeryData.save(applicationUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(applicationUser, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/profile");
    }

@GetMapping("users/{id}")
public String getUserById(@PathVariable Long id , Model model){
        model.addAttribute("username" , repositeryData.findApplicationUserById(id));
        return ("profile");
}


@GetMapping("/posts")
    public String getPostForUsername(Model model , Principal principal) {
    ApplicationUser applicationUser = repositeryData.findApplicationUserByUsername(principal.getName());
    model.addAttribute("username" , applicationUser);
        return "posts";
}

@PostMapping("/posts")
    public RedirectView createPostUsername(Model model , Principal principal , String body)
{
    ApplicationUser applicationUser = repositeryData.findApplicationUserByUsername(principal.getName());
Post post = new Post(applicationUser , body);
post = repositeryPost.save(post);
model.addAttribute("username" , applicationUser.getWrittenPost());
return  new RedirectView("/profile");
}






}
