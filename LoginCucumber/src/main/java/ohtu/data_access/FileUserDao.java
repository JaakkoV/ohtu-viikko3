package ohtu.data_access;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import ohtu.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUserDao implements UserDao {

    private List<User> users;
    private String fileName;

    public FileUserDao(String fileName) {
        initUsers(fileName);
    }

    private void initUsers(String fileName) {
        this.users = new ArrayList<User>();
        this.fileName = fileName;
        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                users.add(new User(line.substring(0, line.indexOf(";")), line.substring(line.indexOf(";") + 1, line.length())));
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not Found Exception, check user-file\n" + ex);
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
        String line = user.getUsername() + ";" + user.getPassword() + "\n";
        try {
            String fileName;
            FileWriter writer = new FileWriter(this.fileName, true);
            writer.write(line);
            writer.close();
        } catch (IOException ex) {
            System.out.println("User not added");
        }
    }
}
