package com.github.paopaoyue.onmyojimod.action;

import com.github.paopaoyue.onmyojimod.object.spirit.Dream;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import static com.megacrit.cardcrawl.core.Settings.ACTION_DUR_FAST;

public class ScaryDreamAction extends AbstractGameAction {
    private static final float DURATION = ACTION_DUR_FAST;
    private DamageInfo info;
    private int spiritPerDamage;

    public ScaryDreamAction(AbstractCreature target, AbstractCreature source, DamageInfo info, int spiritPerDamage) {
        this.setValues(target, this.info = info);
        this.source = source;
        this.actionType = ActionType.DAMAGE;
        this.duration = DURATION;
        this.spiritPerDamage = spiritPerDamage;
    }

    @Override
    public void update() {
        if (this.shouldCancelAction()) {
            this.isDone = true;
            return;
        }
        if (this.duration == DURATION && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.BLUNT_LIGHT, false));
            this.target.damage(this.info);
            if (target.lastDamageTaken / spiritPerDamage > 0) {
                this.addToTop(new RandomAttachSpiritAction(new Dream(), target.lastDamageTaken / spiritPerDamage, x -> true, false, true));
            }
        }
        this.tickDuration();
    }
}
