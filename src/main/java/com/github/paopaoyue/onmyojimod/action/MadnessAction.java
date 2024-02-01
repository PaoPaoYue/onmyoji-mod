package com.github.paopaoyue.onmyojimod.action;

import com.github.paopaoyue.onmyojimod.power.UnyieldingPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class MadnessAction extends AbstractGameAction {
    private static final float DURATION = Settings.ACTION_DUR_FAST;
    private DamageInfo info;

    public MadnessAction(AbstractCreature target, AbstractCreature source, DamageInfo info) {
        this.setValues(target, this.info = info);
        this.source = source;
        this.actionType = ActionType.DAMAGE;
        this.duration = DURATION;
    }

    @Override
    public void update() {
        if (this.duration == DURATION && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SMASH));
            this.target.damage(this.info);
            if ((((AbstractMonster) this.target).isDying || this.target.currentHealth <= 0) && !this.target.halfDead) {
                this.addToTop(new ApplyPowerAction(source, source, new UnyieldingPower(source, 1)));
            }
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
        this.tickDuration();
    }
}
