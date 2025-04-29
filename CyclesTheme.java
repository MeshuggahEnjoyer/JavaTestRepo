package com.startjava.lesson_1.base;

public class CyclesTheme {
    public static void main(String[] args) {
        System.out.println("1.Подсчет суммы четных и нечетных чисел");
        int startRange = -10;
        int endRange = 21;
        int sumEvenNumbers = 0;
        int sumOddNumbers = 0;

        int counter = startRange;
        do {
            if (counter % 2 == 0) {
                sumEvenNumbers += counter;
            } else {
                sumOddNumbers += counter;
            }
            counter++;
        } while (counter <= endRange);

        System.out.printf("В отрезке [%d, %d] сумма четных чисел = %d, а нечетных = %d%n",
                startRange, endRange, sumEvenNumbers, sumOddNumbers);

        System.out.println("\n2.Вывод чисел в порядке убывания");
        int num1 = 5;
        int num2 = 10;
        int num3 = -1;

        int min = num1;
        if (num2 < min) {
            min = num2;
        } else if (num3 < min) {
            min = num3;
        }

        int max = num1;
        if (num2 > num1) {
            max = num2;
        } else if (num3 > max) {
            max = num3;
        }

        System.out.printf("min: %d, max: %d%n", min, max);
        for (int i = max - 1; i > min; i--) {
            System.out.print(i + " ");
        }

        System.out.println("\n\n3.Вывод реверсивного числа и суммы его цифр");
        int num4 = 1234;
        int sumOfDigits = 0;

        System.out.print("исходное число в обратном порядке: ");
        while (num4 > 0) {
            int currentDigit = num4 % 10;
            System.out.print(currentDigit);
            sumOfDigits += currentDigit;
            num4 /= 10;
        }
        System.out.printf("%nсумма цифр: %d%n", sumOfDigits);

        System.out.println("\n4.Вывод чисел в несколько строк");
        startRange = 1;
        endRange = 24;
        int currentPlaceOfNumber = 0;

        for (int i = startRange; i < endRange; i++) {
            if (i % 2 > 0) {
                System.out.printf("%5d", i);
                currentPlaceOfNumber++;
                if (currentPlaceOfNumber % 5 == 0) {
                    System.out.println();
                }
            }
        }

        while (currentPlaceOfNumber % 5 > 0) {
            System.out.printf("%5d", 0);
            currentPlaceOfNumber++;
        }

        System.out.println("\n\n5.Проверка количества двоек числа на четность/нечетность");
        int num5 = 3_242_592;
        int amountOfTwos = 0;

        int num5Copy = num5;
        while (num5Copy > 0) {
            if (num5Copy % 10 == 2) {
                amountOfTwos++;
            }
            num5Copy /= 10;
        }

        if (amountOfTwos % 2 == 0) {
            System.out.printf("В %d четное количество двоек - %d%n", num5, amountOfTwos);
        } else {
            System.out.printf("В %d нечетное количество двоек - %d%n", num5, amountOfTwos);
        }
        
        System.out.println("\n6.Отображение геометрических фигур");
        //Прямоугольник
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 11; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
        System.out.println();

        //Прямоугольный треугольник
        int currentRow = 0;
        int amountOfSpaces = 0;
        while (currentRow < 5) {
            amountOfSpaces = currentRow;

            while (amountOfSpaces < 5) {
                System.out.print("#");
                amountOfSpaces++;
            }

            currentRow++;
            System.out.println();
        }
        System.out.println();

        //Треугольник
        currentRow = 0;
        do {
            if (currentRow < 2) {
                amountOfSpaces = 4 - currentRow;
            } else {
                amountOfSpaces = currentRow;
            }

            do {
                System.out.print("$");
                amountOfSpaces++;
            } while (amountOfSpaces < 5);

            currentRow++;
            System.out.println();
        } while (currentRow < 5);

        System.out.println("\n7.Отображение ASCII-символов");
        System.out.printf("%-8s %-10s %-12s%n", "DECIMAL", "CHARACTER", "DESCRIPTION");

        for (int i = 33; i < 123; i++) {
            if (i >= 48 && i <= 96) {
                continue;
            }
            if ((i < 48 && i % 2 != 0) || (i > 96 && i % 2 == 0)) {
                System.out.printf("%4d %9c           %-25s%n", i, i, Character.getName(i));
            }
        }

        System.out.println("\n8.Проверка, является ли число палиндромом");
        int num6 = 1234321;
        int reversedNum6 = 0;

        //Получаем реверсивное число
        int num6Copy = num6;
        while (num6Copy > 0) {
            reversedNum6 = (reversedNum6 * 10) + num6Copy % 10;
            num6Copy /= 10;
        }

        if (num6 == reversedNum6) {
            System.out.printf("число %d является палиндромом%n", num6);
        } else {
            System.out.printf("число %d не является палиндромом%n", num6);
        }

        System.out.println("\n9.Проверка, является ли число счастливым");
        int num7 = 123222;
        int leftSum = 0;
        int rightSum = 0;

        int num7Copy = num7;
        for (int i = 0; i < 6; i++) {
            if (i < 3) {
                rightSum += num7Copy % 10;
            } else {
                leftSum += num7Copy % 10;
            }
            num7Copy /= 10;
        }

        String not = "не ";
        if (leftSum == rightSum) not = "";
        
        System.out.printf("Число %d %sявляется счастливым%n" +
                "Сумма цифр %d = %d, сумма цифр %d = %d%n", 
                num7, not, num7 / 1000, leftSum, num7 % 1000, rightSum);

        System.out.println("\n10.Отображение таблицы умножения Пифагора");
        System.out.printf("%n%23S%n", "таблица Пифагора");
        
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                if (i == 1 && j == 1) {
                    System.out.printf("    |");
                } else if (i == 1 && j == 9) {
                    System.out.printf("%3d%n", i * j);
                    System.out.printf("%S", "-----------------------------");
                } else if (j == 1) {
                    System.out.printf("%3d |", i * j);
                } else {
                    System.out.printf("%3d", i * j);
                }
            }
            System.out.println();
        }
    }
}