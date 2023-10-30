import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class Game {
    public void startGame(String type) {
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("Start " + type);

        GridPane gridPane = new GridPane();
        int i = 1;
        if (type.equals("client")) {
            Label label1 = new Label("IP-adress: ");
            TextField textField1 = new TextField();
            gridPane.add(label1, 1, i);
            gridPane.add(textField1, 2, i);
            i++;
        }
        Label label2 = new Label("Port: ");
        TextField textField2 = new TextField();
        gridPane.add(label2, 1, i);
        gridPane.add(textField2, 2, i);

        dialog.getDialogPane().setContent(gridPane);

        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Avbryt", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, buttonTypeCancel);
        dialog.setResultConverter(new Callback<ButtonType, String[]>() {
            @Override
            public PhoneBook call(ButtonType b) {

                if (b == buttonTypeOk) {

                    return new PhoneBook(text1.getText(), text2.getText());
                }

                return null;
            }
        });

        Optional<PhoneBook> result = dialog.showAndWait();

        if (result.isPresent()) {

            actionStatus.setText("Result: " + result.get());
        }
    }
}
