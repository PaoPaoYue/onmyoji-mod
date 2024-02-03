package com.github.paopaoyue.onmyojimod.relic;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class GoldenFeather extends CustomRelic {
    public static final String ID = "Onmyoji:Golden Feather";
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final RelicTier TIER = RelicTier.UNCOMMON;
    private static final LandingSound SOUND = LandingSound.CLINK;

    private static final int amount = 1;

    public GoldenFeather() {
        super(ID, ImageMaster.loadImage("image/icon/golden_feather.png"), TIER, SOUND);
    }

    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0] + amount + strings.DESCRIPTIONS[1];
    }

    public void atTurnStart() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(new com.github.paopaoyue.onmyojimod.card.GoldenFeather(), amount));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new GoldenFeather();
    }
}
