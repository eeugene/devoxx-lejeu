package fr.aneo.game.security.ajax;

import org.springframework.security.authentication.AuthenticationServiceException;

/**
 * Created by eeugene on 10/03/2017.
 */
public class AuthMethodNotSupportedException extends AuthenticationServiceException {
    private static final long serialVersionUID = 3705043083010304496L;

    public AuthMethodNotSupportedException(String msg) {
        super(msg);
    }
}
