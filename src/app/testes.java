package app;

import model.Livro;

public class testes {
    public static void main(String[] args) {
        Livro livro = new Livro();
        livro.setData_lancamento(java.sql.Date.valueOf("2013-09-04"));
        System.out.println(livro.getData_lancamento());
    }
}
