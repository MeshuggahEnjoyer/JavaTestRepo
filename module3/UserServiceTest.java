import module2.User;
import module2.UserDAO;
import module2.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class UserServiceTest {
    @Mock
    private UserDAO userDaoMock;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userDaoMock);
    }

    @Test
    public void createUserTest_ValidUser() {
        User user = new User("Vova", 30, "124@sudbg");
        userService.create(user);
        Mockito.verify(userDaoMock).create(user);
    }

    @Test
    public void createUserTest_EmptyName() {
        User user = new User("", 30, "124@sudbg");
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.create(user);
        });
        Assertions.assertEquals("Имя пустое", exception.getMessage());
        Mockito.verify(userDaoMock, Mockito.times(0)).create(user);
    }

    @Test
    public void createUserTest_NegativeAge() {
        User user = new User("Vova", -13, "124@sudbg");
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.create(user);
        });
        Assertions.assertEquals("Возраст не может быть отрицательным", exception.getMessage());
        Mockito.verify(userDaoMock, Mockito.times(0)).create(user);
    }

    @Test
    public void getByIdTest_UserExists() {
        User user = new User("Vova", 30, "124@sudbg");
        Mockito.when(userDaoMock.getById(1)).thenReturn(user);
        userService.getById(1);
        Mockito.verify(userDaoMock).getById(1);
    }

    @Test
    public void getByIdTest_UserNotFound() {
        Mockito.when(userDaoMock.getById(1)).thenReturn(null);
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.getById(1);
        });
        Assertions.assertEquals("Пользователь не найден в БД", exception.getMessage());
        Mockito.verify(userDaoMock).getById(1);
    }

    @Test
    public void updateUserTest() {
        User userToUpdate = new User("Vova", 30, "124@sudbg");
        User updatedUser = new User("Vova", 100, "124@sudbg");
        updatedUser.setId(1L);
        Mockito.when(userDaoMock.getById(1)).thenReturn(userToUpdate);
        userService.update(updatedUser);
        Mockito.verify(userDaoMock).update(updatedUser);
    }

    @Test
    public void updateUserTest_UserNotFound() {
        User user = new User("Vova", 30, "124@sudbg");
        user.setId(1L);
        Mockito.when(userDaoMock.getById(1)).thenReturn(null);
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.update(user);
        });
        Assertions.assertEquals("Пользователь не найден в БД", exception.getMessage());
        Mockito.verify(userDaoMock, Mockito.times(0)).update(user);
    }

    @Test
    public void deleteTest_UserExists() {
        User user = new User("Vova", 30, "124@sudbg");
        Mockito.when(userDaoMock.getById(1)).thenReturn(user);
        userService.delete(1);
        Mockito.verify(userDaoMock).delete(1);
    }

    @Test
    public void deleteTest_UserNotFound() {
        Mockito.when(userDaoMock.getById(1)).thenReturn(null);
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.delete(1);
        });
        Assertions.assertEquals("Пользователь не найден в БД", exception.getMessage());
        Mockito.verify(userDaoMock, Mockito.times(0)).delete(1);
    }
}
