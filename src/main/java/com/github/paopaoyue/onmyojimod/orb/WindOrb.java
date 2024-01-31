package com.github.paopaoyue.onmyojimod.orb;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class WindOrb extends AbstractCountdownOrb {

    public static final String ORB_ID = "Onmyoji:Wind";
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    private static final Texture IMG = ImageMaster.loadImage("image/vfx/orb/wind.png");

    private int block;

    public WindOrb(int countdown, int block) {
        super(countdown);
        this.ID = ORB_ID;
        this.name = orbString.NAME;
        this.img = IMG;
        this.block = block;

        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = orbString.DESCRIPTION[0] + this.passiveAmount + orbString.DESCRIPTION[1] + this.block + orbString.DESCRIPTION[2];
    }

    @Override
    public void onEvoke() {
        AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, block));
    }

    @Override
    public AbstractOrb makeCopy() {
        return new WindOrb(this.basePassiveAmount, this.block);
    }

    @Override
    public void playChannelSFX() {

    }
}
