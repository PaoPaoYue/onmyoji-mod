package com.github.paopaoyue.onmyojimod.action;

import com.github.paopaoyue.onmyojimod.patch.spirit.SpiritField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PuppetAction extends AbstractGameAction {
    private static final float DURATION = 0.0F;

    public PuppetAction(int amount) {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player, amount);
        this.duration = DURATION;
    }

    @Override
    public void update() {
        if (this.duration == DURATION && this.target != null) {
            if (!DrawCardAction.drawnCards.isEmpty() && SpiritField.spirit.get(DrawCardAction.drawnCards.get(DrawCardAction.drawnCards.size() - 1)) != null) {
                this.addToTop(new GainEnergyAction(amount));
            }
        }
        this.tickDuration();
    }
}
