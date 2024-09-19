package com.example.demo.equipe.domaine.model;

import com.example.demo.core.domain.model.BaseEntity;

public class Equipe extends BaseEntity<Equipe> {
    private String name;


    public Equipe(String id,String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public Equipe deepClone() {
        return new Equipe(this.id, this.name);
    }
}
