package clear.solutions.services;

import clear.solutions.exceptions.UserValidationException;
import clear.solutions.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
public class ValidationService {

    private final int minimumAge;

    public ValidationService(@Value("${user.minimum.age}") int minimumAge) {
        this.minimumAge = minimumAge;
    }

    public void validateUser(User user) {
        validationByEmptyOrNull(user);
        validateEmail(user.getEmail());
        validateBirthDate(user.getBirthDate());
    }

    private void validationByEmptyOrNull(User user) {
        if (Objects.isNull(user)) {
            throw new UserValidationException("User is not represented");
        }
        if (Objects.isNull(user.getFirstName()) || user.getFirstName().isBlank()) {
            throw new UserValidationException("FirstName is not valid");
        }
        if (Objects.isNull(user.getLastName()) || user.getLastName().isBlank()) {
            throw new UserValidationException("LastName is not valid");
        }
        if (Objects.isNull(user.getBirthDate())) {
            throw new UserValidationException("BirthDate is not represented");
        }
    }

    private void validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!Pattern.compile(emailRegex)
                .matcher(email)
                .matches()) {
            throw new UserValidationException("Not valid email");
        }
    }

    private void validateBirthDate(LocalDate birthday) {
        LocalDate today = LocalDate.now();
        if (today.isBefore(birthday)) {
            throw new UserValidationException("Not valid birthdate");
        }
        Period period = Period.between(birthday, today);
        if (period.getYears() < minimumAge) {
            throw new UserValidationException("User less than 18 y.o.");
        }
    }

    public void validateBirthDatesRange(LocalDate from, LocalDate to) {
        if (to.isBefore(from)) {
            throw new UserValidationException("Not valid date range");
        }
    }
}
