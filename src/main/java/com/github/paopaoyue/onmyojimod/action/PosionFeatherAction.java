package com.github.paopaoyue.onmyojimod.action;

import com.github.paopaoyue.onmyojimod.power.PenetratedPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import static com.megacrit.cardcrawl.core.Settings.ACTION_DUR_FAST;

public class PosionFeatherAction extends AbstractGameAction {
    private static final float DURATION = ACTION_DUR_FAST;
    private DamageInfo info;

    public PosionFeatherAction(AbstractCreature target, AbstractCreature source, DamageInfo info) {
        this.setValues(target, this.info = info);
        this.source = source;
        this.actionType = ActionType.DAMAGE;
        this.duration = DURATION;
    }

    @Override
    public void update() {
        if (this.shouldCancelAction()) {
            this.isDone = true;
            return;
        }
        if (this.duration == DURATION && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.POISON, false));
            this.target.damage(this.info);
            for (AbstractPower power : target.powers) {
                if (power instanceof PenetratedPower) {
                    AbstractDungeon.actionManager.addToTop(new HealLossHPAction(target.lastDamageTaken));
                }
            }
        }
        this.tickDuration();
    }
}
