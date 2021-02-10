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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Defines how to deserialize {@link LocalDate} instances.
 */
public class LocalDateDeserializer extends StdDeserializer<LocalDate> {

    /**
     * Constructor used to define the module.
     */
    public LocalDateDeserializer() {
        this(null);
    }

    protected LocalDateDeserializer(Class<?> vc) {
        super(vc);
    }

    /**
     * Constructs a {@link LocalDate} instance using only day, month
     * and year values.
     *
     * @param p parser
     * @param txt context
     * @return a {@link LocalDate} instance
     */
    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext txt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        int year = (Integer) node.get("year").numberValue();
        int month = (Integer) node.get("month").numberValue();
        int day = (Integer) node.get("day").numberValue();
        return LocalDate.of(year, month, day);
    }
}
