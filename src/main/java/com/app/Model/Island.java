package com.app.Model;

import com.lib.Vertice;

public class Island {
    public Vertice<String> instance;

    public Island(String name){
        instance = new Vertice<String>(name);
    }
}
