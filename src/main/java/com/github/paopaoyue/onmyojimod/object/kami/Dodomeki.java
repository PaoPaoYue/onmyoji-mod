package com.github.paopaoyue.onmyojimod.object.kami;

import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.github.paopaoyue.onmyojimod.OnmyojiMod;
import com.github.paopaoyue.onmyojimod.action.ProjectileAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class Dodomeki extends AbstractKami {

    public static final String ID = "Onmyoji:Dodomeki";

    public static final String CHARACTER_IMG = "image/character/dodomeki.png";
    private static final Keyword kamiString = OnmyojiMod.MOD_DICTIONARY.get(ID);

    public Dodomeki() {
        this.id = kamiString.ID;
        this.name = kamiString.NAMES[0];
        this.faction = Faction.BLUE;
        this.updateDescription();
    }

    @Override
    public void onDivine(int amount) {
        AbstractDungeon.actionManager.addToBottom(new ProjectileAction(upgraded ? amount * 2 : amount));
    }

    @Override
    public String getCharacterImage() {
        return CHARACTER_IMG;
    }

    @Override
    public void updateDescription() {
        String[] description = kamiString.DESCRIPTION.split("\\|");
        if (this.upgraded) {
            this.description = description[1];
        } else {
            this.description = description[0];
        }
    }

}
