package module2;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserDAO userDAO = new UserDaoHibernate(HibernateSessionFactoryUtil.getSessionFactoryPostgres());
        UserService userService = new UserService(userDAO);

        String userchoice;
        do {
            System.out.println("Выберите действие: C|R|U|D, E - exit");
            userchoice = scanner.nextLine().toLowerCase(Locale.ROOT);
            switch (userchoice) {
                case "c":
                    createUser(scanner, userService);
                    break;
                case "r":
                    getUserById(scanner, userService);
                    break;
                case "u":
                    updateUser(scanner, userService);
                    break;
                case "d":
                    deleteUser(scanner, userService);
                    break;
                case "e":
                    break;
                default:
                    System.out.println("Неизвестная команда, повторите ввод.");
            }
        } while (!userchoice.equals("e"));


        HibernateSessionFactoryUtil.closeSession();
        scanner.close();


    }

    public static void createUser(Scanner scanner, UserService userService) {
        System.out.println("Введите name, age, email через пробел");
        String[] userdata = scanner.nextLine().split(" ");
        if (userdata.length != 3) {
            System.out.println("Ошибка ввода данных");
            return;
        }
        try {
            String name = userdata[0].trim();
            int age = Integer.parseInt(userdata[1].trim());
            String email = userdata[2].trim();
            User user = new User(name, age, email);
            userService.create(user);
            System.out.println("Пользователь создан: " + user);
        } catch (NumberFormatException e) {
            System.out.println("Введите корректный возраст");
        }
    }

    public static void getUserById(Scanner scanner, UserService userService) {
        System.out.println("Введите Id");
        int userid = Integer.parseInt(scanner.nextLine());
        System.out.println(userService.getById(userid));
    }

    public static void updateUser(Scanner scanner, UserService userService) {
        System.out.println("Введите Id");
        long userid = Long.parseLong(scanner.nextLine());
        //User user = userService.getById(userid);

        System.out.println("Введите новые name, age, email через пробел");
        String[] userdata = scanner.nextLine().split(" ");
        if (userdata.length != 3) {
            System.out.println("Ошибка ввода данных");
            return;
        }
        try {
            String name = userdata[0].trim();
            int age = Integer.parseInt(userdata[1].trim());
            String email = userdata[2].trim();
            User user = new User();
            user.setId(userid);
            user.setName(name);
            user.setAge(age);
            user.setEmail(email);
            userService.update(user);
            System.out.println("Пользователь обновлен: " + user);
        } catch (NumberFormatException e) {
            System.out.println("Введите корректный возраст");
        }

    }

    public static void deleteUser(Scanner scanner, UserService userService) {
        System.out.println("Введите Id");
        int userid = Integer.parseInt(scanner.nextLine());
        userService.delete(userid);
        System.out.println("Пользователь удален");
    }
}
