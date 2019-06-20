package controller;

import com.mysql.cj.conf.BooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import model.Autor;

import java.net.URL;
import java.util.ResourceBundle;

public class LivroAutorFormularioController implements Initializable {

    //instanciando Itens das Tabelas
    @FXML TableView tabela_autor = new TableView();
    @FXML TableColumn<Autor, Boolean> tb_select;
    @FXML TableColumn<Autor,String> tb_autor = new TableColumn<>("nome");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabela_autor.setEditable(true);

    }

}
