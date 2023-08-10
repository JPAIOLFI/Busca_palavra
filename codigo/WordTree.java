import java.util.ArrayList;
import java.util.List;

public class WordTree {
    private int totalNodos = 0;
    private int totalWords = 0;
    private CharNode root;

    // Classe interna que representa os nodos
    private class CharNode {
        private char character;
        private String significado;
        private boolean isFinal;
        private CharNode father;
        private List<CharNode> children;

        // Construtor que serve para criar um nodo com o caracter especificado
        public CharNode(char character) {
            this.character = character;
            this.isFinal = false;
            children = new ArrayList<>();
        }

        // Construtor que serve para criar um nodo com o caracter e especificação se é o nodo final ou não
        public CharNode(char character, boolean isFinal) {
            this.character = character;
            this.isFinal = isFinal;
            children = new ArrayList<>();
        }

        // Método para adicionar um nodo filho
        public CharNode addChild(char character, boolean isFinal) {
            for (CharNode charNode : children) {
                if (character == charNode.character) {// confere se ja existe um nodo filho
                                                      // com o mesmo caracter passado como parametro
                    return charNode;
                }
            }
            // caso não existir nenhum filho com o mesmo caracter passado como parametro, adiciona um nodo filho
            CharNode aux = new CharNode(character, isFinal);
            children.add(aux);
            return aux;
        }

        // Retorna a quantidade de nodos filhos
        public int getNumberOfChildren() {
            return children.size();
        }

        // Retorna o nodo filho na posição escolhida
        public CharNode getChild(int i) {
            return children.get(i);
        }

        // Retorna a palavra completa representada por esse nodo e seus nós pais
        private String getWord() {
            if (!isFinal)
                return null;

            // classe para facilitar a manipulaçao da string e reverter a string no final do metodo
            StringBuilder string = new StringBuilder();
            string.append(this.character);
            CharNode n = this.father;
            while (n != null) {// passa por todos os pais do nodo atual,
                               // adicionando os caracteres dos pais na string,
                               // assim que nao existe mais nodo pai,reverte a string criada
                string.append(n.getCharacter());
                n = n.getFather();
            }
            return string.reverse().toString();
        }

        // Retorna o nodo filho correspondente ao caracter dado como parametro 
        private CharNode findChildChar(char character) {
            for (CharNode c : children) {
                if (c.getCharacter() == character) {
                    return c;
                }
            }
            return null;
        }

        // Retorna o caracter deste nodo
        private char getCharacter() {
            return this.character;
        }

        // Retorna o nodo pai deste nodo escolhido
        private CharNode getFather() {
            return this.father;
        }

        // Define o significado da palavra
        public void setSignificado(String significado) {
            this.significado = significado;
        }
    }

    
    public WordTree() {
        root = new CharNode('\0');
        totalNodos++;
    }

    // Retorna o total de palavras
    public int getTotalWords() {
        return totalWords;
    }

    // Retorna o total de nodos na árvore
    public int getTotalNodes() {
        return totalNodos;
    }

    // Adiciona uma palavra com o seu significado na arvore
    public void addWord(String word, String significado) {
        if (word.length() <= 0)
            return;

        CharNode n = root;
        for (int i = 0; i < word.length(); i++) {
            CharNode aux;
            if (i == word.length() - 1) {// confere se o loop ja chegou no ultimo caracter da palavra
                aux = n.addChild(word.charAt(i), true);
                totalWords++;
            } else {
                aux = n.addChild(word.charAt(i), false);
            }

            if (aux == null) {// quer dizer que esse nodo ja existe na arvore, com isso o n vira o filho do caracter atual
                n = n.findChildChar(word.charAt(i));
            } else {
                n = aux;
                totalNodos++;
            }
        }

        // Adiciona a definiçao da palavra
        if (n != null)
            n.setSignificado(significado);
    }

    // Realiza uma busca por palavras que começam com o prefixo especificado
    public List<String> searchString(String prefixo) {
        CharNode ref = search(prefixo, 0, root);
        if (ref != null) {
            ArrayList<String> listaPalavras = new ArrayList<>();
            criarLista(prefixo, ref, listaPalavras);
            return listaPalavras;
        } else {
            return null;
        }
    }

    // Método recursivo utilizado para auxiliar o metodo searchString,realizando a busca das palavras a busca por palavras
    private CharNode search(String str, int pos, CharNode ref) {
        if (ref == null || pos >= str.length())
            return null;

        CharNode filho = ref.findChildChar(str.charAt(pos));
        if (filho == null)
            return null;

        if (pos == str.length() - 1)
            return filho;

        return search(str, pos + 1, filho);
    }

    // Método recursivo utilizado para auxiliar o metodo SearchString tambem, realizando a criaçao da lista de palavras encontradas
    private void criarLista(String prefixo, CharNode ref, ArrayList<String> lista) {
        for (CharNode filho : ref.children) {
            if (filho.isFinal) {
                lista.add(prefixo + filho.character);
            }
            criarLista(prefixo + filho.character, filho, lista);
        }
    }
}
