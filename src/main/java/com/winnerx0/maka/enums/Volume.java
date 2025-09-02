package com.winnerx0.maka.enums;

public enum Volume {

    UP("Up"),
    DOWN("Down");

    private final String name ;

    Volume(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
