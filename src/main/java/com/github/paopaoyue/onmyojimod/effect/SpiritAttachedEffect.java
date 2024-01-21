package com.github.paopaoyue.onmyojimod.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.CardPoofEffect;

public class SpiritAttachedEffect extends AbstractGameEffect {
    private static final float EFFECT_DUR = 1.5F;
    private static final float PADDING;

    static {
        PADDING = 30.0F * Settings.scale;
    }

    private AbstractCard card;

    public SpiritAttachedEffect(AbstractCard srcCard) {
        this.card = srcCard.makeStatEquivalentCopy();
        this.duration = EFFECT_DUR;
        this.card.target_x = MathUtils.random((float) Settings.WIDTH * 0.1F, (float) Settings.WIDTH * 0.9F);
        this.card.target_y = MathUtils.random((float) Settings.HEIGHT * 0.8F, (float) Settings.HEIGHT * 0.2F);
        this.identifySpawnLocation(this.card.target_x, this.card.target_y);

        AbstractDungeon.effectsQueue.add(new CardPoofEffect(this.card.target_x, this.card.target_y));
        this.card.drawScale = 0.01F;
        this.card.targetDrawScale = 1.0F;


        CardCrawlGame.sound.play("CARD_OBTAIN");

    }

    private void identifySpawnLocation(float x, float y) {
        int effectCount = 0;
        for (AbstractGameEffect e : AbstractDungeon.effectList) {
            if (e instanceof SpiritAttachedEffect) {
                ++effectCount;
            }
        }

        this.card.current_x = x;
        this.card.current_y = y;
        this.card.target_y = (float) Settings.HEIGHT * 0.5F;
        switch (effectCount) {
            case 0:
                this.card.target_x = (float) Settings.WIDTH * 0.5F;
                break;
            case 1:
                this.card.target_x = (float) Settings.WIDTH * 0.5F - PADDING - AbstractCard.IMG_WIDTH;
                break;
            case 2:
                this.card.target_x = (float) Settings.WIDTH * 0.5F + PADDING + AbstractCard.IMG_WIDTH;
                break;
            case 3:
                this.card.target_x = (float) Settings.WIDTH * 0.5F - (PADDING + AbstractCard.IMG_WIDTH) * 2.0F;
                break;
            case 4:
                this.card.target_x = (float) Settings.WIDTH * 0.5F + (PADDING + AbstractCard.IMG_WIDTH) * 2.0F;
                break;
            default:
                this.card.target_x = MathUtils.random((float) Settings.WIDTH * 0.1F, (float) Settings.WIDTH * 0.9F);
                this.card.target_y = MathUtils.random((float) Settings.HEIGHT * 0.2F, (float) Settings.HEIGHT * 0.8F);
        }

    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.card.update();
        if (this.duration < 0.0F) {
            this.isDone = true;
            this.card.shrink();
            AbstractDungeon.getCurrRoom().souls.onToDeck(this.card, false, true);
        }

    }

    public void render(SpriteBatch sb) {
        if (!this.isDone) {
            this.card.render(sb);
        }

    }

    public void dispose() {
    }
}
