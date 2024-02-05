package com.github.paopaoyue.onmyojimod.relic;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Dice extends CustomRelic {
    public static final String ID = "Onmyoji:Dice";
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final RelicTier TIER = RelicTier.SHOP;
    private static final LandingSound SOUND = LandingSound.SOLID;

    private static final int INIT_AMOUNT = 2;

    private int amount;

    public Dice() {
        super(ID, ImageMaster.loadImage("image/icon/dice.png"), TIER, SOUND);
        amount = INIT_AMOUNT;
    }

    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0] + INIT_AMOUNT + strings.DESCRIPTIONS[1];
    }

    public int onRollDice(int dice) {
        if (amount > 0) {
            this.amount--;
            this.flash();
            return 6;
        }
        return dice;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Dice();
    }
}
