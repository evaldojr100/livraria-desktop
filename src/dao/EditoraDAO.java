package dao;

import model.Editora;
import model.Municipio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EditoraDAO {
    private Connection conexao;
    public void conectar(){conexao = new ConnectionFactory().getConnection();}

    public void inserir(Editora editora){
        //Conectar no banco de dados
        conectar();

        //Criar Codigo SQL para fazer a inserção
        String sql= "insert into editoras(nome,site,endereco,bairro,telefone,municipio_id) values(?,?,?,?,?,?)";


        try {
            PreparedStatement st = conexao.prepareStatement(sql);

            st.setString(1,editora.getNome());
            st.setString(2,editora.getSite());
            st.setString(3,editora.getEndereco());
            st.setString(4,editora.getBairro());
            st.setString(5,editora.getTelefone());
            st.setInt(6,editora.getMunicipio().getId());

            st.execute();
            System.out.println("Dados Inseridos com sucesso!");

            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);

        }

    }

    public void alterar(Editora editora){
        conectar();

        String sql = "update editoras set nome = ?, site = ?, endereco = ?, bairo=?, telefone =?, municipio_id=? where id=?";

        try {
            PreparedStatement st = conexao.prepareStatement(sql);

            st.setString(1,editora.getNome());
            st.setString(2,editora.getSite());
            st.setString(3,editora.getEndereco());
            st.setString(4,editora.getBairro());
            st.setString(5,editora.getTelefone());
            st.setInt(6,editora.getMunicipio().getId());
            st.setInt(7,editora.getId());

            st.execute();
            System.out.println("Dados Alterados com sucesso!");

            conexao.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);

        }

    }

    public void deletar(int id){
        conectar();

        String sql = "delete from editoras where id = ?";

        try{
            PreparedStatement st = conexao.prepareStatement(sql);

            st.setInt(1,id);

            st.execute();
            System.out.println("Registro deletado com Sucesso");

            conexao.close();

        }catch(SQLException e){
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public Editora buscar_id(int id){
        conectar();

        String sql = "select * from editoras where id=?";

        try {
            PreparedStatement st = conexao.prepareStatement(sql);

            st.setInt(1,id);
            ResultSet result = st.executeQuery();
            result.next();

            Editora editora = new Editora();
            //Municipio municipio = new Municipio();

            editora.setId(result.getInt("id"));
            editora.setNome(result.getString("nome"));
            editora.setSite(result.getString("site"));
            editora.setBairro(result.getString("bairro"));
            editora.setEndereco(result.getString("endereco"));
            editora.setTelefone(result.getString("telefone"));
            editora.setMunicipio(new MunicipioDAO().buscar_id(result.getInt("municipio_id")));

            return editora;

        }catch (SQLException e){
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    public int proximo_id() {
        Connection conexao = new ConnectionFactory().getConnection();

        String sql = "show table status like 'editoras'";

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

    public List<Editora> listarTodos(){
        conectar();

        String sql = "select * from autores";
        List<Editora> editoras =  new ArrayList<>();

        try {
            //Preparar conexao
            PreparedStatement stmt = conexao.prepareStatement(sql);

            //Executar Conexão
            ResultSet rs = stmt.executeQuery();

            //Percorer resultados
            while(rs.next()){
                Editora editora = new Editora();
                editora.setId(rs.getInt("id"));
                editora.setTelefone(rs.getString("telefone"));
                editora.setMunicipio(new MunicipioDAO().buscar_id(rs.getInt("municipio_id")));
                editora.setEndereco(rs.getString("endereco"));
                editora.setBairro(rs.getString("bairro"));
                editora.setSite(rs.getString("site"));
                editora.setNome(rs.getString("nome"));
                editoras.add(editora);
            }
            //Encerrar conexão
            conexao.close();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return editoras;
    }

}
