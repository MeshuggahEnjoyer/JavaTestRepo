package module2;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserDAO userDAO = new UserDaoHibernate();

        String userchoice;
        do {
            System.out.println("Выберите действие: C|R|U|D, E - exit");
            userchoice = scanner.nextLine().toLowerCase(Locale.ROOT);
            switch (userchoice) {
                case "c":
                    createUser(scanner, userDAO);
                    break;
                case "r":
                    getUserById(scanner, userDAO);
                    break;
                case "u":
                    updateUser(scanner, userDAO);
                    break;
                case "d":
                    deleteUser(scanner, userDAO);
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

    public static void createUser(Scanner scanner, UserDAO userDAO) {
        System.out.println("Введите name, age, email через запятую");
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
            userDAO.create(user);
            System.out.println("Пользователь создан: " + user);
        } catch (NumberFormatException e) {
            System.out.println("Возраст введён неверно");
        }
    }

    public static void getUserById(Scanner scanner, UserDAO userDAO) {
        System.out.println("Введите Id");
        int userid = Integer.parseInt(scanner.nextLine());
        System.out.println(userDAO.getById(userid));
    }

    public static void updateUser(Scanner scanner, UserDAO userDAO) {
        System.out.println("Введите Id");
        int userid = Integer.parseInt(scanner.nextLine());
        User user = userDAO.getById(userid);
        if (user == null) {
            System.out.println("Пользователь не найден");
            return;
        }

        System.out.println("Введите новые name, age, email через запятую");
        String[] userdata = scanner.nextLine().split(", ");
        if (userdata.length != 3) {
            System.out.println("Ошибка ввода данных");
            return;
        }
        try {
            String name = userdata[0].trim();
            int age = Integer.parseInt(userdata[1].trim());
            String email = userdata[2].trim();
            user.setName(name);
            user.setAge(age);
            user.setEmail(email);
            userDAO.update(user);
            System.out.println("Пользователь обновлен: " + user);
        } catch (NumberFormatException e) {
            System.out.println("Возраст введён неверно");
        }

    }

    public static void deleteUser(Scanner scanner, UserDAO userDAO) {
        System.out.println("Введите Id");
        int userid = Integer.parseInt(scanner.nextLine());
        if (userDAO.getById(userid) == null) {
            System.out.println("Пользователь не найден");
            return;
        }
        userDAO.delete(userid);
        System.out.println("Пользователь удален");
    }
}
