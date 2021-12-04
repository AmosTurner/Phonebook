package ca.sheridancollege.amos.controllers;

import ca.sheridancollege.amos.beans.Contact;
import ca.sheridancollege.amos.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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

    @PostMapping(consumes="application/json")
    public int postContact(@RequestBody Contact contact) {
        System.out.println("Student inserted 2");
        return da.save(contact);
    }

    // deletes existing data and overwrites it
    @PutMapping(consumes="application/json")
    public String putContactCollection(@RequestBody List<Contact> contactList) {
//        da.deleteAll();
        da.saveAll(contactList);
        return "Total Records: " + da.count();
    }

}
