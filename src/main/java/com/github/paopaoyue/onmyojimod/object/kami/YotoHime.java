package com.github.paopaoyue.onmyojimod.object.kami;

import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.github.paopaoyue.onmyojimod.OnmyojiMod;

public class YotoHime extends AbstractKami {

    public static final String ID = "Onmyoji:Yoto Hime";

    public static final String CHARACTER_IMG = "image/character/yoto_hime.png";
    private static final Keyword kamiString = OnmyojiMod.MOD_DICTIONARY.get(ID);

    public YotoHime() {
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
        description = kamiString.DESCRIPTION;
    }

}
