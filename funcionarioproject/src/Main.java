import java.util.Locale;
import java.util.function.Supplier;

public class Main {
    private static final Locale LOCALE_BR = Locale.forLanguageTag("pt-BR");

    public static void main(String[] args) {
        Funcionario ana = new Funcionario("Ana", 3000.00);
        Funcionario bruno = new Funcionario("Bruno", 6000.00);
        FuncionarioPJ carla = new FuncionarioPJ("Carla", 50.00, 120.00);
        Professor daniel = new Professor("Daniel", 50.00, 20.00);
        Professor elisa = new Professor("Elisa", 100.00, 20.00);

        System.out.println("DEMONSTRATIVO DE PAGAMENTO");
        imprimirDemonstrativo(ana);
        imprimirDemonstrativo(bruno);
        imprimirDemonstrativo(carla);
        imprimirDemonstrativo(daniel);
        imprimirDemonstrativo(elisa);
        executarFichaDeTestes(ana, bruno, daniel, elisa);
    }

    private static void imprimirDemonstrativo(Funcionario funcionario) {
        System.out.printf(LOCALE_BR,
                "%n%s (%s)%nBruto: R$ %,.2f | INSS: R$ %,.2f | IRRF: R$ %,.2f"
                        + " | FGTS: R$ %,.2f | DAS: R$ %,.2f | Líquido: R$ %,.2f%n",
                funcionario.getNome(),
                funcionario.isPJ() ? "PJ/MEI" : "CLT",
                funcionario.calcularBruto(),
                funcionario.calcularINSS(),
                funcionario.calcularIRRF(),
                funcionario.calcularFGTS(),
                funcionario.calcularMEI(),
                funcionario.calcularLiquido());
    }

    private static void executarFichaDeTestes(
            Funcionario ana, Funcionario bruno, Professor daniel, Professor elisa) {
        System.out.println("\nFICHA DE TESTES");
        System.out.println("Teste | Esperado | Obtido | Passou? | Correção realizada");

        registrar("Salário negativo rejeitado", "IllegalArgumentException",
                () -> new Funcionario("Inválido", -1.0));
        registrar("Valor da hora negativo rejeitado", "IllegalArgumentException",
                () -> new FuncionarioPJ("Inválido", -1.0, 1.0));
        registrar("Horas negativas rejeitadas", "IllegalArgumentException",
                () -> new FuncionarioPJ("Inválido", 1.0, -1.0));
        registrar("Nome vazio rejeitado", "IllegalArgumentException",
                () -> new Funcionario(" ", 0.0));

        Funcionario cltZero = new Funcionario("CLT zero", 0.0);
        boolean cltZeroOk = cltZero.calcularBruto() == 0.0
                && cltZero.calcularINSS() == 0.0
                && cltZero.calcularIRRF() == 0.0
                && cltZero.calcularFGTS() == 0.0
                && cltZero.calcularMEI() == 0.0
                && cltZero.calcularLiquido() == 0.0;
        registrarResultado("CLT com salário zero", "todos os valores R$ 0,00",
                cltZeroOk ? "todos os valores R$ 0,00" : "divergente", cltZeroOk);

        FuncionarioPJ pjZero = new FuncionarioPJ("PJ sem horas", 50.0, 0.0);
        boolean pjZeroOk = perto(pjZero.calcularBruto(), 0.0)
                && perto(pjZero.calcularMEI(), 86.05)
                && perto(pjZero.calcularLiquido(), -86.05);
        registrarResultado("PJ com zero horas", "bruto R$ 0,00; DAS R$ 86,05; líquido -R$ 86,05",
                pjZeroOk ? "bruto R$ 0,00; DAS R$ 86,05; líquido -R$ 86,05" : "divergente",
                pjZeroOk);

        Funcionario clt8000 = new Funcionario("CLT 8000", 8000.0);
        Funcionario clt10500 = new Funcionario("CLT 10500", 10500.0);
        Funcionario clt12000 = new Funcionario("CLT 12000", 12000.0);
        boolean inssOk = perto(clt8000.calcularINSS(), 921.51)
                && perto(clt10500.calcularINSS(), 988.09)
                && perto(clt12000.calcularINSS(), 988.09);
        registrarResultado("INSS cresce até o teto", "R$ 921,51; R$ 988,09; R$ 988,09",
                inssOk ? "R$ 921,51; R$ 988,09; R$ 988,09" : "divergente", inssOk);

        Funcionario clt5000 = new Funcionario("CLT 5000", 5000.0);
        Funcionario clt6000 = new Funcionario("CLT 6000", 6000.0);
        boolean irrfOk = perto(clt5000.calcularIRRF(), 0.0)
                && perto(clt6000.calcularIRRF(), 385.10);
        registrarResultado("Isenção e redução do IRRF", "R$ 0,00; R$ 385,10",
                irrfOk ? "R$ 0,00; R$ 385,10" : "divergente", irrfOk);

        boolean danielOk = perto(daniel.calcularBruto(), 5250.00)
                && perto(daniel.calcularINSS(), 536.51)
                && perto(daniel.calcularIRRF(), 89.53)
                && perto(daniel.calcularFGTS(), 420.00)
                && perto(daniel.calcularMEI(), 0.00)
                && perto(daniel.calcularLiquido(), 4623.96);
        registrarResultado("Professor Daniel", "bruto 5.250,00; INSS 536,51; IRRF 89,53;"
                        + " FGTS 420,00; DAS 0,00; líquido 4.623,96",
                danielOk ? "valores conferidos" : "divergente", danielOk);

        boolean elisaOk = perto(elisa.calcularBruto(), 10500.00)
                && perto(elisa.calcularINSS(), 988.09)
                && perto(elisa.calcularIRRF(), 1707.05)
                && perto(elisa.calcularFGTS(), 840.00)
                && perto(elisa.calcularMEI(), 0.00)
                && perto(elisa.calcularLiquido(), 7804.86);
        registrarResultado("Professora Elisa", "bruto 10.500,00; INSS 988,09; IRRF 1.707,05;"
                        + " FGTS 840,00; DAS 0,00; líquido 7.804,86",
                elisaOk ? "valores conferidos" : "divergente", elisaOk);

        double liquidoBruno = bruno.calcularLiquido();
        boolean repeticaoOk = perto(liquidoBruno, bruno.calcularLiquido())
                && perto(bruno.calcularBruto(), 6000.0);
        registrarResultado("Cálculo repetido não altera o objeto", "mesmo líquido e bruto R$ 6.000,00",
                repeticaoOk ? "mesmo líquido e bruto R$ 6.000,00" : "divergente", repeticaoOk);

        boolean independenciaOk = perto(ana.calcularBruto(), 3000.0)
                && perto(ana.calcularINSS(), 248.60)
                && perto(ana.calcularLiquido(), 2751.40);
        registrarResultado("Ana permanece igual após criar Bruno",
                "bruto R$ 3.000,00; INSS R$ 248,60; líquido R$ 2.751,40",
                independenciaOk
                        ? "bruto R$ 3.000,00; INSS R$ 248,60; líquido R$ 2.751,40"
                        : "divergente",
                independenciaOk);
    }

    private static void registrar(String teste, String esperado, Supplier<?> criacao) {
        boolean rejeitado = false;
        try {
            criacao.get();
        } catch (IllegalArgumentException e) {
            rejeitado = true;
        }
        registrarResultado(teste, esperado,
                rejeitado ? "IllegalArgumentException" : "criação aceita", rejeitado);
    }

    private static void registrarResultado(
            String teste, String esperado, String obtido, boolean passou) {
        System.out.printf("%s | %s | %s | %s | %s%n",
                teste, esperado, obtido, passou ? "Sim" : "Não",
                passou ? "N/A" : "Revisar a regra de cálculo ou validação");
    }

    private static boolean perto(double obtido, double esperado) {
        return Math.abs(obtido - esperado) < 0.005;
    }
}
