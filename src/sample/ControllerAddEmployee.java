package sample;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerAddEmployee implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button addButton;
    @FXML
    private TextField firstNameTextField, lastNameTextField, emailTextField, phoneNumberTextField, socialSecurityNumberTextField;
    @FXML
    private ChoiceBox choiceBox;
    @FXML
    private CheckBox checkBox1, checkBox2;
    @FXML
    private Label successLabel, successLabel1;

    private String department;

    private ArrayList<String> underDepartmentList = new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image mediumBackgroundimage = new Image("resourses/mediumBackground.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(mediumBackgroundimage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        anchorPane.setBackground(new Background(backgroundImage));

        Image checkImage = new Image("resourses/check.png");
        ImageView checkImageView = new ImageView(checkImage);
        addButton.setGraphic(checkImageView);
        addButton.setStyle("-fx-background-color: TRANSPARENT");

        firstNameTextField.addEventFilter(KeyEvent.KEY_TYPED, letter_Validation(20));
        lastNameTextField.addEventFilter(KeyEvent.KEY_TYPED, letter_Validation(20));

        choiceBox.autosize();
    }

    @FXML
    private EventHandler<KeyEvent> letter_Validation(final Integer max_Lengh) {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                TextField textField = (TextField) event.getSource();
                if (textField.getText().length() >= max_Lengh) {
                    event.consume();
                }
                if (event.getCharacter().matches("[A-Za-z]")) {
                } else {
                    event.consume();
                }
            }
        };
    }

    @FXML
    private void handleAddButton(ActionEvent event) {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        String socialSecurityNumber = socialSecurityNumberTextField.getText();
        String role = "Employee";
        String department = choiceBox.getSelectionModel().getSelectedItem().toString();

        String firstLetterFirstName;
        firstLetterFirstName = String.valueOf(firstName.charAt(0));

        String firstLetterLastName;
        firstLetterLastName = String.valueOf(lastName.charAt(0));
        String initials = firstLetterFirstName + firstLetterLastName;

        Person person = new Person(firstName, lastName, initials, role, email, phoneNumber, department, socialSecurityNumber);


            DBConnect dbConnect = new DBConnect();
            dbConnect.addPerson(person);

            successLabel.setText("Successfully added "+person.getFirstName());
            successLabel1.setText(" to the database.");



    }

    @FXML
    private void handleHoverEffect(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color: LIGHTGRAY");
    }

    @FXML
    private void handleNoHoverEffect(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color: TRANSPARENT");
    }

    @FXML
    private void handleCheckBoxes(ActionEvent event){
        CheckBox checkBox = (CheckBox) event.getSource();
        if (checkBox.getId().equals("checkBox1")){
            department = "Lanternan";
            checkBox2.setSelected(false);
        }else if (checkBox.getId().equals("checkBox2")){
            department = "Åhaga";
            checkBox1.setSelected(false);

        }
        setChoiseBox(department);
    }

    private void setChoiseBox(String department) {
        DBConnect dbConnect = new DBConnect();
        underDepartmentList = (dbConnect.getUnderDepartments(department));

        if (underDepartmentList.size() == 5) {
            choiceBox.setItems(FXCollections.observableArrayList(underDepartmentList.get(0), underDepartmentList.get(1), underDepartmentList.get(2), underDepartmentList.get(3), underDepartmentList.get(4)));
        }else if (underDepartmentList.size() == 4){
            choiceBox.setItems(FXCollections.observableArrayList(underDepartmentList.get(0), underDepartmentList.get(1), underDepartmentList.get(2), underDepartmentList.get(3)));
        }
    }
}
