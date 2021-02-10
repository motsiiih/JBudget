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

/**
 * Defines an expected budget to spend on a category and
 * keeps track of how much money is spent on that category.
 */
@JsonDeserialize(as = Tag.class)
public interface ITag {

    /**
     * Sets this tag's description.
     *
     * @param description the description to set
     */
    void setDescription(String description);

    /**
     * @return this tag's description
     */
    String getDescription();

    /**
     * Sets this tag's name.
     *
     * @param name the name to set
     */
    void setName(String name);

    /**
     * @return this tag's name
     */
    String getName();

    /**
     * @return this tag's id
     */
    int getId();

    /**
     * Sets this tag's expected amount of money
     * to spend.
     *
     * @param expectedValue the expected amount
     */
    void setExpectedValue(Double expectedValue);

    /**
     * @return this tag's expected amount of money
     */
    Double getExpectedValue();

    /**
     * @return the actual amount of money spent on this
     * tag so far
     */
    Double getActual();

    /**
     * Given an input instance of {@code ILedger}, calculates
     * the actual amount of money spent on a tag.
     *
     * @param ledger the input ledger
     */
    void calculateActual(ILedger ledger);
}
