package ca.sheridancollege.amos.controllers;

import ca.sheridancollege.amos.beans.Contact;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

// TODO should this be homeController or contactController?
@Controller
public class HomeController {

    // Autowired needed?
    ModelAndView mv;

    @GetMapping("/")
    public ModelAndView homePage() {
        mv = new ModelAndView("home");
        return mv;
    }

    @GetMapping("/add-contact")
    public ModelAndView addContactPage() {
        mv = new ModelAndView("add-contact", "myContact", new Contact());
        return mv;
    }

    // TODO change "/changeName"
    @GetMapping("/changeName")
    public String viewContacts(Model model, RestTemplate restTemplate) {
        ResponseEntity<Contact[]> responseEntity = 			restTemplate.getForEntity
                ("http://localhost:8080/contacts", Contact[].class);
        model.addAttribute("contactList", responseEntity.getBody());
        return "home";
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
}
