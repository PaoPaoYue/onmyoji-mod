package com.github.paopaoyue.onmyojimod.power;

import com.badlogic.gdx.graphics.Texture;
import com.github.paopaoyue.onmyojimod.orb.AbstractCountdownOrb;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class WindOfDragonPower extends AbstractPower {
    public static final String POWER_ID = "Onmyoji:Wind of Dragon";
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = ImageMaster.loadImage("image/icon/wind_of_dragon.png");

    public WindOfDragonPower(AbstractCreature owner) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = IMG;
        this.type = PowerType.BUFF;
        this.amount = -1;
        this.canGoNegative = false;
        updateDescription();
    }

    @Override
    public void onChannel(AbstractOrb orb) {
        if (orb instanceof AbstractCountdownOrb) {
            this.flash();
            orb.onEvoke();
        }
    }

    public void updateDescription() {
        this.description = strings.DESCRIPTIONS[0];
    }

}
