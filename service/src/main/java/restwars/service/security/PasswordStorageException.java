package restwars.service.security;

import restwars.service.ServiceException;

public class PasswordStorageException extends ServiceException {
    public PasswordStorageException(String message) {
        super(message);
    }
}
