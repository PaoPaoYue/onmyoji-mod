package com.github.paopaoyue.onmyojimod.orb;

import com.badlogic.gdx.graphics.Texture;
import com.github.paopaoyue.onmyojimod.card.GoldenFeather;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class FeatherOrb extends AbstractCountdownOrb {

    public static final String ORB_ID = "Onmyoji:Feather";
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    private static final Texture IMG = ImageMaster.loadImage("image/vfx/orb/feather.png");

    public FeatherOrb(int countdown) {
        super(countdown);
        this.ID = ORB_ID;
        this.name = orbString.NAME;
        this.img = IMG;

        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = orbString.DESCRIPTION[0] + this.passiveAmount + orbString.DESCRIPTION[1];
    }

    @Override
    public void onEvoke() {
        AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(new GoldenFeather(), 2));
    }

    @Override
    public AbstractOrb makeCopy() {
        return new FeatherOrb(this.basePassiveAmount);
    }

    @Override
    public void playChannelSFX() {

    }
}
