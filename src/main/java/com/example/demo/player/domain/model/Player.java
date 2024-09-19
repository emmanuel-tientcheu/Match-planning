package com.example.demo.player.domain.model;

import com.example.demo.core.domain.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "players")
public class Player extends BaseEntity<Player> {
    private String id;
    private String name;

    public Player(){}
    public Player(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {return this.id;}
    public String getName() {return this.name;}
    public void rename(String name) { this.name = name; }

    @Override
    public Player deepClone() {
        return new Player(this.id, this.name);
    }
}
