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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Basic implementation of the {@code ILedger} interface. Implements
 * methods to add and remove elements to and from its lists.
 */
public class Ledger implements ILedger {

    private static Ledger ledger = null;

    /**
     * Instance of {@code ArrayList} that contains elements that implement
     * the {@code IAccount} interface and keeps track of the accounts created.
     */
    private List<IAccount> accounts = new ArrayList<>();

    /**
     * Instance of {@code ArrayList} that contains elements that implement
     * the {@code ITransaction} interface and keeps track of the transactions created.
     */
    private List<ITransaction> transactions = new ArrayList<>();

    /**
     * Instance of {@code ArrayList} that contains elements that implement
     * the {@code ITag} interface and keeps track of the tags created.
     */
    private List<ITag> tags = new ArrayList<>();

    /**
     * Constructs a {@code ledger} instance.
     */
    private Ledger(){}

    /**
     * Returns the unique instance of this class.
     * If it is null, it creates one.
     *
     * @return the instance of {@code ledger}
     */
    public static Ledger getInstance(){
        if (ledger == null)
            ledger = new Ledger();
        return ledger;
    }

    /**
     * Creates a new instance of {@code IAccount} by using the input parameters,
     * associates this {@code Ledger} instance to it and adds it to {@link Ledger#accounts}.
     *
     * @param name account name
     * @param description account description
     * @param opening account balance to start with
     * @param type account type
     * @return the new instance of {@code IAccount} created
     */
    @Override
    public IAccount addAccount(String name, String description, double opening, AccountType type) {
        IAccount newAccount = new Account(name, description, opening, type);
        this.accounts.add(newAccount);
        return newAccount;
    }

    /**
     * Iterates transactions and removes each instance of {@code IMovement}
     * that is associated with the account argument, then removes the account
     * argument itself from {@link Ledger#accounts}.
     *
     * @param account the {@code IAccount} instance to remove
     */
    @Override
    public void removeAccount(IAccount account) {
        this.getTransactions().forEach(
                transaction -> transaction.getMovements().removeIf(
                        movement -> movement.getAccount().equals(account)));
        this.getAccounts().remove(account);
    }

    /**
     * Returns the list that manages instances of {@code IAccount}.
     *
     * @return {@link Ledger#accounts}
     */
    @Override
    public List<IAccount> getAccounts() {
        return this.accounts;
    }

    /**
     * Adds the instance of {@code ITransaction} to {@link Ledger#transactions}.
     *
     * @param t the instance of {@code ITransaction} to add
     */
    @Override
    public void addTransaction(ITransaction t) {
        this.transactions.add(t);
    }

    /**
     * Removes the argument instance of {@code ITransaction} from {@link Ledger#transactions}.
     *
     * @param t the instance of {@code ITransaction} to remove
     */
    @Override
    public void removeTransaction(ITransaction t) {
        this.transactions.remove(t);
    }

    /**
     * @return {@link Ledger#transactions}
     */
    @Override
    public List<ITransaction> getTransactions(){
        return this.transactions;
    }

    /**
     * Applies a filter, defined by the {@code Predicate} p argument, to the
     * list returned by the {@link Ledger#getTransactions()} method.
     * Iterates the list returned by the {@link #getTransactions()} method and executes
     * the {@link Predicate#test(Object)} method on each {@code ITransaction} instance.
     * If the {@link Predicate#test(Object)} method returns {@code true}, the
     * {@code ITransaction} instance gets added to a temporary list which is returned
     * at the end of the method.
     *
     * @param p instance of {@code Predicate} which determines how the list
     *          will be filtered
     * @return the filtered transactions list
     */
    @Override
    public List<ITransaction> getTransactions(Predicate<ITransaction> p) {
        List<ITransaction> tmpList = new ArrayList<>();
        for (ITransaction t : getTransactions()){
            if (p.test(t))
                tmpList.add(t);
        }
        return tmpList;
    }

    /**
     * Creates a new instance of {@code ITag} by using the input parameters and
     * adds it to {@link Ledger#tags}. Then, returns the new instance created.
     *
     * @param name tag name
     * @param description tag description
     * @return the new {@code ITag} instance created
     */
    @Override
    public ITag addTag(String name, String description, Double expectedValue) {
        ITag newTag = new Tag(name, description, expectedValue);
        this.tags.add(newTag);
        return newTag;
    }

    /**
     * Removes the argument instance of {@code ITag} from {@link Ledger#tags}. Then,
     * {@link #getTransactions(Predicate)} is used to get a list of the
     * transactions that contain the instance of {@code ITag} to remove and, for each
     * of those transactions, the tag gets removed.
     *
     * @param t the instance of {@code ITag} to remove
     */
    @Override
    public void removeTag(ITag t) {
        this.tags.remove(t);
        this.getTransactions(transaction -> transaction.getTags().contains(t)).
                forEach(transaction -> transaction.removeTag(t));
    }

    /**
     * Returns the list that manages instances of {@code ITag}
     *
     * @return {@link Ledger#tags}
     */
    @Override
    public List<ITag> getTags() {
        return this.tags;
    }
}