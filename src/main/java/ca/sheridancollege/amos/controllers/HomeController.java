package ca.sheridancollege.amos.controllers;

import ca.sheridancollege.amos.beans.Contact;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

// TODO should this be homeController or contactController?
@Controller
public class HomeController {

    // Autowired needed and private?
    ModelAndView mv;

    @GetMapping("/")
    public ModelAndView homePage() {
        mv = new ModelAndView("home");
        return mv;
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
        return "list-contacts";
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

//    // TODO contact name is used as the object in HTML
//    @PostMapping(value = "/addContact", produces ="application/json")
//    // ResponseBody tells controller this should not request dispatch to Thymeleaf like other controller methods
//    @ResponseBody
//    public Map<String, Object> addContact(@PathVariable int id, RestTemplate restTemplate) {
//        System.out.println("home controller post"); // test
//        Map<String, Object> data = new HashMap<String, Object>();
//        ResponseEntity<Contact> responseEntity = restTemplate.getForEntity
//                ("http://localhost:8080/contacts/ + id", Contact.class);
////        model.addAttribute("contact", responseEntity.getBody());
//        data.put("contact", responseEntity.getBody());
//        return data;
//    }
}
