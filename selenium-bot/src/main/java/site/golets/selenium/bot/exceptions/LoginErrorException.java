package site.golets.selenium.bot.exceptions;


public class LoginErrorException extends RuntimeException{

    public LoginErrorException(String message) {
        super(message);
    }

    public LoginErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
