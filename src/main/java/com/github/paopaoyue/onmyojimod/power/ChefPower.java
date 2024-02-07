package com.github.paopaoyue.onmyojimod.power;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ChefPower extends AbstractPower {
    public static final String POWER_ID = "Onmyoji:Chef";
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = ImageMaster.loadImage("image/icon/chef.png");

    private int encouragedCount = 0;

    public ChefPower(AbstractCreature owner, int amount) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = IMG;
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.encouragedCount = this.amount;
        this.canGoNegative = false;
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        this.encouragedCount = this.amount;
        updateDescription();
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card.rarity == AbstractCard.CardRarity.SPECIAL && this.encouragedCount > 0) {
            this.flash();
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new EncouragedPower(this.owner, 1)));
            this.encouragedCount--;
            updateDescription();
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        this.encouragedCount += stackAmount;
    }

    public void updateDescription() {
        this.description = strings.DESCRIPTIONS[0] + strings.DESCRIPTIONS[1] + this.encouragedCount + strings.DESCRIPTIONS[2];
    }

}
