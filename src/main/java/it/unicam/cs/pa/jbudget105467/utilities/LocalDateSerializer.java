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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Defines how to serialize {@link LocalDate} instances.
 */
public class LocalDateSerializer extends StdSerializer<LocalDate> {

    /**
     * Constructor used to define the module.
     */
    public LocalDateSerializer() {
        this(null);
    }

    protected LocalDateSerializer(Class<LocalDate> t) {
        super(t);
    }

    /**
     * Saves a {@link LocalDate} instances using only its day, month
     * and year values.
     *
     * @param value the {@link LocalDate} instance to save
     * @param gen generator
     * @param provider provider
     */
    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("year", value.getYear());
        gen.writeNumberField("month", value.getMonthValue());
        gen.writeNumberField("day", value.getDayOfMonth());
        gen.writeEndObject();
    }
}
