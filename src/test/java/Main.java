import com.lib.Graph;

public class Main {
    public static void main(String[] args) {
        // Criação de um grafo não-direcionado
        Graph<String> grafo = new Graph<>();

        // Adiciona vértices
        grafo.addVertex("A");
        grafo.addVertex("B");
        grafo.addVertex("C");

        // Adiciona arestas com pesos
        grafo.addEdge("A", "B", 1.5f);
        grafo.addEdge("B", "C", 2.5f);
        grafo.addEdge("A", "C", 3.5f);
        
        // Testa adição de vértice automático (não existe "D", será criado)
        grafo.addEdge("C", "D", 4.5f);

        // Testa o método getVertexIndex
        System.out.println("Índice do vértice 'A': " + grafo.getVertexIndex("A"));
        System.out.println("Índice do vértice 'D': " + grafo.getVertexIndex("D"));
        System.out.println("Índice do vértice 'Z' (inexistente): " + grafo.getVertexIndex("Z"));

        // Exibe a matriz de adjacência
        System.out.println("\nMatriz de adjacência:");
        grafo.displayMatrix();
    }
}
