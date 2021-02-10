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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class that controls the main menu scene.
 */
public class JavaFxMainMenuController{

    /**
     * {@link Scene} instance of the accounts scene param.
     */
    @FXML private Scene accountsScene = null;

    /**
     * Takes the user to the accounts scene.
     *
     * @param event the event
     * @throws IOException when the .fxml file is null
     */
    @FXML
    public void accountsScene(ActionEvent event) throws IOException {
        Parent accountsParent = FXMLLoader.load(getClass().getResource("/Accounts.fxml"));
        if (accountsScene == null)
            accountsScene = new Scene(accountsParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(accountsScene);
        window.show();
    }

    /**
     * Takes the user to the transactions scene.
     *
     * @param event the event
     * @throws IOException when the .fxml file is null
     */
    @FXML
    public void transactionsScene(ActionEvent event) throws IOException {
        Parent accountsParent = FXMLLoader.load(getClass().getResource("/Transactions.fxml"));
        Scene scene = new Scene(accountsParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * Takes the user to the budgets scene.
     *
     * @param event the event
     * @throws IOException when the .fxml file is null
     */
    @FXML
    public void budgetsScene(ActionEvent event) throws IOException {
        Parent accountsParent = FXMLLoader.load(getClass().getResource("/Budgets.fxml"));
        Scene scene = new Scene(accountsParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}