package module2;

public interface UserDAO {
    void create(User user);
    User getById(long id);
    void update(User user);
    void delete(long id);

}
