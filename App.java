import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        // Obtenha o diretório da pasta onde os arquivos estão localizados
        String pasta = "g:\\senai\\GuilhermeBrum\\ProjetoBanco";

        // Verificar se o caminho especificado é um diretório válido
        File diretorio = new File(pasta);
        if (!diretorio.isDirectory()) {
            System.out.println("O caminho especificado não é um diretório válido.");
            return;
        }

        // Listar os arquivos na pasta
        File[] arquivos = diretorio.listFiles();

        // Verificar se existem arquivos na pasta
        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhum arquivo encontrado na pasta.");
            return;
        }

        // Mostrar os arquivos disponíveis para execução
        System.out.println("Arquivos disponíveis para execução:");
        for (int i = 0; i < arquivos.length; i++) {
            System.out.println((i + 1) + ". " + arquivos[i].getName());
        }

        // Obter a seleção do usuário
        Scanner scanner = new Scanner(System.in);
        System.out.print("Selecione o número do arquivo a ser executado: ");
        int selecao = scanner.nextInt();

        // Verificar se a seleção é válida
        if (selecao >= 1 && selecao <= arquivos.length) {
            // Obter o arquivo selecionado
            File arquivoSelecionado = arquivos[selecao - 1];

            // Verificar a extensão do arquivo selecionado
            String nomeArquivo = arquivoSelecionado.getName();
            String extensao = nomeArquivo.substring(nomeArquivo.lastIndexOf(".") + 1);

            // Executar o arquivo selecionado
            try {
                if (extensao.equals("java")) {
                    ProcessBuilder processoBuilder = new ProcessBuilder("java", "-cp", diretorio.getAbsolutePath(), nomeArquivo);
                    processoBuilder.inheritIO();
                    Process processo = processoBuilder.start();
                    processo.waitFor();
                } else {
                    System.out.println("Arquivo não suportado: " + nomeArquivo);
                    return;
                }
                // Você pode realizar outras operações com o processo, se necessário
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Seleção inválida.");
        }
    }
}
