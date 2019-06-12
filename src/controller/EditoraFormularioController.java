package controller;

import dao.EditoraDAO;
import dao.EstadoDAO;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Editora;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.input.MouseEvent;
import model.Estado;
import model.Municipio;

public class EditoraFormularioController implements Initializable {
    @FXML private TableView<Editora> tabela_editoras = new TableView<>();
    @FXML private TableColumn<Editora,Integer> tb_id = new TableColumn<>("id");
    @FXML private TableColumn<Editora,String> tb_nome = new TableColumn<>("nome");
    @FXML private TableColumn<Editora,String> tb_site = new TableColumn<>("site");
    @FXML private TableColumn<Editora,String> tb_telefone =  new TableColumn<>("telefone");
    @FXML private TableColumn<Editora,String> tb_endereco =  new TableColumn<>("endereco");
    @FXML private TableColumn<Editora,String> tb_bairro =  new TableColumn<>("bairro");
    @FXML private TableColumn<Editora, Municipio> tb_municipio =  new TableColumn<>("municipio_id");
    @FXML private TableColumn<Editora,Estado> tb_estado =  new TableColumn<>("uf");
    @FXML private ComboBox<Estado> cmb_estado = new ComboBox();
    Editora editora = new Editora();
    EditoraDAO editoraDao = new EditoraDAO();
    EstadoDAO estadoDAO =  new EstadoDAO();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editoraDao.proximo_id();
        listar();

    }

    public void listar() {
        try{
            System.out.println("Iniciou Pesquisa");
            System.out.println("Itens Encontrados:" + new EditoraDAO().listarTodos().size());
            tb_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            tb_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            tb_site.setCellValueFactory(new PropertyValueFactory<>("site"));
            tb_telefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
            tb_endereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
            tb_bairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));



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
