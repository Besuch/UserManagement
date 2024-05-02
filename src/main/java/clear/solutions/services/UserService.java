package clear.solutions.services;

import clear.solutions.models.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;


@Service
public class UserService {

    Map<Long, User> users = new HashMap<>();

    public User createUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    public User getUser(Long id) {
        return users.get(id);
    }

    public void deleteUser(Long id) {
        User user = users.get(id);
        if (Objects.nonNull(user)) {
            users.remove(id);
        } else {
            throw new RuntimeException("User with id: " + id + " not found");
        }
    }

    public void updateUser(Long id, User user) {
        if (users.containsKey(id)) {
            users.remove(id);
            user.setId(id);
            users.put(id, user);
        }
    }

    public User updateUserInfo(Long id, User user) {
        if (users.containsKey(id)) {
            user.setId(id);
            users.put(id, user);
            return user;
        } else {
            throw new RuntimeException("User with id: " + id + " is not exist");
        }
    }

    public List<User> getUsersByDateRange(LocalDate from, LocalDate to) {
        List<User> usersByBirthRange = new ArrayList<>();
        users.values().stream()
                .filter(user -> user.getBirthDate().isAfter(from) && user.getBirthDate().isBefore(to))
                .forEach(usersByBirthRange::add);

        return usersByBirthRange;
    }

}
