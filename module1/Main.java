package module1;

public class Main {
    public static void main(String[] args) {
        MyHashMap<Integer, String> myHashMap = new MyHashMap<>();

        myHashMap.put(1, "одын");
        myHashMap.put(2, "два");
        myHashMap.put(3, "три");
        myHashMap.put(4, "четыре");
        myHashMap.put(5, "пять");
        myHashMap.put(6, "шесть");
        myHashMap.put(7, "семь");
        myHashMap.put(8, "восемь");
        myHashMap.put(9, "девять");
        myHashMap.put(10, "десять");
        myHashMap.put(11, "одиннадцать");

        System.out.println(myHashMap.get(1));
        myHashMap.remove(1);
        myHashMap.forEach(System.out::println);
        System.out.println();
        myHashMap.put(1, "один");
        myHashMap.forEach(System.out::println);
        System.out.println("Size: " + myHashMap.size);
    }
}
