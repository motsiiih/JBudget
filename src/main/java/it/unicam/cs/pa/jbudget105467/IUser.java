package it.unicam.cs.pa.jbudget105467;

/**
 * Defines a user object composed of username and password.
 */
public interface IUser {

    /**
     * @return the username
     */
    String getUsername();

    /**
     * Sets the username.
     *
     * @param username the username to set
     */
    void setUsername(String username);

    /**
     * @return the password
     */
    String getPassword();

    /**
     * Sets the password.
     *
     * @param password the password to set
     */
    void setPassword(String password);
}
