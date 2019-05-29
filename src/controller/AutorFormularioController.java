package controller;

import dao.AutorDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Autor;

public class AutorFormularioController {

    @FXML private TextField txfNome;
    @FXML private TextField txfEmail;
    @FXML private Button btnSalvar;

    public void salvar(){
        Autor autor = new Autor();
        autor.setNome(txfNome.getText());
        autor.setEmail(txfEmail.getText());
        new AutorDAO().inserir(autor);

        Alert mensagem = new Alert(Alert.AlertType.INFORMATION);
        mensagem.setTitle("Cadastro de Autores");
        mensagem.setHeaderText("Autor Cadastrado com sucesso");
        mensagem.setContentText("Autor: "+txfNome.getText()+"\nEmail: "+txfEmail.getText());
        mensagem.showAndWait();
        limparCampos();

    }

    private void limparCampos() {
        txfNome.setText("");
        txfEmail.setText("");
        txfNome.requestFocus();
    }


}
