package com.github.paopaoyue.onmyojimod.object.kami;

import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.github.paopaoyue.onmyojimod.OnmyojiMod;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class Hangan extends AbstractKami {

    public static final String ID = "Onmyoji:Hangan";

    public static final String CHARACTER_IMG = "image/character/hangan.png";
    private static final Keyword kamiString = OnmyojiMod.MOD_DICTIONARY.get(ID);

    public Hangan() {
        this.id = kamiString.ID;
        this.name = kamiString.NAMES[0];
        this.faction = Faction.BLUE;
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
