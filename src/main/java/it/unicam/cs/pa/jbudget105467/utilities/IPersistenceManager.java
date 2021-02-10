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
package it.unicam.cs.pa.jbudget105467.utilities;

import it.unicam.cs.pa.jbudget105467.ILedger;
import java.io.File;
import java.io.IOException;

/**
 * Updates and loads files in order to achieve persistence.
 */
public interface IPersistenceManager {

    /**
     * Saves the input ledger to the input file.
     *
     * @param ledger the ledger to save
     * @param file the file used to keep
     *             the input ledger's data
     * @throws IOException when the input file is null
     */
    void save(ILedger ledger, File file) throws IOException;

    /**
     * Loads the input file's content.
     *
     * @param file the file used to keep
     *             the saved ledger's data
     * @return the saved ledger instance
     * @throws IOException when the input file is null
     */
    ILedger load(File file) throws IOException;
}