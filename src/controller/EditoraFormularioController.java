package controller;

import dao.EditoraDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Editora;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.input.MouseEvent;

public class EditoraFormularioController implements Initializable {
    @FXML private TableView<Editora> tabela_editoras = new TableView<>();
    @FXML private TableColumn<Editora,Integer> col_id = new TableColumn<>("id");
    @FXML private TableColumn<Editora,String> col_nome = new TableColumn<>("nome");
    @FXML private TableColumn<Editora,String> col_site = new TableColumn<>("site");
    @FXML private TableColumn<Editora,String> col_telefone =  new TableColumn<>("telefone");
    Editora editora= new Editora();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listar();
    }

    public void listar() {
        try{
            System.out.println("Iniciou Pesquisa");
            System.out.println("Itens Encontrados:" + new EditoraDAO().listarTodos().size());
            col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            col_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            col_site.setCellValueFactory(new PropertyValueFactory<>("site"));
            col_telefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
            tabela_editoras.setItems(new EditoraDAO().listarTodos());

            EventHandler<MouseEvent> clickListener = evt -> {

                editora=tabela_editoras.getSelectionModel().getSelectedItem();
                    System.out.println("Selecionado: " + tabela_editoras.getSelectionModel().getSelectedItem().getNome());
            };

            tabela_editoras.setOnMouseClicked(clickListener);

        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException(e);

        }
        System.out.println("Finalizou a Pesquisa");


    }



}
