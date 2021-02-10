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
package it.unicam.cs.pa.jbudget105467.view;

import it.unicam.cs.pa.jbudget105467.ILedger;
import it.unicam.cs.pa.jbudget105467.Ledger;
import it.unicam.cs.pa.jbudget105467.LedgerController;
import it.unicam.cs.pa.jbudget105467.utilities.JsonPersistenceManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

/**
 * Main class.
 */
public class JavaFxJBudget extends Application {

    /**
     * The unique {@link Ledger} instance.
     */
    ILedger ledger = Ledger.getInstance();

    /**
     * The unique {@link LedgerController} instance.
     */
    LedgerController ledgerController = LedgerController.getInstance();

    /**
     * File where {@link #ledger} is saved.
     */
    private File file = new File("ledger.json");

    /**
     * Instance of {@link JsonPersistenceManager} used to save
     * and load content from {@link #file}
     */
    private JsonPersistenceManager jsonPersistenceManager = new JsonPersistenceManager();

    /**
     * Loads content from {@link #file} and starts the application.
     *
     * @param stage stage
     * @throws IOException when the .fxml file is null
     */
    @Override
    public void start(Stage stage) throws IOException {
        ledger = jsonPersistenceManager.load(file);
        ledgerController.setLedger(ledger);
        Parent root = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("JBudget");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * main method.
     *
     * @param args args
     */
    public static void main(String[] args) {
        launch(args);
    }
}