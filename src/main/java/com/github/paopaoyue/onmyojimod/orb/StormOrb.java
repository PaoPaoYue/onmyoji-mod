package com.github.paopaoyue.onmyojimod.orb;

import com.badlogic.gdx.graphics.Texture;
import com.github.paopaoyue.onmyojimod.action.StormAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class StormOrb extends AbstractCountdownOrb {

    public static final String ORB_ID = "Onmyoji:Storm";
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    private static final Texture IMG = ImageMaster.loadImage("image/vfx/orb/storm.png");
    private int damage;

    public StormOrb(int countdown, int damage) {
        super(countdown);
        this.ID = ORB_ID;
        this.name = orbString.NAME;
        this.img = IMG;
        this.damage = damage;

        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = orbString.DESCRIPTION[0] + this.passiveAmount + orbString.DESCRIPTION[1] + this.damage + orbString.DESCRIPTION[2];
    }

    @Override
    public void onEvoke() {
        AbstractDungeon.actionManager.addToTop(new StormAction(new DamageInfo(AbstractDungeon.player, this.damage, DamageInfo.DamageType.THORNS)));
    }

    @Override
    public AbstractOrb makeCopy() {
        return new StormOrb(this.basePassiveAmount, this.damage);
    }

    @Override
    public void playChannelSFX() {

    }
}
