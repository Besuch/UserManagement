package clear.solutions.services;

import clear.solutions.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;
    private User user;

    @BeforeEach
    void setUp() {
        userService = new UserService();
        user = getUser();
        userService.createUser(user);
    }

    @Test
    public void shouldCreateUser() {
        // given when
        User actualUser = userService.createUser(user);
        // then
        assertEquals(user, actualUser);
    }

    @Test
    void shouldGetUser() {
        // given when
        User actualUser = userService.getUser(user.getId());
        //then
        assertEquals(user, actualUser);
    }

    @Test
    void shouldDeleteUser() {
        // given when
        userService.deleteUser(user.getId());
        //then
        assertNull(userService.getUser(user.getId()));
    }

    @Test
    void shouldThrowException_whenUserIsNull_duringDelete() {
        // given when
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.deleteUser(20L));
        String expectedMessage = "User with id: 20 not found";
        String actualMessage = exception.getMessage();

        // then
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldUpdateUser() {
        // given
        User newUser = User.builder()
                .id(user.getId())
                .email("new@gmail.com")
                .firstName("New First Name")
                .lastName("New Last Name")
                .birthDate(LocalDate.of(1990, 1, 1))
                .build();
        // when
        userService.updateUser(user.getId(), newUser);
        User updatedUser = userService.getUser(user.getId());
        // then
        assertEquals(user.getId(), updatedUser.getId());
        assertEquals("new@gmail.com", updatedUser.getEmail());
        assertEquals("New First Name", updatedUser.getFirstName());
        assertEquals("New Last Name", updatedUser.getLastName());
        assertEquals(LocalDate.of(1990, 1, 1), newUser.getBirthDate());
    }

    @Test
    void shouldUpdateUserInfo() {
        // given
        user.setEmail("updatedEmail@gmail.com");
        user.setFirstName("Denys");
        user.setBirthDate(LocalDate.of(2000, 2, 2));
        // when
        User updatedUser = userService.updateUserInfo(user.getId(), user);
        // then
        assertEquals("updatedEmail@gmail.com", updatedUser.getEmail());
        assertEquals("Denys", updatedUser.getFirstName());
        assertEquals(LocalDate.of(2000, 2, 2), updatedUser.getBirthDate());
    }

    @Test
    void shouldThrowException_whenUserIsNull_duringUpdateUserInfo() {
        // given when
        RuntimeException exception =
                assertThrows(RuntimeException.class, () -> userService.updateUserInfo(20L, user));
        String expectedMessage = "User with id: 20 is not exist";
        String actualMessage = exception.getMessage();

        // then
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldGetUsersByDateRangeByDateRange() {
        // given
        List<User> expectedUsers = List.of(user);
        // when
        List<User> actualUsers = userService.getUsersByDateRange(LocalDate.MIN, LocalDate.MAX);
        // then
        assertEquals(expectedUsers, actualUsers);

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