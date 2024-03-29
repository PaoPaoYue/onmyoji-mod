package com.github.paopaoyue.onmyojimod.object.kami;

import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.github.paopaoyue.onmyojimod.OnmyojiMod;
import com.github.paopaoyue.onmyojimod.action.RandomAttachSpiritAction;
import com.github.paopaoyue.onmyojimod.object.spirit.Knot;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class Enmusubi extends AbstractKami {

    public static final String ID = "Onmyoji:Enmusubi";

    public static final String CHARACTER_IMG = "image/character/enmusubi.png";
    private static final Keyword kamiString = OnmyojiMod.MOD_DICTIONARY.get(ID);


    public Enmusubi() {
        this.id = kamiString.ID;
        this.name = kamiString.NAMES[0];
        this.faction = Faction.PURPLE;
        this.updateDescription();
    }

    public void onEnter() {
        if (upgraded) {
            AbstractDungeon.actionManager.addToBottom(new RandomAttachSpiritAction(new Knot(), 1, x -> true, false, true));
        }
    }

    @Override
    public void onEndOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new RandomAttachSpiritAction(new Knot(), 1, x -> true, false, true));
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
