package com.github.paopaoyue.onmyojimod.orb;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.github.paopaoyue.onmyojimod.effect.CountdownActivateEffect;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.BobEffect;

public abstract class AbstractCountdownOrb extends AbstractOrb {

    private static final Texture MASK_1 = ImageMaster.loadImage("image/vfx/orb/mask1.png");
    private static final Texture MASK_2 = ImageMaster.loadImage("image/vfx/orb/mask2.png");
    private static final Texture MASK_3 = ImageMaster.loadImage("image/vfx/orb/mask3.png");
    private static final Texture TIMER = ImageMaster.loadImage("image/vfx/orb/timer.png");

    private static float stableTimer = 5.0F;
    private static float actionTimer = 0.0f;
    private static float timerAngle = 0.0f;
    private float vfxAngle1 = 0.0F;
    private float vfxAngle2 = 0.0F;


    public AbstractCountdownOrb(int countdown) {
        this.c = Settings.CREAM_COLOR.cpy();
        this.shineColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        this.hb = new Hitbox(96.0F * Settings.scale, 96.0F * Settings.scale);
        this.img = null;
        this.bobEffect = new BobEffect(3.0F * Settings.scale, 3.0F);
        this.fontScale = 0.5F;
        this.showEvokeValue = false;
        this.channelAnimTimer = 0.5F;

        this.basePassiveAmount = countdown;
        this.baseEvokeAmount = countdown;
        this.passiveAmount = this.basePassiveAmount;
        this.evokeAmount = this.baseEvokeAmount;
    }

    public static void updateTimerAnimation() {
        stableTimer -= Gdx.graphics.getDeltaTime();
        if (stableTimer <= 0.0F) {
            stableTimer = 5.0F;
            actionTimer = 0.2F;
        }
        actionTimer -= Gdx.graphics.getDeltaTime();
        if (actionTimer <= 0.0F) {
            actionTimer = 0.0F;
            if (timerAngle > 0.0F && timerAngle <= 180.0F) {
                timerAngle = 180.0F;
            } else {
                timerAngle = 0.0F;
            }
        } else {
            if (timerAngle >= 0.0F && timerAngle < 180.0F) {
                timerAngle = Interpolation.pow2Out.apply(0.0F, 180.0F, 1 - actionTimer / 0.2F);
            } else {
                timerAngle = Interpolation.pow2Out.apply(180.0F, 360.0F, 1 - actionTimer / 0.2F);
            }
        }
    }

    public boolean isTimeout() {
        return this.passiveAmount == 0;
    }

    public int getCountdown() {
        return this.passiveAmount;
    }

    public void countdown() {
        this.passiveAmount = Math.max(this.passiveAmount - 1, 0);
        this.evokeAmount = passiveAmount;
        this.updateDescription();
    }

    public void onStartOfTurn() {
    }

    public void onEndOfTurn() {
    }

    public void applyFocus() {
    }

    public void triggerEvokeAnimation() {
        AbstractDungeon.effectsQueue.add(new CountdownActivateEffect(this.cX, this.cY));
    }

    public void updateAnimation() {
        super.updateAnimation();

        this.vfxAngle1 = MathUtils.clamp(this.vfxAngle1 + Gdx.graphics.getDeltaTime() * 5.0F, 0, 360);
        this.vfxAngle1 = this.vfxAngle1 == 360 ? 0F : this.vfxAngle1;
        this.vfxAngle2 = MathUtils.clamp(this.vfxAngle2 + Gdx.graphics.getDeltaTime() * 10.0F, 0, 360);
        this.vfxAngle2 = this.vfxAngle2 == 360 ? 0F : this.vfxAngle2;

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.c);
        sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0, 0, 0, this.img.getWidth(), this.img.getHeight(), false, false);
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        sb.draw(MASK_1, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, this.vfxAngle1, 0, 0, MASK_1.getWidth(), MASK_1.getHeight(), false, false);
        sb.draw(MASK_2, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, -this.vfxAngle2, 0, 0, MASK_2.getWidth(), MASK_2.getHeight(), false, false);
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        sb.draw(TIMER, this.cX + NUM_X_OFFSET - 20.0F, this.cY + NUM_Y_OFFSET - 18.0F + this.bobEffect.y / 2.0F, 18.0F, 18.0F, 36.0F, 36.0F, this.scale, this.scale, this.timerAngle, 0, 0, TIMER.getWidth(), TIMER.getHeight(), false, false);
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        this.renderText(sb);
        this.hb.render(sb);
    }

}
