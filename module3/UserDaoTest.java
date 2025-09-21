import module2.User;
import module2.UserDaoHibernate;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.*;

@Testcontainers
public class UserDaoTest {
    private static PostgreSQLContainer<?> postgresContainer;
    static private UserDaoHibernate userDao;
    static SessionFactory sessionFactory;

    @BeforeAll
    static void createTestContainerDbConnection() {
        postgresContainer = new PostgreSQLContainer<>("postgres:15.3");
        postgresContainer.start();
        overrideHibernateConfig();
    }
    static void overrideHibernateConfig() {
        Configuration configHibernate = new Configuration();
        configHibernate.configure("hibernate_postgres.cfg.xml")
                .setProperty("hibernate.connection.url", postgresContainer.getJdbcUrl())
                .setProperty("hibernate.connection.username", postgresContainer.getUsername())
                .setProperty("hibernate.connection.password", postgresContainer.getPassword());
        sessionFactory = configHibernate.buildSessionFactory();
        userDao = new UserDaoHibernate(sessionFactory);
    }

    @BeforeEach
    void initTable() {
        try (Connection connection = DriverManager.getConnection(postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(), postgresContainer.getPassword());
                Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS users;");
            statement.execute("CREATE TABLE users (\n" +
                    "    id BIGSERIAL PRIMARY KEY,\n" +
                    "    name VARCHAR NOT NULL,\n" +
                    "    email VARCHAR,\n" +
                    "    age INT NOT NULL,\n" +
                    "    created_at TIMESTAMP NOT NULL DEFAULT now()\n" +
                    ");");

            statement.execute(
                    "INSERT INTO public.users (age, email, name) " +
                            "VALUES (20, '12333@mail.cb', 'Lexa');");

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка в инициализации бд", e);
        }

    }

    @AfterAll
    static void stopContainer() {
        postgresContainer.stop();
        sessionFactory.close();
    }

    @Test
    void createUserTest() throws SQLException {
        User user = new User("Vova", 23, "234@234");
        userDao.create(user);

        User userFromDB = getUserFromDB(user.getName());
        Assertions.assertNotNull(userFromDB, "Юзер не найден в БД");
        Assertions.assertEquals(user.getName(), userFromDB.getName());
        Assertions.assertEquals(user.getAge(), userFromDB.getAge());
        Assertions.assertEquals(user.getEmail(), userFromDB.getEmail());
    }

    private User getUserFromDB(String name) throws SQLException {
        try (Connection connection = DriverManager.getConnection(
                postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword());
                PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT id, name, age, email FROM users WHERE name = ?")) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setName(resultSet.getString("name"));
                    user.setAge(resultSet.getInt("age"));
                    user.setEmail(resultSet.getString("email"));
                    return user;
                } else {
                    return null;
                }
            }
        }
    }

    @Test
    void getByIdTest() {
        User user = userDao.getById(1);
        if (user == null) {
            Assertions.fail("Запись не найдена");
        } else {
            Assertions.assertEquals(1, user.getId());
            Assertions.assertEquals("Lexa", user.getName());
            Assertions.assertEquals(20, user.getAge());
        }
    }

    @Test
    void updateUserTest() {
        User user = new User("name", 1, "");
        user.setId(1L);
        userDao.update(user);

        User userFromDB = userDao.getById(1L);
        Assertions.assertNotNull(userFromDB, "Пользователь не найден по Id");
        Assertions.assertEquals(1L, userFromDB.getId());
        Assertions.assertEquals("name", userFromDB.getName());
        Assertions.assertEquals(1, userFromDB.getAge());
        Assertions.assertEquals("", userFromDB.getEmail());
    }

    @Test
    void deleteTest() {
        User user = userDao.getById(1);
        Assertions.assertNotNull(user);

        userDao.delete(1);

        user = userDao.getById(1);
        Assertions.assertNull(user);

    }


}
