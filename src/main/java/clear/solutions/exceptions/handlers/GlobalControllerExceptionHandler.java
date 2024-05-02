package clear.solutions.exceptions.handlers;

import clear.solutions.exceptions.UserValidationException;
import clear.solutions.models.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity<UserResponse> handleException(UserValidationException e) {
        UserResponse response = new UserResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
