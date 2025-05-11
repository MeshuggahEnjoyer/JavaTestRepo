    public class Calculator {
        public static void main(String[] args) {
            int a = 9;
            int b = 2;
            double result = 1;
            char operation = '/';

            if (operation == '+') {
                result = a + b;
            } else if (operation == '-') {
                result = a - b;
            } else if (operation == '*') {
                result = a * b;
            } else if (operation == '/') {
                result = (float) a / b;
            } else if (operation == '^') {
                for (int i = 1; i <= b; i++) {
                    result *= a;
                }
            } else if (operation == '%') {
                result = a % b;
            }

            System.out.printf("%d %c %d = %f\n", a, operation, b, result);
        }
    }