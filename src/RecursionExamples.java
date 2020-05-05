import javax.swing.*;

public class RecursionExamples {
    public static void main(String[] args) throws Exception {
        String factorialUserInput = JOptionPane.showInputDialog(null, "Input a positive number to discover its factorial:");
        int factorialResult = factorial(Integer.parseInt(factorialUserInput));
        System.out.println("Factorial result is: " + factorialResult);

        String fibonacciUserInput = JOptionPane.showInputDialog(null, "Input a positive number to know its fibonacci value:");
        int fibonacciResult = fibonacci(Integer.parseInt(fibonacciUserInput));
        System.out.println("Fibonacci result is: " + fibonacciResult);
    }

    static int factorial(int number) throws Exception {
        if (number < 0) {
            throw new Exception("The input must be a positive number.");
        } else if (number == 0) {
            return 1;
        }
        return number * factorial(number - 1);
    }

    static int fibonacci(int number) throws Exception {
        if (number < 0) {
            throw new Exception("The input must be a positive number.");
        } else if (number == 0) {
            return 1;
        } else if (number == 1) {
            return 1;
        }
        return fibonacci(number - 1) + fibonacci(number - 2);
    }
}
