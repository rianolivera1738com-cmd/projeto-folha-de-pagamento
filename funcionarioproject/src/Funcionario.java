public class Funcionario {
    private String nome;
    private double salarioMensal;
    private final boolean pj;

    public Funcionario(String nome, double salarioMensal) {
        this(nome, salarioMensal, false);
    }

    protected Funcionario(String nome, double salarioMensal, boolean pj) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do funcionário deve ser preenchido.");
        }
        validarNaoNegativo("Salário mensal", salarioMensal);
        this.nome = nome;
        this.salarioMensal = salarioMensal;
        this.pj = pj;
    }

    public String getNome() {
        return nome;
    }

    public double getSalarioMensal() {
        return salarioMensal;
    }

    public void setSalarioMensal(double salarioMensal) {
        validarNaoNegativo("Salário mensal", salarioMensal);
        this.salarioMensal = salarioMensal;
    }

    public boolean isPJ() {
        return pj;
    }

    public double calcularBruto() {
        return salarioMensal;
    }

    public double calcularINSS() {
        if (isPJ()) {
            return 0.0;
        }

        double bruto = calcularBruto();
        double inss = Math.min(bruto, 1621.00) * 0.075;
        inss += Math.max(0.0, Math.min(bruto, 2902.84) - 1621.00) * 0.09;
        inss += Math.max(0.0, Math.min(bruto, 4354.27) - 2902.84) * 0.12;
        inss += Math.max(0.0, Math.min(bruto, 8475.55) - 4354.27) * 0.14;
        return arredondar(inss);
    }

    public double calcularIRRF() {
        if (isPJ()) {
            return 0.0;
        }

        double bruto = calcularBruto();
        double deducao = Math.max(calcularINSS(), 607.20);
        double base = Math.max(0.0, bruto - deducao);
        double impostoInicial;

        if (base <= 2428.80) {
            impostoInicial = 0.0;
        } else if (base <= 2826.65) {
            impostoInicial = base * 0.075 - 182.16;
        } else if (base <= 3751.05) {
            impostoInicial = base * 0.15 - 394.16;
        } else if (base <= 4664.68) {
            impostoInicial = base * 0.225 - 675.49;
        } else {
            impostoInicial = base * 0.275 - 908.73;
        }
        impostoInicial = arredondar(Math.max(0.0, impostoInicial));

        double reducao = 0.0;
        if (bruto > 5000.00 && bruto <= 7350.00) {
            reducao = arredondar(Math.max(0.0, 978.62 - 0.133145 * bruto));
        }
        if (bruto <= 5000.00) {
            return 0.0;
        }
        return arredondar(Math.max(0.0, impostoInicial - reducao));
    }

    public double calcularFGTS() {
        return isPJ() ? 0.0 : arredondar(calcularBruto() * 0.08);
    }

    public double calcularMEI() {
        return isPJ() ? arredondar(FuncionarioPJ.DAS_MENSAL) : 0.0;
    }

    public double calcularLiquido() {
        double bruto = calcularBruto();
        double descontos = isPJ()
                ? calcularMEI()
                : calcularINSS() + calcularIRRF();
        return arredondar(bruto - descontos);
    }

    public static double arredondar(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }

    protected static void validarNaoNegativo(String campo, double valor) {
        if (!Double.isFinite(valor) || valor < 0.0) {
            throw new IllegalArgumentException(campo + " deve ser um valor não negativo.");
        }
    }
}
