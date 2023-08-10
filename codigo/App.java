import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        LinkedList<Palavra> lista = new LinkedList<>();
        String aux[];

        WordTree word = new WordTree();

        Path path1 = Paths.get("dicionario.csv");

        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.forName("UTF-8"))) {
            String line = reader.readLine();
            while (line != null) {
                aux = line.split(";");
                Palavra p = new Palavra(aux[0], aux[1]);
                lista.add(p);
                word.addWord(aux[0], ""); // Adicione essa linha para adicionar a palavra à árvore WordTree

                line = reader.readLine();
            }
        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        }

        boolean continuar = true;
        // funçao de deixar o metodo rodando continuamente
        while (continuar) {
            System.out.println("Escreva os dois caracteres que deseja pesquisar!!");
            String palavra = in.nextLine();//palavra escolhida pelo usuario

            List<String> letras = word.searchString(palavra);
            if (letras != null && letras.isEmpty() == false) {// confere se a lista de palavras encontradas não é nula ou esta vazia
                System.out.println("Palavras encontradas:");
                for (int i = 0; i < letras.size(); i++) {
                    System.out.println(i + 1 + ". " + letras.get(i));
                }

                System.out.println("Escolha o número correspondente à palavra para obter o significado:");
                int escolha = in.nextInt();
                in.nextLine(); 

                if (escolha >= 1 && escolha <= letras.size()) {// funçao de ler a escolha do usuario
                    String palavraEscolhida = letras.get(escolha - 1);
                    System.out.println("--------------------------------------------------------------");
                    System.out.println("Palavra escolhida: " + palavraEscolhida);

                    for (Palavra pa : lista) { // confere se a palavra esta contida na lista de palavras
                        if (pa.getPalavra().equals(palavraEscolhida)) {// se estiver contida na lista e for igual a palavra escolhida 
                                                                       //pelo usuario, impirmi o significado
                                                                     
                            System.out.println("Significado: " + pa.getSignificado());
                            System.out.println("--------------------------------------------------------------");
                            System.out.println();
                            break;
                        }
                    }
                } else {
                    System.out.println("Escolha inválida.");
                }
            } else {
                System.out.println("Nenhuma palavra encontrada.");
            }

            System.out.println("Deseja continuar? (S/N)");
            String opcao = in.nextLine();
            if(opcao.equals("N")){// funçao de dar a opção ao usuario , se ele quer continuar pesquisando ou não
                System.out.println("Programa finalizado!!");
                continuar = false;
            }
            
        }
    }
}
