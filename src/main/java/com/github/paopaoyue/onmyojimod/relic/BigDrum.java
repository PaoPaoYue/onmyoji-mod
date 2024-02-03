package com.github.paopaoyue.onmyojimod.relic;

import basemod.abstracts.CustomRelic;
import com.github.paopaoyue.onmyojimod.power.EncouragedPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class BigDrum extends CustomRelic {
    public static final String ID = "Onmyoji:Big Drum";
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final RelicTier TIER = RelicTier.COMMON;
    private static final LandingSound SOUND = LandingSound.HEAVY;

    private static final int amount = 1;

    public BigDrum() {
        super(ID, ImageMaster.loadImage("image/icon/big_drum.png"), TIER, SOUND);
    }

    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0] + amount + strings.DESCRIPTIONS[1];
    }

    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EncouragedPower(AbstractDungeon.player, amount), amount, true));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BigDrum();
    }
}
