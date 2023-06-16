import java.util.Scanner;
import java.util.Random;

public class AplicacaoInvestimentos {

    public static String java;
    private ContaInvestimento conta;
    private Scanner scanner;
    private Random random;
    private static final double LIMITE_SAQUE_DEPOSITO = 5000;
    private static final int TEMPO_ATUALIZACAO_ITOKENS = 60;

    private long tempoUltimaAtualizacao;

    public void iniciarInvestimentos() {
        scanner = new Scanner(System.in);
        random = new Random();
        tempoUltimaAtualizacao = System.currentTimeMillis() / 1000;
        exibirMenuPrincipal();
    }

    public void exibirMenuPrincipal() {
        System.out.println("============== Banco Investimentos ==============");
        System.out.println("             MENU PRINCIPAL            ");
        System.out.println("==========================================");
        System.out.println("Selecione uma opção:");
        System.out.println("1. Criar conta de investimento");
        System.out.println("2. Acessar conta de investimento");
        System.out.println("3. Buscar conta de investimento");
        System.out.println("4. Atualizar iTokens");
        System.out.println("5. Sair");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1:
                criarContaInvestimento();
                break;
            case 2:
                acessarContaInvestimento();
                break;
            case 3:
                buscarContaInvestimento();
                break;
            case 4:
                atualizarITokens();
                break;
            case 5:
                System.out.println("Obrigado por utilizar o Banco de Investimentos. Volte sempre!");
                System.exit(0);
            default:
                System.out.println("Opção inválida. Por favor, tente novamente.");
                exibirMenuPrincipal();
                break;
        }
    }

    public void criarContaInvestimento() {
        System.out.println("============== Banco Investimentos ==============");
        System.out.println("             CRIAR CONTA DE INVESTIMENTO                ");
        System.out.println("==========================================");
        System.out.println("Selecione o tipo de conta:");
        System.out.println("1. Conta Individual");
        System.out.println("2. Conta Empresarial");

        int tipoConta = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Informe o nome do titular da conta:");
        String titular = scanner.nextLine();

        System.out.println("Informe o CPF/CNPJ do titular:");
        String cpfCnpj = scanner.nextLine();

        System.out.println("Informe a senha da conta:");
        String senha = scanner.nextLine();

        System.out.println("Informe o saldo inicial:");
        double saldo = scanner.nextDouble();
        scanner.nextLine();

        int numeroConta = gerarNumeroConta();
        conta = new ContaInvestimento(numeroConta, titular, saldo, tipoConta, cpfCnpj, senha);
        System.out.println("Conta de investimento criada com sucesso. Número da conta: " + numeroConta);
        exibirMenuContaInvestimento();
    }

    public void acessarContaInvestimento() {
        System.out.println("============== Banco Investimentos ==============");
        System.out.println("            ACESSAR CONTA DE INVESTIMENTO               ");
        System.out.println("==========================================");
        System.out.println("Informe o número da conta de investimento, nome do titular ou CPF/CNPJ:");

        String identificador = scanner.nextLine();

        if (conta != null && (conta.getNumero() == Integer.parseInt(identificador) ||
                conta.getTitular().equalsIgnoreCase(identificador) ||
                conta.getCpfCnpj().equalsIgnoreCase(identificador))) {
            System.out.println("Acesso autorizado. Bem-vindo, " + conta.getTitular() + "!");
            exibirMenuContaInvestimento();
        } else {
            System.out.println("Conta de investimento não encontrada. Por favor, tente novamente.");
            exibirMenuPrincipal();
        }
    }

    public void buscarContaInvestimento() {
        System.out.println("============== Banco Investimentos ==============");
        System.out.println("            BUSCAR CONTA DE INVESTIMENTO               ");
        System.out.println("==========================================");
        System.out.println("Informe o número da conta de investimento, nome do titular ou CPF/CNPJ:");

        String identificador = scanner.nextLine();

        // Lógica para buscar a conta no banco de dados ou em algum sistema de armazenamento

        if (conta != null && (conta.getNumero() == Integer.parseInt(identificador) ||
                conta.getTitular().equalsIgnoreCase(identificador) ||
                conta.getCpfCnpj().equalsIgnoreCase(identificador))) {
            System.out.println("Conta de investimento encontrada.");
            System.out.println("Número da conta: " + conta.getNumero());
            System.out.println("Titular: " + conta.getTitular());
            System.out.println("CPF/CNPJ: " + conta.getCpfCnpj());
            System.out.println("Saldo: " + conta.getSaldo());
        } else {
            System.out.println("Conta de investimento não encontrada.");
        }

        exibirMenuPrincipal();
    }

    public void atualizarITokens() {
        long tempoAtual = System.currentTimeMillis() / 1000;

        if (tempoAtual - tempoUltimaAtualizacao >= TEMPO_ATUALIZACAO_ITOKENS) {
            // Lógica para atualizar os iTokens da conta

            tempoUltimaAtualizacao = tempoAtual;
            System.out.println("iTokens atualizados com sucesso.");
        } else {
            System.out.println("Aguarde " + (TEMPO_ATUALIZACAO_ITOKENS - (tempoAtual - tempoUltimaAtualizacao)) +
                    " segundos para atualizar os iTokens novamente.");
        }

        exibirMenuPrincipal();
    }

    public void exibirMenuContaInvestimento() {
        System.out.println("============== Banco Investimentos ==============");
        System.out.println("         MENU CONTA DE INVESTIMENTO           ");
        System.out.println("==========================================");
        System.out.println("Selecione uma opção:");
        System.out.println("1. Exibir saldo");
        System.out.println("2. Exibir extrato");
        System.out.println("3. Realizar saque");
        System.out.println("4. Realizar depósito");
        System.out.println("5. Comprar ações");
        System.out.println("6. Comprar títulos");
        System.out.println("7. Comprar fundos de investimento");
        System.out.println("8. Voltar para o menu principal");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1:
                exibirSaldo();
                break;
            case 2:
                exibirExtrato();
                break;
            case 3:
                realizarSaque();
                break;
            case 4:
                realizarDeposito();
                break;
            case 5:
                comprarAcoes();
                break;
            case 6:
                comprarTitulos();
                break;
            case 7:
                comprarFundosInvestimento();
                break;
            case 8:
                exibirMenuPrincipal();
                break;
            default:
                System.out.println("Opção inválida. Por favor, tente novamente.");
                exibirMenuContaInvestimento();
                break;
        }
    }

    public void exibirSaldo() {
        System.out.println("============== Banco Investimentos ==============");
        System.out.println("                SALDO DA CONTA               ");
        System.out.println("==========================================");
        System.out.println("Saldo atual: " + conta.getSaldo());
        exibirMenuContaInvestimento();
    }

    public void exibirExtrato() {
        System.out.println("============== Banco Investimentos ==============");
        System.out.println("                EXTRATO DA CONTA               ");
        System.out.println("==========================================");
        // Lógica para exibir o extrato da conta

        exibirMenuContaInvestimento();
    }

    public void realizarSaque() {
        System.out.println("============== Banco Investimentos ==============");
        System.out.println("                REALIZAR SAQUE               ");
        System.out.println("==========================================");
        System.out.println("Informe o valor do saque:");

        double valor = scanner.nextDouble();
        scanner.nextLine();

        if (valor > 0 && valor <= LIMITE_SAQUE_DEPOSITO && valor <= conta.getSaldo()) {
            conta.sacar(valor);
            System.out.println("Saque realizado com sucesso. Novo saldo: " + conta.getSaldo());
        } else {
            System.out.println("Valor de saque inválido. Por favor, tente novamente.");
        }

        exibirMenuContaInvestimento();
    }

    public void realizarDeposito() {
        System.out.println("============== Banco Investimentos ==============");
        System.out.println("                REALIZAR DEPÓSITO               ");
        System.out.println("==========================================");
        System.out.println("Informe o valor do depósito:");

        double valor = scanner.nextDouble();
        scanner.nextLine();

        if (valor > 0 && valor <= LIMITE_SAQUE_DEPOSITO) {
            conta.depositar(valor);
            System.out.println("Depósito realizado com sucesso. Novo saldo: " + conta.getSaldo());
        } else {
            System.out.println("Valor de depósito inválido. Por favor, tente novamente.");
        }

        exibirMenuContaInvestimento();
    }

     public void comprarAcoes() {
        System.out.println("============== Banco Investimentos ==============");
        System.out.println("                COMPRAR AÇÕES               ");
        System.out.println("==========================================");

        System.out.println("Informe o valor em dinheiro para comprar ações:");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        // Lógica para comprar ações
        // ...

        System.out.println("Ações compradas com sucesso.");
        exibirMenuContaInvestimento();
    }

    public void comprarTitulos() {
        System.out.println("============== Banco Investimentos ==============");
        System.out.println("                COMPRAR TÍTULOS               ");
        System.out.println("==========================================");

        System.out.println("Informe o valor em dinheiro para comprar títulos:");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        // Lógica para comprar títulos
        // ...

        System.out.println("Títulos comprados com sucesso.");
        exibirMenuContaInvestimento();
    }

    public void comprarFundosInvestimento() {
        System.out.println("============== Banco Investimentos ==============");
        System.out.println("                COMPRAR FUNDOS DE INVESTIMENTO               ");
        System.out.println("==========================================");

        System.out.println("Informe o valor em dinheiro para comprar fundos de investimento:");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        // Lógica para comprar fundos de investimento
        // ...

        System.out.println("Fundos de investimento comprados com sucesso.");
        exibirMenuContaInvestimento();
    }


    public int gerarNumeroConta() {
        return random.nextInt(900000) + 100000;
    }

    public static void main(String[] args) {
        AplicacaoInvestimentos aplicacao = new AplicacaoInvestimentos();
        aplicacao.iniciarInvestimentos();
    }
}

class ContaInvestimento {
    private int numero;
    private String titular;
    private double saldo;
    private int tipoConta;
    private String cpfCnpj;
    private String senha;

    public ContaInvestimento(int numero, String titular, double saldo, int tipoConta, String cpfCnpj, String senha) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = saldo;
        this.tipoConta = tipoConta;
        this.cpfCnpj = cpfCnpj;
        this.senha = senha;
    }

    public int getNumero() {
        return numero;
    }

    public String getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void sacar(double valor) {
        saldo -= valor;
    }

    public void depositar(double valor) {
        saldo += valor;
    }
}
