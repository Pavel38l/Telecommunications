package ru.vsu.telecom.data.loader;

/**
 * @author Pavel Burdyug
 * Error when loading data from csv file
 */
public class LoadException extends RuntimeException {
    public LoadException() {
    }

    public LoadException(String message) {
        super(message);
    }

    public LoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoadException(Throwable cause) {
        super(cause);
    }
}
