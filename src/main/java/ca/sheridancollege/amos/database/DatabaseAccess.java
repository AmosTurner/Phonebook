package ca.sheridancollege.amos.database;

import ca.sheridancollege.amos.beans.Contact;
import ca.sheridancollege.amos.beans.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class DatabaseAccess {

    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User findUserAccount(String email) {

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = "SELECT * FROM sec_user where email=:email";
        parameters.addValue("email", email);
        ArrayList<User> users = (ArrayList<User>)jdbc.query(query, parameters, new
                BeanPropertyRowMapper<User>(User.class));
        if (users.size()>0)
            return users.get(0);
        else
            return null;
    }

    public List<String> getRolesById(Long userId) {

        ArrayList<String> roles = new ArrayList<String>();
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = "select user_role.userId, sec_role.roleName "
                + "FROM user_role, sec_role "
                + "WHERE user_role.roleId=sec_role.roleId "
                + "AND userId=:userId";
        parameters.addValue("userId", userId);
        List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
        for (Map<String, Object> row : rows) {
            roles.add((String)row.get("roleName"));
        }
        return roles;
    }

    public void addUser(String email, String password){
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = "INSERT INTO sec_user"
                + "(email, encryptedPassword, enabled)"
                + " VALUES (:email, :encryptedPassword, 1)";
        parameters.addValue("email", email);
        parameters.addValue("encryptedPassword", passwordEncoder.encode(password));
        jdbc.update(query, parameters);
    }

    public void addRole(Long userId, Long roleId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = "INSERT INTO user_role(userId, roleId)"
                + " VALUES (:userId, :roleId)";
        parameters.addValue("userId", userId);
        parameters.addValue("roleId", roleId);
        jdbc.update(query, parameters);
    }

    public List<Contact> findAll() {
        MapSqlParameterSource namedParameters= new MapSqlParameterSource();
        String query = "SELECT * FROM contacts";
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Contact>(Contact.class));
    }

    public List<Contact> findById(int id) {
        MapSqlParameterSource namedParameters= new MapSqlParameterSource();
        String query = "SELECT * FROM contacts WHERE id = :id";
        namedParameters.addValue("id", id);
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Contact>(Contact.class));
    }

    public int save(Contact contact) {
        MapSqlParameterSource namedParameters= new MapSqlParameterSource();
        KeyHolder generatedKeyHolder= new GeneratedKeyHolder();
        String query = "INSERT INTO contacts(name, phoneNumber, address, email)" +
                "VALUES(:name, :phoneNumber, :address, :email)";
        namedParameters.addValue("name", contact.getName());
        namedParameters.addValue("phoneNumber", contact.getPhoneNumber());
        namedParameters.addValue("address", contact.getAddress());
        namedParameters.addValue("email", contact.getEmail());
        System.out.println("saved");

        jdbc.update(query, namedParameters, generatedKeyHolder);
        return (int) generatedKeyHolder.getKey(); // Is this int or Long?
    }

    public void deleteById(int id) {
        MapSqlParameterSource namedParameters= new MapSqlParameterSource();
        String query = "DELETE FROM contacts where id = :id";
        namedParameters.addValue("id", id);
        System.out.println("test 2");
        jdbc.update(query, namedParameters);
    }

    public void deleteAll() {
        MapSqlParameterSource namedParameters= new MapSqlParameterSource();
        String query = "DELETE FROM contacts";
        jdbc.update(query, namedParameters);
    }

    public int count() { // is the return type Integer or int?
        MapSqlParameterSource namedParameters= new MapSqlParameterSource();
        String query = "SELECT count(*) FROM contacts";
        return jdbc.queryForObject(query, namedParameters, Integer.TYPE);
    }

    public void saveAll(List<Contact> contactList) {
        for (Contact c : contactList) {
            save(c);
        }
    }

    public void updateContact(Contact contact) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "UPDATE contacts SET name = :name, phoneNumber= :phoneNumber, address= :address, " +
                "email= :email";
        namedParameters.addValue("name", contact.getName());
        namedParameters.addValue("phoneNumber", contact.getPhoneNumber());
        namedParameters.addValue("address", contact.getAddress());
        namedParameters.addValue("email", contact.getEmail());
        jdbc.update(query, namedParameters);
    }

}
