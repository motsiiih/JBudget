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
import java.util.function.Predicate;

/**
 * Manages a transaction allowing to get and set its data (id, tags,
 * movements and date) and to add or remove tags and movements.
 */
@JsonDeserialize(as = Transaction.class)
public interface ITransaction {

    /**
     * @return transaction's id
     */
    int getId();

    /**
     * Adds a new instance of {@code IMovement} to the movement list
     *
     * @param m the movement to add
     */
    void addMovement(IMovement m);

    /**
     * Removes a new instance of {@code IMovement} from the movement list
     *
     * @param m the movement to remove
     */
    void removeMovement(IMovement m);

    /**
     * @return transaction's associated movements
     */
    List<IMovement> getMovements();

    /**
     * Sets this transaction's description to the given argument.
     *
     * @param description the new transaction's description
     */
    void setDescription(String description);

    /**
     * @return transaction's description
     */
    String getDescription();

    /**
     * Returns the list of movements associated to this transaction
     * filtered by a certain condition defined by an instance
     * of {@code Predicate}.
     *
     * @param p predicate
     * @return filtered transaction's associated movements
     */
    List<IMovement> getMovements(Predicate<IMovement> p);

    /**
     * @return transaction's associated tags
     */
    List<ITag> getTags();

    /**
     * Adds a new instance of {@code ITag} to the tag list
     *
     * @param t the tag to add
     */
    void addTag(ITag t);

    /**
     * Removes a new instance of {@code ITag} from the tag list
     *
     * @param t the tag to remove
     */
    void removeTag(ITag t);

    /**
     * @return transaction's date
     */
    LocalDate getDate();

    /**
     * Sets this transaction's date to the given argument.
     *
     * @param d the new transaction's date
     */
    void setDate(LocalDate d);

    /**
     * @return transaction's total amount of money
     */
    double getTotal();

    /**
     * @return true if the transaction is scheduled
     */
    boolean isScheduled();

    /**
     * Transforms a scheduled transaction to
     * a normal one.
     */
    void accredit();
}