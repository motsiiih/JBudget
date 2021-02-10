package it.unicam.cs.pa.jbudget105467;

/**
 * Exception thrown when an object is being created and there
 * is an instance of that object with the same identifier.
 */
public class AlreadyInUseException extends Exception {

        public AlreadyInUseException() {
            super("A value you entered already identifies another instanced object");
        }
}
