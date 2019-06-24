package controller;


import dao.AutorDAO;
import dao.LivroDAO;
import dao.Livro_AutorDAO;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Autor;
import model.Livro;
import java.net.URL;
import java.util.ResourceBundle;

public class LivroAutorFormularioController implements Initializable {

    //instanciando Itens das Tabelas
    @FXML TableView tabela_autor = new TableView();
    @FXML TableColumn<Autor, Integer> tb_id;
    @FXML TableColumn<Autor,String> tb_autor = new TableColumn<>("nome");
    @FXML Button btn_voltar;
    @FXML TextField txt_livro;


    Autor autor =  new Autor();
    Livro livro = new Livro_AutorDAO().buscar_temp();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txt_livro.setEditable(false);
        txt_livro.setText(livro.getTitulo());
        tabela_autor.setEditable(true);
        listar();
        System.out.println("Numero:"+livro.getId());
        System.out.println("livro selecionado: "+ livro.getTitulo());

    }

    public void listar(){
        try{
            System.out.println("Iniciando listar_livros Autores");
            System.out.println("Itens Buscados:"+new AutorDAO().listarTodos().size());
            tb_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            tb_autor.setCellValueFactory(new PropertyValueFactory<>("nome"));

            tabela_autor.setItems(new AutorDAO().listarTodos());


            EventHandler<MouseEvent> clickListener = evt -> {

                autor= (Autor) tabela_autor.getSelectionModel().getSelectedItem();
                System.out.println("Selecionado: " + ((Autor) tabela_autor.getSelectionModel().getSelectedItem()).getNome());
            };
            tabela_autor.setOnMouseClicked(clickListener);

        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
    public void link_livro_autor(){
        new LivroDAO().linkar_livro_autor(livro,autor);
        Alert mensagem = new Alert(Alert.AlertType.INFORMATION);
        mensagem.setTitle("Cadastro de Livros");
        mensagem.setHeaderText("Autor Adicionado no livro");
        mensagem.showAndWait();
    }
    public void fechar_tela(){
        Stage stage = (Stage) btn_voltar.getScene().getWindow();
        stage.close();
    }

    EventHandler<MouseEvent> autorSelecionado = evt -> {
        autor = (Autor) tabela_autor.getSelectionModel().getSelectedItem();
        System.out.println("Selecionado: " + ((Autor) tabela_autor.getSelectionModel().getSelectedItem()).getNome());
    };


}


