package com.github.paopaoyue.onmyojimod.relic;

import basemod.abstracts.CustomRelic;
import com.github.paopaoyue.onmyojimod.power.PenetratedPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class PoisonFeather extends CustomRelic {
    public static final String ID = "Onmyoji:Poison Feather";
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final RelicTier TIER = RelicTier.UNCOMMON;
    private static final LandingSound SOUND = LandingSound.CLINK;

    private static final int amount = 2;

    public PoisonFeather() {
        super(ID, ImageMaster.loadImage("image/icon/poison_feather.png"), TIER, SOUND);
    }

    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0] + amount + strings.DESCRIPTIONS[1];
    }

    public void atBattleStart() {
        this.flash();
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, AbstractDungeon.player, new PenetratedPower(m, amount), amount, true));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new PoisonFeather();
    }
}
