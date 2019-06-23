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
        try{
            Stage tela_autor  = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/Editora_Formulario.fxml"));
            tela_autor.setTitle("Adcionar Autores");
            tela_autor.setScene(new Scene(root, 850,600));
            tela_autor.show();
        }catch (IOException e){
            System.out.println(e);
            e.printStackTrace();
        }
}
    public void viewLivro(){
        try{
            Stage tela_autor  = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/Livro_Formulario.fxml"));
            tela_autor.setTitle("Adcionar Autores");
            tela_autor.setScene(new Scene(root, 910,600));
            tela_autor.show();
        }catch (IOException e){
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void viewAutor(){
        try{
            Stage tela_autor  = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/autor_formulario.fxml"));
            tela_autor.setTitle("Adcionar Autores");
            tela_autor.setScene(new Scene(root, 600,450));
            tela_autor.show();
        }catch (IOException e){
            System.out.println(e);
            e.printStackTrace();
        }
    }

}
