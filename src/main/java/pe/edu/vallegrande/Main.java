package pe.edu.vallegrande;

public class Main {
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        System.out.println("=== Calculadora Simple ===");
        System.out.println("5 + 3 = " + calc.add(5, 3));
        System.out.println("10 - 4 = " + calc.subtract(10, 4));
        System.out.println("6 * 7 = " + calc.multiply(6, 7));
        System.out.println("20 / 4 = " + calc.divide(20, 4));
    }
}