package com.github.paopaoyue.onmyojimod.object.kami;

import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.github.paopaoyue.onmyojimod.OnmyojiMod;

public class Yamausagi extends AbstractKami {

    public static final String ID = "Onmyoji:Yamausagi";

    public static final String CHARACTER_IMG = "image/character/yamausagi.png";
    private static final Keyword kamiString = OnmyojiMod.MOD_DICTIONARY.get(ID);

    public Yamausagi() {
        this.id = kamiString.ID;
        this.name = kamiString.NAMES[0];
        this.faction = Faction.GREEN;
        this.updateDescription();
    }

    @Override
    public int onRollDice(int dice) {
        return 6;
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
