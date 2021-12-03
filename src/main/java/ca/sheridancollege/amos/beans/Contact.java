package ca.sheridancollege.amos.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact implements Serializable {

    private int id;
    private String name;
    private String phoneNumber;
    private String address;
    private String email;
    private Boolean role;
}
