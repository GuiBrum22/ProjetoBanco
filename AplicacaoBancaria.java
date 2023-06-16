import java.util.Scanner;
import java.util.Random;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;




public class AplicacaoBancaria {
    private Conta conta;
    private Scanner scanner;
    private Random random;
    private static final double LIMITE_SAQUE_DEPOSITO = 5000;
    private static final int TEMPO_ATUALIZACAO_ITOKENS = 60; 

    private long tempoUltimaAtualizacao; 

    public void iniciar() {
        scanner = new Scanner(System.in);
        random = new Random();
        tempoUltimaAtualizacao = System.currentTimeMillis() / 1000; 
        exibirMenuPrincipal();
    }

   public void exibirMenuPrincipal() {
        System.out.println("============== Banco Brum ==============");
        System.out.println("             MENU PRINCIPAL            ");
        System.out.println("========================================");
        System.out.println("Selecione uma opção:");
        System.out.println("1. Criar conta");
        System.out.println("2. Acessar conta");
        System.out.println("3. Buscar conta");
        System.out.println("4. Atualizar iTokens");
        System.out.println("5. Sair");

        int opcao = scanner.nextInt();
        scanner.nextLine(); 

        switch (opcao) {
            case 1:
                criarConta();
                break;
            case 2:
                acessarConta();
                break;
            case 3:
                buscarConta();
                break;
            case 4:
                atualizarITokens();
                break;
            case 5:
                System.out.println("Obrigado por utilizar o Banco Brum. Volte sempre!");
                System.exit(0);
            default:
                System.out.println("Opção inválida. Por favor, tente novamente.");
                exibirMenuPrincipal();
                break;
        }
    }

    public void criarConta() {
        System.out.println("============== Banco Brum ==============");
        System.out.println("             CRIAR CONTA                ");
        System.out.println("========================================");
        System.out.println("Selecione o tipo de conta:");
        System.out.println("1. Conta Física");
        System.out.println("2. Conta Jurídica");

        int tipoConta = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Informe o titular da conta:");
        String titular = scanner.nextLine();

        System.out.println("Informe o CPF ou CNPJ do titular:");
        String cpfCnpj = scanner.nextLine();

        System.out.println("Informe a senha da conta:");
        String senha = scanner.nextLine();

        System.out.println("Informe o saldo inicial:");
        double saldo = scanner.nextDouble();
        scanner.nextLine();

        int numeroConta = gerarNumeroConta();
        conta = new Conta(numeroConta, titular, saldo, 0, tipoConta, cpfCnpj, senha);
        System.out.println("Conta criada com sucesso. Número da conta: " + numeroConta);
        exibirMenuConta();
    }

    public void acessarConta() {
        System.out.println("============== Banco Brum ==============");
        System.out.println("            ACESSAR CONTA               ");
        System.out.println("========================================");
        System.out.println("Informe o número da conta, nome do titular ou CPF/CNPJ:");

        String identificador = scanner.nextLine();

        if (conta != null && (conta.getNumero() == Integer.parseInt(identificador) ||
                conta.getTitular().equalsIgnoreCase(identificador) ||
                conta.getCpfCnpj().equals(identificador))) {
            System.out.println("Informe a senha da conta:");
            String senha = scanner.nextLine();

            if (conta.getSenha().equals(senha)) {
                System.out.println("Bem-vindo(a), " + conta.getTitular() + "!");
                System.out.println("Tipo de conta: " + (conta.getTipoConta() == 1 ? "Física" : "Jurídica"));
                System.out.println("Saldo: R$ " + conta.getSaldo());
                System.out.println("Saldo da Conta Corrente: R$ " + conta.getSaldoContaCorrente());
                System.out.println("Saldo da Conta Poupança: R$ " + conta.getSaldoContaPoupanca());
                exibirMenuConta();
            } else {
                System.out.println("Senha incorreta. Tente novamente.");
                exibirMenuPrincipal();
            }
        } else {
            System.out.println("Conta não encontrada. Tente novamente.");
            exibirMenuPrincipal();
        }
    }

    public void buscarConta() {
        System.out.println("============== Banco Brum ==============");
        System.out.println("            BUSCAR CONTA                ");
        System.out.println("========================================");
        System.out.println("Informe o número da conta:");

        int numeroConta = scanner.nextInt();
        scanner.nextLine(); 

        if (conta != null && conta.getNumero() == numeroConta) {
            System.out.println("Número da conta: " + conta.getNumero());
            System.out.println("Titular: " + conta.getTitular());
            System.out.println("Tipo de conta: " + (conta.getTipoConta() == 1 ? "Física" : "Jurídica"));
            System.out.println("CPF/CNPJ do titular: " + conta.getCpfCnpj());
            System.out.println("Saldo: R$ " + conta.getSaldo());
            System.out.println("Saldo da Conta Corrente: R$ " + conta.getSaldoContaCorrente());
            System.out.println("Saldo da Conta Poupança: R$ " + conta.getSaldoContaPoupanca());
        } else {
            System.out.println("Conta não encontrada.");
        }
        exibirMenuPrincipal();
    }

  public void atualizarITokens() {
    System.out.println("============== Banco Brum ==============");
    System.out.println("           ATUALIZAR ITOKENS            ");
    System.out.println("========================================");
    System.out.println("Gerando novo iTokens...");

    int novoIToken = random.nextInt(1000) + 1; 
    conta.setITokens(novoIToken);
    tempoUltimaAtualizacao = System.currentTimeMillis() / 1000; 

    System.out.println("Novo iTokens gerado: " + novoIToken);
    exibirMenuConta();
}


    private boolean verificarTempoAtualizacaoITokens() {
        long tempoAtual = System.currentTimeMillis() / 1000;
        long tempoDecorrido = tempoAtual - tempoUltimaAtualizacao;
        return tempoDecorrido >= TEMPO_ATUALIZACAO_ITOKENS;
    }


    public void exibirMenuItoken() {
        if (verificarTempoAtualizacaoITokens()) {
            int iTokensAtualizados = conta.getITokens() + 1;
            conta.setITokens(iTokensAtualizados);
            tempoUltimaAtualizacao = System.currentTimeMillis() / 1000;} 
        }

    public void exibirMenuConta() {
        System.out.println("============== Banco Brum ==============");
        System.out.println("              MENU CONTA                ");
        System.out.println("========================================");
        System.out.println("Selecione uma opção:");
        System.out.println("1. Sacar");
        System.out.println("2. Depositar");
        System.out.println("3. Transferência");
        System.out.println("4. PIX");
        System.out.println("5. Cartão");
        System.out.println("6. Voltar ao menu principal");

        int opcao = scanner.nextInt();
        scanner.nextLine(); 

        switch (opcao) {
            case 1:
                System.out.println("Informe o valor para saque:");
                double valorSaque = scanner.nextDouble();
                scanner.nextLine(); 
                if (valorSaque > LIMITE_SAQUE_DEPOSITO) {
                    System.out.println("Limite de saque excedido. O limite máximo é de R$ " + LIMITE_SAQUE_DEPOSITO);
                } else if (valorSaque > conta.getSaldo()) {
                    System.out.println("Saldo insuficiente para saque.");
                } else {
                    conta.sacar(valorSaque);
                    System.out.println("Saque realizado com sucesso. Novo saldo: R$ " + conta.getSaldo());
                }
                exibirMenuConta();
                break;
            case 2:
                System.out.println("Informe o valor para depósito:");
                double valorDeposito = scanner.nextDouble();
                scanner.nextLine(); 
                if (valorDeposito > LIMITE_SAQUE_DEPOSITO) {
                    System.out.println("Limite de depósito excedido. O limite máximo é de R$ " + LIMITE_SAQUE_DEPOSITO);
                } else {
                    conta.depositar(valorDeposito);
                    System.out.println("Depósito realizado com sucesso. Novo saldo: R$ " + conta.getSaldo());
                }
                exibirMenuConta();
                break;
            case 3:
                System.out.println("Informe o número da conta de destino:");
    String numeroContaDestino = scanner.nextLine();

    System.out.println("Informe o valor para transferência:");
    double valorTransferencia = scanner.nextDouble();
    scanner.nextLine(); 

    if (valorTransferencia > conta.getSaldo()) {
        System.out.println("Saldo insuficiente para transferência.");
    } else {
        Conta contaDestino = encontrarContaPorNumero(numeroContaDestino);
        if (contaDestino == null) {
            System.out.println("Conta de destino não encontrada.");
        } else {
            conta.transferir(contaDestino, valorTransferencia);
            System.out.println("Transferência realizada com sucesso. Novo saldo: R$ " + conta.getSaldo());
        }
    }
    exibirMenuConta();
                break;
            case 4:
                exibirMenuPix();
                break;
            case 5:
                exibirMenuCartao();
                break;
            case 6:
                exibirMenuPrincipal();
                break;
            default:
                System.out.println("Opção inválida. Por favor, tente novamente.");
                exibirMenuConta();
                break;
        }
    }

    private Conta encontrarContaPorNumero(String numeroContaDestino) {
        return null;
    }

    public void exibirMenuPix() {
    System.out.println("============== Banco Brum ==============");
    System.out.println("                MENU PIX                ");
    System.out.println("========================================");
    System.out.println("Selecione uma opção:");
    System.out.println("1. Gerar QR Code");
    System.out.println("2. Visualizar chave PIX");
    System.out.println("3. Voltar ao menu conta");

    int opcao = scanner.nextInt();
    scanner.nextLine(); 

    switch (opcao) {
        case 1:
            System.out.println("Gerando QR Code...");

            try {
                // Gerar o QR Code
                ByteArrayOutputStream out = QRCode.from(conta.getChavePix())
                        .to(ImageType.PNG)
                        .withSize(250, 250)
                        .stream();

                // Salvar o QR Code em um arquivo
                FileOutputStream fileOutputStream = new FileOutputStream("qrcode.png");
                fileOutputStream.write(out.toByteArray());
                fileOutputStream.flush();
                fileOutputStream.close();

                System.out.println("QR Code gerado e salvo no arquivo 'qrcode.png'.");
            } catch (Exception e) {
                System.out.println("Ocorreu um erro ao gerar o QR Code: " + e.getMessage());
            }
            break;
        case 2:
            System.out.println("Chave PIX: " + conta.getChavePix());
            break;
        case 3:
            exibirMenuConta();
            break;
        default:
            System.out.println("Opção inválida. Por favor, tente novamente.");
            exibirMenuPix();
            break;
    }
    exibirMenuPix();
}


    public void exibirMenuCartao() {
        System.out.println("\n============== Banco Brum ==============");
        System.out.println("              MENU CARTÃO               ");
        System.out.println("========================================");
        System.out.println("Selecione uma opção:");
        System.out.println("1. Alterar limite do cartão");
        System.out.println("2. Voltar ao menu conta");

        int opcao = scanner.nextInt();
        scanner.nextLine(); 

        switch (opcao) {
            case 1:
                System.out.println("Informe o novo limite do cartão:");
                double novoLimite = scanner.nextDouble();
                scanner.nextLine(); 
                conta.setLimiteCartao(novoLimite);
                System.out.println("Limite do cartão atualizado com sucesso. Novo limite: R$ " + conta.getLimiteCartao());
                break;
            case 2:
                exibirMenuConta();
                break;
            default:
                System.out.println("Opção inválida. Por favor, tente novamente.");
                exibirMenuCartao();
                break;
        }
        exibirMenuCartao();
    }

    public int gerarNumeroConta() {
        return random.nextInt(9000) + 1000;
    }

    public static void main(String[] args) {
        AplicacaoBancaria banco = new AplicacaoBancaria();
        banco.iniciar();
    }
}

class Conta {
    private int numero;
    private String titular;
    private double saldo;
    private double saldoContaCorrente;
    private double saldoContaPoupanca;
    private int tipoConta;
    private String cpfCnpj;
    private String senha;
    private int iTokens;
    private String chavePix;
    private double limiteCartao;

    public Conta(int numero, String titular, double saldo, double saldoContaCorrente, int tipoConta,
            String cpfCnpj, String senha) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = saldo;
        this.saldoContaCorrente = saldoContaCorrente;
        this.tipoConta = tipoConta;
        this.cpfCnpj = cpfCnpj;
        this.senha = senha;
        this.iTokens = 0;
        this.chavePix = gerarChavePix();
        this.limiteCartao = 0;
    }

    public void transferir(Conta contaDestino, double valorTransferencia) {
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

    public double getSaldoContaCorrente() {
        return saldoContaCorrente;
    }

    public double getSaldoContaPoupanca() {
        return saldoContaPoupanca;
    }

    public int getTipoConta() {
        return tipoConta;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public String getSenha() {
        return senha;
    }

    public int getITokens() {
        return iTokens;
    }

    public void setITokens(int iTokens) {
        this.iTokens = iTokens;
    }

    public String getChavePix() {
        return chavePix;
    }

    public double getLimiteCartao() {
        return limiteCartao;
    }

    public void setLimiteCartao(double limiteCartao) {
        this.limiteCartao = limiteCartao;
    }

    public void sacar(double valor) {
        saldo -= valor;
    }

    public void depositar(double valor) {
        saldo += valor;
    }

    private String gerarChavePix() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder chave = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            int index = random.nextInt(caracteres.length());
            chave.append(caracteres.charAt(index));
        }
        return chave.toString();
    }
}
