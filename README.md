
# 🧾 Relatório de Desenvolvimento - Biblioteca de Grafos & Aplicativo de Navegação

> **Integrantes (Grupo 4 )**: Gabriel, Guilherme, Henrique, Miguel  
> **Tecnologia Principal**: Java  
> **Objetivo**: Desenvolver uma biblioteca de grafos reutilizável e uma aplicação prática que a utilize.

---

## 📌 1. Introdução

Este relatório descreve o processo de desenvolvimento de uma biblioteca de grafos em **Java**, juntamente com uma aplicação que a utiliza. Serão abordadas as **estratégias de representação dos grafos**, os **algoritmos clássicos implementados**, o **uso prático desses algoritmos**, além do apoio de ferramentas de **Inteligência Artificial (IA/LLM)** no desenvolvimento.

---

## 🧩 2. Representação de Grafos

A implementação da biblioteca de grafos possui duas representações na forma de classes:
- Vértices (Vertex<T>);
- Grafos (Graph<T>)

Uma instância de Vertex<T> é um objeto simples que contém um tipo T genérico que representa seu valor, como representado no código abaixo:
````java
public class Vertex<T>{
    private T value;
    
    public T getValue(){return this.value;}
    public void setValue(T v){this.value = v;}
    
    
}
````

Uma instância de Graph<T> contém dois atributos:
- Uma lista dos vértices existentes para o tipo genérico T (do tipo ArrayList<Vertex<T>>);
- Uma matriz de adjacência (do tipo ArrayList<ArrayList<Float>>);
- Uma flag para dizer se o grafo é ou não direcionado (do tipo boolean)

O código abaixo mostra a implementação geral da classe Graph<T>: 
````java
public class Graph<T>{
    private final ArrayList<Vertex<T>> vertices; // List of vertices
    private ArrayList<ArrayList<Float>> edges; // Adjacency matrix to represent edges
    private boolean directed; // Default is false

    public Graph() {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.directed = false;
    }
    
    //Métodos
}
````

---

## 🧠 3. Algoritmos Clássicos de Grafos

### **Dijkstra**

O método dijkstra(T origin, T destination) implementa o algoritmo de Dijkstra com o objetivo de encontrar o menor custo de caminho entre dois vértices de um grafo ponderado. A premissa do algoritmo é que todas as arestas possuem pesos não negativos. Na implementação abaixo, o método retorna apenas o valor total do caminho mais curto entre os vértices origin e destination, assumindo que o destino é alcançável a partir da origem:


````java
public float dijkstra(T origin, T destination) { // assuming that the destination is reachable from the origin vertex
    try {
      int originIndex = getVertexIndex(origin);
      int destinationIndex = getVertexIndex(destination);

      if (originIndex == -1 || destinationIndex == -1) { 
        throw new Exception();
      }

      float[] distances = toFullFloatArray(this.edges.size(), 99999);
      int[] predecessors = toFullIntArray(this.edges.size(), 0);
      boolean[] visited = new boolean[this.edges.size()];

      distances[originIndex] = 0;
      predecessors[originIndex] = -1;
     
      int i = originIndex;
      float minDistance;

      visited[originIndex] = true;
      for (int n = 0; n < this.edges.size() && visited[destinationIndex] == false; n++) { // worst case -> n - 1 times
        minDistance = 99999;

        for (int j = 0; j < this.edges.size(); j++) { // to pick all weight values from `i` vertex
          float pathWeight = this.edges.get(i).get(j); 

          if (pathWeight != 0) { // existence of an edge to `j`
            if (visited[j] == false && pathWeight + distances[i] < distances[j] ) { // new distance < previous stored distance
              distances[j] = pathWeight + distances[i];  // then, update the minimal distance to vertex `j`
              predecessors[j] = i;  // update the last predecessor from `j`
            }
          }
        }

````

O algoritmo inicia obtendo os índices dos vértices de origem e destino:

```java
int originIndex = getVertexIndex(origin);
int destinationIndex = getVertexIndex(destination);
```

Caso qualquer um dos vértices não seja encontrado no grafo (índice igual a -1), uma exceção é lançada:

```java
if (originIndex == -1 || destinationIndex == -1) {
    throw new Exception();
}
```

Em seguida, são inicializados três vetores auxiliares:

- `distances`: guarda a menor distância conhecida até cada vértice (inicializado com `99999`).
- `predecessors`: armazena o último vértice anterior no caminho mínimo.
- `visited`: indica se um vértice já foi processado.

```java
float[] distances = toFullFloatArray(this.edges.size(), 99999);
int[] predecessors = toFullIntArray(this.edges.size(), 0);
boolean[] visited = new boolean[this.edges.size()];

distances[originIndex] = 0;
predecessors[originIndex] = -1;
```

O laço principal se repete no máximo `n` vezes (onde `n` é o número de vértices), ou até que o destino seja alcançado. Ele percorre os vizinhos do vértice atual (`i`) buscando caminhos mais curtos:

```java
for (int n = 0; n < this.edges.size() && visited[destinationIndex] == false; n++) {
    ...
}
```

Em cada iteração, o algoritmo percorre todos os vértices `j` verificando a existência de uma aresta `i → j`:

```java
float pathWeight = this.edges.get(i).get(j);
if (pathWeight != 0) {
    ...
}
```

Se `j` ainda não foi visitado e o novo caminho através de `i` é mais curto, os valores são atualizados:

```java
if (!visited[j] && pathWeight + distances[i] < distances[j]) {
    distances[j] = pathWeight + distances[i];
    predecessors[j] = i;
}
```

Percorrido todas as arestas que partem de `i`, marcamos o vértice `i` como visitado, obtendo, em seguida o caminho de menor peso dentre os vértices não marcados, para a próxima atualizar o valor de `i` na próxima iteração, para que se faça as verificações restantes: 

```java
visited[j] 
for (int j = 0; j < this.edges.size(); j++) { // pick next vertex with minimal distance before destination
  if (visited[j] == false && distances[j] < minDistance) {
    minDistance = distances[j];
    i = j;
  }
}
```

Esta implementação utiliza uma **matriz de adjacência** e uma abordagem de **força bruta** para seleção do próximo vértice. Isso resulta em uma complexidade de tempo:

```
O(n²)
```

Essa abordagem é aceitável para grafos **pequenos ou densos**, mas se torna ineficiente em grafos **grandes ou esparsos**.

Para melhorar a eficiência, seria bom:

- Utilizar uma **lista de adjacência** no lugar da matriz;
- Adotar uma **fila de prioridade** (min-heap) para selecionar o próximo vértice com menor distância;

Com essas mudanças, é possível atingir complexidade:

```
O((V + E) log V)
```
 
Essa versão do algoritmo de Dijkstra é simples e funcional, ideal para fins educacionais e grafos de pequena escala. Contudo, para aplicações reais com grande volume de dados, adaptações estruturais são fortemente recomendadas.

### **Árvore Geradora Mínima**


O método `minnimalSpanningTree()` implementa o algoritmo de Prim para encontrar o custo total da **Árvore Geradora Mínima** (MST - Minimum Spanning Tree) de um grafo não direcionado e ponderado. A função retorna esse custo como um valor `float`, assumindo que o grafo é conexo e que os pesos das arestas são todos positivos.
Segue abaixo o trecho de código que implementa o algoritmo:



````java
public int minIndxPrim(int[] predecessors, float[] weights, boolean[] inTree, int row) {
  float minWeight = 99999;

  int j = 0;
  for (int indx = 0; indx < inTree.length; indx++) {
    if (inTree[indx] == false && this.edges.get(row).get(indx) < minWeight) {
      minWeight = weights[indx];
      j = indx;
    }
  }
  return j;
}

public float minnimalSpanningTree(){
  if (this.edges.isEmpty()) return 0;

  else {
    float[] weights = toFullFloatArray(this.edges.size(), 99999);
    int[] predecessors = toFullIntArray(this.edges.size(), 0);
    boolean[] itsInTree = new boolean[this.edges.size()];

    weights[0] = 0;
    predecessors[0] = -1;

    for (int aux = 0; aux < itsInTree.length - 1; aux++) {
      int j = minIndxPrim(predecessors, weights, itsInTree, aux);

      itsInTree[j] = true;

      for (int w = 0; w < itsInTree.length; w++) {
        if (this.edges.get(j).get(w) > 0 && itsInTree[w] == false &&
          this.edges.get(j).get(w) < weights[w] ) {
          predecessors[w] = j;
          weights[w] = this.edges.get(j).get(w);
        }
      }
      itsInTree[j] = true;
    }

    float total = 0;
    for (float wgt : weights) {
      total += wgt;
    }
    System.out.println("Rotas:");
    for (int i = 0; i < weights.length; i++) {
      if (weights[i] != 0) {
        System.out.println(this.vertices.get(predecessors[i]).getValue() + " -(Risco: "
        + weights[i] +")-> " 
        + this.vertices.get(i).getValue());
      }
    }
    return total;
  }
}
````

A função verifica inicialmente se o grafo está vazio:

```java
if (this.edges.isEmpty()) return 0;
```

Se houver vértices e arestas, o algoritmo inicializa três vetores auxiliares:

- `weights`: guarda os menores pesos conhecidos de arestas conectando vértices fora da árvore.
- `predecessors`: mantém o índice do vértice anterior na árvore (não usado diretamente no retorno).
- `itsInTree`: indica se o vértice já foi incluído na árvore geradora.

```java
float[] weights = toFullFloatArray(this.edges.size(), 99999);
int[] predecessors = toFullIntArray(this.edges.size(), 0);
boolean[] itsInTree = new boolean[this.edges.size()];

itsInTree[0] = true;
predecessors[0] = -1;
```

O vértice de índice `0` é escolhido como ponto de partida, sendo o primeiro incluído na árvore.


O laço externo executa `n - 1` vezes, onde `n` é o número de vértices. A cada iteração, o algoritmo escolhe a próxima aresta de menor peso que conecta um vértice fora da árvore:

```java
for (int aux = 0; aux < itsInTree.length - 1; aux++) {
    ...
}
```

Dentro do laço, o trecho abaixo busca o vértice `j` que tem o menor peso de conexão com a árvore atual:

```java
for (indx = 0; indx < itsInTree.length; indx++) {
    if (!itsInTree[indx] && this.edges.get(aux).get(indx) < minWeight) {
        minWeight = weights[indx];
        j = indx;
    }
}
```

Depois, o vértice `j` é incluído na árvore:

```java
itsInTree[j] = true;
```

Em seguida, o algoritmo atualiza os pesos e predecessores para os vértices vizinhos de `j`, caso encontre conexões mais baratas:

```java
for (int w = 0; w < itsInTree.length; w++) {
    if (this.edges.get(j).get(w) > 0 && !itsInTree[w] &&
        this.edges.get(j).get(w) < weights[w]) {
        predecessors[w] = j;
        weights[w] = this.edges.get(j).get(w);
    }
}
```


Esta implementação de Prim utiliza uma **matriz de adjacência** e busca o próximo vértice de forma **linear**, o que resulta em uma complexidade de tempo:

```
O(n²)
```

onde `n` é o número de vértices. Isso se deve ao fato de que, em cada uma das `n` iterações, percorremos novamente todos os vértices (`n` buscas internas).


O método `minnimalSpanningTree()` apresenta uma implementação funcional e direta do algoritmo de Prim, retornando o custo total da árvore geradora mínima. Embora simples e didático, seu desempenho pode ser melhorado significativamente em casos com muitos vértices e poucas arestas através de otimizações estruturais.

---

## 🚀 4. Aplicação Prática & Manual de Uso

### 📱 Descrição da Aplicação
A aplicação de navegação veio com a proposta de facilitar a gestão de tráfego marítimo. Ele visa resolver problemas de eficiência com o planejamento de rotas, oferencendo um conjunto de funcionalidades que o usuário navegador pode utilizar em suas viagens.
### 🛠️ Funcionalidades
A aplicação se utiliza do conceito de grafos e algoritmos para trazer dados informativos de maneira que o usuário possa:
- Acessar os pontos de embarque e desembarque disponiveis;
- Adicionar portos personalizados;
- Visualizar um mapa com todos os portos definidos;
- Informar a rota mais curta entre um ponto de embarcação e outro;
- Informar a rota global mais "segura".

### 📘 Manual de Uso
Para executar a aplicação, faz-se necessária um IDE como, por exemplo, o JEtBrains IntelliJ.
Ao extrair o projeto do repositório (link do github no final da documentação), haverá duas pastas separadas no caminho *src/main/java/com:* **app** e **lib**

Ao acessar a pasta **app**, a classe inicial para a execução do programa estará logo na sua raiz; é uma classe chamada **Application**. 

Na execução da classe **Application**, a seguinte interface aparecerá no terminal:

```bash
╔═══════════════════════════════╗                                        
║           BEM VINDO!          ║          «░░▒▒╗                       
╠═══════════════════════════════╣               ▐                        
║      Selecione uma opção:     ║             ░▓▐▓▒░                      
║ ───────────────────────────── ║            ░▒▓▐▓▓▓▒▒░                   
║   1) Criar um mapa            ║           ░▒▓▓▐▓▓▓▓▓▒▒▒░                
║   2) Adicionar um porto       ║          ░▒▒▓▓▐▓▓▓▓▓▓▓▒▒▒░░             
║   3) Adicionar uma rota       ║    ▀█▄▄▄▄▄    ▐       ▄▄▄▄▄▄▄▄▄         
║   4) Visualizar mapa          ║     ▀████████████████████████         
║   5) Rota global mais segura  ║       ▀██████████████████▀▀           
║   6) Melhor caminho A->B      ║      «▓▓▓▀▀██████████▀▀▀               
║   0) Sair                     ║      «╜      ▀▀▀▀                      
╚═══════════════════════════════╝   
```
Ela contém 6 opções (excluindo a opção de sair), assim como representada acima; cada uma responsável por uma funcionalidade específica

1.  Criar um novo mapa (instancia um novo grafo);
2. Adicionar um porto (instancia um novo vértice ao grafo criado);
3. Adicionar uma nova rota (define uma aresta entre dois vértices do grafo instanciado);
4. Visualizar o mapa (Printa uma estrutura do grafo instanciado); 
5. Informa a rota global mais segura (Define, por meio da Árvore Geradora Mínima, a rota global mais segura de navegação)
6. Informa o melhor caminho entre dois portos (Define, por meio de Djikstra, o caminho mínimo entre dois vértices do grafo instanciado)

---

## 🤖 5. Uso de Ferramentas de IA/LLM no Desenvolvimento

Foi utilizado o **Manus** para a criação do template desta documentação e o **ChatGPT** para refino visual e de estrutura. Isto gerou economia de tempo na elaboração e montagem da documentação. 

Embora não se trate de LLM, as explicações e códigos fornecidos pelo site geekforgeeks e pelos slides/videoaulas foram amplamente utilizados na correção e desenvolvimento dos códigos utilizados. Por vezes, dúvidas de sintaxe da linguagem Java foram rapidamente solucionadas pela ferramenta de pesquisa Gemini, agora integrada nas pesquisas comuns do Google.

---

## 👥 6. Contribuições Individuais

| Integrante      | Responsabilidades principais                        |
|------------------|------------------------------------------------------|
| Gabriel        | Documentação do projeto;Montagem de arquitetura MVC da aplicação        |
| Guilherme        | Lib da Matriz de Adjacência e APP                   |
| Henrique         | Algoritmos Dijaskra, Prim e APP               |
| Miguel           | APP                   |

---

## 📂 7. Repositório do Projeto

🔗 [Graphs Navigation](https://github.com/henriqk0/graphs-navigation)

---
