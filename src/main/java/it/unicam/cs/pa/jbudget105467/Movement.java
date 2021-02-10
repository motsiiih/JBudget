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

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Basic implementation of the {@code ITag} interface. Implements
 * methods to get and set this {@code Tag} instance's data.
 */
@JsonIdentityInfo(
        generator= ObjectIdGenerators.PropertyGenerator.class,
        property="id",
        scope = Movement.class)
public class Movement implements IMovement {

    /**
     * An instance of {@code AtomicInteger} that starts from 0 and
     * keeps track of the next id number to assign.
     */
    private static final AtomicInteger count = new AtomicInteger(0);

    /**
     * The id number of this instance.
     */
    private int id = count.incrementAndGet();

    /**
     * This movement's type.
     */
    private MovementType type;

    /**
     * This movement's amount.
     */
    private double amount;

    /**
     * This movement's tag list.
     */
    private List<ITag> tags = new ArrayList<>();

    /**
     * This movement's date.
     */
    private LocalDate date;

    /**
     * This movement's associated transaction.
     */
    private ITransaction transaction;

    /**
     * This movement's associated account.
     */
    private IAccount account;

    /**
     * Creates a new instance of {@code IAccount} by using the input parameters.
     *
     * @param type movement's type
     * @param amount movement's amount
     * @param account movement's associated account
     * @param transaction movement's associated transaction
     */
    public Movement(MovementType type, double amount, IAccount account, ITransaction transaction) {
        this.type = type;
        this.amount = amount;
        this.account = account;
        this.transaction = transaction;
    }

    public Movement() {
    }

    /**
     * @return movement's type
     */
    @Override
    public MovementType getType() {
        return this.type;
    }

    /**
     * @return movement's amount
     */
    @Override
    public double getAmount() {
        return amount;
    }

    /**
     * Sets a new {@link #transaction}.
     *
     * @param transaction the new associated transaction
     */
    @Override
    public void setTransaction(ITransaction transaction) {
        this.transaction = transaction;
    }

    /**
     * @return movement's associated transaction
     */
    @Override
    public ITransaction getTransaction() {
        return this.transaction;
    }

    /**
     * @return movement's associated account
     */
    @Override
    public IAccount getAccount() {
        return this.account;
    }

    /**
     * @return movement's id
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * @return movement's associated transaction's date
     */
    @Override
    public LocalDate getDate() {
        return date = transaction.getDate();
    }

    /**
     * @return {@link #tags}
     */
    @Override
    public List<ITag> getTags() {
        return this.tags;
    }

    /**
     * Adds a new tag to the movement.
     *
     * @param t the tag to add
     */
    @Override
    public void addTag(ITag t) {
        this.tags.add(t);
    }

    /**
     * Removes a tag from the movement.
     *
     * @param t the tag to remove
     */
    @Override
    public void removeTag(ITag t) {
        this.tags.remove(t);
    }

    /**
     * Sets a new {@link #account}.
     *
     * @param account the new associated account
     */
    @Override
    public void setAccount(IAccount account) {
        this.account = account;
    }
}