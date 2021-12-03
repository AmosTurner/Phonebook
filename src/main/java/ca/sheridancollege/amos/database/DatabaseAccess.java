package ca.sheridancollege.amos.database;

import ca.sheridancollege.amos.beans.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DatabaseAccess {

    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    public List<Contact> findAll() {
        MapSqlParameterSource namedParameters= new MapSqlParameterSource();
        String query = "SELECT * FROM contacts";
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Contact>(Contact.class));
    }

    public List<Contact> findById(int id) {
        MapSqlParameterSource namedParameters= new MapSqlParameterSource();
        String query = "SELECT * FROM Contacts WHERE id = :id";
        namedParameters.addValue("id", id);
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Contact>(Contact.class));
    }

    public int save(Contact contact) {
        MapSqlParameterSource namedParameters= new MapSqlParameterSource();
        KeyHolder generatedKeyHolder= new GeneratedKeyHolder();
        String query = "INSERT INTO contacts(name, phoneNumber, address, email," +
                "VALUES(:name, :phoneNumber, :address, :email)";
        namedParameters.addValue("name", contact.getName());
        namedParameters.addValue("phoneNumber", contact.getPhoneNumber());
        namedParameters.addValue("address", contact.getAddress());
        namedParameters.addValue("email", contact.getEmail());

        jdbc.update(query, namedParameters, generatedKeyHolder);
        return (int) generatedKeyHolder.getKey(); // Is this int or Long?
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
}
