package com.github.paopaoyue.onmyojimod.object.kami;

import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.github.paopaoyue.onmyojimod.OnmyojiMod;

public class Yamata extends AbstractKami {

    public static final String ID = "Onmyoji:Yamata";

    public static final String CHARACTER_IMG = "image/character/yamata.png";
    private static final Keyword kamiString = OnmyojiMod.MOD_DICTIONARY.get(ID);

    public Yamata() {
        this.id = kamiString.ID;
        this.name = kamiString.NAMES[0];
        this.faction = Faction.RED;
        this.updateDescription();
    }

    @Override
    public String getCharacterImage() {
        return CHARACTER_IMG;
    }

    @Override
    public void updateDescription() {
        this.description = kamiString.DESCRIPTION;
    }

}
