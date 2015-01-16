package restwars.service.security;

import restwars.service.ServiceException;

public class PasswordException extends ServiceException {
    public PasswordException(String message) {
        super(message);
    }
}
