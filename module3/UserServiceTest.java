import module2.User;
import module2.UserDAO;
import module2.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class UserServiceTest {
    @Mock
    private UserDAO userDaoMock;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void createUserTest_ValidUser() {
        User user = new User("Vova", 30, "124@sudbg");

        userService.create(user);

        verify(userDaoMock).create(user);
    }

    @Test
    public void createUserTest_EmptyName() {
        User user = new User("", 30, "124@sudbg");

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.create(user);
        });
        Assertions.assertEquals("Имя пустое", exception.getMessage());
        verify(userDaoMock, times(0)).create(user);
    }

    @Test
    public void createUserTest_NegativeAge() {
        User user = new User("Vova", -13, "124@sudbg");

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.create(user);
        });
        Assertions.assertEquals("Возраст не может быть отрицательным", exception.getMessage());
        verify(userDaoMock, times(0)).create(user);
    }

    @Test
    public void getByIdTest_UserExists() {
        User user = new User("Vova", 30, "124@sudbg");
        when(userDaoMock.getById(1)).thenReturn(user);

        userService.getById(1);

        verify(userDaoMock).getById(1);
    }

    @Test
    public void getByIdTest_UserNotFound() {
        when(userDaoMock.getById(1)).thenReturn(null);

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.getById(1);
        });
        Assertions.assertEquals("Пользователь не найден в БД", exception.getMessage());
        verify(userDaoMock).getById(1);
    }

    @Test
    public void updateUserTest() {
        User userToUpdate = new User("Vova", 30, "124@sudbg");
        User updatedUser = new User("Vova", 100, "124@sudbg");
        updatedUser.setId(1L);

        when(userDaoMock.getById(1)).thenReturn(userToUpdate);
        userService.update(updatedUser);

        verify(userDaoMock).update(updatedUser);
    }

    @Test
    public void updateUserTest_UserNotFound() {
        User user = new User("Vova", 30, "124@sudbg");
        user.setId(1L);
        when(userDaoMock.getById(1)).thenReturn(null);

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.update(user);
        });
        Assertions.assertEquals("Пользователь не найден в БД", exception.getMessage());
        verify(userDaoMock, times(0)).update(user);
    }

    @Test
    public void deleteTest_UserExists() {
        User user = new User("Vova", 30, "124@sudbg");
        when(userDaoMock.getById(1)).thenReturn(user);

        userService.delete(1);

        verify(userDaoMock).delete(1);
    }

    @Test
    public void deleteTest_UserNotFound() {
        when(userDaoMock.getById(1)).thenReturn(null);

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.delete(1);
        });
        Assertions.assertEquals("Пользователь не найден в БД", exception.getMessage());
        verify(userDaoMock, times(0)).delete(1);
    }
}
