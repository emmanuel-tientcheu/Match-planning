package com.example.demo.player.domain.viewmodel;

public class PlayerViewModel {
    private String name;
    private String id;

    public PlayerViewModel() {}

    public PlayerViewModel(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }


    public String getId() {
        return id;
    }
}
