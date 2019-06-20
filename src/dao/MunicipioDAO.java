package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Estado;

import model.Municipio;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class MunicipioDAO {
    private Connection conexao;
    public void conectar(){conexao = new ConnectionFactory().getConnection();}

    public void inserir(Municipio municipio){
        conectar();

        String sql = "insert into municipio (nome,estado_id) values(?,?)";

        try{
            PreparedStatement st = conexao.prepareStatement(sql);

            st.setString(1,municipio.getNome());
            st.setInt(2, municipio.getEstado().getId());

            st.execute();

            System.out.println("Municipio cadastrado");

            conexao.close();

        }catch(SQLException e){
            System.out.println(e);

            throw new RuntimeException(e);

        }
    }
    public void alterar(Municipio municipio){
        conectar();

        String sql = "update municipio set nome=? estado_id=? where id=?";

        try{

            PreparedStatement st = conexao.prepareStatement(sql);

            st.setString(1,municipio.getNome());
            st.setInt(2,municipio.getEstado().getId());
            st.setInt(3,municipio.getId());

            st.execute();

            conexao.close();

        }catch (SQLException e){
            System.out.println(e);
            throw new RuntimeException(e);
        }

    }
    public void deletar(int id){
        conectar();

        String sql ="delete from municipio where id=?";

        try{
        PreparedStatement st = conexao.prepareStatement(sql);

        st.setInt(1,id);

        st.execute();
        System.out.println("Registro deletado com sucesso");
        conexao.close();

        }catch(SQLException e){
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
    public Municipio buscar_id(int id){
        conectar();
        Municipio municipio =  new Municipio();
        String sql = "select * from municipio  where id=?";
        System.out.println(id);
        try{

            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1,id);

            ResultSet rs = st.executeQuery();
            rs.next();
            municipio.setId(rs.getInt("id"));
            municipio.setNome(rs.getString("nome"));
            //municipio.setEstado(new EstadoDAO().buscar_id(rs.getInt("estado_id")));

            rs.close();

            conexao.close();

            return municipio;
        }catch(SQLException e){
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
    public int proximo_id() {
        Connection conexao = new ConnectionFactory().getConnection();

        String sql = "show table status like 'municipio'";

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
    public List<Municipio> listarTodos(){
        conectar();

        String sql = "select * from autores";
        List<Municipio> municipios =  new ArrayList<>();

        try {
            //Preparar conexao
            PreparedStatement stmt = conexao.prepareStatement(sql);

            //Executar Conexão
            ResultSet rs = stmt.executeQuery();

            //Percorer resultados
            while(rs.next()){
                Municipio municipio = new Municipio();
                municipio.setId(rs.getInt("id"));
                municipio.setNome(rs.getString("nome"));
                municipio.setEstado(new EstadoDAO().buscar_id(rs.getInt("estado_id")));
                municipios.add(municipio);

            }
            //Encerrar conexão
            conexao.close();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return municipios;
    }
    public ObservableList listarEstado (Estado estado){
        conectar();

        String sql = "select * from municipio where estado_id=? group by id";

        List<Municipio> municipios = new ArrayList<>();
        try{
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1,estado.getId());


            ResultSet rs = st.executeQuery();

            while(rs.next()){
                Municipio municipio = new Municipio();
                municipio.setId(rs.getInt("id"));
                municipio.setNome(rs.getString("nome"));
                municipios.add(municipio);
            }
            rs.close();

            conexao.close();

            ObservableList retorno = FXCollections.observableArrayList(municipios);
            return  retorno;

        }catch (SQLException e){
            System.out.println(e);
            throw new RuntimeException(e);
        }

    }
}
