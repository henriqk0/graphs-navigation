package com.lib;

import java.util.ArrayList;
import java.util.HashMap;

public class GraphWithHash<T> {

    private final HashMap<T ,ArrayList<Edge<T>>> adj_list = new HashMap<>(); //Lista de adjacência (usando hash map)

    //Método que adiciona um conjunto de ligações de um vértice do grafo
    //Levando em conta aplicação:
    //Chave - Nome da ilha (por exemplo); Valor - Lista de Edges que ligam essa ilha a outras

    public T addNodeRelations(T key, ArrayList<Edge<T>> edges)
    {
      adj_list.put(key,edges);
      return key;
    }

    //Pega as ligações de um nó específico pela sua chave
    // (No exemplo da aplicação, pega as ligações de uma ilha pelo nome dela)

    public ArrayList<Edge<T>> getNodeRelations(T key){
      return adj_list.get(key);
    }

    //Printa a lista de adjacência
    public void printGraph(){


    }

    /*Dúvidas a resolver:
    * 1 - Onde essa lista de Edges será instanciada?;
    * */
}
