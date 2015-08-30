package ua.lviv.odyldzhon.contacts.exception;

public class PersistentException extends Exception {

    private static final long serialVersionUID = 1L;

    public PersistentException() {
        super();
    }

    public PersistentException(String description, Throwable exception) {
        super(description, exception);
    }

    public PersistentException(String description) {
        super(description);
    }

}
