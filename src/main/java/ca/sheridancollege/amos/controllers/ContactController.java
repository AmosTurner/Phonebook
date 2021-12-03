package ca.sheridancollege.amos.controllers;

import ca.sheridancollege.amos.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    DatabaseAccess da;
}
