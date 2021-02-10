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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class that controls the transactions details scene.
 */
public class JavaFxTransactionsController implements Initializable {

    /**
     * The unique {@link LedgerController} instance.
     */
    private LedgerController ledgerController = LedgerController.getInstance();

    /**
     * List of transactions.
     */
    @FXML private ObservableList<ITransaction> transactions = FXCollections.observableArrayList();

    /**
     * Text field used to insert a new transaction's amount.
     */
    @FXML private TextField amountTextField;

    /**
     * Text field used to insert a new transaction's date.
     */
    @FXML private DatePicker transactionDatePicker;

    /**
     * Text field used to insert a new transaction's associated account.
     */
    @FXML private ComboBox<IAccount> accountComboBox;

    /**
     * Text field used to insert a new transaction's movement type.
     */
    @FXML private ComboBox<MovementType> movementTypeComboBox;

    /**
     * Table containing all the transactions.
     */
    @FXML private TableView<ITransaction> tableView;

    /**
     * {@link #tableView}'s id column.
     */
    @FXML private TableColumn<ITransaction, AtomicInteger> idColumn;

    /**
     * {@link #tableView}'s date column.
     */
    @FXML private TableColumn<ITransaction, LocalDate> dateColumn;

    /**
     * {@link #tableView}'s amount column.
     */
    @FXML private TableColumn<ITransaction, Double> amountColumn;

    /**
     * List of scheduled transactions.
     */
    @FXML private ObservableList<ITransaction> scheduledTransactions = FXCollections.observableArrayList();

    /**
     * Text field used to insert a new scheduled transaction's amount.
     */
    @FXML private TextField scheduledAmountTextField;

    /**
     * Text field used to insert a new scheduled transaction's date.
     */
    @FXML private DatePicker scheduledTransactionDatePicker;

    /**
     * Text field used to insert a new scheduled transaction's associated account.
     */
    @FXML private ComboBox<IAccount> scheduledAccountComboBox;

    /**
     * Text field used to insert a new scheduled transaction's movement type.
     */
    @FXML private ComboBox<MovementType> scheduledMovementTypeComboBox;

    /**
     * Table containing all the scheduled transactions.
     */
    @FXML private TableView<ITransaction> scheduledTableView;

    /**
     * {@link #tableView}'s id column.
     */
    @FXML private TableColumn<ITransaction, AtomicInteger> scheduledIdColumn;

    /**
     * {@link #tableView}'s date column.
     */
    @FXML private TableColumn<ITransaction, LocalDate> scheduledDateColumn;

    /**
     * {@link #tableView}'s amount column.
     */
    @FXML private TableColumn<ITransaction, Double> scheduledAmountColumn;

    /**
     * Takes the user to the home scene.
     *
     * @param event the event
     * @throws IOException when the .fxml file is null
     */
    @FXML
    public void homeScene(ActionEvent event) throws IOException {
        Parent accountsParent = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));

        Scene scene = new Scene(accountsParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * If one of the mandatory text field is empty, it
     * calls {@link #resetForm()}. Otherwise, it calls
     * {@link #newTransaction()}.
     */
    @FXML
    public void confirmNewTransactionButtonIsPushed(){
        if (amountTextField.getText().isEmpty() ||
                transactionDatePicker.getValue() == null ||
                accountComboBox.getSelectionModel().isEmpty() ||
                movementTypeComboBox.getSelectionModel().isEmpty())
            resetForm();
        else newTransaction();
    }

    /**
     * Creates a new transaction with {@link #createNewTransaction()},
     * adds it to the table and calls {@link #resetForm()}.
     *
     * @throws NumberFormatException when {@link #amountTextField}
     * does not contain a numeric value
     */
    @FXML
    private void newTransaction() throws NumberFormatException {
        try {
            ITransaction newTransaction = createNewTransaction();
            tableView.getItems().add(newTransaction);
            resetForm();
        } catch (NumberFormatException e) {
            if (!amountTextField.getText().isEmpty()) {
                resetForm();
                amountTextField.setPromptText("INSERT A NUMBER VALUE!");
            }
        }
    }

    /**
     * Creates a new transaction with the parameters insert in
     * the text cases.
     *
     * @return the transaction created
     */
    @FXML
    private ITransaction createNewTransaction() {
        return ledgerController.addTransaction(
                        transactionDatePicker.getValue(),
                        movementTypeComboBox.getValue(),
                        Double.parseDouble(amountTextField.getText()),
                        accountComboBox.getValue());
    }

    /**
     * Reset the text cases to their original state.
     */
    @FXML
    private void resetForm() {
        amountTextField.setPromptText("First Movement Amount");
        transactionDatePicker.setValue(null);
        movementTypeComboBox.setValue(null);
        accountComboBox.setValue(null);
        amountTextField.setText("");
    }

    /**
     * Opens a transaction details window passing to its {@link JavaFxTransactionDetailsController}
     * instance all the necessary data and disables this window.
     *
     * @throws IOException when the .fxml file is null
     */
    @FXML
    public void openMovementsWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TransactionDetails.fxml"));
        ITransaction selectedTransaction = tableView.getSelectionModel().getSelectedItem();
        Parent root = loader.load();
        JavaFxTransactionDetailsController javaFxTransactionDetailsController = loader.getController();
        javaFxTransactionDetailsController.addTransactionMovements(ledgerController.getTransactionMovements(selectedTransaction));
        javaFxTransactionDetailsController.addTransactionTags(ledgerController.getTransactionTags(selectedTransaction));
        javaFxTransactionDetailsController.showAccountDescription(ledgerController.getTransactionDescription(selectedTransaction));
        javaFxTransactionDetailsController.setSelectedTransaction(selectedTransaction);
        Stage stage = new Stage();
        stageSetup(stage, root);
        stage.show();
    }

    /**
     * Sets up the account details stage.
     *
     * @param stage the stage to set up
     * @param root the root
     */
    private void stageSetup(Stage stage, Parent root) {
        stage.setTitle("Movements");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    /**
     * If one of the mandatory text field is empty, it
     * calls {@link #resetForm()}. Otherwise, it calls
     * {@link #newScheduledTransaction()} ()}.
     */
    @FXML
    public void confirmNewScheduledTransactionButtonIsPushed(){
        if (scheduledAmountTextField.getText().isEmpty() ||
                scheduledTransactionDatePicker.getValue() == null ||
                scheduledAccountComboBox.getSelectionModel().isEmpty() ||
                scheduledMovementTypeComboBox.getSelectionModel().isEmpty())
            resetForm();
        else newScheduledTransaction();
    }

    /**
     * Creates a new scheduled transaction with {@link #createNewScheduledTransaction()},
     * adds it to the table and calls {@link #resetForm()}.
     *
     * @throws NumberFormatException when {@link #scheduledAmountTextField}
     * does not contain a numeric value
     */
    @FXML
    private void newScheduledTransaction() throws NumberFormatException {
        try {
            ITransaction newTransaction = createNewScheduledTransaction();
            scheduledTableView.getItems().add(newTransaction);
            resetForm();
        } catch (NumberFormatException e) {
            if (!scheduledAmountTextField.getText().isEmpty()) {
                resetForm();
                scheduledAmountTextField.setPromptText("INSERT A NUMBER VALUE!");
            }
        }
    }

    /**
     * Creates a new scheduled transaction with the parameters
     * insert in the text cases.
     *
     * @return the scheduled transaction created
     */
    @FXML
    private ITransaction createNewScheduledTransaction() {
        return ledgerController.addTransaction(
                scheduledTransactionDatePicker.getValue(),
                scheduledMovementTypeComboBox.getValue(),
                Double.parseDouble(scheduledAmountTextField.getText()),
                scheduledAccountComboBox.getValue());
    }

    /**
     * Opens a scheduled transaction details window passing to its {@link JavaFxTransactionDetailsController}
     * instance all the necessary data and disables this window.
     *
     * @throws IOException when the .fxml file is null
     */
    @FXML
    public void openScheduledMovementsWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TransactionDetails.fxml"));
        ITransaction selectedTransaction = scheduledTableView.getSelectionModel().getSelectedItem();
        Parent root = loader.load();
        JavaFxTransactionDetailsController javaFxTransactionDetailsController = loader.getController();
        javaFxTransactionDetailsController.setSelectedTransaction(selectedTransaction);
        javaFxTransactionDetailsController.addTransactionMovements(ledgerController.getTransactionMovements(selectedTransaction));
        Stage stage = new Stage();
        stageSetup(stage, root);
        stage.show();
    }

    /**
     * Accredits the selected scheduled transaction.
     */
    @FXML
    public void accreditScheduledTransaction(){
        ITransaction transactionToAccredit = scheduledTableView.getSelectionModel().getSelectedItem();
        ledgerController.accreditScheduledTransaction(transactionToAccredit);
    }

    /**
     * Initializes the scene with the desired values.
     *
     * @param location location
     * @param resources resource
     */
    @FXML
    @Override
    public void initialize (URL location, ResourceBundle resources){
        transactions.addAll(ledgerController.getTransactions(transaction -> !transaction.isScheduled()));
        this.setColumns(idColumn, dateColumn, amountColumn);
        tableView.setItems(transactions);
        this.acbSetup(accountComboBox);
        movementTypeComboBox.getItems().addAll(MovementType.CREDIT, MovementType.DEBIT);
        transactionDatePicker.setDayCellFactory(param -> new DateCell(){
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) > 0);
            }
        });

        scheduledTransactions.addAll(ledgerController.getTransactions(ITransaction::isScheduled));
        this.setColumns(scheduledIdColumn, scheduledDateColumn, scheduledAmountColumn);
        scheduledTableView.setItems(scheduledTransactions);
        this.acbSetup(scheduledAccountComboBox);
        scheduledMovementTypeComboBox.getItems().addAll(MovementType.CREDIT, MovementType.DEBIT);
        scheduledTransactionDatePicker.setDayCellFactory(param -> new DateCell(){
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 1);
            }
        });
    }

    /**
     * Sets up a {@link ComboBox} containing {@link IAccount} instances.
     *
     * @param comboBox the combobox to setup
     */
    private void acbSetup(ComboBox<IAccount> comboBox){
        comboBox.getItems().addAll(ledgerController.getAccounts());
        comboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(IAccount object) {
                return object == null ? null : object.getName();
            }

            @Override
            public IAccount fromString(String string) {
                return comboBox.getItems().stream().filter(i -> i.getName().equals(string)).findAny().orElse(null);
            }
        });
    }

    /**
     * Sets up columns of a {@link TableView} containing {@link IAccount} instances.
     * 
     * @param a the id column
     * @param b the date column
     * @param c the total column
     */
    private void setColumns(TableColumn<ITransaction, AtomicInteger> a,
                            TableColumn<ITransaction, LocalDate> b,
                            TableColumn<ITransaction, Double> c){
        a.setCellValueFactory(new PropertyValueFactory<>("id"));
        b.setCellValueFactory(new PropertyValueFactory<>("date"));
        c.setCellValueFactory(new PropertyValueFactory<>("total"));
    }
}
