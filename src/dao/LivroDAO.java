package dao;

import com.mysql.cj.protocol.Resultset;
import model.Autor;
import model.Livro;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {
    private Connection conexao;

    public void conectar() {
        conexao = new ConnectionFactory().getConnection();
    }

    public void inserir(Livro livro) {
        conectar();

        String sql = "insert into livros(titulo,data_lancamento,quantidade,preco,editora_id) values (?,?,?,?,?)";

        try {
            PreparedStatement st = conexao.prepareStatement(sql);

            st.setString(1, livro.getTitulo());
            st.setDate(2, Date.valueOf(livro.getData_lancamento()));
            st.setInt(3, livro.getQuantidade());
            st.setFloat(4, livro.getPreco());
            st.setInt(5, livro.getEditora().getId());

            st.execute();
            System.out.println("Registro Inserido com Sucesso");

            st.close();
            conexao.close();
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    public void alterar(Livro livro) {
        conectar();

        String sql = "update livros set titulo=?,data_lancamento=?,quantidade=?,preco=?, editora_id=? where id=?";

        try {
            PreparedStatement st = conexao.prepareStatement(sql);

            st.setString(1, livro.getTitulo());
            //st.setDate(2,livro.getData_lancamento());
            st.setInt(3, livro.getQuantidade());
            st.setFloat(4, livro.getPreco());
            st.setInt(5, livro.getEditora().getId());
            st.setInt(6, livro.getId());

            st.execute();
            System.out.println("Registro alterado com sucesso!!!");
            st.close();
            conexao.close();

        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    public void deletar(int id) {
        conectar();

        String sql = "delete from livros where id=?";

        try {
            PreparedStatement st = conexao.prepareStatement(sql);

            st.setInt(1, id);

            st.execute();
            System.out.println("Registro deletado com sucesso!!!");
            st.close();
            conexao.close();
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    public Livro buscar_id(int id) {
        conectar();

        String sql = "select * from livros where id=?";

        try {
            PreparedStatement st = conexao.prepareStatement(sql);

            st.setInt(1, id);

            ResultSet rs = st.executeQuery(sql);
            rs.next();

            Livro livro = new Livro();

            livro.setId(rs.getInt("id"));
            livro.setTitulo(rs.getString("titulo"));
            livro.setData_lancamento(LocalDate.parse(rs.getDate("data_lancamento").toString()));
            livro.setQuantidade(rs.getInt("quantidade"));
            livro.setPreco(rs.getFloat("preco"));
            livro.setEditora(new EditoraDAO().buscar_id(rs.getInt("editora_id")));

            rs.close();
            conexao.close();
            return livro;

        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    public int proximo_id() {
        Connection conexao = new ConnectionFactory().getConnection();

        String sql = "show table status like 'livros'";

        try {
            PreparedStatement st = conexao.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            rs.next();
           return rs.getInt("auto_increment");
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    public List<Livro> listarTodos(){
        conectar();

        String sql = "select * from autores";
        List<Livro> livros =  new ArrayList<>();

        try {
            //Preparar conexao
            PreparedStatement stmt = conexao.prepareStatement(sql);

            //Executar Conexão
            ResultSet rs = stmt.executeQuery();

            //Percorer resultados
            while(rs.next()){
                Livro livro = new Livro();
                livro.setId(rs.getInt("id"));
                livro.setTitulo((rs.getString("titulo")));
                livro.setQuantidade(rs.getInt("quantidade"));
                livro.setData_lancamento(LocalDate.parse(rs.getDate("data_lancamento").toString()));
                livro.setEditora(new EditoraDAO().buscar_id(rs.getInt("editora_id)")));
                livro.setPreco(rs.getFloat("preco"));
                livros.add(livro);
            }
            //Encerrar conexão
            conexao.close();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return livros;
    }

}
