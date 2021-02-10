/**
 * Copyright (c) 2020 Luca Mozzoni
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package it.unicam.cs.pa.jbudget105467;

import it.unicam.cs.pa.jbudget105467.utilities.JsonPersistenceManager;
import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

/**
 * Controller class that manages to have the associated
 * {@code ILedger} instance to behave correctly.
 */
public class LedgerController {

    private static LedgerController ledgerController = null;

    /**
     * The associated instance of {@code ILedger}.
     */
    private ILedger ledger;

    /**
     * Instance of {@link Logger} used to keep track
     * of the user actions.
     */
    private Logger logger = Logger.getLogger("it.unicam.cs.pa.jbudget105467");

    /**
     * An instance of {@code File} that refers to a .json file
     */
    private File file = new File("ledger.json");

    /**
     * An instance of {@code JsonPersistenceManager} used to update
     * the data into {@link #file} after every action done.
     */
    private JsonPersistenceManager jsonPersistenceManager = new JsonPersistenceManager();

    /**
     * Constructs a {@code ledgerController} instance.
     */
    private LedgerController() {
    }

    /**
     * Returns the unique instance of this class.
     * If it is null, it creates one.
     *
     * @return the instance of {@code ledgerController}
     */
    public static LedgerController getInstance(){
        if (ledgerController == null)
            ledgerController = new LedgerController();
        return ledgerController;
    }

    /**
     * Sets the {@link #ledger} param.
     *
     * @param ledger the associated ledger
     */
    public void setLedger(ILedger ledger) {
        this.ledger = ledger;
    }

    /**
     * @return {@link #ledger}
     */
    public ILedger getLedger() {
        return ledger;
    }

    /**
     * Adds an account to {@link #ledger} and updates {@link #file}.
     *
     * @param name account's name
     * @param description account's description
     * @param opening account's opening balance
     * @param type account's type
     * @return the account created
     * @throws AlreadyInUseException when the user is trying to
     * create an account using a name already used for another
     * {@code IAccount} instance
     */
    public IAccount addAccount(String name, String description, double opening, AccountType type) throws AlreadyInUseException {
        if (this.getAccounts().stream().anyMatch(account -> account.getName().equals(name)))
            throw new AlreadyInUseException();
        logger.info("Adding account...");
        IAccount newAccount = ledger.addAccount(name, description, opening, type);
        jsonPersistenceManager.save(ledger, file);
        logger.info("Account added!");
        return newAccount;
    }

    /**
     * For each transaction of {@link ILedger#getTransactions()}, removes
     * the movements associated to the input account and, if the transaction's
     * movement list is empty at the end of the process, the transaction gets
     * deleted. Then, the account is removed and {@link #file} updated.
     *
     * @param account the account to remove
     * @throws IllegalArgumentException when the user is trying to delete a
     * non-existing account
     */
    public void removeAccount(IAccount account) throws IllegalArgumentException{
        if (!ledger.getAccounts().contains(account))
            throw new IllegalArgumentException("The account you are trying to delete does not exist!");
        logger.info("Removing account...");
        for (ITransaction t : ledger.getTransactions()){
            t.getMovements().removeIf(movement -> movement.getAccount().equals(account));
            if (t.getMovements().isEmpty())
                ledger.removeTransaction(t);
        }
        ledger.removeAccount(account);
        logger.info("Account removed!");
        jsonPersistenceManager.save(ledger, file);
    }

    /**
     * @return the account list of {@link #ledger}
     */
    public List<IAccount> getAccounts() {
        logger.info("Reading account list...");
        return ledger.getAccounts();
    }

    /**
     * Returns the movements associated to the input account excluding
     * movements that are part of scheduled transactions.
     *
     * @param account the account associated to the movements
     *                the user is looking for
     * @return the movements associated to the input account
     */
    public List<IMovement> getAccountMovements(IAccount account){
        if (!ledger.getAccounts().contains(account))
            throw new IllegalArgumentException("No selected account.");
        logger.info("Reading account's movement list...");
        return account.getMovements(movement -> !movement.getTransaction().isScheduled());
    }

    /**
     * @param selectedAccount the account the user
     *                        needs the description of
     * @return the input account's description
     */
    public String getAccountDescription(IAccount selectedAccount) {
        logger.info("Reading account's description...");
        return selectedAccount.getDescription();
    }

    /**
     * @param text account's new description
     * @param account account the user wants to change
     *                the description of
     */
    public void changeAccountDescription(String text, IAccount account) {
        logger.info("Changing account's description...");
        account.setDescription(text);
        jsonPersistenceManager.save(ledger, file);
        logger.info("Account's description changed!");
    }

    /**
     * Creates a new transaction and passes it to
     * {@link #addMovementToTransaction(MovementType, double, IAccount, ITransaction)}
     * in order to have a transaction which already contains a movement.
     * The transaction gets added to {@link #ledger} and {@link #file} updated.
     *
     * @param date transaction's date
     * @param type movement's type
     * @param amount movement's amount
     * @param account movement's associated account
     * @return the new transaction created
     */
    public ITransaction addTransaction(LocalDate date, MovementType type, double amount, IAccount account) {
        logger.info("Adding transaction...");
        ITransaction transaction = new Transaction(date);
        addMovementToTransaction(type, amount, account, transaction);
        ledger.addTransaction(transaction);
        jsonPersistenceManager.save(ledger, file);
        logger.info("Transaction added!");
        return transaction;
    }

    /**
     * Creates a new movement and associates a transaction to it. For each tag
     * associated to the transaction the actual amount of money spent gets updated
     * and, at the end, {@link #file} gets updated too.
     *
     * @param type movement's type
     * @param amount movement's amount
     * @param account movement's associated account
     * @param transaction movement's associated transaction
     * @return the new movement created
     */
    public IMovement addMovementToTransaction(MovementType type, double amount, IAccount account, ITransaction transaction){
        logger.info("Adding movement to transaction...");
        IMovement movement = new Movement(type, amount, account, transaction);
        transaction.addMovement(movement);
        for (ITag tag : transaction.getTags())
            tag.calculateActual(ledger);
        jsonPersistenceManager.save(ledger, file);
        logger.info("Movement added!");
        return movement;
    }

    /**
     * The input movement gets removed from the transaction and, for each tag,
     * the actual amount of money spent gets updated. At the end of the process,
     * if the transaction has no movements left, it gets deleted.
     * {@link #file} gets updated.
     *
     * @param movement the movement to remove
     * @param transaction the transaction that the movement
     *                    to remove is part of
     */
    public void removeMovementFromTransaction(IMovement movement, ITransaction transaction){
        logger.info("Removing movement from transaction...");
        transaction.removeMovement(movement);
        for (ITag tag : transaction.getTags())
            tag.calculateActual(ledger);
        if (transaction.getMovements().isEmpty())
            ledger.removeTransaction(transaction);
        jsonPersistenceManager.save(ledger, file);
        logger.info("Movement removed from transaction!");
    }

    /**
     * The input transaction gets accredited and, for each of its tags,
     * the actual value spent gets updated as well as {@link #file}.
     *
     * @param transactionToAccredit the transaction to accredit
     */
    public void accreditScheduledTransaction(ITransaction transactionToAccredit) {
        logger.info("Adding scheduled transaction to transaction list...");
        transactionToAccredit.accredit();
        for (ITag tag : transactionToAccredit.getTags())
            tag.calculateActual(ledger);
        jsonPersistenceManager.save(ledger, file);
        logger.info("Scheduled transaction added!");
    }

    /**
     * Performs a filtered research determined by the input predicate.
     *
     * @param p the filter predicate
     * @return a list of the transactions matching the predicate
     */
    public List<ITransaction> getTransactions(Predicate<ITransaction> p){
        logger.info("Reading transaction filtered list...");
        if (ledger.getTransactions(p).isEmpty())
            logger.info("No transaction found matching the input predicate.");
        return ledger.getTransactions(p);
    }

    /**
     * Given an input transaction, finds its associated movements.
     *
     * @param transaction the transaction associated to the
     *                    movements that the user needs
     * @return the movements associated to the input transaction
     */
    public List<IMovement> getTransactionMovements(ITransaction transaction){
        if (!ledger.getTransactions().contains(transaction))
            throw new IllegalArgumentException("No selected transaction.");
        logger.info("Reading transaction's movement list...");
        return transaction.getMovements();
    }

    /**
     * Adds a tag to a transaction and updates the tag's actual amount of
     * money spent. Then, {@link #file} gets updated.
     *
     * @param transaction the transaction to add the tag to
     * @param tag the tag to add
     */
    public void addTagToTransaction(ITransaction transaction, ITag tag){
        logger.info("Adding tag to transaction...");
        transaction.addTag(tag);
        tag.calculateActual(ledger);
        jsonPersistenceManager.save(ledger, file);
        logger.info("Tag added to transaction!");
    }

    /**
     * Removes a tag from a transaction and updates the tag's actual amount of
     * money spent. Then, {@link #file} gets updated.
     *
     * @param transaction the transaction to remove the tag from
     * @param tag the tag to remove
     */
    public void removeTagFromTransaction(ITransaction transaction, ITag tag){
        logger.info("Removing tag to transaction...");
        transaction.removeTag(tag);
        tag.calculateActual(ledger);
        jsonPersistenceManager.save(ledger, file);
        logger.info("Tag removed from transaction!");
    }

    /**
     * Getter method of the input transaction tag list.
     *
     * @param selectedTransaction the transaction the user needs
     *                            the tags of
     * @return the tags associated to the input transaction
     */
    public List<ITag> getTransactionTags(ITransaction selectedTransaction) {
        return selectedTransaction.getTags();
    }

    /**
     * Getter method of the input transaction description.
     *
     * @param selectedTransaction the transaction the user needs
     *                            the description of
     * @return the description associated to the input transaction
     */
    public String getTransactionDescription(ITransaction selectedTransaction) {
        return selectedTransaction.getDescription();
    }

    /**
     * Setter method of the input transaction description and updates
     * {@link #file}.
     *
     * @param transaction the transaction the user needs
     *                    to change the description of
     * @param text the new description
     */
    public void changeTransactionDescription(String text, ITransaction transaction) {
        logger.info("Changing description...");
        transaction.setDescription(text);
        jsonPersistenceManager.save(ledger, file);
        logger.info("Description changed!");
    }

    /**
     * Getter method of the global tag list.
     *
     * @return the list of all the existing tags
     */
    public List<ITag> getTags(){
        logger.info("Reading tag list...");
        return ledger.getTags();
    }

    /**
     * Creates a new tag and updates {@link #file}. Then returns the tag.
     *
     * @param name tag's name
     * @param description tag's description
     * @param expectedValue tag's expected value
     * @return the tag created
     * @throws AlreadyInUseException when the user is trying to
     * create an account using a name already used for another
     * {@code ITag} instance
     */
    public ITag addTag(String name, String description, Double expectedValue) throws AlreadyInUseException {
        if (this.getTags().stream().anyMatch(tag -> tag.getName().equals(name)))
            throw new AlreadyInUseException();
        logger.info("Adding tag...");
        ITag newTag = ledger.addTag(name, description, expectedValue);
        jsonPersistenceManager.save(ledger, file);
        logger.info("Tag added!");
        return newTag;
    }

    /**
     * Removes the input tag from the global list and updates {@link #file}.
     *
     * @param t the tag to remove
     */
    public void removeTag(ITag t){
        if (!ledger.getTags().contains(t))
            throw new IllegalArgumentException("The tag you are trying to delete doesn't exist!");
        logger.info("Removing tag...");
        ledger.removeTag(t);
        jsonPersistenceManager.save(ledger, file);
        logger.info("Tag removed!");
    }
}