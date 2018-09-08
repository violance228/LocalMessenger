import javafx.fxml.FXML;

public class ChatController {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> nameColunmn;
    @FXML
    private TableColumn<Person, String> surnameColumn;

    @FXML
    private Label nameLabel;
    @FXML
    private Label surnameLable;
    @FXML
    private Label streetLable;
    @FXML
    private Label postalCodeLable;
    @FXML
    private Label cityLable;
    @FXML
    private Label birthdayLable;

    private ClientServerMain main;

    public Controller() { }

    @FXML
    private void initialize() {
        nameColunmn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        surnameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        showPersonDetails(null);

        personTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> showPersonDetails(newValue)));

    }

    public void setMain(Main main) {
        this.main = main;

        personTable.setItems(main.getPersonData());
    }

    public void showPersonDetails(Person person) {
        if(person != null) {
            nameLabel.setText(person.getName());
            surnameLable.setText(person.getSurname());
            streetLable.setText(person.getStreet());
            postalCodeLable.setText(Integer.toString(person.getPostalCode()));
            cityLable.setText(person.getCity());
            birthdayLable.setText(String.valueOf(person.getBirthday()));
        } else {
            nameLabel.setText("");
            surnameLable.setText("");
            streetLable.setText("");
            postalCodeLable.setText("");
            cityLable.setText("");
            birthdayLable.setText("");
        }
    }

    @FXML
    private void handleDeletePerson() {

        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        try {
            if (selectedIndex >= 0) {
                personTable.getItems().remove(selectedIndex);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(main.getPrimaryStage());
                alert.setTitle("No selection");
                alert.setHeaderText("No Person Selected");
                alert.setContentText("Please select a person in the table.");

                alert.showAndWait();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleNewPerson() {
        Person tempPersone = new Person();

        boolean okClicked = main.showPersonEditDialog(tempPersone);
        if (okClicked) {
            main.getPersonData().add(tempPersone);
        }
    }

    @FXML
    private void handleEditPerson() {
        Person selectedPersone = personTable.getSelectionModel().getSelectedItem();
        if (selectedPersone != null) {
            boolean okClicked = main.showPersonEditDialog(selectedPersone);
            if (okClicked) {
                showPersonDetails(selectedPersone);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a persone in the table.");

            alert.showAndWait();
        }
    }
}