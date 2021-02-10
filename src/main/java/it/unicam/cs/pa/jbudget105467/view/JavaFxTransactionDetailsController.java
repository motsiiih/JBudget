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

import it.unicam.cs.pa.jbudget105467.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class that controls the transaction details scene.
 */
public class JavaFxTransactionDetailsController implements Initializable {

    /**
     * The unique {@link LedgerController} instance.
     */
    private LedgerController ledgerController = LedgerController.getInstance();

    /**
     * The transaction the user wants to see the details of.
     */
    private ITransaction selectedTransaction;

    /**
     * Transaction's movements table.
     */
    @FXML private TableView<IMovement> tableView;

    /**
     * {@link #tableView}'s id column.
     */
    @FXML private TableColumn<IMovement, AtomicInteger> movementIdColumn;

    /**
     * {@link #tableView}'s type column.
     */
    @FXML private TableColumn<IMovement, MovementType> movementTypeColumn;

    /**
     * {@link #tableView}'s amount column.
     */
    @FXML private TableColumn<IMovement, Double> movementAmountColumn;

    /**
     * {@link #tableView}'s account column.
     */
    @FXML private TableColumn<IMovement, String> movementAccountColumn;

    /**
     * Text field used to insert a new movement's amount.
     */
    @FXML private TextField amountTextField;

    /**
     * Text field used to insert a new movement's associated account.
     */
    @FXML private ComboBox<IAccount> accountComboBox;

    /**
     * Text field used to insert a new movement's type.
     */
    @FXML private ComboBox<MovementType> movementTypeComboBox;

    /**
     * Button used to delete a movement from a transaction.
     */
    @FXML private Button deleteMovementButton;

    /**
     * CombooBox used to select tags to add to the transaction.
     */
    @FXML private ComboBox<ITag> tagComboBox;

    /**
     * List that contains this transaction's tags.
     */
    @FXML private ListView<ITag> tagList;

    /**
     * Transaction's description text area.
     */
    @FXML private TextArea descriptionArea;

    /**
     * If one of the mandatory text field is empty, it
     * calls {@link #resetForm()}. Otherwise, it calls
     * {@link #newMovement()}.
     */
    @FXML
    public void confirmNewMovementButtonIsPushed() {
        if (amountTextField.getText().isEmpty() ||
                accountComboBox.getSelectionModel().isEmpty() ||
                movementTypeComboBox.getSelectionModel().isEmpty())
            resetForm();
        else newMovement();
    }

    /**
     * Creates a new movement with {@link #createNewMovement()},
     * adds it to the table and calls {@link #resetForm()}.
     *
     * @throws NumberFormatException when {@link #amountTextField}
     * does not contain a numeric value
     */
    @FXML
    public void newMovement() throws NumberFormatException{
        try {
            IMovement newMovement = createNewMovement();
            tableView.getItems().add(newMovement);
            resetForm();
        } catch (NumberFormatException e) {
            if (!amountTextField.getText().isEmpty()) {
                resetForm();
                amountTextField.setPromptText("INSERT A NUMBER VALUE!");
            }
        }
    }

    /**
     * Creates a new movement with the parameters insert in
     * the text cases.
     *
     * @return the movement created
     */
    @FXML
    public IMovement createNewMovement(){
        return ledgerController.addMovementToTransaction(
                movementTypeComboBox.getValue(),
                Double.parseDouble(amountTextField.getText()),
                accountComboBox.getValue(),
                selectedTransaction
        );
    }

    /**
     * Deletes an account and removes it from the table.
     */
    @FXML
    public void deleteMovement(){
        IMovement movementToDelete = tableView.getSelectionModel().getSelectedItem();
        ledgerController.removeMovementFromTransaction(movementToDelete, movementToDelete.getTransaction());
        tableView.getItems().remove(movementToDelete);
    }

    /**
     * Reset the text cases to their original state.
     */
    @FXML
    private void resetForm() {
        accountComboBox.setValue(null);
        movementTypeComboBox.setValue(null);
        amountTextField.setText("");
        amountTextField.setPromptText("Movement Amount");
    }

    /**
     * Adds a tag to {@link #selectedTransaction}.
     */
    @FXML
    public void addTagToTransaction(){
        if (tagList.getItems().contains(tagComboBox.getValue()) || tagComboBox.getSelectionModel().getSelectedItem() == null)
            tagComboBox.setValue(null);
        else {
            tagList.getItems().add(tagComboBox.getValue());
            ledgerController.addTagToTransaction(selectedTransaction, tagComboBox.getValue());
        }
    }

    /**
     * Removes a tag to {@link #selectedTransaction}.
     */
    @FXML
    public void removeTagFromTransaction(){
        if (!(tagList.getSelectionModel().getSelectedItem() == null)){
            ledgerController.removeTagFromTransaction(selectedTransaction, tagList.getSelectionModel().getSelectedItem());
            tagList.getItems().remove(tagList.getSelectionModel().getSelectedItem());
        }
    }

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
        movementAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        movementAccountColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAccount().getName()));
        movementTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        accountComboBox.getItems().addAll(ledgerController.getAccounts());
        accountComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(IAccount object) {
                return object == null ? null : object.getName();
            }

            @Override
            public IAccount fromString(String string) {
                return accountComboBox.getItems().stream().filter(i -> i.getName().equals(string)).findAny().orElse(null);
            }
        });
        movementTypeComboBox.getItems().addAll(MovementType.CREDIT, MovementType.DEBIT);
        tagComboBox.getItems().addAll(ledgerController.getTags());
        tagComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(ITag object) {
                return object == null ? null : object.getName();
            }

            @Override
            public ITag fromString(String string) {
                return tagComboBox.getItems().stream().filter(i -> i.getName().equals(string)).findAny().orElse(null);
            }
        });
        tagList.setCellFactory(lv -> {
            TextFieldListCell<ITag> cell = new TextFieldListCell<>();
            cell.setConverter(new StringConverter<>() {
                @Override
                public String toString(ITag tag) {
                    return tag.getName();
                }

                @Override
                public ITag fromString(String string) {
                    ITag tag = cell.getItem();
                    tag.setName(string);
                    return tag;
                }
            });
            return cell;
        });
    }

    /**
     * Adds {@link #selectedTransaction}'s movements to {@link #tableView}.
     *
     * @param movements the movements to add
     */
    @FXML
    public void addTransactionMovements(List<IMovement> movements){
        tableView.getItems().addAll(movements);
    }

    /**
     * Adds {@link #selectedTransaction}'s tags to {@link #tableView}.
     *
     * @param tags movements to add
     */
    @FXML
    public void addTransactionTags(List<ITag> tags){tagList.getItems().addAll(tags);}

    /**
     * Sets the description visible in {@link #descriptionArea}.
     *
     * @param transactionDescription the description
     */
    @FXML
    public void showAccountDescription(String transactionDescription) {
        descriptionArea.setText(transactionDescription);
    }

    /**
     * Updates the visible description.
     */
    @FXML
    public void saveChanges(){
        ledgerController.changeTransactionDescription(descriptionArea.getText(), selectedTransaction);
    }

    /**
     * Sets {@link #selectedTransaction}.
     *
     * @param selectedTransaction the new transaction set
     */
    public void setSelectedTransaction(ITransaction selectedTransaction) {
        this.selectedTransaction = selectedTransaction;
    }
}