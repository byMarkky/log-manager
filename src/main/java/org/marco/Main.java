package org.marco;

import org.marco.exceptions.InvalidMenuEntryException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);
        LogManager logMgr = new LogManager();
        int menuOpt;

        do {
            printMenu();
            menuOpt = getMenuOption(reader);

            optionMgr(menuOpt, reader, logMgr);

        } while (menuOpt != 6);
    }

    private static void optionMgr(int option, Scanner reader, LogManager logMgr) {
        switch (option) {
            case 1:
                System.out.print("Nombre del fichero: ");
                String file = reader.next();
                logMgr.loadLogs(file);
                break;
            case 2:
                LogLevel filter = selectFilter(reader);
                logMgr.filter(filter);
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
            case 7:
                logMgr.showLogs();  // Debug only
                break;
            default:
                System.out.println("Opcion desconocida.");
                break;
        }
    }

    // TODO Optimize the error management of this function
    private static LogLevel selectFilter(Scanner reader) {
        System.out.println("Puede filtrar por:");
        LogLevel filter = null;
        showFilterMenu();
        int opt;
        try {
            opt = reader.nextInt();

            switch (opt) {
                case 1:
                    filter = LogLevel.values()[0];
                    break;
                case 2:
                    filter = LogLevel.values()[1];
                    break;
                case 3:
                    filter = LogLevel.values()[2];
                    break;
                case 4:
                    break;  // Return null
                default:
                    System.out.println("Esa opcion no existe.");
                    break;
            }

        } catch (InputMismatchException e) {
            throw new InvalidMenuEntryException();
        }
        return filter;
    }

    private static void showFilterMenu() {
        int i = 1;
        for (LogLevel level : LogLevel.values()) {
            System.out.format("%d. %s\n", i++, level);
        }
        System.out.format("%d. Ninguno\n", i);
        System.out.print("> ");
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
            throw new InvalidMenuEntryException();
        }

        return opt;
    }

}