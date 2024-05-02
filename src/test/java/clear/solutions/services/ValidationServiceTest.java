package clear.solutions.services;

import clear.solutions.exceptions.UserValidationException;
import clear.solutions.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ValidationServiceTest {

    ValidationService validationService;
    private User user;

    @BeforeEach
    void setUp() {
        validationService = new ValidationService(18);
        user = getUser();
    }

    @Test
    void shouldThrowException_whenUserIsNull() {
        // given when
        UserValidationException exception =
                assertThrows(UserValidationException.class, () -> validationService.validateUser(null));

        String expectedMessage = "User is not represented";
        String actualMessage = exception.getMessage();

        // then
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldThrowException_whenUserFirstNameIsNull() {
        // given when
        user.setFirstName(null);
        UserValidationException exception =
                assertThrows(UserValidationException.class, () -> validationService.validateUser(user));

        String expectedMessage = "FirstName is not valid";
        String actualMessage = exception.getMessage();

        // then
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldThrowException_whenUserLastNameIsNull() {
        // given when
        user.setLastName(null);
        UserValidationException exception =
                assertThrows(UserValidationException.class, () -> validationService.validateUser(user));

        String expectedMessage = "LastName is not valid";
        String actualMessage = exception.getMessage();

        // then
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldThrowException_whenUserBirthdateIsNull() {
        // given when
        user.setBirthDate(null);
        UserValidationException exception =
                assertThrows(UserValidationException.class, () -> validationService.validateUser(user));

        String expectedMessage = "BirthDate is not represented";
        String actualMessage = exception.getMessage();

        // then
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldThrowException_whenEmailIsNotValid() {
        // given when
        user.setEmail("email");
        UserValidationException exception =
                assertThrows(UserValidationException.class, () -> validationService.validateUser(user));

        String expectedMessage = "Not valid email";
        String actualMessage = exception.getMessage();

        // then
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldThrowException_whenBirthDateIsNotValid() {
        // given when
        user.setBirthDate(LocalDate.MAX.minusYears(1L));
        UserValidationException exception =
                assertThrows(UserValidationException.class, () -> validationService.validateUser(user));

        String expectedMessage = "Not valid birthdate";
        String actualMessage = exception.getMessage();

        // then
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldThrowException_whenUserLessThan18YearsOld() {
        // given when
        user.setBirthDate(LocalDate.now());
        UserValidationException exception =
                assertThrows(UserValidationException.class, () -> validationService.validateUser(user));

        String expectedMessage = "User less than 18 y.o.";
        String actualMessage = exception.getMessage();

        // then
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldThrowException_whenDateFromAfterDateTo() {
        // given when
        UserValidationException exception =
                assertThrows(UserValidationException.class,
                        () -> validationService.validateBirthDatesRange(LocalDate.MAX, LocalDate.MIN));

        String expectedMessage = "Not valid date range";
        String actualMessage = exception.getMessage();

        // then
        assertTrue(actualMessage.contains(expectedMessage));
    }

    private User getUser() {
        return User.builder()
                .id(1L)
                .email("test@test.com")
                .firstName("FirstName")
                .lastName("LastName")
                .birthDate(LocalDate.of(2024, 1, 1))
                .address("Dnipro")
                .phoneNumber("111")
                .build();
    }
}