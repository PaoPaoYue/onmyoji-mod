package com.github.paopaoyue.onmyojimod.orb;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class ChordOrb extends AbstractCountdownOrb {

    public static final String ORB_ID = "Onmyoji:Chord";
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    private static final Texture IMG = ImageMaster.loadImage("image/vfx/orb/chord.png");

    private int powerAmount;

    public ChordOrb(int countdown, int powerAmount) {
        super(countdown);
        this.ID = ORB_ID;
        this.name = orbString.NAME;
        this.img = IMG;
        this.powerAmount = powerAmount;

        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = orbString.DESCRIPTION[0] + this.passiveAmount + orbString.DESCRIPTION[1] + powerAmount + orbString.DESCRIPTION[2] + powerAmount + orbString.DESCRIPTION[3];
    }

    @Override
    public void onEvoke() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, this.powerAmount)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, this.powerAmount)));
    }

    @Override
    public AbstractOrb makeCopy() {
        return new ChordOrb(this.basePassiveAmount, this.powerAmount);
    }

    @Override
    public void playChannelSFX() {

    }
}
