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

public class Sachet extends CustomRelic {
    public static final String ID = "Onmyoji:Sachet";
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final AbstractRelic.RelicTier TIER = AbstractRelic.RelicTier.STARTER;
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.FLAT;

    public Sachet() {
        super(ID, ImageMaster.loadImage("image/icon/sachet.png"), TIER, SOUND);
    }

    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0];
    }

    public void atBattleStartPreDraw() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new RandomAttachSpiritAction(new Enhancement(), 2, c -> c instanceof AbstractKamiCard, true, true));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Sachet();
    }
}
