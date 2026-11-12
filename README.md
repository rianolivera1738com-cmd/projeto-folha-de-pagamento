Folha de pagamento

Exercício didático de orientação a objetos com quatro classes Java: Funcionario, FuncionarioPJ, Professor e Main.
Executar

No terminal, a partir de src:

javac *.java
java Main

O programa imprime os demonstrativos dos cinco exemplos e uma ficha de verificação dos casos obrigatórios. Os arquivos .class são gerados no próprio diretório src.
Resultados conferidos
Pessoa 	Regime 	Bruto 	INSS 	IRRF 	FGTS 	DAS 	Líquido
Ana 	CLT 	R$ 3.000,00 	R$ 248,60 	R$ 0,00 	R$ 240,00 	R$ 0,00 	R$ 2.751,40
Bruno 	CLT 	R$ 6.000,00 	R$ 641,51 	R$ 385,10 	R$ 480,00 	R$ 0,00 	R$ 4.973,39
Carla 	PJ/MEI 	R$ 6.000,00 	R$ 0,00 	R$ 0,00 	R$ 0,00 	R$ 86,05 	R$ 5.913,95
Daniel 	Professor CLT 	R$ 5.250,00 	R$ 536,51 	R$ 89,53 	R$ 420,00 	R$ 0,00 	R$ 4.623,96
Elisa 	Professora CLT 	R$ 10.500,00 	R$ 988,09 	R$ 1.707,05 	R$ 840,00 	R$ 0,00 	R$ 7.804,86

A ficha executada pela Main também verifica rejeição de salário, valor-hora e horas negativos, nome vazio, salário CLT zero, PJ sem horas, teto do INSS, isenção/redução do IRRF, repetição do cálculo e independência entre Ana e Bruno. Todos esses casos passaram na execução.
Reflexão

    Classe e objeto: classe é a definição de atributos e comportamentos; objeto é uma instância concreta dessa definição. Ana e Bruno são dois objetos diferentes da classe Funcionario.
    Construtor e regime: o construtor valida os dados e deixa o objeto pronto para ser usado. O regime não tem setter porque é escolhido na criação e deve permanecer consistente durante a vida do objeto.
    FGTS: é um depósito feito pelo empregador, não um desconto do salário do trabalhador; por isso aparece no demonstrativo, mas não reduz o líquido.
    Sobrescrita: Professor sobrescreve calcularBruto(). Quando o cálculo do INSS chama esse método, o Java executa a versão do professor e usa a remuneração das aulas acrescida do DSR.
    Modelagem: professor CLT não é um prestador PJ, então herdar de FuncionarioPJ representa uma relação conceitualmente incorreta. Uma organização melhor seria extrair o cálculo por hora para uma classe-base abstrata ou um componente compartilhado, mantendo Professor e FuncionarioPJ como tipos separados.

Os valores e regras tributárias são os fornecidos para esta atividade e não representam aconselhamento tributário ou um modelo completo de folha real. Para cálculos financeiros de produção, prefira tipos decimais como BigDecimal.
