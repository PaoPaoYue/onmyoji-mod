package com.github.paopaoyue.onmyojimod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class SnowSlashAction extends AbstractGameAction {
    private static final float DURATION = 0.0F;
    private DamageInfo info;

    public SnowSlashAction(AbstractCreature source, DamageInfo info) {
        this.info = info;
        this.source = source;
        this.actionType = ActionType.DAMAGE;
        this.duration = DURATION;
    }

    @Override
    public void update() {
        if (this.duration == DURATION) {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (mo.isDead) continue;
                for (AbstractPower power : mo.powers) {
                    if (power instanceof WeakPower) {
                        AbstractDungeon.actionManager.addToTop(new DamageAction(mo, info, AttackEffect.SLASH_HEAVY));
                    }
                }
            }
        }
        this.tickDuration();
    }
}
