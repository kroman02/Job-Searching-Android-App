package bbk.finalyearproject.constructionjobs;

import java.util.List;

public interface UserDataHandler {
    public boolean addUser(User user);
    public User getUser(int uid);
    public List<User> getAllUsers();
}
