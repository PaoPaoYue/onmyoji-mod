package com.github.paopaoyue.onmyojimod.object.kami;

import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.github.paopaoyue.onmyojimod.OnmyojiMod;
import com.github.paopaoyue.onmyojimod.card.GoldenSeimei;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class Bokku extends AbstractKami {

    public static final String ID = "Onmyoji:Bokku";

    public static final String CHARACTER_IMG = "image/character/bokku.png";
    private static final Keyword kamiString = OnmyojiMod.MOD_DICTIONARY.get(ID);

    public Bokku() {
        this.id = kamiString.ID;
        this.name = kamiString.NAMES[0];
        this.faction = Faction.BLUE;
        this.updateDescription();
    }

    @Override
    public void onEnter() {
        AbstractCard card = new GoldenSeimei();
        if (upgraded) {
            card.upgrade();
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(card, 1, false, true, true, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
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
