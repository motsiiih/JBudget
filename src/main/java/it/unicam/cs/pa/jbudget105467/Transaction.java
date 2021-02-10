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
import it.unicam.cs.pa.jbudget105467.utilities.Rounder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/**
 * Basic implementation of the {@code ITransaction} interface.
 */
@JsonIdentityInfo(
        generator= ObjectIdGenerators.PropertyGenerator.class,
        property="id",
        scope = Transaction.class)
public class Transaction implements ITransaction {

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
     * Transaction's tag list.
     */
    private List<ITag> tags = new ArrayList<>();

    /**
     * Transaction's movement's list.
     */
    private List<IMovement> movements = new ArrayList<>();

    /**
     * Transaction's total amount of money involved.
     */
    private double total;

    /**
     * Transaction's date.
     */
    private LocalDate date;

    /**
     * Transaction's description.
     */
    private String description;

    /**
     * Boolean param that indicates if this transaction
     * is scheduled for the future.
     */
    private boolean scheduled = false;

    /**
     * Creates an instance of this class by using
     * the input parameters.
     *
     * @param date transaction's date
     */
    public Transaction(LocalDate date) {
        if (date.isAfter(LocalDate.now()))
            this.scheduled = true;
        this.date = date;
        this.total = getTotal();
    }

    public Transaction() {
    }

    /**
     * @return {@link #id}
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Adds a movement to this transaction and to the associated account,
     * then, updates the movement's tag list and {@link #total}.
     *
     * @param m the movement to add
     */
    @Override
    public void addMovement(IMovement m) {
        this.movements.add(m);
        m.getAccount().getMovements().add(m);
        for (ITag t : this.tags) {
            m.addTag(t);
        }
        this.getTotal();
    }

    /**
     * Removes a movement from this transaction and from the associated account,
     * then, updates the movement's tag list and {@link #total}.
     *
     * @param m the movement to remove
     */
    @Override
    public void removeMovement(IMovement m) {
        this.movements.remove(m);
        m.getAccount().getMovements().remove(m);
        for (ITag t : this.tags) {
            m.removeTag(t);
        }
        this.getTotal();
    }

    /**
     * @return {@link #movements}
     */
    @Override
    public List<IMovement> getMovements() {
        return this.movements;
    }

    /**
     * @param p the filter predicate
     * @return {@link #movements} filtered by the input predicate
     */
    @Override
    public List<IMovement> getMovements(Predicate<IMovement> p) {
        List<IMovement> tmpList = new ArrayList<>();
        for (IMovement m : this.getMovements()){
            if (p.test(m))
                tmpList.add(m);
        }
        return tmpList;
    }

    /**
     * @return {@link #description}
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Sets {@link #description} to the given argument.
     *
     * @param description the new transaction's description
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Adds the input tag to this transaction and to all its
     * movements.
     *
     * @param t the tag to add
     */
    @Override
    public void addTag(ITag t) {
        if (tags.contains(t))
            throw new IllegalArgumentException("The input tag is already part of this transaction.");
        this.tags.add(t);
        for (IMovement m : this.movements) {
            m.addTag(t);
        }
    }

    /**
     * Removes the input tag from this transaction and from all its
     * movements.
     *
     * @param t the tag to remove
     */
    @Override
    public void removeTag(ITag t) {
        if (!tags.contains(t))
            throw new IllegalArgumentException("The input tag is not part of this transaction.");
        this.tags.remove(t);
        for (IMovement m : this.movements) {
            m.removeTag(t);
        }
    }

    /**
     * @return {@link #tags}
     */
    @Override
    public List<ITag> getTags() {
        return this.tags;
    }

    /**
     * @return {@link #date}
     */
    @Override
    public LocalDate getDate() {
        return this.date;
    }

    /**
     * Sets {@link #date} to the given argument.
     *
     * @param d the new transaction's date
     */
    @Override
    public void setDate(LocalDate d) {
        this.date = d;
    }

    /**
     * Calculates {@link #total} value by iterating all the
     * movements and checking their type according to the
     * associated account.
     *
     * @return {@link #total}
     */
    @Override
    public double getTotal() {
        total = 0.0;
        for (IMovement m : this.movements){
            if (m.getAccount().getType().equals(AccountType.ASSETS)){
                if (m.getType().equals(MovementType.CREDIT))
                    total += m.getAmount();
                else if(m.getType().equals(MovementType.DEBIT))
                    total -= m.getAmount();
            } else if (m.getAccount().getType().equals(AccountType.LIABILITIES)){
                if (m.getType().equals(MovementType.DEBIT))
                    total += m.getAmount();
                else if(m.getType().equals(MovementType.CREDIT))
                    total -= m.getAmount();
            }
        }
        return total = Rounder.round(total, 2);
    }

    /**
     * @return {@link #scheduled}
     */
    @Override
    public boolean isScheduled() {
        return scheduled;
    }

    /**
     * Sets {@link #scheduled} to false.
     */
    @Override
    public void accredit() {
        this.scheduled = false;
    }
}