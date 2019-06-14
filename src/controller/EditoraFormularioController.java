package controller;

import dao.EditoraDAO;
import dao.EstadoDAO;
import dao.MunicipioDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import model.Autor;
import model.Editora;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.input.MouseEvent;
import model.Estado;
import model.Municipio;
import javafx.util.Callback;

public class EditoraFormularioController implements Initializable {
    // Itens Da TableView
    @FXML private TableView<Editora> tabela_editoras = new TableView<>();
    @FXML private TableColumn<Editora, Integer> tb_id = new TableColumn<>("id");
    @FXML private TableColumn<Editora, String> tb_nome = new TableColumn<>("nome");
    @FXML private TableColumn<Editora, String> tb_site = new TableColumn<>("site");
    @FXML private TableColumn<Editora, String> tb_telefone = new TableColumn<>("telefone");
    @FXML private TableColumn<Editora, String> tb_endereco = new TableColumn<>("endereco");
    @FXML private TableColumn<Editora, String> tb_bairro = new TableColumn<>("bairro");
    @FXML private TableColumn<Editora, Municipio> tb_municipio = new TableColumn<>("municipio_id");
    @FXML private TableColumn<Editora,Estado> tb_estado =  new TableColumn<>("uf");

    // Text Field's
    @FXML private TextField txt_id;
    @FXML private TextField txt_nome;
    @FXML private TextField txt_site;
    @FXML private TextField txt_telefone;
    @FXML private TextField txt_bairro;
    @FXML private TextField txt_endereco;

    // Combo Boxes
    @FXML private ComboBox<Estado> cb_estado = new ComboBox();
    @FXML private ComboBox<Municipio> cb_municipio = new ComboBox<>();

    // Objetos Iniciados
    Editora editora = new Editora();
    EditoraDAO editoraDao = new EditoraDAO();
    EstadoDAO estadoDAO = new EstadoDAO();
    Estado estado = new Estado();
    Municipio municipio = new Municipio();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //txt_id.setEditable(false);
        txt_id.setDisable(true);
        txt_nome.requestFocus();
        txt_nome.setFocusTraversable(true);
        cb_municipio.setDisable(true);
        txt_id.setText(Integer.toString(estadoDAO.proximo_id()));
        editoraDao.proximo_id();
        listar();
        popular_estado();
        popular_municipio();
        tabela_editoras.setEditable(true);

        tb_nome.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getNome()) );
        tb_nome.setCellFactory(TextFieldTableCell.forTableColumn());
        tb_nome.setOnEditCommit(alteraNome);

        tb_site.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getSite()) );
        tb_site.setCellFactory(TextFieldTableCell.forTableColumn());
        tb_site.setOnEditCommit(alteraSite);

        tb_bairro.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getBairro()) );
        tb_bairro.setCellFactory(TextFieldTableCell.forTableColumn());
        tb_bairro.setOnEditCommit(alteraBairro);

        tb_endereco.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getEndereco()) );
        tb_endereco.setCellFactory(TextFieldTableCell.forTableColumn());
        tb_endereco.setOnEditCommit(alteraEndereco);

        tb_telefone.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getTelefone()) );
        tb_telefone.setCellFactory(TextFieldTableCell.forTableColumn());
        tb_telefone.setOnEditCommit(alteraTelefone);






    }

    public void popular_estado() {
        cb_estado.cellFactoryProperty();
        cb_estado.setOnMouseClicked(cb_estado_clicked);
        cb_estado.setCellFactory(cb_estado_format);
        cb_estado.setOnAction(mudar_cb_estado);
    }

    public void popular_municipio(){
        cb_municipio.cellFactoryProperty();
        cb_municipio.setOnMouseClicked(cb_municipio_clicked);
        cb_municipio.setCellFactory(cb_municipio_format);
        cb_municipio.setOnAction(mudar_cb_municipio);

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
            tabela_editoras.setOnMouseClicked(editoraSelecionada);

        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);

        }
        System.out.println("Finalizou a Pesquisa");


    }

    //-------------------- Açoes do Combo Box ------------------------------------

    //Printar Editora funcionando
    EventHandler<MouseEvent> editoraSelecionada = evt -> {

        editora = tabela_editoras.getSelectionModel().getSelectedItem();
        System.out.println("Selecionado: " + tabela_editoras.getSelectionModel().getSelectedItem().getNome());
    };
    //Ao clicar no Combo Box do estado puxa a lista
    EventHandler<MouseEvent> cb_estado_clicked = evt -> {
        cb_municipio.setDisable(true);
        System.out.println("\nEntrou Estado");
        System.out.println("Valores retornados:" + new EstadoDAO().listarTodos().size());
        cb_estado.setItems(new EstadoDAO().listarTodos());
        System.out.println("\nSaiu Estado");
    };
    // ao clicar em um item deixar na comboBox
    EventHandler<ActionEvent> mudar_cb_estado = evt ->{
        estado =  cb_estado.getSelectionModel().getSelectedItem();
        cb_municipio.setDisable(false);
        cb_municipio.setVisibleRowCount(new MunicipioDAO().listarEstado(estado).size());
        cb_estado.setConverter(new StringConverter<Estado>() {
            @Override
            public String toString(Estado estado) {
                return estado.getUf();
            }

            @Override
            public Estado fromString(String s) {
                return null;
            }

        })
        ;
        if(estado != null){
            System.out.println("Estado Selecionado: "+estado.getUf());
        }
    };
    //Ao clicar no Combo Box do municipio puxa a lista
    EventHandler<MouseEvent> cb_municipio_clicked = evt ->{
        cb_municipio.setItems(null);
        System.out.println("\n Entrou Municipios");
        System.out.println("Valores Retornados:" + new MunicipioDAO().listarEstado(estado).size());
        cb_municipio.setItems(new MunicipioDAO().listarEstado(estado));
        System.out.println("Saiu Municipios\n");


    };
    //Ao clicar no Municipio deixar na ComboxBox
    EventHandler<ActionEvent> mudar_cb_municipio = evt ->{
        municipio=cb_municipio.getSelectionModel().getSelectedItem();
        cb_municipio.setConverter(new StringConverter<Municipio>() {
            @Override
            public String toString(Municipio municipio) {  return municipio.getNome();  }

            @Override
            public Municipio fromString(String s) { return null;  }
        });
            if(municipio!=null)
                System.out.println("Municipio Selecionado:"+municipio.getNome());
    };

    //-------------------- Alterar ----------------------------

    //Alterar Nome
    private EventHandler<TableColumn.CellEditEvent<Editora, String> > alteraNome = evt -> {
        (evt.getTableView().getItems().get(evt.getTablePosition().getRow())).setNome(evt.getNewValue());
        editoraDao.alterar( (evt.getTableView().getItems().get(evt.getTablePosition().getRow())));
        listar();
    };
    //Altera Site
    private EventHandler<TableColumn.CellEditEvent<Editora, String> > alteraSite = evt -> {
        (evt.getTableView().getItems().get(evt.getTablePosition().getRow())).setSite(evt.getNewValue());
        editoraDao.alterar((evt.getTableView().getItems().get(evt.getTablePosition().getRow())));
    };
    //Altera Telefone
    private EventHandler<TableColumn.CellEditEvent<Editora, String> > alteraTelefone = evt -> {
        (evt.getTableView().getItems().get(evt.getTablePosition().getRow())).setTelefone(evt.getNewValue());
        editoraDao.alterar( (evt.getTableView().getItems().get(evt.getTablePosition().getRow())));
    };
    //Altera Bairro
    private EventHandler<TableColumn.CellEditEvent<Editora, String> > alteraBairro = evt -> {
        (evt.getTableView().getItems().get(evt.getTablePosition().getRow())).setBairro(evt.getNewValue());
        editoraDao.alterar((evt.getTableView().getItems().get(evt.getTablePosition().getRow())));
    };
    //Altera Endereço
    private EventHandler<TableColumn.CellEditEvent<Editora, String> > alteraEndereco = evt -> {
        (evt.getTableView().getItems().get(evt.getTablePosition().getRow())).setEndereco(evt.getNewValue());
        editoraDao.alterar( (evt.getTableView().getItems().get(evt.getTablePosition().getRow())));
    };

    private Callback<ListView<Estado>, ListCell<Estado>> cb_estado_format = evt ->{
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
    private Callback<ListView<Municipio>, ListCell<Municipio>> cb_municipio_format = evt ->{
        return new ListCell<Municipio>() {
            @Override
            protected void updateItem(Municipio item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    setText(item.getNome());
                }
            }
        };

    };


    public void salvar(){
            Editora editora = new Editora();
            editora.setNome(txt_nome.getText());
            editora.setSite(txt_site.getText());
            editora.setBairro(txt_bairro.getText());
            editora.setEndereco(txt_endereco.getText());
            editora.setTelefone(txt_telefone.getText());
            editora.setMunicipio(municipio);
            txt_endereco.setText("");

            new EditoraDAO().inserir(editora);

            Alert mensagem = new Alert(Alert.AlertType.INFORMATION);
            mensagem.setTitle("Cadastro de Autores");
            mensagem.setHeaderText("Autor Cadastrado com sucesso");
            mensagem.setContentText("Editora: " + txt_nome.getText());
            mensagem.showAndWait();
            listar();


    }
    public void deletar(){
        editoraDao.deletar(editora.getId());
        listar();
    }
}
