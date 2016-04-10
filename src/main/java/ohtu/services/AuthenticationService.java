package ohtu.services;

import ohtu.domain.User;
import java.util.ArrayList;
import java.util.List;
import ohtu.data_access.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class AuthenticationService {

    private UserDao userDao;
    @Autowired
    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) {
        for (User user : userDao.listAll()) {
            if (checkCorrectInformation(user, username, password)) return true;
        }
        return false;
    }

    private boolean checkCorrectInformation(User user, String username, String password) {
        if (user.getUsername().equals(username)
                && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    public boolean createUser(String username, String password) {
        if (userDao.findByName(username) != null) {
            return false;
        }
        if (checkValidNameAndPassword(username, password)) return false;
        userDao.add(new User(username, password));
        return true;
    }

    private boolean checkValidNameAndPassword(String username, String password) {
        if (username.equals("")  || password.equals("")) {
            return true;
        }
        return invalid(username, password);
    }

    private boolean invalid(String username, String password) {
        return !((username.length() > 2) && (password.length() > 7) && (password.matches(".*[0-9].*")));
    }
}
