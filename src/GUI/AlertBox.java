package GUI;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class AlertBox {

    public static void display(String title, String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(500);
        Label label = new Label(message);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());
        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);
        Scene alertScene = new Scene(layout);
        window.setScene(alertScene);
        window.showAndWait();
    }
}
