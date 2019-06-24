package dao;

import model.Autor;
import model.Livro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Livro_AutorDAO {
    private Connection conexao;

    public void conectar() {
        conexao = new ConnectionFactory().getConnection();
    }

    public void inserir_temp(Livro livro){
        conectar();

        String sql= "update temp_livro set livro_id=?,titulo=? where id=1";

        try{
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1,livro.getId());
            st.setString(2,livro.getTitulo());

            st.execute();
            st.close();
            conexao.close();

        }catch (SQLException e){
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
    public Livro buscar_temp(){
        conectar();

        String sql = "select * from temp_livro";
        Livro livro = new Livro();
        try{
            PreparedStatement st = conexao.prepareStatement(sql);
            ResultSet rs =st.executeQuery();
            rs.next();
            livro.setId(rs.getInt("livro_id"));
            livro.setTitulo(rs.getString("titulo"));

            rs.close();
            st.close();
            conexao.close();
            return livro;


        }catch (SQLException e){
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
    public int select_for_delete(Livro livro, Autor autor){
        conectar();
        int aux;
        String sql = "select id from livro_autor where livro_id=? and autor_id=?";

        try{
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1,livro.getId());
            st.setInt(2,autor.getId());

            ResultSet rs = st.executeQuery();
            rs.next();
            aux = rs.getInt("id");

            rs.close();
            conexao.close();
            return aux;

        }catch (SQLException e){
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
    public void delete_livro_autor(int id){
        conectar();

        String sql = "delete from livro_autor where id=?";

        try{
            PreparedStatement st= conexao.prepareStatement(sql);
            st.setInt(1,id);
            st.execute();
            st.close();
            conexao.close();

        }catch (SQLException e){
            System.out.println(e);
            throw new RuntimeException(e);
        }

    }
    public void delete_all_autores(Livro livro){
        conectar();
        String sql = "delete from livro_autor where livro_id=?";
        try{
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1,livro.getId());
            st.execute();
            st.close();
            conexao.close();

        }catch (SQLException e){
            System.out.println(e);
            throw new RuntimeException(e);
        }


    }
}
