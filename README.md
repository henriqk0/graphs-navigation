
# 🧾 Relatório de Desenvolvimento
## Biblioteca de Grafos & Aplicativo de Navegação

> **Integrantes (Grupo 4 )**: Gabriel, Guilherme, Henrique, Miguel  
> **Tecnologia Principal**: Java  
> **Objetivo**: Desenvolver uma biblioteca de grafos reutilizável e uma aplicação prática que a utilize.

---

## 📌 1. Introdução

Este relatório descreve o processo de desenvolvimento de uma biblioteca de grafos em **Java**, juntamente com uma aplicação que a utiliza. Serão abordadas as **estratégias de representação dos grafos**, os **algoritmos clássicos implementados**, o **uso prático desses algoritmos**, além do apoio de ferramentas de **Inteligência Artificial (IA/LLM)** no desenvolvimento.

---

## 🧩 2. Representação de Grafos

> _Descreva aqui a forma de representação adotada, como:_
- Lista de arestas
- Lista de adjacências
- Matriz de adjacências
- Outras formas

Inclua também trechos de código relevantes com destaques, por exemplo:

```java
// Exemplo de representação por lista de adjacências
Map<String, List<String>> grafo = new HashMap<>();
grafo.put("A", List.of("B", "C"));
```

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

> **Nota:** A parte responsável por encontrar o próximo vértice `i` com a menor distância ainda não foi incluída no trecho apresentado, mas é essencial para o funcionamento correto do algoritmo de Dijkstra.


Esta implementação utiliza uma **matriz de adjacência** e uma abordagem de **força bruta** para seleção do próximo vértice. Isso resulta em uma complexidade de tempo:

```
O(n²)
```

Essa abordagem é aceitável para grafos **pequenos ou densos**, mas se torna ineficiente em grafos **grandes ou esparsos**.

Para melhorar a eficiência, recomenda-se:

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
public float minnimalSpanningTree(){
    if (this.edges.isEmpty()) return 0;

    else {
      float[] weights = toFullFloatArray(this.edges.size(), 99999);
      float[] predecessors = toFullFloatArray(this.edges.size(), 0);
      boolean[] itsInTree = new boolean[this.edges.size()];

      itsInTree[0] = true;
      predecessors[0] = -1;

      for (int aux = 0; aux < itsInTree.length - 1; aux++) {

        int indx; float minWeight = 99999;
        int j = 0;
        for (indx = 0; indx < itsInTree.length; indx++) {
          if (itsInTree[indx] == false && this.edges.get(aux).get(indx) < minWeight) {
            minWeight = weights[indx];
            j = indx;
          }
        }

        itsInTree[j] = true;

        for (int w = 0; w < itsInTree.length; w++) {
          if (this.edges.get(j).get(w) > 0 && itsInTree[w] == false &&
            this.edges.get(j).get(w) < weights[w] ) {
            predecessors[w] = j;
            weights[w] = this.edges.get(j).get(w);
          }
        }
      }

      float total = 0;
      for (float wgt : weights) {
        total += wgt;
      }
      return total;
    }
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
float[] predecessors = toFullFloatArray(this.edges.size(), 0);
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

1. Baixar o projeto
2. Compilar o código
3. Executar a aplicação
4. Interagir com a interface (se houver)

### 💻 Execução
Exemplo de execução:

```bash
javac Main.java
java Main
```

---

## 🤖 5. Uso de Ferramentas de IA/LLM no Desenvolvimento

Foi utilizado o **Manus** para a criação do template desta documentação e o **ChatGPT** para refino visual e de estrutura. Isto gerou economia de tempo na elaboração e montagem da documentação. 


---

## 👥 6. Contribuições Individuais

| Integrante      | Responsabilidades principais                        |
|------------------|------------------------------------------------------|
| Gabriel        | Documentação do projeto;Montagem de arquitetura MVC da aplicação        |
| Guilherme        | ...                 |
| Henrique         | ...               |
| Miguel           | ...                  |

---

## 📂 7. Repositório do Projeto

🔗 [Graphs Navigation](https://github.com/henriqk0/graphs-navigation)

---
