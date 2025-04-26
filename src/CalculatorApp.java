import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase principal de la aplicación para la Calculadora de Geometría y Potencia.
 * Proporciona una interfaz basada en menú para que los usuarios realicen varios cálculos.
 * Este programa demuestra la aplicación de la Programación Orientada a Objetos (POO)
 * utilizando una interfaz para operaciones y clases separadas para cada tipo de cálculo.
 */
public class CalculatorApp {
    // Define PI como una constante pública estática final para usar en todas las clases relacionadas.
    public static final double PI = 3.1416;

    /**
     * Método principal para ejecutar la aplicación de la calculadora.
     * Presenta menús, maneja la entrada del usuario, realiza cálculos utilizando
     * el OperationFactory, y almacena/muestra los resultados.
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        // Inicializa un objeto Scanner para leer la entrada del usuario desde la consola.
        Scanner input = new Scanner(System.in);
        // Lista para almacenar los resultados de los cálculos realizados por el usuario.
        ArrayList<Double> results = new ArrayList<>();

        // Bucle principal de la aplicación: continúa hasta que el usuario elige salir.
        while (true) {
            // Muestra el menú de figuras y obtiene la elección del usuario.
            int shapeChoice = askShapeMenu(input);
            // Sale del bucle si el usuario selecciona la opción 0 (Salir).
            if (shapeChoice == 0) break;

            // Muestra el menú de operaciones y obtiene la elección del usuario.
            int operationChoice = askOperationMenu(input);
            // Continúa a la siguiente iteración del bucle si el usuario selecciona la opción 0 (Volver).
            if (operationChoice == 0) continue;

            try {
                // Utiliza la fábrica para crear el objeto de operación apropiado basado en las elecciones del usuario.
                Operation operation = OperationFactory.create(shapeChoice, operationChoice, input);
                // Ejecuta el cálculo y almacena el resultado.
                double result = operation.calculate();
                results.add(result);
                // Muestra el resultado al usuario.
                System.out.println("Resultado: " + result);
            } catch (InputMismatchException e) {
                // Captura y maneja tipos de entrada inválidos (ej. entrada no numérica cuando se espera un número).
                System.out.println("Entrada inválida. Intenta de nuevo.");
                input.nextLine(); // Consume la entrada inválida para evitar un bucle infinito.
            } catch (IllegalArgumentException e) {
                // Captura y maneja argumentos inválidos, como elecciones de figura u operación desconocidas.
                System.out.println(e.getMessage());
                input.nextLine(); // Consume cualquier entrada restante en la línea.
            }
        }

        // Después de que el bucle principal finaliza (el usuario sale), imprime todos los resultados almacenados.
        System.out.println("Resultados almacenados:");
        for (double value : results) {
            System.out.println(value);
        }

        input.close(); // Cierra el recurso Scanner para prevenir fugas de recursos.
    }

    /**
     * Muestra el menú de selección de figuras al usuario.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     * @return El entero que representa la figura seleccionada por el usuario (1-5) o la opción de salida (0).
     */
    private static int askShapeMenu(Scanner input) {
        System.out.println("\nElige una figura:");
        System.out.println("1. Círculo");
        System.out.println("2. Cuadrado");
        System.out.println("3. Triángulo");
        System.out.println("4. Rectángulo");
        System.out.println("5. Pentágono");
        System.out.println("0. Salir");
        // Lee y valida la opción del menú del usuario, asegurando que esté dentro del rango válido [0, 5].
        return readMenuOption(input, 5);
    }

    /**
     * Muestra el menú de selección de operaciones al usuario.
     * Incluye opciones para Área, Perímetro y cálculo de Potencia.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     * @return El entero que representa la operación seleccionada por el usuario (1-3) o la opción de volver (0).
     */
    private static int askOperationMenu(Scanner input) {
        System.out.println("Elige una operación:");
        System.out.println("1. Área");
        System.out.println("2. Perímetro");
        System.out.println("3. Potencia");
        System.out.println("0. Volver");
        // Lee y valida la opción del menú del usuario, asegurando que esté dentro del rango válido [0, 3].
        return readMenuOption(input, 3);
    }

    /**
     * Lee una entrada entera del usuario para una opción de menú y la valida
     * contra un valor máximo permitido. Repite la solicitud hasta que se recibe una entrada válida.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     * @param max El valor entero máximo válido para la opción del menú.
     * @return La opción de menú entera validada.
     */
    private static int readMenuOption(Scanner input, int max) {
        int option;
        // Bucle infinito hasta que se ingresa una opción entera válida dentro del rango [0, max].
        while (true) {
            System.out.print("Opción: ");
            try {
                // Lee la línea completa de entrada, elimina los espacios en blanco y parsea como un entero.
                option = Integer.parseInt(input.nextLine().trim());
                // Verifica si el entero parseado está dentro del rango aceptable.
                if (option >= 0 && option <= max)
                    return option; // Retorna la opción válida.
                System.out.println("Opción fuera de rango."); // Informa al usuario si la opción está fuera de rango.
            } catch (NumberFormatException e) {
                // Captura y maneja casos donde la entrada no puede ser parseada como un entero.
                System.out.println("Por favor ingresa un número válido.");
            }
        }
    }

    /**
     * Lee un valor double positivo de la entrada del usuario.
     * El bucle continúa hasta que se ingresa un double válido mayor que 0.
     * Este método es público estático para que pueda ser accedido por las implementaciones de Operation.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     * @return El valor double positivo validado.
     */
    public static double readPositiveDouble(Scanner input) {
        double value;
        // Bucle infinito hasta que se proporcione un double positivo válido.
        while (true) {
            System.out.print("Valor: "); // Solicita la entrada al usuario.
            try {
                // Lee la línea completa de entrada, elimina los espacios en blanco y parsea como un double.
                value = Double.parseDouble(input.nextLine().trim());
                // Verifica si el valor double parseado es positivo.
                if (value > 0)
                    return value; // Retorna el valor positivo válido.
                System.out.println("El valor debe ser mayor que 0."); // Informa al usuario si el valor no es positivo.
            } catch (NumberFormatException e) {
                // Captura y maneja casos donde la entrada no puede ser parseada como un double.
                System.out.println("Entrada inválida. Ingresa un número válido.");
            }
        }
    }

    /**
     * Lee un valor entero no negativo de la entrada del usuario.
     * El bucle continúa hasta que se ingresa un entero válido mayor o igual a 0.
     * Este método es público estático para que pueda ser accedido por las implementaciones de Operation.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     * @return El valor entero no negativo validado.
     */
    public static int readPositiveInt(Scanner input) {
        int value;
        // Bucle infinito hasta que se proporcione un entero no negativo válido.
        while (true) {
            System.out.print("Valor: "); // Solicita la entrada al usuario.
            try {
                // Lee la línea completa de entrada, elimina los espacios en blanco y parsea como un entero.
                value = Integer.parseInt(input.nextLine().trim());
                // Verifica si el valor entero parseado es no negativo.
                if (value >= 0) return value; // Retorna el valor no negativo válido.
                System.out.println("Debe ser 0 o mayor."); // Informa al usuario si el valor es negativo.
            } catch (NumberFormatException e) {
                // Captura y maneja casos donde la entrada no puede ser parseada como un entero.
                System.out.println("Número inválido.");
            }
        }
    }
}

/**
 * Interfaz para todas las operaciones de cálculo.
 * Define un único método para realizar el cálculo.
 * Promueve un diseño flexible permitiendo que diferentes clases implementen esta operación.
 */
interface Operation {
    /**
     * Realiza el cálculo específico para la clase que implementa la interfaz.
     * @return El resultado del cálculo.
     */
    double calculate();
}

/**
 * Clase de fábrica responsable de crear instancias de diferentes implementaciones de Operation
 * basándose en las elecciones de figura y operación del usuario.
 * Sigue el patrón Factory para desacoplar la creación de objetos del código cliente.
 */
class OperationFactory {
    /**
     * Crea y retorna un objeto Operation apropiado basado en la figura y operación seleccionadas.
     * @param shapeChoice El entero que representa la figura elegida (1-5).
     * @param operationChoice El entero que representa la operación elegida (1 para Área, 2 para Perímetro, 3 para Potencia).
     * @param input El objeto Scanner utilizado para leer las dimensiones necesarias para la operación.
     * @return Una instancia de una clase que implementa la interfaz Operation.
     * @throws IllegalArgumentException si la figura elegida no es reconocida (no entre 1 y 5 cuando la operación no es Potencia).
     */
    public static Operation create(int shapeChoice, int operationChoice, Scanner input) {
        // Si la operación es Potencia (3), crea y retorna una instancia de PowerOperation.
        if (operationChoice == 3) {
            return new PowerOperation(input);
        }
        // De lo contrario, utiliza un switch statement para crear la instancia de operación geométrica apropiada.
        return switch (shapeChoice) {
            case 1 -> operationChoice == 1 ? new CircleArea(input) : new CirclePerimeter(input);
            case 2 -> operationChoice == 1 ? new SquareArea(input) : new SquarePerimeter(input);
            case 3 -> operationChoice == 1 ? new TriangleArea(input) : new TrianglePerimeter(input);
            case 4 -> operationChoice == 1 ? new RectangleArea(input) : new RectanglePerimeter(input);
            case 5 -> operationChoice == 1 ? new PentagonArea(input) : new PentagonPerimeter(input);
            // Si la figura elegida no es reconocida para operaciones geométricas, lanza una excepción.
            default -> throw new IllegalArgumentException("Figura desconocida.");
        };
    }
}

/**
 * Representa la operación de cálculo de Potencia.
 * Calcula el resultado de una base elevada a un exponente.
 * Implementa la interfaz Operation.
 */
class PowerOperation implements Operation {
    private final double base;
    private final int exponent;

    /**
     * Construye un objeto PowerOperation, solicitando al usuario la base y el exponente.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     */
    public PowerOperation(Scanner input) {
        System.out.println("Base:");
        // Lee y valida la base utilizando el método auxiliar de CalculatorApp.
        this.base = CalculatorApp.readPositiveDouble(input);
        System.out.println("Exponente (entero):");
        // Lee y valida el exponente utilizando el método auxiliar de CalculatorApp.
        this.exponent = CalculatorApp.readPositiveInt(input);
    }

    /**
     * Calcula la potencia de la base elevada al exponente utilizando recursividad.
     * @return El resultado del cálculo de potencia.
     */
    @Override
    public double calculate() {
        return powerRecursive(base, exponent);
    }

    /**
     * Método auxiliar recursivo para calcular la potencia.
     * @param base La base.
     * @param exponent El exponente (entero no negativo).
     * @return El resultado de la base elevada a la potencia del exponente.
     */
    private double powerRecursive(double base, int exponent) {
        // Caso base: cualquier número elevado a la potencia de 0 es 1.
        if (exponent == 0)
            return 1;
        // Paso recursivo: multiplica la base por el resultado del cálculo de potencia para exponente - 1.
        return base * powerRecursive(base, exponent - 1);
    }
}

/**
 * Representa el cálculo del Área para un Círculo.
 * Implementa la interfaz Operation.
 */
class CircleArea implements Operation {
    private final double radius;

    /**
     * Construye un objeto CircleArea, solicitando al usuario el radio.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     */
    public CircleArea(Scanner input) {
        System.out.print("Radio: ");
        // Lee y valida el radio utilizando el método auxiliar de CalculatorApp.
        this.radius = CalculatorApp.readPositiveDouble(input);
    }

    /**
     * Calcula el área del círculo.
     * @return El área calculada.
     */
    @Override
    public double calculate() {
        return CalculatorApp.PI * radius * radius; // Utiliza la constante PI de CalculatorApp.
    }
}

/**
 * Representa el cálculo del Perímetro para un Círculo.
 * Implementa la interfaz Operation.
 */
class CirclePerimeter implements Operation {
    private final double radius;

    /**
     * Construye un objeto CirclePerimeter, solicitando al usuario el radio.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     */
    public CirclePerimeter(Scanner input) {
        System.out.println("Radio:");
        // Lee y valida el radio utilizando el método auxiliar de CalculatorApp.
        this.radius = CalculatorApp.readPositiveDouble(input);
    }

    /**
     * Calcula el perímetro del círculo.
     * @return El perímetro calculado.
     */
    @Override
    public double calculate() {
        return 2 * CalculatorApp.PI * radius; // Utiliza la constante PI de CalculatorApp.
    }
}

/**
 * Representa el cálculo del Área para un Cuadrado.
 * Implementa la interfaz Operation.
 */
class SquareArea implements Operation {
    private final double side;

    /**
     * Construye un objeto SquareArea, solicitando al usuario la longitud del lado.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     */
    public SquareArea(Scanner input) {
        System.out.println("Lado:");
        // Lee y valida la longitud del lado utilizando el método auxiliar de CalculatorApp.
        this.side = CalculatorApp.readPositiveDouble(input);
    }

    /**
     * Calcula el área del cuadrado.
     * @return El área calculada.
     */
    @Override
    public double calculate() {
        return side * side;
    }
}

/**
 * Representa el cálculo del Perímetro para un Cuadrado.
 * Implementa la interfaz Operation.
 */
class SquarePerimeter implements Operation {
    private final double side;

    /**
     * Construye un objeto SquarePerimeter, solicitando al usuario la longitud del lado.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     */
    public SquarePerimeter(Scanner input) {
        System.out.println("Lado:");
        // Lee y valida la longitud del lado utilizando el método auxiliar de CalculatorApp.
        this.side = CalculatorApp.readPositiveDouble(input);
    }

    /**
     * Calcula el perímetro del cuadrado.
     * @return El perímetro calculado.
     */
    @Override
    public double calculate() {
        return 4 * side;
    }
}

/**
 * Representa el cálculo del Área para un Triángulo.
 * Implementa la interfaz Operation.
 */
class TriangleArea implements Operation {
    private final double base, height;

    /**
     * Construye un objeto TriangleArea, solicitando al usuario la base y la altura.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     */
    public TriangleArea(Scanner input) {
        System.out.println("Base:");
        // Lee y valida la base utilizando el método auxiliar de CalculatorApp.
        this.base = CalculatorApp.readPositiveDouble(input);
        System.out.println("Altura:");
        // Lee y valida la altura utilizando el método auxiliar de CalculatorApp.
        this.height = CalculatorApp.readPositiveDouble(input);
    }

    /**
     * Calcula el área del triángulo.
     * @return El área calculada.
     */
    @Override
    public double calculate() {
        return 0.5 * base * height;
    }
}

/**
 * Representa el cálculo del Perímetro para un Triángulo.
 * Implementa la interfaz Operation.
 */
class TrianglePerimeter implements Operation {
    private final double a, b, c;

    /**
     * Construye un objeto TrianglePerimeter, solicitando al usuario las longitudes de los tres lados.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     */
    public TrianglePerimeter(Scanner input) {
        System.out.println("Lado 1:");
        // Lee y valida el lado a utilizando el método auxiliar de CalculatorApp.
        this.a = CalculatorApp.readPositiveDouble(input);
        System.out.println("Lado 2:");
        // Lee y valida el lado b utilizando el método auxiliar de CalculatorApp.
        this.b = CalculatorApp.readPositiveDouble(input);
        System.out.println("Lado 3:");
        // Lee y valida el lado c utilizando el método auxiliar de CalculatorApp.
        this.c = CalculatorApp.readPositiveDouble(input);
    }

    /**
     * Calcula el perímetro del triángulo.
     * @return El perímetro calculado.
     */
    @Override
    public double calculate() {
        return a + b + c;
    }
}

/**
 * Representa el cálculo del Área para un Rectángulo.
 * Implementa la interfaz Operation.
 */
class RectangleArea implements Operation {
    private final double base, height;

    /**
     * Construye un objeto RectangleArea, solicitando al usuario la base y la altura.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     */
    public RectangleArea(Scanner input) {
        System.out.println("Base:");
        // Lee y valida la base utilizando el método auxiliar de CalculatorApp.
        this.base = CalculatorApp.readPositiveDouble(input);
        System.out.println("Altura:");
        // Lee y valida la altura utilizando el método auxiliar de CalculatorApp.
        this.height = CalculatorApp.readPositiveDouble(input);
    }

    /**
     * Calcula el área del rectángulo.
     * @return El área calculada.
     */
    @Override
    public double calculate() {
        return base * height;
    }
}

/**
 * Representa el cálculo del Perímetro para un Rectángulo.
 * Implementa la interfaz Operation.
 */
class RectanglePerimeter implements Operation {
    private final double base, height;

    /**
     * Construye un objeto RectanglePerimeter, solicitando al usuario la base y la altura.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     */
    public RectanglePerimeter(Scanner input) {
        System.out.println("Base:");
        // Lee y valida la base utilizando el método auxiliar de CalculatorApp.
        this.base = CalculatorApp.readPositiveDouble(input);
        System.out.println("Altura:");
        // Lee y valida la altura utilizando el método auxiliar de CalculatorApp.
        this.height = CalculatorApp.readPositiveDouble(input);
    }

    /**
     * Calcula el perímetro del rectángulo.
     * @return El perímetro calculado.
     */
    @Override
    public double calculate() {
        return 2 * (base + height);
    }
}

/**
 * Representa el cálculo del Área para un Pentágono.
 * Implementa la interfaz Operation.
 */
class PentagonArea implements Operation {
    private final double side, apothem;

    /**
     * Construye un objeto PentagonArea, solicitando al usuario la longitud del lado y la apotema.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     */
    public PentagonArea(Scanner input) {
        System.out.println("Lado:");
        // Lee y valida la longitud del lado utilizando el método auxiliar de CalculatorApp.
        this.side = CalculatorApp.readPositiveDouble(input);
        System.out.println("Apotema:");
        // Lee y valida la apotema utilizando el método auxiliar de CalculatorApp.
        this.apothem = CalculatorApp.readPositiveDouble(input);
    }

    /**
     * Calcula el área del pentágono.
     * @return El área calculada.
     */
    @Override
    public double calculate() {
        return (5 * side * apothem) / 2;
    }
}

/**
 * Representa el cálculo del Perímetro para un Pentágono.
 * Implementa la interfaz Operation.
 */
class PentagonPerimeter implements Operation {
    private final double side;

    /**
     * Construye un objeto PentagonPerimeter, solicitando al usuario la longitud del lado.
     * @param input El objeto Scanner utilizado para leer la entrada del usuario.
     */
    public PentagonPerimeter(Scanner input) {
        System.out.println("Lado:");
        // Lee y valida la longitud del lado utilizando el método auxiliar de CalculatorApp.
        this.side = CalculatorApp.readPositiveDouble(input);
    }

    /**
     * Calcula el perímetro del pentágono.
     * @return El perímetro calculado.
     */
    @Override
    public double calculate() {
        return 5 * side;
    }
}