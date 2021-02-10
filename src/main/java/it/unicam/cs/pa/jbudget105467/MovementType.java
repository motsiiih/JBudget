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

/**
 * An enumeration created to distinguish
 * different types of movement.
 */
public enum MovementType {

    /**
     * This type of movements will be treated as outcomes by
     * {@link AccountType#ASSETS} accounts and as incomes
     * by {@link AccountType#LIABILITIES}.
     */
    DEBIT,

    /**
     * This type of movements will be treated as incomes by
     * {@link AccountType#ASSETS} accounts and as outcomes
     * by {@link AccountType#LIABILITIES}.
     */
    CREDIT
}