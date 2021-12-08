package ca.sheridancollege.amos.service;

import ca.sheridancollege.amos.beans.User;
import ca.sheridancollege.amos.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Searches for users in the database by username entered
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    @Lazy
    private DatabaseAccess da;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Searches for user based on username
        ca.sheridancollege.amos.beans.User user = da.findUserAccount(username);

        // If the user is not found, an exception is thrown
        if (user == null) {
            System.out.println("User not found:" + username);
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }

        // Retrieves a list of roles for that user
        List<String> roleNames= da.getRolesById(user.getUserId());

        // Change the list of the user's roles into a list of GrantedAuthority
        List<GrantedAuthority> grantList= new
                ArrayList<GrantedAuthority>();
        if (roleNames!= null) {
            for (String role : roleNames) {
                grantList.add(new SimpleGrantedAuthority(role));
            }
        }

        // Creates a user based on the information above
        UserDetails userDetails= (UserDetails) new org.springframework.security.core.userdetails.User(user.getEmail(), user.getEncryptedPassword(), grantList);
        return userDetails;
    }
}
