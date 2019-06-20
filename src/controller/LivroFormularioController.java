package controller;

import dao.EditoraDAO;
import dao.LivroDAO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import model.*;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.scene.input.MouseEvent;

public class LivroFormularioController implements Initializable {

    //Text Fields
    @FXML private TextField txt_id;
    @FXML private TextField txt_titulo;
    @FXML private TextField txt_preco;
    @FXML private TextField txt_quantidade;
    @FXML private TextField txt_data_lancamento;



    //Combo Boxes
    @FXML private ComboBox cb_editoras;

    //Inicialização atributos da Tabela Livros
    @FXML TableView tabela_livros = new TableView();
    @FXML TableColumn<Livro, Integer> tb_id = new TableColumn<>("id");
    @FXML TableColumn<Livro, String> tb_titulo = new TableColumn<>("titulo");
    @FXML TableColumn<Livro, Float> tb_preco = new TableColumn<>("preco");
    @FXML TableColumn<Livro, Integer> tb_quantidade = new TableColumn<>("quantidade");
    @FXML TableColumn<Livro, String> tb_data_lancamento = new TableColumn<>("data_lancamento");
    @FXML TableColumn<Livro, String> tb_editora = new TableColumn<>("editora_id");

    //Inicialização atributos da Tabela autores
    @FXML TableView tabela_autores = new TableView();
    @FXML TableColumn<Autor,String> tb_autor  = new TableColumn<>("autor_id");

    //Instanciação de Objetos
    Livro livro = new Livro();
    LivroDAO livroDao = new LivroDAO();
    Editora editora = new Editora();
    Autor autor = new Autor();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Deixar ID sem edição
        txt_id.setDisable(true);
        //Deixar o Titulo com o foco do teclado
        txt_titulo.requestFocus();
        txt_titulo.setFocusTraversable(true);
        //Puxar o Proximo ID do livro
        txt_id.setText(Integer.toString(livroDao.proximo_id()));
        //Printar Proximo ID
        System.out.println("Proximo item para ser implementado na tabela:" + livroDao.proximo_id());
        listar_livros();
        popular_editoras();
        tabela_livros.setEditable(true);

        tb_titulo.setCellFactory(TextFieldTableCell.forTableColumn());
        tb_preco.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        tb_quantidade.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tb_data_lancamento.setCellFactory(TextFieldTableCell.forTableColumn());
        tb_editora.setCellFactory(ComboBoxTableCell.forTableColumn(new EditoraDAO().listarTodos()));



    }
    public void popular_editoras() {
        cb_editoras.cellFactoryProperty();
        cb_editoras.setOnMouseClicked(cb_editora_clicked);
        cb_editoras.setOnAction(mudar_cb_editora);
    }
    public void limpar_campos(){
        txt_titulo.setText("");
        txt_data_lancamento.setText("");
        txt_preco.setText("");
        txt_quantidade.setText("");
        cb_editoras.setItems(null);
    }
    public void listar_livros() {
        System.out.println("Iniciou a Pesquisa Livros");
        System.out.println("Itens retornados na Pesquisa:" + livroDao.listarTodos().size());
        try {
            // pre-setando os valores da tabela
            tb_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            tb_titulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
            tb_preco.setCellValueFactory(new PropertyValueFactory<>("preco"));
            tb_quantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
            tb_data_lancamento.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getData_lancamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            tb_editora.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getEditora().getNome()));


            // fazendo a busca no banco de dados para a tabela
            tabela_livros.setItems(livroDao.listarTodos());

            //Fazendo a verificação se o objeto esta sendo selecionado
            tabela_livros.setOnMouseClicked(livroSelecionado);


        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }

        System.out.println("Encerrou a Pesquisa Livros");
    }
    public void listar_autores(Livro livro){
        if(livro != null){
            System.out.println("Iniciando Pesquisa de autores");
            System.out.println("Resultado da Pesquisa: "+new LivroDAO().ListarAutores(livro).size());
            try{
                tb_autor.setCellValueFactory(new PropertyValueFactory<>("nome"));
                tabela_autores.setItems(new LivroDAO().ListarAutores(livro));

                tabela_autores.setOnMouseClicked(autorSelecionado);

            }catch (Exception e){
                System.out.println(e);
                throw new RuntimeException(e);
            }
        }

    }
    //Evento do Mouse para Selecionar Autor na tabela autores
    EventHandler<MouseEvent> autorSelecionado = evt -> {
        autor = (Autor) tabela_autores.getSelectionModel().getSelectedItem();
        System.out.println("Selecionado: " + ((Autor) tabela_autores.getSelectionModel().getSelectedItem()).getNome());
    };
    //Evento do Mouse para selecionar Livro na tabela
    EventHandler<MouseEvent> livroSelecionado = evt -> {
        livro = (Livro) tabela_livros.getSelectionModel().getSelectedItem();
        System.out.println("Selecionado: " + ((Livro) tabela_livros.getSelectionModel().getSelectedItem()).getTitulo());
        listar_autores(livro);
    };
    //Evento que com o Clique do mouse abra a lista de Editoras
    EventHandler<MouseEvent> cb_editora_clicked = evt -> {
        System.out.println("\nEntrou Editoras");
        System.out.println("Valores retornados:" + new EditoraDAO().listarTodos().size());
        cb_editoras.setItems(new EditoraDAO().listarTodos());
        System.out.println("\nSaiu Editoras");
    };
    // ao clicar em um item deixar na comboBox
    EventHandler<ActionEvent> mudar_cb_editora = evt ->{
        editora =  (Editora)cb_editoras.getSelectionModel().getSelectedItem();
        cb_editoras.setConverter(new StringConverter<Editora>() {
            @Override
            public String toString(Editora editora) {
                return editora.getNome();
            }

            @Override
            public Editora fromString(String s) {
                return null;
            }

        });
        if(editora != null){
            System.out.println("Estado Selecionado: "+editora.getNome());
        }
    };

    // ao apertar no botão novo cadastro insere um novo item
    public void salvar() {

            Livro livro = new Livro();

            livro.setTitulo(txt_titulo.getText());
            livro.setPreco(Float.valueOf(txt_preco.getText()));
            livro.setQuantidade(Integer.valueOf(txt_quantidade.getText()));
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            livro.setData_lancamento(LocalDate.parse(txt_data_lancamento.getText(),formatador));
            System.out.println();
            livro.setEditora(editora);

            livroDao.inserir(livro);
            txt_id.setText(Integer.toString(livroDao.proximo_id()));

            Alert mensagem = new Alert(Alert.AlertType.INFORMATION);
            mensagem.setTitle("Cadastro de Livros");
            mensagem.setHeaderText("Livro Cadastrado com sucesso");
            mensagem.setContentText("Livro: " + txt_titulo.getText());
            mensagem.showAndWait();
            limpar_campos();
            listar_livros();

        }

    public void deletar() {
        livroDao.deletar(livro.getId());
        Alert mensagem = new Alert(Alert.AlertType.INFORMATION);
        mensagem.setTitle("Controle de Livros");
        mensagem.setHeaderText("Livro Deletado com sucesso");
        mensagem.showAndWait();
        listar_livros();
    }

    //Alterar da Tabela
    public void alteraTitulo(TableColumn.CellEditEvent<Livro,String> edit_titulo){
        livro.setTitulo(edit_titulo.getNewValue());
        livroDao.alterar(livro);
        listar_livros();
    }
    public void alteraPreco(TableColumn.CellEditEvent<Livro, Float> edit_preco) {
        livro.setPreco(edit_preco.getNewValue());
        livroDao.alterar(livro);
        listar_livros();
    }
    public void alteraQuantidade(TableColumn.CellEditEvent<Livro,Integer>edit_quuantidade){
        livro.setQuantidade(edit_quuantidade.getNewValue());
        livroDao.alterar(livro);
        listar_livros();
    }
    public void alteraData(TableColumn.CellEditEvent<Livro, String> edit_data) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        livro.setData_lancamento(LocalDate.parse(edit_data.getNewValue(),formatador));
        livroDao.alterar(livro);
        listar_livros();
    }
    public void alteraEditora(TableColumn.CellEditEvent<Livro, Editora> edit_editora) {
        livro.setEditora(edit_editora.getNewValue());
        livroDao.alterar(livro);
        listar_livros();
    }
}
