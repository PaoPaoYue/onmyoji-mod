package com.github.paopaoyue.onmyojimod.power;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PenetratedPower extends AbstractPower {
    public static final String POWER_ID = "Onmyoji:Penetrated";
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = ImageMaster.loadImage("image/icon/penetrated.png");

    public PenetratedPower(AbstractCreature owner, int amount) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = IMG;
        this.type = PowerType.DEBUFF;
        this.amount = amount;
        this.priority = 2;
        this.isTurnBased = true;
        this.canGoNegative = false;

        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power instanceof FragileSoundPower)
                this.amount = ((FragileSoundPower) power).onApplyPenetratedPower(this.amount);
        }

        updateDescription();
    }


    public void updateDescription() {
        this.description = strings.DESCRIPTIONS[0] + this.amount + strings.DESCRIPTIONS[1];
    }

    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        if (this.owner != null && damageType == DamageInfo.DamageType.NORMAL) {
            damage += this.amount;
        }
        return damage;
    }

    @Override
    public void atStartOfTurn() {
        this.addToBot(new ReducePowerAction(this.owner, this.owner, this.ID, this.amount));
    }

}
