package app.security;

import app.APIConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    @Autowired
    APIConfig APIConfig;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                 FilterChain chain) throws IOException, ServletException {

        String servletPath = request.getServletPath();

        // OPTIONS requests and non-secured area should pass through without check
        if (HttpMethod.OPTIONS.matches(request.getMethod()) ||
                this.APIConfig.SECURED_PATHS.stream().noneMatch(servletPath::startsWith)) {

            chain.doFilter(request, response);
            return;
        }

        // get the encrypted token string from the authorization request header
        String encryptedToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        // block the request if no token was found
        if (encryptedToken == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No token provided. You need to logon first.");
            return;
        }

        // decode the encoded and signed token, after removing optional Bearer prefix
        JWToken jwToken = null;
        try {
            jwToken = JWToken.decode(encryptedToken.replace("Bearer ", ""),
                    this.APIConfig.getIssuer(), this.APIConfig.getPassphrase());
        } catch (RuntimeException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage() + " You need to logon first.");
            return;
        }

        // pass-on the token info as an attribute for the request
        request.setAttribute(JWToken.JWT_ATTRIBUTE_NAME, jwToken);

        chain.doFilter(request, response);
    }
}
