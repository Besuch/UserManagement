package clear.solutions.controllers;

import clear.solutions.models.User;
import clear.solutions.services.UserService;
import clear.solutions.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("api/v1/users")
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private ValidationService validationService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createUser(@RequestBody User user) {
        validationService.validateUser(user);
        User createdUser = userService.createUser(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Collections.singletonMap("data", createdUser));
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        validationService.validateUser(user);
        userService.updateUser(id, user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateUserParam(@PathVariable Long id, @RequestBody User user) {
        validationService.validateUser(user);
        User updatedUser = userService.updateUserInfo(id, user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Collections.singletonMap("data", updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(
            value = "/range",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getUserByBirthdayRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) {
        validationService.validateBirthDatesRange(from, to);
        List<User> users = userService.getUsersByDateRange(from, to);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Collections.singletonMap("data", users));
    }
}
