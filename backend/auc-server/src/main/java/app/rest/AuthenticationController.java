package app.rest;

import app.APIConfig;
import app.exeptions.NotAcceptableException;
import app.models.User;
import app.security.JWToken;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    APIConfig APIConfig;

    @PostMapping(path = "/login")
    public ResponseEntity<User> authAccount(@RequestBody ObjectNode loginData) {

        String email = loginData.get("email").asText();
        String password = loginData.get("password").asText();

        String correctPassword = email.substring(0, email.indexOf("@"));

        //correctPassword contains the username of the user it used before the @ symbol in the email
        User user = new User(0, correctPassword, email);

        if (!password.equals(correctPassword)) {
            throw new NotAcceptableException("Password-email combination is incorrect");
        }

        // Issue a token for the account, valid for some time
        JWToken jwToken = new JWToken(user.getName(), user.getId(), user.getRole());
        String tokenString = jwToken.encode(this.APIConfig.getIssuer(),
                this.APIConfig.getPassphrase(),
                this.APIConfig.getTokenDurationOfValidity());

        return ResponseEntity.accepted()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .body(user);
    }
}
