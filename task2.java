package module2.task2;

import org.w3c.dom.ls.LSOutput;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class task2 {
    public static void main(String[] args) {

        List<Book> booksOfSt1 = Arrays.asList(new Book("Гарри Поттер и философский камень", 1996, 647),
                new Book("Гарри Поттер и Тайная комната", 1997, 500),
                new Book("Гарри Поттер и узник Азкабана", 1998, 550),
                new Book("Гарри Поттер и Кубок огня", 2000, 450),
                new Book("Гарри Поттер и Орден Феникса", 2005, 357));
        Student st1 = new Student(booksOfSt1, "Петров");

        List<Book> booksOfSt2 = Arrays.asList(new Book("Чапаев и Пустота", 1996, 647),
                new Book("Generation П", 1997, 500),
                new Book("Хохот шамана", 2000, 357),
                new Book("Гарри Поттер и Принц-полукровка", 1999, 450),
                new Book("Гарри Поттер и Дары Смерти", 2002, 659));
        Student st2 = new Student(booksOfSt2, "Сидоров");

        List<Book> booksOfSt3 = Arrays.asList(new Book("Последнее желание", 1996, 647),
                new Book("Меч предназначения", 1997, 500),
                new Book("Кровь эльфов", 1998, 550),
                new Book("Час презрения", 2003, 357),
                new Book("Крещение огнём", 2002, 450));
        Student st3 = new Student(booksOfSt3, "Иванов");

        List<Student> students = new ArrayList<>(Arrays.asList(st1, st2, st3));

        students.stream().forEach( s -> {
            System.out.println(s);
            s.books.stream()
                    .distinct()
                    .filter(b -> b.releaseYear >= 2000)
                    .sorted(Comparator.comparingInt(b -> b.numOfPages))
                    .limit(3)
                    .map(b -> b.releaseYear)
                    .findAny().ifPresentOrElse(releaseYear -> System.out.println(releaseYear), () -> System.out.println("Книг не найдено"));
        });

    }

    public static class Student {
        List<Book> books;
        String name;

        public Student(List<Book> books, String name) {
            this.books = books;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Студент " + name;
        }
    }

    public static class Book {
        String name;
        int numOfPages;
        public int releaseYear;

        public Book(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Book{" +
                    "name='" + name + '\'' +
                    ", numOfPages=" + numOfPages +
                    ", releaseYear=" + releaseYear +
                    '}';
        }

        public Book(String name, int releaseYear, int numOfPages) {
            this.name = name;
            this.numOfPages = numOfPages;
            this.releaseYear = releaseYear;


        }

        public boolean equals(Book toCompare) {
            return this.name.toLowerCase() == toCompare.name.toLowerCase();
        }
    }
}
