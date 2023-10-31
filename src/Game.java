import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.util.Optional;

public class Game {
    public void startGame(ConnectionType type) {
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("Start " + type);

        GridPane gridPane = new GridPane();
        int i = 1;
        Label label1 = new Label("IP-adress: ");
        TextField textField1 = new TextField();
        if (type == ConnectionType.CLIENT) {
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
            public String[] call(ButtonType b) {

                if (b == buttonTypeOk) {
                    if (type == ConnectionType.CLIENT)
                        return new String[]{textField1.getText(), textField2.getText()};
                    else
                        return new String[]{textField2.getText()};
                }

                return null;
            }
        });

        Optional<String[]> result = dialog.showAndWait();

        if (result.isPresent()) {
            if (type == ConnectionType.CLIENT) {
                System.out.println("Starter client");
                System.out.print("IP-adress: " + result.get()[0]);
                System.out.println(", port: " + result.get()[1]);
            } else {
                System.out.println("Starter server");
                System.out.println("Port: " + result.get()[0]);
            }
        }
    }
}
