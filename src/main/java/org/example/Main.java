package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);
        int menuOpt;

        do {
            printMenu();
            menuOpt = getMenuOption(reader);

            optionMgr(menuOpt);

        } while (menuOpt != 6);
    }

    private static void optionMgr(int option) {
        switch (option) {
            case 1:
                // TODO LOAD FILE
                break;
            case 2:
                // TODO FILTER FILE
                break;
            case 3:
                // TODO EXPORT TO XML
                break;
            case 4:
                // TODO EXPORT TO JSON
                break;
            case 5:
                // TODO RESET FILTER
                break;
            case 6:
                System.out.println("Saliendo...");
                break;
            default:
                System.out.println("Opcion desconocida.");
                break;
        }
    }

    private static void printMenu() {

        System.out.println("1. Cargar archivo");
        System.out.println("2. Filtrar archivo");
        System.out.println("3. Exportar filtrado a XML");
        System.out.println("4. Exportar filtrado a JSON");
        System.out.println("5. Resetear filtrado");
        System.out.println("6. Salir");
        System.out.print("> ");

    }

    private static int getMenuOption(Scanner reader) {
        int opt = 0;

        try {
            opt = reader.nextInt();
        } catch (InputMismatchException e) {
            System.err.println("[ERROR] Opcion del menu incorrecta.");
        }

        return opt;
    }

}