package org.marco.exceptions;

public class InvalidMenuEntryException extends RuntimeException {
    public InvalidMenuEntryException() {
        super("¡¡OJO!! Opcion del menu desconocida o incorrecta.");
    }
}
