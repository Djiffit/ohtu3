package ohtu.data_access;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import ohtu.domain.User;

public class FileUserDAO implements UserDao {

    private File accounts;
    private List<User> users;

    public FileUserDAO(String file) throws FileNotFoundException {
        accounts = new File("accounts.txt");
        usersToList();
    }

    private void usersToList() throws FileNotFoundException {
        String name = "";
        users = new ArrayList<User>();
        try {
            addUser(name);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileUserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addUser(String name) throws FileNotFoundException {
        Scanner reader = new Scanner(accounts);
        int i = 0;
        while (reader.hasNextLine()) {
            if (i % 2 == 0) name = reader.nextLine();
            else users.add(new User(name, reader.nextLine()));
            i++;
        }
    }

    private void createUser(int i, String name, String line) {
        if (i % 2 == 0) {
            name = line;
        } else {
            users.add(new User(name, line));
        }
    }

    @Override
    public List<User> listAll() {
        return users;
    }

    @Override
    public User findByName(String name) {
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void add(User user) {
        try {
            writeToFile(user);
        } catch (Exception e) {
            System.out.println("JOTAIN ONKELMAA TIEDOSTOJEN KANSSA");
        }
        users.add(user);
    }

    private void writeToFile(User user) throws IOException {
        BufferedWriter outStream = new BufferedWriter(new FileWriter(accounts, true));
        outStream.append(user.getUsername() + "\n");
        outStream.append(user.getPassword() + "\n");
        outStream.close();
    }

}
