package View;

import Controller.ConnectionType;
import Controller.GameController;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

/** Written by Morten Sandgrav **/

public class Dialog {

    public void show(GameController gameController) {
        javafx.scene.control.Dialog<String[]> dialog = new javafx.scene.control.Dialog<>();
        dialog.setTitle("Start " + gameController.getConnectionType());

        GridPane gridPane = new GridPane();
        int i = 1;
        Label label1 = new Label("IP-adress: ");
        TextField textField1 = new TextField();
        if (gameController.getConnectionType() == ConnectionType.CLIENT) {
            try {
                textField1.setText(InetAddress.getLocalHost().getHostAddress());
            } catch(UnknownHostException e) {
                textField1.setText("");
            }
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
                    if (gameController.getConnectionType() == ConnectionType.CLIENT)
                        return new String[]{textField1.getText(), textField2.getText()};
                    else
                        return new String[]{textField2.getText()};
                }

                return null;
            }
        });

        Optional<String[]> result = dialog.showAndWait();
//        String[] strings = new String[]{"i shot 6c", "h shot 7b", "m shot 6g", "s shot 5f", "game over"};

        Thread thread;

        if (result.isPresent()) {
            if (gameController.getConnectionType() == ConnectionType.CLIENT) {
                System.out.println("Starter client");
                gameController.setIpAdress(result.get()[0]);
                gameController.setPort(Integer.parseInt(result.get()[1]));
                System.out.print("IP-adress: " + gameController.getIpAdress());
                System.out.println(", port: " + gameController.getPort());
                thread = new Thread(gameController.getStartClient());
                thread.start();
            } else {
                System.out.println("Starter server");
                gameController.setPort(Integer.parseInt(result.get()[0]));
                System.out.println("Port: " + result.get()[0]);
                thread = new Thread(gameController.getStartServer());
                thread.start();
            }
        }
    }


    public static void showMessage(String message){
        javafx.scene.control.Dialog<String> dialog = new javafx.scene.control.Dialog();

        dialog.setTitle("Message");


        dialog.setContentText(message);

        ButtonType buttonType = new ButtonType("Ok",ButtonBar.ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().add(buttonType);

        dialog.show();
    }
}
