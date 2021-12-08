package ca.sheridancollege.amos.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * This class represents a contact in the contact list who has an id, name, phone number, address, email
 *
 * @author Amos Turner
 * @since 2021-12-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact implements Serializable {

    private int id;
    private String name;
    private String phoneNumber;
    private String address;
    private String email;
}
