package com.github.paopaoyue.onmyojimod.action;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.RipAndTearEffect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GhostHandAction extends AbstractGameAction {
    private static final float DURATION = Settings.ACTION_DUR_MED;
    private static Method refreshLocationMethod;

    static {
        try {
            refreshLocationMethod = AbstractCreature.class.getDeclaredMethod("refreshHitboxLocation");
            refreshLocationMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private DamageInfo info;
    private AbstractMonster swapTarget;
    private float a_draw_X;
    private float b_draw_X;
    private float a_draw_Y;
    private float b_draw_Y;
    private float a_draw_tX;
    private float b_draw_tX;
    private float a_draw_tY;
    private float b_draw_tY;

    public GhostHandAction(AbstractCreature target, AbstractCreature source, DamageInfo info) {
        this.setValues(target, this.info = info);
        this.source = source;
        this.actionType = ActionType.DAMAGE;
        this.duration = DURATION;
    }

    @Override
    public void update() {
        if (this.target == null) {
            this.isDone = true;
            return;
        }
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
            this.isDone = true;
            return;
        }
        if (this.duration == DURATION && this.target != null) {
            AbstractDungeon.effectsQueue.add(new RipAndTearEffect(this.target.hb.cX, this.target.hb.cY, Color.RED, Color.GOLD));
            this.target.damage(this.info);

            float minX = Float.POSITIVE_INFINITY;
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (m.isDeadOrEscaped()) {
                    continue;
                }
                if (m.hb.cX < minX) {
                    minX = m.hb.cX;
                    this.swapTarget = m;
                }
            }
            if (this.target != this.swapTarget && this.target != null && this.swapTarget != null) {
                a_draw_X = target.drawX;
                a_draw_Y = target.drawY;
                b_draw_X = swapTarget.drawX;
                b_draw_Y = swapTarget.drawY;
                a_draw_tX = swapTarget.hb.cX - target.animX - target.hb_x;
                a_draw_tY = swapTarget.hb.cY - target.hb_h / 2.0F - target.hb_y;
                b_draw_tX = target.hb.cX - swapTarget.animX - swapTarget.hb_x;
                b_draw_tY = target.hb.cY - swapTarget.hb_h / 2.0F - swapTarget.hb_y;


            }
        }
        if (this.duration <= 0.4F && this.target != this.swapTarget && this.target != null && this.swapTarget != null) {
            swapDraw((AbstractMonster) target, swapTarget, duration, 0.4F);
        }
        this.tickDuration();
    }

    private void swapDraw(AbstractMonster a, AbstractMonster b, float duration, float maxDuration) {

        a.drawX = Interpolation.pow3.apply(a_draw_X, a_draw_tX, 1 - duration / maxDuration);
        b.drawX = Interpolation.pow3.apply(b_draw_X, b_draw_tX, 1 - duration / maxDuration);

        try {
            refreshLocationMethod.invoke(a);
            refreshLocationMethod.invoke(b);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        a.refreshIntentHbLocation();
        b.refreshIntentHbLocation();

    }

}
