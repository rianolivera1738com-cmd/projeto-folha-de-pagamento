public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
    }
}
public class Funcionario {
private String nome;
private double salarioMensal;
private final boolean pj;
public Funcionario(String nome, double salarioMensal) {
this(nome, salarioMensal, false);
}
protected Funcionario(String nome, double salarioMensal, boolean pj) {
if (nome == null || nome.isBlank() || salarioMensal < 0) {
throw new IllegalArgumentException("Nome e salário inválidos.");
}
this.nome = nome;
this.salarioMensal = salarioMensal;
this.pj = pj;
}
public boolean isPJ() {
return pj;
}    