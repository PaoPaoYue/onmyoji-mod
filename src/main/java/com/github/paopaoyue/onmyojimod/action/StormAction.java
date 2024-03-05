package com.github.paopaoyue.onmyojimod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class StormAction extends AbstractGameAction {
    private DamageInfo info;

    public StormAction(DamageInfo info) {
        this.info = info;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = AttackEffect.NONE;
    }

    public void update() {
        float speedTime = 0.0F;

        this.addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(this.info.base, true, true), info.type, AttackEffect.NONE));

        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDeadOrEscaped() && !m.halfDead) {
                this.addToTop(new VFXAction(new LightningEffect(m.drawX, m.drawY), speedTime));
            }
        }

        this.addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));

        this.isDone = true;
    }
}