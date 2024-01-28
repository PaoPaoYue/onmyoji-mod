package com.github.paopaoyue.onmyojimod.action;

import com.github.paopaoyue.onmyojimod.character.Sanme;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class HealLossHPAction extends AbstractGameAction {
    public HealLossHPAction(final int amount) {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player, amount);
        this.startDuration = this.duration;
        if (Settings.FAST_MODE) {
            final float action_DUR_FAST = Settings.ACTION_DUR_FAST;
            this.startDuration = action_DUR_FAST;
            this.duration = action_DUR_FAST;
        }
        this.actionType = ActionType.HEAL;
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration && this.target instanceof Sanme) {
            ((Sanme) this.target).healLossHP(this.amount, true);
        }
        this.tickDuration();
    }
}