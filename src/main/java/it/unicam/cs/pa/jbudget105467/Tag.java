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
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Basic implementation of the {@code ITag} interface.
 */
@JsonIdentityInfo(
        generator= ObjectIdGenerators.PropertyGenerator.class,
        property="id",
        scope = Tag.class)
public class Tag implements ITag {

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
     * Tag's name.
     */
    private String name;

    /**
     * Tag's description.
     */
    private String description;

    /**
     * Tag's expected money to spend.
     */
    private double expectedValue;

    /**
     * Tag's actual money spent so far.
     */
    private double actual;

    /**
     * Creates a new instance by using the input parameters.
     *
     * @param name tag's name
     * @param description tag's description
     * @param expectedValue tag's expected value
     */
    public Tag(String name, String description, Double expectedValue) {
        this.name = name;
        this.description = description;
        this.expectedValue = expectedValue;
        this.actual = 0.0;
    }

    public Tag() {
    }

    /**
     * Sets {@link #description} to the given argument.
     *
     * @param description the new tag's description
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns this tag's description.
     *
     * @return {@link #description}
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets {@link #name} to the given argument.
     *
     * @param name the new tag's name
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns this tag's name.
     *
     * @return {@link #name}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns this tag's id.
     *
     * @return {@link #id}
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Returns this tag's expected amount of money to spend.
     *
     * @return {@link #expectedValue}
     */
    @Override
    public Double getExpectedValue() {
        return expectedValue;
    }

    /**
     * Iterates each transaction contained in the input ledger.
     * If the current transaction contains this instance of
     * {@code ITag}, has a total value smaller than zero and
     * is not scheduled for the future, it will be considered
     * part of the outcomes of this tag.
     *
     *
     * @param ledger the input ledger
     */
    @Override
    public void calculateActual(ILedger ledger) {
        actual = 0.0;
        for (ITransaction transaction : ledger.getTransactions()){
            if (transaction.getTags().contains(this) &&
                    transaction.getTotal() < 0 &&
                    !transaction.isScheduled())
                actual -= transaction.getTotal();
        }
        actual = Rounder.round(actual, 2);
    }

    /**
     * This tag's actual amount of money to spend.
     *
     * @return {@link #actual}
     */
    @Override
    public Double getActual() {
        return actual;
    }

    /**
     * Sets {@link #expectedValue} to the given argument.
     *
     * @param expectedValue the new tag's expected value
     */
    @Override
    public void setExpectedValue(Double expectedValue) {
        this.expectedValue = expectedValue;
    }
}
