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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class that controls the accounts scene.
 */
public class JavaFxAccountsController implements Initializable {

    /**
     * The unique {@link LedgerController} instance.
     */
    private LedgerController ledgerController = LedgerController.getInstance();

    /**
     * List of accounts.
     */
    @FXML private ObservableList<IAccount> accounts = FXCollections.observableArrayList();

    /**
     * Table containing all the accounts.
     */
    @FXML private TableView<IAccount> tableView;

    /**
     * {@link #tableView}'s id column.
     */
    @FXML private TableColumn<IAccount, AtomicInteger> accountIdColumn;

    /**
     * {@link #tableView}'s name column.
     */
    @FXML private TableColumn<IAccount, String> accountNameColumn;

    /**
     * {@link #tableView}'s balance column.
     */
    @FXML private TableColumn<IAccount, Double> accountBalanceColumn;

    /**
     * {@link #tableView}'s opening balance column.
     */
    @FXML private TableColumn<IAccount, Double> accountOpeningBalanceColumn;

    /**
     * {@link #tableView}'s type column.
     */
    @FXML private TableColumn<IAccount, AccountType> accountTypeColumn;

    /**
     * Text field used to insert a new account's name.
     */
    @FXML private TextField accountNameTextField;

    /**
     * Text field used to insert a new account's description.
     */
    @FXML private TextField accountDescriptionTextField;

    /**
     * Text field used to insert a new account's opening balance.
     */
    @FXML private TextField openingBalanceTextField;

    /**
     * Text field used to insert a new account's type.
     */
    @FXML private ComboBox<AccountType> accountTypeComboBox;

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
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * If one of the mandatory text field is empty, it
     * calls {@link #resetForm()}. Otherwise, it calls
     * {@link #newAccount()}.
     */
    @FXML
    public void confirmNewAccountButtonIsPushed(){
        if (accountNameTextField.getText().isEmpty() ||
                openingBalanceTextField.getText().isEmpty() ||
                accountTypeComboBox.getSelectionModel().isEmpty())
            resetForm();
        else {
            newAccount();
        }
    }

    /**
     * Creates a new account with {@link #createNewAccount()},
     * adds it to the table and calls {@link #resetForm()}.
     *
     * @throws NumberFormatException when {@link #openingBalanceTextField}
     * does not contain a numeric value
     */
    @FXML
    private void newAccount() throws NumberFormatException {
        try {
            IAccount newAccount = createNewAccount();
            tableView.getItems().add(newAccount);
            resetForm();
        }catch (NumberFormatException e1){
            if (!openingBalanceTextField.getText().isEmpty()){
                resetForm();
                openingBalanceTextField.setPromptText("INSERT A NUMBER VALUE!");
            }
        }catch (AlreadyInUseException e2){
            resetForm();
            accountNameTextField.setPromptText("NAME ALREADY IN USE!");
        }
    }

    /**
     * Creates a new account with the parameters insert in
     * the text cases.
     *
     * @return the account created
     * @throws AlreadyInUseException when the account name is already in use
     */
    @FXML
    private IAccount createNewAccount() throws AlreadyInUseException {
        return ledgerController.addAccount(
                accountNameTextField.getText(),
                accountDescriptionTextField.getText(),
                Double.parseDouble(openingBalanceTextField.getText()),
                accountTypeComboBox.getValue());
    }

    /**
     * Deletes an account and removes it from the table.
     */
    @FXML
    public void deleteAccount(){
        IAccount accountToDelete = tableView.getSelectionModel().getSelectedItem();
        ledgerController.removeAccount(accountToDelete);
        tableView.getItems().remove(accountToDelete);
    }

    /**
     * Reset the text cases to their original state.
     */
    @FXML
    private void resetForm() {
        accountNameTextField.setPromptText("Account Name");
        accountNameTextField.setText("");
        openingBalanceTextField.setPromptText("Opening Balance");
        openingBalanceTextField.setText("");
        accountDescriptionTextField.setText("");
        accountTypeComboBox.setValue(null);
    }

    /**
     * Opens an account details window passing to its {@link JavaFxAccountDetailsController}
     * instance all the necessary data and disables this window.
     *
     * @throws IOException when the .fxml file is null
     */
    @FXML
    public void openMovementsWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AccountDetails.fxml"));
        Parent root = loader.load();
        IAccount selectedAccount = tableView.getSelectionModel().getSelectedItem();
        JavaFxAccountDetailsController javaFxAccountDetailsController = loader.getController();
        javaFxAccountDetailsController.setSelectedAccount(selectedAccount);
        javaFxAccountDetailsController.getAccountMovements(ledgerController.getAccountMovements(selectedAccount));
        javaFxAccountDetailsController.showAccountDescription(ledgerController.getAccountDescription(selectedAccount));
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
        stage.setScene(new Scene(root));
        stage.setTitle("Account Details");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
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
        accounts.addAll(ledgerController.getAccounts());
        tableView.setItems(accounts);
        accountIdColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        accountNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        accountOpeningBalanceColumn.setCellValueFactory(new PropertyValueFactory<>("openingBalance"));
        accountBalanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        accountTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        accountTypeComboBox.getItems().addAll(AccountType.ASSETS, AccountType.LIABILITIES);
    }
}