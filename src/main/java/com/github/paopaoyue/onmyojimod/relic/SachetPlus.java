package com.github.paopaoyue.onmyojimod.relic;

import basemod.abstracts.CustomRelic;
import com.github.paopaoyue.onmyojimod.action.RandomAttachSpiritAction;
import com.github.paopaoyue.onmyojimod.card.AbstractKamiCard;
import com.github.paopaoyue.onmyojimod.object.spirit.Enhancement;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class SachetPlus extends CustomRelic {
    private static final String ID = "Onmyoji:Sachet Plus";
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final RelicTier TIER = RelicTier.BOSS;
    private static final LandingSound SOUND = LandingSound.FLAT;

    public SachetPlus() {
        super(ID, ImageMaster.loadImage("image/icon/sachet_plus.png"), TIER, SOUND);
    }

    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0];
    }

    public void onPlayerEndTurn() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new RandomAttachSpiritAction(new Enhancement(), 1, c -> c instanceof AbstractKamiCard, true, true));
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic("Burning Blood");
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SachetPlus();
    }

}
