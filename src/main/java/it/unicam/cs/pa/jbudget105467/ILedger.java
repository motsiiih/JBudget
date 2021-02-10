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

import java.util.List;
import java.util.function.Predicate;

/**
 * A logbook that keeps track of accounts, transactions and tags and allows to
 * add and remove such instances in different lists.
 */
public interface ILedger {

    /**
     * Creates a new account and adds it to the account list.
     *
     * @param name account name
     * @param description account description
     * @param opening account balance to start with
     * @param type account type
     * @return the new instance of {@code IAccount} created
     */
    IAccount addAccount(String name, String description, double opening, AccountType type);

    /**
     * Removes an account from the account list.
     *
     * @param account the {@code IAccount} to remove
     */
    void removeAccount(IAccount account);

    /**
     * Returns the account list.
     *
     * @return the list that manages instances of {@code IAccount}.
     */
    List<IAccount> getAccounts();

    /**
     * Adds a transaction to the transaction list.
     *
     * @param t the instance of {@code ITransaction} to add
     */
    void addTransaction(ITransaction t);

    /**
     * Removes a transactions.
     *
     * @param t the instance of {@code ITransaction} to remove
     */
    void removeTransaction(ITransaction t);

    /**
     * Returns the transaction list.
     *
     * @return the list that manages instances of {@code ITransaction}.
     */
    List<ITransaction> getTransactions();

    /**
     * Returns the transaction list filtered by a certain condition.
     *
     * @param p the instance of {@code Predicate} that defines the filtering condition
     * @return a filtered list of {@code ITransaction} instances.
     */
    List<ITransaction> getTransactions(Predicate<ITransaction> p);

    /**
     * Creates a new instance of {@code ITag} and adds to the tag list.
     *
     * @param name tag's name
     * @param description tag's description
     * @param expectedValue tag's expected value
     * @return the {@code ITag} instance created
     */
    ITag addTag(String name, String description, Double expectedValue);

    /**
     * Removes a tag from the tag list.
     *
     * @param t the instance of {@code ITag} to remove
     */
    void removeTag(ITag t);

    /**
     * Returns the tag list.
     *
     * @return the list that manages the instances of {@code ITag}
     */
    List<ITag> getTags();
}
