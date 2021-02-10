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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.LocalDate;
import java.util.List;

/**
 * An object that represents a financial movement, which contains
 * information about the amount of money involved, the transaction
 * and the account which this movement is part of, date and tags.
 */
@JsonDeserialize(as = Movement.class)
public interface IMovement {

    /**
     * Returns this movement's type.
     *
     * @return an instance of {@link AccountType}
     */
    MovementType getType();

    /**
     * @return this movement's amount
     */
    double getAmount();

    /**
     * Sets the transaction which this movement
     * is part of.
     *
     * @param transaction the transaction to set
     */
    void setTransaction(ITransaction transaction);

    /**
     * Returns this movement's transaction.
     *
     * @return an instance of {@code ITransaction}
     */
    ITransaction getTransaction();

    /**
     * Returns this movement's account.
     *
     * @return an instance of {@code IAccount}
     */
    IAccount getAccount();

    /**
     * @return this movement's id
     */
    int getId();

    /**
     * @return this movement's date
     */
    LocalDate getDate();

    /**
     * Returns this movement's list of tags.
     *
     * @return a list of {@code ITag} instances
     */
    List<ITag> getTags();

    /**
     * Adds a tag to the list of tags associated
     * to this movement.
     *
     * @param t the tag to add
     */
    void addTag(ITag t);

    /**
     * Removes a tag from the list of tags associated
     * to this movement.
     *
     * @param t the tag to remove
     */
    void removeTag(ITag t);

    /**
     * Sets the account which this movement
     * is part of.
     *
     * @param a the account to set
     */
    void setAccount(IAccount a);
}