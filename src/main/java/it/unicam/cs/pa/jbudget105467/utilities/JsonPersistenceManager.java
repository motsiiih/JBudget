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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import it.unicam.cs.pa.jbudget105467.ILedger;
import it.unicam.cs.pa.jbudget105467.Ledger;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Implementation of the {@link IPersistenceManager} interface.
 */
public class JsonPersistenceManager implements IPersistenceManager {

    /**
     * Ledger's unique instance param.
     */
    ILedger ledger = Ledger.getInstance();

    /**
     * Defines how to serializes/deserialize {@link LocalDate} instances.
     */
    SimpleModule module = new SimpleModule()
            .addSerializer(LocalDate.class, new LocalDateSerializer())
            .addDeserializer(LocalDate.class, new LocalDateDeserializer());

    /**
     * Registers {@link #module} to the {@link ObjectMapper} instance.
     */
    private ObjectMapper mapper = new ObjectMapper().registerModule(module);

    /**
     * Saves the input ledger to the input file.
     *
     * @param ledger the ledger to save
     * @param file the file used to keep
     *             the input ledger's data
     */
    @Override
    public void save(ILedger ledger, File file){
        try {
            mapper.writeValue(file, ledger);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the input file's content.
     *
     * @param file the file used to keep
     *             the saved ledger's data
     * @return the saved ledger instance
     */
    @Override
    public ILedger load(File file){
        try {
            ledger = mapper.readValue(file, Ledger.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ledger;
    }
}