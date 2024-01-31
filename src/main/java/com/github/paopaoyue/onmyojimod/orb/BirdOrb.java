package com.github.paopaoyue.onmyojimod.orb;

import com.badlogic.gdx.graphics.Texture;
import com.github.paopaoyue.onmyojimod.power.PenetratedPower;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.PressurePointEffect;

public class BirdOrb extends AbstractCountdownOrb {

    public static final String ORB_ID = "Onmyoji:Bird";
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    private static final Texture IMG = ImageMaster.loadImage("image/vfx/orb/bird.png");

    private int penetratedAmount;

    public BirdOrb(int countdown, int penetratedAmount) {
        super(countdown);
        this.ID = ORB_ID;
        this.name = orbString.NAME;
        this.img = IMG;
        this.penetratedAmount = penetratedAmount;

        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = orbString.DESCRIPTION[0] + this.passiveAmount + orbString.DESCRIPTION[1] + penetratedAmount + orbString.DESCRIPTION[2];
    }

    @Override
    public void onEvoke() {
        float minX = Float.POSITIVE_INFINITY;
        AbstractMonster target = null;
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (m.isDead) {
                continue;
            }
            if (m.hb.cX < minX) {
                minX = m.hb.cX;
                target = m;
            }
        }
        if (target != null) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new PenetratedPower(target, this.penetratedAmount)));
        }
    }

    @Override
    public AbstractOrb makeCopy() {
        return new BirdOrb(this.basePassiveAmount, this.penetratedAmount);
    }

    @Override
    public void playChannelSFX() {

    }
}
