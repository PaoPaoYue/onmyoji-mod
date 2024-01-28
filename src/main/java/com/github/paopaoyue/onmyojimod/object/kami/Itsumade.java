package com.github.paopaoyue.onmyojimod.object.kami;

import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.github.paopaoyue.onmyojimod.OnmyojiMod;
import com.github.paopaoyue.onmyojimod.orb.FeatherOrb;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class Itsumade extends AbstractKami {

    public static final String ID = "Onmyoji:Itsumade";

    public static final String CHARACTER_IMG = "image/character/itsumade.png";
    private static final Keyword kamiString = OnmyojiMod.MOD_DICTIONARY.get(ID);

    public Itsumade() {
        this.id = kamiString.ID;
        this.name = kamiString.NAMES[0];
        this.faction = Faction.GREEN;
        this.updateDescription();
    }

    @Override
    public void onEnter() {
        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new FeatherOrb(4)));
    }

    @Override
    public void onExit() {
        if (!upgraded) {
            return;
        }
        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new FeatherOrb(4)));
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
