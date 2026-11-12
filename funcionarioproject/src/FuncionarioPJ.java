public class FuncionarioPJ extends Funcionario {
    public static final double DAS_MENSAL = 86.05;

    private double valorHora;
    private double horasTrabalhadas;

    public FuncionarioPJ(String nome, double valorHora, double horasTrabalhadas) {
        this(nome, valorHora, horasTrabalhadas, true);
    }

    protected FuncionarioPJ(
            String nome, double valorHora, double horasTrabalhadas, boolean pj) {
        super(nome, 0.0, pj);
        validarNaoNegativo("Valor da hora", valorHora);
        validarNaoNegativo("Quantidade de horas", horasTrabalhadas);
        this.valorHora = valorHora;
        this.horasTrabalhadas = horasTrabalhadas;
    }

    public double getValorHora() {
        return valorHora;
    }

    public void setValorHora(double valorHora) {
        validarNaoNegativo("Valor da hora", valorHora);
        this.valorHora = valorHora;
    }

    public double getHorasTrabalhadas() {
        return horasTrabalhadas;
    }

    public void setHorasTrabalhadas(double horasTrabalhadas) {
        validarNaoNegativo("Quantidade de horas", horasTrabalhadas);
        this.horasTrabalhadas = horasTrabalhadas;
    }

    @Override
    public double calcularBruto() {
        return valorHora * horasTrabalhadas;
    }
}
