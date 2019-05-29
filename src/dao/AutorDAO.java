package dao;

import com.mysql.cj.protocol.Resultset;
import model.Autor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AutorDAO {
    private Connection conexao;

    public void conectar(){
        conexao= new ConnectionFactory().getConnection();
    }
    public void inserir(Autor autor){
        //conectar no banco
        conectar();
        //Criar String SQL
        String sql = "insert into autores(nome,email) values (?,?)";

        try{
            // preparar conexão
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1,autor.getNome());
            stmt.setString(2,autor.getEmail());

            //Executar Comando

            stmt.execute();

            //Fechar conexão
            conexao.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    public List<Autor> listarTodos(){
        conectar();

        String sql = "select * from autores";
        List<Autor> autores =  new ArrayList<>();

        try {
            //Preparar conexao
            PreparedStatement stmt = conexao.prepareStatement(sql);

            //Executar Conexão
            ResultSet rs = stmt.executeQuery();

            //Percorer resultados
            while(rs.next()){
                Autor autor = new Autor();
                autor.setId(rs.getInt("id"));
                autor.setNome(rs.getString("nome"));
                autor.setEmail(rs.getString("email"));
                autores.add(autor);
            }
            //Encerrar conexão
            conexao.close();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return autores;
    }
    public void alterar(Autor autor){
        conectar();

        String sql = "update autores set nome= ?, email = ? where id=?";

        //PrepararConexão
        try{
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1,autor.getNome());
            st.setString(2,autor.getEmail());
            st.setInt(3,autor.getId());

            //Executar Conexão
            st.execute();
            System.out.println("Registro Alterado!!!");
            //Fechar Conexao
            conexao.close();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }


    }
    public void deletar(int id){
        conectar();

        String sql = "delete from autores where id=?";

        //Preparar conexão
        try{
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1,id);

            //executar a query
            st.execute();
            System.out.println("Registro Deletado!!!");

            //fechar conexão
            conexao.close();

        }catch(SQLException e){
            System.out.println(e);
            throw new RuntimeException(e);
        }


    }
    public Autor Busca_id(int id){
        conectar();

        String sql ="select * from autores where id=?";

        try {
            //preparar conexão
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1,id);

            ResultSet rs = st.executeQuery();
            rs.next();
            Autor autor = new Autor();
            autor.setId(rs.getInt("id"));
            autor.setNome(rs.getString("nome"));
            autor.setEmail(rs.getString("email"));

            conexao.close();
            return autor;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }
}



