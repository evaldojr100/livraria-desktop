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
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import model.Editora;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.input.MouseEvent;
import model.Estado;
import model.Municipio;

public class EditoraFormularioController implements Initializable {
    // Itens Da TableView
    @FXML private TableView<Editora> tabela_editoras = new TableView<>();
    @FXML private TableColumn<Editora, Integer> tb_id = new TableColumn<>("id");
    @FXML private TableColumn<Editora, String> tb_nome = new TableColumn<>("nome");
    @FXML private TableColumn<Editora, String> tb_site = new TableColumn<>("site");
    @FXML private TableColumn<Editora, String> tb_telefone = new TableColumn<>("telefone");
    @FXML private TableColumn<Editora, String> tb_endereco = new TableColumn<>("endereco");
    @FXML private TableColumn<Editora, String> tb_bairro = new TableColumn<>("bairro");
    @FXML private TableColumn<Editora, String> tb_municipio = new TableColumn<>("municipio_id");
    @FXML private TableColumn<Editora, String> tb_estado =  new TableColumn<>("estado_id");

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
        config_edit_tabela();



    }

    private void config_edit_tabela(){
        tb_nome.setCellFactory(TextFieldTableCell.forTableColumn());
        tb_site.setCellFactory(TextFieldTableCell.forTableColumn());
        tb_endereco.setCellFactory(TextFieldTableCell.forTableColumn());
        tb_bairro.setCellFactory(TextFieldTableCell.forTableColumn());
        tb_telefone.setCellFactory(TextFieldTableCell.forTableColumn());


    }

    public void popular_estado() {
        cb_estado.cellFactoryProperty();
        cb_estado.setOnMouseClicked(cb_estado_clicked);
        cb_estado.setOnAction(mudar_cb_estado);
    }

    public void popular_municipio(){
        cb_municipio.cellFactoryProperty();
        cb_municipio.setOnMouseClicked(cb_municipio_clicked);
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
            tb_municipio.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getMunicipio().getNome()));
            tb_estado.setCellValueFactory((param)-> new SimpleStringProperty(param.getValue().getEstado().getUf()));



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
        estado=tabela_editoras.getSelectionModel().getSelectedItem().getEstado();
        municipio=tabela_editoras.getSelectionModel().getSelectedItem().getMunicipio();
        tb_municipio.setCellFactory(ComboBoxTableCell.forTableColumn(new MunicipioDAO().listarEstado(estado)));
        tb_estado.setCellFactory(ComboBoxTableCell.forTableColumn(new EstadoDAO().listarTodos()));
        System.out.println("Selecionado: " + tabela_editoras.getSelectionModel().getSelectedItem().getNome());
    };
    //Ao clicar no Combo Box do estado puxa a lista
    EventHandler<MouseEvent> cb_estado_clicked = evt -> {
        cb_municipio.setItems(null);
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

    public void alteraNome(TableColumn.CellEditEvent<Editora, String> editNome) {
        editora.setNome(editNome.getNewValue());
        editoraDao.alterar(editora);
        listar();
    }

    public void alteraSite(TableColumn.CellEditEvent<Editora, String> edit_site) {
        editora.setSite(edit_site.getNewValue());
        editoraDao.alterar(editora);
        listar();
    }

    public void alteraTelefone(TableColumn.CellEditEvent<Editora, String> edit_telefone) {
        editora.setTelefone(edit_telefone.getNewValue());
        editoraDao.alterar(editora);
        listar();
    }

    public void alteraEndereco(TableColumn.CellEditEvent<Editora, String> edit_endereco) {
        editora.setEndereco(edit_endereco.getNewValue());
        editoraDao.alterar(editora);
        listar();
    }

    public void alteraBairro(TableColumn.CellEditEvent<Editora, String> edit_bairro) {
        editora.setBairro(edit_bairro.getNewValue());
        editoraDao.alterar(editora);
        listar();
    }

    public void alteraMunicipio(TableColumn.CellEditEvent<Editora, Municipio> edit_municipio) {
        editora.setMunicipio(edit_municipio.getNewValue());
        editoraDao.alterar(editora);
        listar();
    }

    public void alteraEstado(TableColumn.CellEditEvent<Editora,Estado>edit_estado){
        editora.setEstado(edit_estado.getNewValue());
        editora.setMunicipio(new MunicipioDAO().first_munic(editora.getEstado()));
        editoraDao.alterar(editora);
        listar();
    }
}
