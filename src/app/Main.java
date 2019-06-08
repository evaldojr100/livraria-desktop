package app;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Editora_Formulario.fxml"));
        primaryStage.setTitle("Livraria Do Gabriel");
        primaryStage.setScene(new Scene(root, 650, 430));
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
