package com.github.paopaoyue.onmyojimod.power;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.HashSet;

public class IceShieldPower extends AbstractPower {
    public static final String POWER_ID = "Onmyoji:Ice Shield";
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = ImageMaster.loadImage("image/icon/ice_shield.png");
    private HashSet<AbstractCreature> enemySet;

    public IceShieldPower(AbstractCreature owner, int amount) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = IMG;
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.isTurnBased = true;
        this.canGoNegative = false;

        this.enemySet = new HashSet<>();

        updateDescription();
    }


    public void updateDescription() {
        this.description = strings.DESCRIPTIONS[0] + this.amount + strings.DESCRIPTIONS[1];
    }

    public void onAttacked(float damage, DamageInfo info, boolean hadBlock) {
        if (this.owner != null && info.type == DamageInfo.DamageType.NORMAL && hadBlock && !enemySet.contains(info.owner)) {
            enemySet.add(info.owner);
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(info.owner, this.owner, new WeakPower(info.owner, this.amount, false), this.amount, true));
        }
    }

    @Override
    public void atStartOfTurn() {
        this.enemySet.clear();
        this.addToBot(new ReducePowerAction(this.owner, this.owner, this.ID, this.amount));
    }

}
