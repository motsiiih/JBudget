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

import it.unicam.cs.pa.jbudget105467.AlreadyInUseException;
import it.unicam.cs.pa.jbudget105467.ITag;
import it.unicam.cs.pa.jbudget105467.LedgerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class that controls the budgets scene.
 */
public class JavaFxBudgetsController implements Initializable {

    /**
     * The unique {@link LedgerController} instance.
     */
    private LedgerController ledgerController = LedgerController.getInstance();

    /**
     * List of tags.
     */
    @FXML private ObservableList<ITag> tags = FXCollections.observableArrayList();

    /**
     * Table containing all the tags.
     */
    @FXML private TableView<ITag> tagTable;

    /**
     * {@link #tagTable}'s id column.
     */
    @FXML private TableColumn<ITag, AtomicInteger> idColumn;

    /**
     * {@link #tagTable}'s name column.
     */
    @FXML private TableColumn<ITag, String> nameColumn;

    /**
     * {@link #tagTable}'s description column.
     */
    @FXML private TableColumn<ITag, String> descriptionColumn;

    /**
     * {@link #tagTable}'s expected amount column.
     */
    @FXML private TableColumn<ITag, Double> expectedColumn;

    /**
     * {@link #tagTable}'s actual amount column.
     */
    @FXML private TableColumn<ITag, Double> actualColumn;

    /**
     * Text field used to insert a new tag's name.
     */
    @FXML private TextField nameTextField;

    /**
     * Text field used to insert a new tag's description.
     */
    @FXML private TextField descriptionTextField;

    /**
     * Text field used to insert a new tag's expected amount.
     */
    @FXML private TextField expectedTextField;

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
     * {@link #newTag()}.
     */
    @FXML
    public void confirmNewTagButtonIsPushed(){
        if (nameTextField.getText().isEmpty() ||
                descriptionTextField.getText().isEmpty() ||
                expectedTextField.getText().isEmpty())
            resetForm();
        else
            newTag();
    }

    /**
     * Creates a new tag with {@link #createNewTag()},
     * adds it to the table and calls {@link #resetForm()}.
     *
     * @throws NumberFormatException when {@link #expectedColumn}
     * does not contain a numeric value
     */
    private void newTag() {
        try {
            ITag newTag = createNewTag();
            tagTable.getItems().add(newTag);
            resetForm();
        } catch (AlreadyInUseException e) {
            resetForm();
            nameTextField.setPromptText("NAME ALREADY IN USE!");
        } catch (NumberFormatException e1) {
            if (!expectedTextField.getText().isEmpty()) {
                resetForm();
                expectedTextField.setPromptText("INSERT A NUMBER VALUE!");
            }
        }
    }

    /**
     * Creates a new tag with the parameters insert in
     * the text cases.
     *
     * @return the tag created
     * @throws AlreadyInUseException when the tag name is already in use
     */
    @FXML
    private ITag createNewTag() throws AlreadyInUseException {
        return ledgerController.addTag(
                nameTextField.getText(),
                descriptionTextField.getText(),
                Double.parseDouble(expectedTextField.getText())
        );
    }

    /**
     * Deletes a tag and removes it from the table.
     */
    @FXML
    public void deleteTag(){
        ITag tagToDelete = tagTable.getSelectionModel().getSelectedItem();
        ledgerController.removeTag(tagToDelete);
        tagTable.getItems().remove(tagToDelete);
    }

    /**
     * Reset the text cases to their original state.
     */
    private void resetForm() {
        nameTextField.setPromptText("Tag Name");
        descriptionTextField.setPromptText("Tag Description");
        nameTextField.setText("");
        descriptionTextField.setText("");
    }

    /**
     * Initializes the scene with the desired values.
     *
     * @param location location
     * @param resources resource
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tags.addAll(ledgerController.getTags());
        tagTable.setItems(tags);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        expectedColumn.setCellValueFactory(new PropertyValueFactory<>("expectedValue"));
        actualColumn.setCellValueFactory(new PropertyValueFactory<>("actual"));
    }
}