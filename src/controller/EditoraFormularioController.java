package controller;

import dao.EditoraDAO;
import dao.EstadoDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Editora;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.input.MouseEvent;
import model.Estado;
import model.Municipio;
import javafx.util.Callback;

public class EditoraFormularioController implements Initializable {
    @FXML
    private TableView<Editora> tabela_editoras = new TableView<>();
    @FXML
    private TableColumn<Editora, Integer> tb_id = new TableColumn<>("id");
    @FXML
    private TableColumn<Editora, String> tb_nome = new TableColumn<>("nome");
    @FXML
    private TableColumn<Editora, String> tb_site = new TableColumn<>("site");
    @FXML
    private TableColumn<Editora, String> tb_telefone = new TableColumn<>("telefone");
    @FXML
    private TableColumn<Editora, String> tb_endereco = new TableColumn<>("endereco");
    @FXML
    private TableColumn<Editora, String> tb_bairro = new TableColumn<>("bairro");
    @FXML
    private TableColumn<Editora, Municipio> tb_municipio = new TableColumn<>("municipio_id");
    //@FXML private TableColumn<Editora,Estado> tb_estado =  new TableColumn<>("uf");
    @FXML
    private ComboBox<Estado> cb_estado = new ComboBox();
    Editora editora = new Editora();
    EditoraDAO editoraDao = new EditoraDAO();
    EstadoDAO estadoDAO = new EstadoDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editoraDao.proximo_id();
        listar();
        popular_estado();

    }

    public void popular_estado() {
        cb_estado.cellFactoryProperty();
        cb_estado.setOnMouseClicked(cb_estado_clicked);
        cb_estado.setCellFactory(ComboFactory);

    }

    public void listar() {
        try {
            System.out.println("Iniciou Pesquisa");
            System.out.println("Itens Encontrados:" + new EditoraDAO().listarTodos().size());
            tb_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            tb_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            tb_site.setCellValueFactory(new PropertyValueFactory<>("site"));
            tb_telefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
            tb_endereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
            tb_bairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));


            tabela_editoras.setItems(new EditoraDAO().listarTodos());


            tabela_editoras.setOnMouseClicked(clickListener);

        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);

        }
        System.out.println("Finalizou a Pesquisa");


    }

    EventHandler<MouseEvent> clickListener = evt -> {

        editora = tabela_editoras.getSelectionModel().getSelectedItem();
        System.out.println("Selecionado: " + tabela_editoras.getSelectionModel().getSelectedItem().getNome());
    };
    EventHandler<MouseEvent> cb_estado_clicked = evt -> {
        System.out.println("\nEntrou Estado");
        System.out.println("Valores retornados:" + new EstadoDAO().listarTodos().size());


        cb_estado.setItems(new EstadoDAO().listarTodos());
        System.out.println("\nSaiu Estado");
    };
    private Callback<ListView<Estado>, ListCell<Estado>> ComboFactory = evt ->
    {
        return new ListCell<Estado>() {
            @Override
            protected void updateItem(Estado item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    setText(item.getUf());
                }
            }
        };

    };
}
