package com.github.paopaoyue.onmyojimod.action;

import com.github.paopaoyue.onmyojimod.power.PenetratedPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class CorruptedPunchAction extends AbstractGameAction {
    private static final float DURATION = 0.0F;
    private DamageInfo info;
    private int spiritPerDamage;

    public CorruptedPunchAction(AbstractCreature target, int amount) {
        this.setValues(target, AbstractDungeon.player, amount);
        this.actionType = ActionType.DEBUFF;
        this.duration = DURATION;
    }

    @Override
    public void update() {
        if (this.shouldCancelAction()) {
            this.isDone = true;
            return;
        }
        if (this.duration == DURATION && this.target != null) {
            int targetAmount = amount;
            for (AbstractPower power : source.powers) {
                if (power instanceof PenetratedPower) {
                    targetAmount += power.amount;
                }
            }
            this.addToTop(new ApplyPowerAction(source, source, new PenetratedPower(source, this.amount)));
            this.addToTop(new ApplyPowerAction(target, source, new PenetratedPower(target, targetAmount)));

        }
        this.tickDuration();
    }
}
