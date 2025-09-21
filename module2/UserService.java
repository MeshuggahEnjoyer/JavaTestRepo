package module2;

public class UserService {
    private final UserDAO userDao;

    public UserService(UserDAO userDao) {
        this.userDao = userDao;
    }

    public User create(User user) {
        validate(user);
        userDao.create(user);
        return user;
    }

    public User getById(long id) {
        User user = userDao.getById(id);
        checkUserExist(user);
        return user;
    }

    public User update(User user) {
        validate(user);
        User userDB = userDao.getById(user.getId());
        checkUserExist(userDB);
        userDao.update(user);
        return user;
    }

    public void delete(long id) {
        User user = userDao.getById(id);
        checkUserExist(user);
        userDao.delete(id);
    }

    private void validate(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Передан пустой пользователь");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            throw new IllegalArgumentException("Имя пустое");
        }
        if (user.getAge() < 0) {
            throw new IllegalArgumentException("Возраст не может быть отрицательным");
        }
    }

    private void checkUserExist(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Пользователь не найден в БД");
        }
    }
}
