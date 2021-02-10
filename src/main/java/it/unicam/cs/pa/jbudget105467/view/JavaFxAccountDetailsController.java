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

import it.unicam.cs.pa.jbudget105467.IAccount;
import it.unicam.cs.pa.jbudget105467.IMovement;
import it.unicam.cs.pa.jbudget105467.LedgerController;
import it.unicam.cs.pa.jbudget105467.MovementType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class that controls the account details scene.
 */
public class JavaFxAccountDetailsController implements Initializable {

    /**
     * The unique {@link LedgerController} instance.
     */
    private LedgerController ledgerController = LedgerController.getInstance();

    /**
     * The account the user wants to see the details of.
     */
    private IAccount selectedAccount;

    /**
     * Account's movements table.
     */
    @FXML private TableView<IMovement> tableView;

    /**
     * {@link #tableView}'s movements column.
     */
    @FXML private TableColumn<IMovement, AtomicInteger> movementIdColumn;

    /**
     * {@link #tableView}'s movement type column.
     */
    @FXML private TableColumn<IMovement, MovementType> movementTypeColumn;

    /**
     * {@link #tableView}'s total amount column.
     */
    @FXML private TableColumn<IMovement, Double> movementAmountColumn;

    /**
     * Account's description text area.
     */
    @FXML private TextArea descriptionArea;

    /**
     * Initializes the scene with the desired values.
     *
     * @param location location
     * @param resources resource
     */
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        movementIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        movementTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        movementAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }

    /**
     * Sets {@link #selectedAccount}.
     *
     * @param selectedAccount the new account set
     */
    public void setSelectedAccount(IAccount selectedAccount) {
        this.selectedAccount = selectedAccount;
    }

    /**
     * Adds all the account's movements to {@link #tableView}.
     *
     * @param movements to add
     */
    @FXML
    public void getAccountMovements(List<IMovement> movements){
        tableView.getItems().addAll(movements);
    }

    /**
     * Sets the description visible in {@link #descriptionArea}.
     *
     * @param accountDescription the description
     */
    @FXML
    public void showAccountDescription(String accountDescription) {
        descriptionArea.setText(accountDescription);
    }

    /**
     * Updates the visible description.
     */
    @FXML
    public void saveChanges(){
        ledgerController.changeAccountDescription(descriptionArea.getText(), selectedAccount);
    }
}
