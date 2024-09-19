package com.example.demo.player.infrastructure.persistance.jpa;

import com.example.demo.core.domain.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "players")
public class SQLPlayer extends BaseEntity<SQLPlayer> {

    @Column
    private String name;

    public SQLPlayer(String id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public SQLPlayer deepClone() {
        return new SQLPlayer(this.id, this.name);
    }

    public SQLPlayer() {}

    public String getName() {return this.name; }
}
