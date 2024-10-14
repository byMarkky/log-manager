package org.marco.exceptions;

public class InvalidFileException extends RuntimeException {
    public InvalidFileException() {
        super("¡¡OJO!! El fichero especificado no existe o es inaccesible.");
    }

    // Override method just for debug
    public InvalidFileException(Exception e) {
        super("¡¡OJO!! El fichero especificado no existe o es inaccesible. Exception: " + e);
    }
}
