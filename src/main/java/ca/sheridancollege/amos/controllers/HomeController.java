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

// TODO should this be homeController or contactController?
@Controller
public class HomeController {

    @Autowired
    @Lazy
    private DatabaseAccess da;

    // Autowired needed and private?
    ModelAndView mv;

    @GetMapping("/")
    public ModelAndView processHomePage() {
        mv = new ModelAndView("home");
        return mv;
    }

    @GetMapping("/home")
    public ModelAndView homePage() {
        mv = new ModelAndView("home");
        return mv;
    }

    @GetMapping("/secure/add-contact")
    public String secureAddContagePage(Authentication authentication, Model model){

        String email = authentication.getName();
        List<String> roleList= new ArrayList<String>();
        for (GrantedAuthority ga: authentication.getAuthorities()) {
            roleList.add(ga.getAuthority());
        }
        model.addAttribute("email", email);
        model.addAttribute("roleList", roleList);

        return "/add-contact"; // fix resource here
    }

    @GetMapping("/secure/list-contacts")
    public String secureListContactsPage(Authentication authentication, Model model){

        String email = authentication.getName();
        List<String> roleList= new ArrayList<String>();
        for (GrantedAuthority ga: authentication.getAuthorities()) {
            roleList.add(ga.getAuthority());
        }
        model.addAttribute("email", email);
        model.addAttribute("roleList", roleList);

        return "/list-contacts"; // fix resource here
    }

    @GetMapping("/login")
    public String loginPage(){

        return "login";
    }


    @GetMapping("/permission_denied")
    public String noPermission(){

        return "/error/accessdenied";
    }

//    @GetMapping("/register")
//    public String getRegister() {
//        return "register-user";
//    }

    // should be in user controller
    @PostMapping("/register")
    public String postRegister(@RequestParam String username, @RequestParam String password) {
        da.addUser(username, password);
        Long userId= da.findUserAccount(username).getUserId();
        da.addRole(userId, Long.valueOf(2));

        return "redirect:/";
    }

    @GetMapping("/register-user")
    public String registerUserPage(Model model) {
        return "register-user";
    }

    // TODO use ModelAndView instead
    @GetMapping("/add-contact")
    public String addContactPage(Model model) {
        model.addAttribute("contact", new Contact());
        return "add-contact";
    }

//    // TODO use ModelAndView instead
//    @PostMapping("/addContact")
//    public String processContact() {
//        System.out.println("post mapping redirect");
//        return "redirect:/add-contact";
//    }

    // TODO change "/changeName"
    @GetMapping("/list-contacts")
    public String viewContacts(Model model, RestTemplate restTemplate) {
        ResponseEntity<Contact[]> responseEntity = 			restTemplate.getForEntity
                ("http://localhost:8080/contacts", Contact[].class);
        model.addAttribute("contactList", responseEntity.getBody());
//       TODO use home or list-contacts?
        return "/secure/list-contacts";
    }

    @GetMapping(value="/getContact/{id}", produces="application/json")
    @ResponseBody
    public Map<String, Object> getContact(@PathVariable int id, RestTemplate restTemplate) {
        Map<String, Object> data = new HashMap<String, Object>();
        ResponseEntity<Contact> responseEntity = restTemplate.getForEntity
                    ("http://localhost:8080/contacts/" + id, Contact.class);
            data.put("contact", responseEntity.getBody());
            return data;
        }

    // TODO contact name is used as the object in HTML
    @GetMapping(value = "/addContact")
    // ResponseBody tells controller this should not request dispatch to Thymeleaf like other controller methods
    @ResponseBody
    public Map<String, Object> addContact(RestTemplate restTemplate) {
        System.out.println("home controller post"); // test
        Map<String, Object> data = new HashMap<String, Object>();
        ResponseEntity<Contact> responseEntity = restTemplate.getForEntity
                ("http://localhost:8080/add-contact/", Contact.class);
//        model.addAttribute("contact", responseEntity.getBody());
        data.put("contact", responseEntity.getBody());
        return data;
    }
}
