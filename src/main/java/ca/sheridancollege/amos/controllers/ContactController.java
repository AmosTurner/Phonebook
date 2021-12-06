package ca.sheridancollege.amos.controllers;

import ca.sheridancollege.amos.beans.Contact;
import ca.sheridancollege.amos.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/*
    add fragments in HTML
    add roles for spring security
    finish restful controller
 */
@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
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

    @PostMapping(consumes="application/json")
    public int postContact(@RequestBody Contact contact) { // requests body parses through the JSON file
        System.out.println("post");
        return da.save(contact);
    }

    @DeleteMapping("/contact/id")
    public ResponseEntity deleteContact(@PathVariable int id) {
        da.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

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
