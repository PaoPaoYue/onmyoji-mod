package com.github.paopaoyue.onmyojimod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BuddhistAction extends AbstractGameAction {
    private static final float DURATION = 0.0f;

    private int maxHpIncrAmount;

    public BuddhistAction(int healAmount, int maxHpIncrAmount) {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player, healAmount);
        this.actionType = ActionType.HEAL;
        this.duration = DURATION;
        this.maxHpIncrAmount = maxHpIncrAmount;
    }

    @Override
    public void update() {
        if (this.duration == DURATION) {
            this.addToTop(new HealAction(target, target, this.amount));
            int fullHpNum = target.currentHealth == target.maxHealth ? 1 : 0;
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (mo.isDead) continue;
                fullHpNum += mo.currentHealth == mo.maxHealth ? 1 : 0;
                this.addToTop(new HealAction(mo, target, this.amount));
            }
            AbstractDungeon.player.increaseMaxHp(fullHpNum * maxHpIncrAmount, true);
        }
        this.tickDuration();
    }
}
