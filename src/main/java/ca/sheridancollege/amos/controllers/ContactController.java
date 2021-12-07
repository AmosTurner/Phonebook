package ca.sheridancollege.amos.controllers;

import ca.sheridancollege.amos.beans.Contact;
import ca.sheridancollege.amos.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* TODO
    add fragments in HTML
    add roles for spring security
    finish restful controller
    add data validation for form
    use tables for forms
    Security config error needs to be fixed
 */
@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
//    @Lazy
    DatabaseAccess da;

    // returns all records; all the contacts in the database
    @GetMapping
    public List<Contact> getStudentCollection() {
        return da.findAll();
    }

    // returns a contact by ID
    @GetMapping("/{id}")
    public Contact getIndividualContact(@PathVariable int id) {
        return da.findById(id).get(0);
    }

    // deletes existing data and overwrites it
    @PutMapping(consumes="application/json")
    public String putContactCollection(@RequestBody List<Contact> contactList) {
        da.deleteAll();
        System.out.println("put"); // test
        da.saveAll(contactList);
        return "Total Records: " + da.count();
    }

    @PostMapping(value = "/addContact")
    @ResponseBody
    public String postContact(@ModelAttribute Contact contact, RestTemplate restTemplate) { // requests body parses through the JSON file
        System.out.println("post");
        da.save(contact);
        return "add-contact";
    }

//    @PostMapping(value = "/addContact", consumes = "application/json")
//    public int postContact(@RequestBody Contact contact) { // requests body parses through the JSON file
//        System.out.println("post");
//        return da.save(contact);
//    }

    @DeleteMapping("{id}")
    public Map<String, Boolean> deleteContact(@PathVariable(value = "id") int id) {
        System.out.println("test 1");
        da.deleteById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

//    @DeleteMapping("/contact/{id}")
//    public ResponseEntity deleteContact(@PathVariable int id) {
//        da.deleteById(id);
//        return new ResponseEntity(HttpStatus.OK);
//    }

    @GetMapping("/editTeam/{id}")
    public String editTeam(Model model, @PathVariable int id) {
        Contact contact = da.findById(id).get(0);
        model.addAttribute("contacts", da.findAll());
        model.addAttribute("contact", contact);
        return "edit-contact";
    }

    @PostMapping("/updateTeam")
    public String updateTeam(Model model, @ModelAttribute Contact contact) {

        da.updateContact(contact);
        model.addAttribute("contacts", da.findAll());
        model.addAttribute("contact", new Contact()); // write "contact"?
        return "redirect:/list-contacts";
    }

}
