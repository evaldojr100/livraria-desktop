package dao;

import model.Estado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstadoDAO {
    private Connection conexao;
    public void conectar(){conexao = new ConnectionFactory().getConnection();}

    public void inserir(Estado estado){
        conectar();

        String sql = "insert into estado (uf) values(?)";

        try{
            PreparedStatement st = conexao.prepareStatement(sql);

            st.setString(1,estado.getUf());


            st.execute();

            System.out.println("Estado cadastrado");

            conexao.close();

        }catch(SQLException e){
            System.out.println(e);

            throw new RuntimeException(e);

        }
    }
    public void alterar(Estado estado){
        conectar();

        String sql = "update estado set uf=? where id=?";

        try{
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1,estado.getUf());
            st.setInt(2,estado.getId());

            st.execute();
            System.out.println("Alterado com Sucesso");
            conexao.close();
        }catch(SQLException e){
            System.out.println(e);
            throw  new RuntimeException(e);
        }
    }
    public void deletar(int id){
        conectar();

        String sql = "delete from estado where id=?";

        try{
            PreparedStatement st = conexao.prepareStatement(sql);

            st.setInt(1,id);

            st.execute();
            System.out.println("Registro Deletado com sucesso!!");
            conexao.close();

        }catch(SQLException e){
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
    public Estado buscar_id(int id){
        conectar();

        String sql = "select * from estado where id=?";

        try{
            PreparedStatement st = conexao.prepareStatement(sql);

            st.setInt(1,id);

            ResultSet rs = st.executeQuery();
            rs.next();

            Estado estado = new Estado();
            estado.setId(rs.getInt("id"));
            estado.setUf(rs.getString("uf"));

            rs.close();
            conexao.close();
            return estado;

        }catch(SQLException e){
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
    public int proximo_id() {
        Connection conexao = new ConnectionFactory().getConnection();

        String sql = "show table status like 'estado'";

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
    public List<Estado> listarTodos(){
        conectar();

        String sql = "select * from autores";
        List<Estado> estados =  new ArrayList<>();

        try {
            //Preparar conexao
            PreparedStatement stmt = conexao.prepareStatement(sql);

            //Executar Conexão
            ResultSet rs = stmt.executeQuery();

            //Percorer resultados
            while(rs.next()){
                Estado estado = new Estado();
                estado.setId(rs.getInt("id"));
                estado.setUf(rs.getString("uf"));
                estados.add(estado);
            }
            //Encerrar conexão
            conexao.close();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return estados;
    }
}
