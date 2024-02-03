package com.github.paopaoyue.onmyojimod.relic;

import basemod.abstracts.CustomRelic;
import com.github.paopaoyue.onmyojimod.orb.AbstractCountdownOrb;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Sushi extends CustomRelic {
    public static final String ID = "Onmyoji:Sushi";
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final RelicTier TIER = RelicTier.RARE;
    private static final LandingSound SOUND = LandingSound.SOLID;

    public Sushi() {
        super(ID, ImageMaster.loadImage("image/icon/sushi.png"), TIER, SOUND);
    }

    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0];
    }

    public void onPlayerEndTurn() {
        int amount = 0;
        for (AbstractOrb orb : AbstractDungeon.player.orbs) {
            if (orb instanceof AbstractCountdownOrb) {
                amount += ((AbstractCountdownOrb) orb).getCountdown();
            }
        }
        if (amount > 0) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, amount));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Sushi();
    }
}
