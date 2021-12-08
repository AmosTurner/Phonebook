package ca.sheridancollege.amos.controllers;

import ca.sheridancollege.amos.beans.Contact;
import ca.sheridancollege.amos.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Home Controller is used for intercepting requests from Thymeleaf Templates
 *
 * @author Amos Turner
 * @since 2021-12-08
 */
@Controller
public class HomeController {

    @Autowired
    @Lazy
    private DatabaseAccess da; // Database Object that is manipulating databases in H2

    // TODO use this?
    ModelAndView mv;

    /**
     * Root of the web application
     *
     * @return the home template
     */
    @GetMapping("/")
    public ModelAndView processHomePage() {
        mv = new ModelAndView("home");
        return mv;
    }

    /**
     * Home page of the web application
     *
     * @return the home template
     */
    @GetMapping("/home")
    public ModelAndView homePage() {
        mv = new ModelAndView("home");
        return mv;
    }

    /**
     * Directs user to add-contact template if they are authorized
     *
     * @return a string representing the add-contact template
     */
    @GetMapping("/secure/add-contact")
    public String secureAddContactPage(Authentication authentication, Model model) {

        String email = authentication.getName();
        List<String> roleList = new ArrayList<String>();
        for (GrantedAuthority ga : authentication.getAuthorities()) {
            roleList.add(ga.getAuthority());
        }
        model.addAttribute("email", email);
        model.addAttribute("roleList", roleList);
        model.addAttribute("contact", new Contact());

        return "/secure/add-contact";
    }

    /**
     * Directs user to list-contacts template if they are authorized
     *
     * @return a string representing the list-contacts template
     */
    @GetMapping("/secure/list-contacts")
    public String secureListContactsPage(Authentication authentication, Model model, RestTemplate restTemplate) {

        String email = authentication.getName(); // fix suggestions here
        List<String> roleList = new ArrayList<String>();
        for (GrantedAuthority ga : authentication.getAuthorities()) {
            roleList.add(ga.getAuthority());
        }
        model.addAttribute("email", email);
        model.addAttribute("roleList", roleList);

        ResponseEntity<Contact[]> responseEntity = restTemplate.getForEntity
                ("http://localhost:8080/contacts/getAll", Contact[].class);
        model.addAttribute("contactList", responseEntity.getBody());

        return "/secure/list-contacts";
    }

    /**
     * Directs user to login for entering user credentials
     *
     * @return a string representing the login template
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    /**
     * Directs user to a page indicating they do not have valid authorization
     *
     * @return a string representing the accessdenied template
     */
    @GetMapping("/permission_denied")
    public String noPermission() {

        return "/error/accessdenied";
    }

    // TODO should be in user controller
    @PostMapping("/register")
    public String postRegister(@RequestParam String username, @RequestParam String password) {
        da.addUser(username, password);
        Long userId = da.findUserAccount(username).getUserId();
        da.addRole(userId, Long.valueOf(2)); // TODO originally 2

        return "redirect:/register-user";
    }

    /**
     * Directs user to registration page for creating a new user
     *
     * @return a string representing the register-user template
     */
    @GetMapping("/register-user")
    public String registerUserPage(Model model) {
        return "register-user";
    }

    /**
     * TODO add comments
     *
     * @return
     */
    @GetMapping(value = "/getContact/{id}", produces = "application/json")
    @ResponseBody
    public Map<String, Object> getContact(@PathVariable int id, RestTemplate restTemplate) {
        Map<String, Object> data = new HashMap<String, Object>();
        ResponseEntity<Contact> responseEntity = restTemplate.getForEntity
                ("http://localhost:8080/contacts/" + id, Contact.class);
        data.put("contact", responseEntity.getBody());
        return data;
    }

    /**
     * TODO add comments
     *
     * @return
     */
    @PostMapping("/addContact")
    // ResponseBody tells controller this should not request dispatch to Thymeleaf like other controller methods
//    @ResponseBody
    public String addContact(RestTemplate restTemplate, Model model) {
        System.out.println("home controller post"); // test

        ResponseEntity<Contact> responseEntity = restTemplate.getForEntity
                ("http://localhost:8080/contacts/saveContact", Contact.class);
        model.addAttribute("contact", responseEntity.getBody());
        return "redirect:/secure/add-contact";
    }

}
