package com.github.paopaoyue.onmyojimod.kami;

public enum Faction {

    NONE,
    BLUE,
    GREEN,
    PURPLE,
    RED;

    public static final int FACTOIN_NUM = 4;

    @Override
    public String toString() {
        return "FACTION_" + super.toString();
    }
}


