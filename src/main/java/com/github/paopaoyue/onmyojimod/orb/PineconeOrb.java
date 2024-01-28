package com.github.paopaoyue.onmyojimod.orb;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class PineconeOrb extends AbstractCountdownOrb {

    public static final String ORB_ID = "Onmyoji:Pinecone";
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    private static final Texture IMG = ImageMaster.loadImage("image/vfx/orb/pinecone.png");
    private AbstractCard kamiCard;

    public PineconeOrb(int countdown, AbstractCard kamiCard) {
        super(countdown);
        this.ID = ORB_ID;
        this.name = orbString.NAME;
        this.img = IMG;
        this.kamiCard = kamiCard;

        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = orbString.DESCRIPTION[0] + this.passiveAmount + orbString.DESCRIPTION[1];
    }

    @Override
    public void onEvoke() {
        AbstractDungeon.actionManager.addToTop(new DiscardToHandAction(this.kamiCard));
    }

    @Override
    public AbstractOrb makeCopy() {
        return new PineconeOrb(this.basePassiveAmount, this.kamiCard);
    }

    @Override
    public void playChannelSFX() {

    }
}
