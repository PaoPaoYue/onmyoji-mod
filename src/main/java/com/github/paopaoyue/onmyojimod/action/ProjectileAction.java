package com.github.paopaoyue.onmyojimod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.PressurePointEffect;

public class ProjectileAction extends AbstractGameAction {

    public ProjectileAction(int amount) {
        this.setValues(null, AbstractDungeon.player, amount);
        this.duration = 0;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;

    }

    @Override
    public void update() {
        if (this.duration == 0) {
            float minX = Float.POSITIVE_INFINITY;
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (m.isDead) {
                    continue;
                }
                if (m.hb.cX < minX) {
                    minX = m.hb.cX;
                    this.target = m;
                }
            }
            if (target != null) {
                this.addToBot(new VFXAction(new PressurePointEffect(target.hb.cX, target.hb.cY)));
                this.addToBot(new DamageAction(this.target, new DamageInfo(this.source, this.amount, DamageInfo.DamageType.NORMAL), AttackEffect.FIRE));
            }
        }
        this.tickDuration();
    }
}
