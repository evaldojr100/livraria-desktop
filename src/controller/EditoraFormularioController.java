package controller;

import dao.EditoraDAO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Editora;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class EditoraFormularioController implements Initializable {
    @FXML
    private TableView<Editora> tabela_editoras;
    @FXML
    private TableColumn<Editora,String> tb_nome;
    @FXML
    private TableColumn<Editora,Integer> tb_id;
    @FXML
    private TableColumn<Editora,String> tb_site;
    @FXML
    private TableColumn<Editora,String> tb_endereco;
    @FXML
    private TableColumn<Editora,String> tb_bairro;
    @FXML
    private TableColumn<Editora,String> tb_telefone;
    @FXML
    private TableColumn<Editora,String> tb_municipio;

    private ObservableList<Editora> ObList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tabela_editoras=new TableView<>();
        tb_id.setCellValueFactory(new PropertyValueFactory<>("id"));
//        tb_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
//        tb_site.setCellValueFactory(new PropertyValueFactory<>("site"));
//        tb_endereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
//        tb_bairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));
//        tb_telefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
//        tb_municipio.setCellValueFactory(new PropertyValueFactory<>("municipio"));

        List<Editora> lista = new EditoraDAO().listarTodos();

        ObList = FXCollections.observableArrayList(lista);
        tabela_editoras.setItems(ObList);

    }


}
