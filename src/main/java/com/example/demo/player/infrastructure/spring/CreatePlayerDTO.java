package com.example.demo.player.infrastructure.spring;

public class CreatePlayerDTO {
    private String name;

    public CreatePlayerDTO() {}
    public CreatePlayerDTO(String name) {this.name = name;}

    public String getName(){return this.name;}
    public void setName(String name){this.name = name;}
}
