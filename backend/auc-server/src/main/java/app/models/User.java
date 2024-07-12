package app.models;

import app.repositories.EntityRepository;
import app.repositories.UserRepositoryJPA;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Entity
public class User {
    @SequenceGenerator(name="Account_ids", initialValue=100001)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="Account_ids")
    @Id
    private long id = 0L;

    private String name;

    private String email = "";

    private String role = "Regular User";

    private String hashedPassword = null;

    public User(){}
    public User(long id){this.id = id;}
    public User(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public static ArrayList<User> createSampleUsers(){

        ArrayList<User> users = new ArrayList<>();

        users.add(new User(0, "admin@hva.nl", "Admin"));
        users.add(new User(0, "user1@hva.nl", "User1"));
        users.add(new User(0, "user2@hva.nl", "User2"));
        users.add(new User(0, "user3@hva.nl", "User3"));
        // updates will be persisted by the transaction
        for (User user : users) {
            user.setHashedPassword("welcome");
        }
        users.get(0).setRole("Administrator");

        return users;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}
