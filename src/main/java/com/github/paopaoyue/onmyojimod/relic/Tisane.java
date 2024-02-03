package com.github.paopaoyue.onmyojimod.relic;

import basemod.abstracts.CustomRelic;
import com.github.paopaoyue.onmyojimod.action.DivineAction;
import com.github.paopaoyue.onmyojimod.action.HealLossHPAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Tisane extends CustomRelic {
    public static final String ID = "Onmyoji:Tisane";
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final RelicTier TIER = RelicTier.RARE;
    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final int amount = 1;

    public Tisane() {
        super(ID, ImageMaster.loadImage("image/icon/tisane.png"), TIER, SOUND);
    }

    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0] + amount + strings.DESCRIPTIONS[1];
    }

    public void onExhaust(AbstractCard card) {
        this.addToBot(new HealLossHPAction(amount));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Tisane();
    }
}
