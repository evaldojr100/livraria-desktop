package app;


import dao.EditoraDAO;
import dao.EstadoDAO;
import model.Estado;

public class testes {
        public static void main(String[] args) {
            Estado estado = new EstadoDAO().buscar_id(2);
            System.out.println(estado.getUf());







    }
}
