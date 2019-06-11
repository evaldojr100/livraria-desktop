package controller;

import dao.AutorDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import model.Autor;

import java.net.URL;
import java.util.ResourceBundle;


public class AutorFormularioController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txfId.setText(Integer.toString(new AutorDAO().proximo_id()));
        listar();
        txfNome.requestFocus();
        tabela_autor.setEditable(true);
        tb_id.setCellValueFactory( new PropertyValueFactory<>("id") );

        tb_nome.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getNome()) );
        tb_nome.setCellFactory(TextFieldTableCell.forTableColumn());
        tb_nome.setOnEditCommit(alteraNome);

        tb_email.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getEmail()) );
        tb_email.setCellFactory(TextFieldTableCell.forTableColumn());
        tb_email.setOnEditCommit(alteraEmail);

    }
    @FXML private TextField txfNome;
    @FXML private TextField txfEmail;
    @FXML private TextField txfId;
    @FXML private Button btnSalvar;
    @FXML private Button btnBuscar;
    @FXML private TableView<Autor> tabela_autor = new TableView<>();
    @FXML private TableColumn<Autor,Integer> tb_id = new TableColumn<>("id");
    @FXML private TableColumn<Autor,String> tb_nome = new TableColumn<>("nome");
    @FXML private TableColumn<Autor,String> tb_email = new TableColumn<>("email");
    Autor autor = new Autor();
    AutorDAO autordao = new AutorDAO();

    private EventHandler<TableColumn.CellEditEvent<Autor, String> > alteraNome = evt -> {
        (evt.getTableView().getItems().get(evt.getTablePosition().getRow())).setNome(evt.getNewValue());
        autordao.alterar( (evt.getTableView().getItems().get(evt.getTablePosition().getRow())));
    };

    private EventHandler<TableColumn.CellEditEvent<Autor, String> > alteraEmail = evt -> {
        (evt.getTableView().getItems().get(evt.getTablePosition().getRow())).setEmail(evt.getNewValue());
        autordao.alterar((evt.getTableView().getItems().get(evt.getTablePosition().getRow())));
    };

    public void salvar(){
        if(txfNome.getText()==null || txfEmail.getText()==null){
            Alert mensagem = new Alert(Alert.AlertType.ERROR);
            mensagem.setTitle("Cadastro de Autores");
            mensagem.setHeaderText("Valores não inseridos ou com problema");
            mensagem.showAndWait();
        }else {
            Autor autor = new Autor();
            autor.setNome(txfNome.getText());
            autor.setEmail(txfEmail.getText());
            new AutorDAO().inserir(autor);

            Alert mensagem = new Alert(Alert.AlertType.INFORMATION);
            mensagem.setTitle("Cadastro de Autores");
            mensagem.setHeaderText("Autor Cadastrado com sucesso");
            mensagem.setContentText("Autor: " + txfNome.getText() + "\nEmail: " + txfEmail.getText());
            mensagem.showAndWait();
            listar();
            limparCampos();
        }
    }
    public void listar(){
        try{
            System.out.println("Iniciando listar Autores");
            System.out.println("Itens Buscados:"+new AutorDAO().listarTodos().size());
            tb_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            tb_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            tb_email.setCellValueFactory(new PropertyValueFactory<>("email"));
            tabela_autor.setItems(new AutorDAO().listarTodos());


            EventHandler<MouseEvent> clickListener = evt -> {

                autor=tabela_autor.getSelectionModel().getSelectedItem();
                System.out.println("Selecionado: " + tabela_autor.getSelectionModel().getSelectedItem().getNome());
            };
            tabela_autor.setOnMouseClicked(clickListener);


        }catch(Exception e){
            System.out.println(e);
            throw new RuntimeException(e);
        }
        System.out.println("Finalizando Listar Autores");
    }
    public void deletar(){

        Alert mensagem = new Alert(Alert.AlertType.CONFIRMATION);
        mensagem.setTitle("Cadastro de Autores");
        mensagem.setHeaderText("Autor Deletado com sucesso");
        mensagem.setContentText("Autor: " + autor.getNome() + "\nEmail: " + autor.getEmail());
        mensagem.showAndWait();
        new AutorDAO().deletar(autor.getId());
        listar();
    }
    private void limparCampos() {
        txfNome.setText("");
        txfEmail.setText("");
        txfNome.requestFocus();
    }



}
