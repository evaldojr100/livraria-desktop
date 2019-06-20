package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;


public class LibrariaMenuController {

    public void viewEditora(){

}
    public void viewLivro(){

    }

    @FXML  public void viewAutor(ActionEvent event) throws Exception {
        try{
            Stage stage = null;
            Parent root = FXMLLoader.load(getClass().getResource("/view/autor_formulario.fxml"));
            Scene scene = new Scene(root, 400, 240);
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
}
