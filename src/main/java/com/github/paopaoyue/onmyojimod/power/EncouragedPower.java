package com.github.paopaoyue.onmyojimod.power;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EncouragedPower extends AbstractPower {
    public static final String POWER_ID = "Onmyoji:Encouraged";
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = ImageMaster.loadImage("image/icon/encouraged.png");

    public EncouragedPower(AbstractCreature owner, int amount) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = IMG;
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.priority = 10;
        this.canGoNegative = false;
        updateDescription();
    }


    public void updateDescription() {
        this.description = strings.DESCRIPTIONS[0];
    }

    public float atDamageFinalGive(float damage, DamageInfo.DamageType damageType, AbstractCard card) {
        if (this.owner != null && damageType == DamageInfo.DamageType.NORMAL && card.type == AbstractCard.CardType.ATTACK) {
            damage *= 2;
        }
        return damage;
    }


    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (this.owner != null && info.type == DamageInfo.DamageType.NORMAL && damageAmount > 0) {
            this.flash();
            this.addToBot(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
    }
}
