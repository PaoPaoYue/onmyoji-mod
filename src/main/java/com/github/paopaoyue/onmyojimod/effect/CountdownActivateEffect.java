package com.github.paopaoyue.onmyojimod.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class CountdownActivateEffect extends AbstractGameEffect {
    private static final Texture[] images = new Texture[]{
            ImageMaster.loadImage("image/vfx/orb/1.png"),
            ImageMaster.loadImage("image/vfx/orb/2.png"),
            ImageMaster.loadImage("image/vfx/orb/3.png"),
            ImageMaster.loadImage("image/vfx/orb/4.png"),
            ImageMaster.loadImage("image/vfx/orb/5.png"),
            ImageMaster.loadImage("image/vfx/orb/6.png"),
            ImageMaster.loadImage("image/vfx/orb/7.png"),
            ImageMaster.loadImage("image/vfx/orb/8.png"),
            ImageMaster.loadImage("image/vfx/orb/9.png"),
            ImageMaster.loadImage("image/vfx/orb/10.png"),
            ImageMaster.loadImage("image/vfx/orb/11.png"),
    };
    private int index = 0;
    private float x;
    private float y;

    public CountdownActivateEffect(float x, float y) {
        this.renderBehind = false;
        this.color = Settings.LIGHT_YELLOW_COLOR.cpy();
        this.x = x - 48.0F;
        this.y = y - 48.0F;
        this.scale = 2.0F * Settings.scale;
        this.duration = 0.03F;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            ++this.index;
            if (this.index >= images.length) {
                this.isDone = true;
                return;
            }
            this.duration = 0.03F;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.setBlendFunction(770, 1);
        Texture image = images[this.index];
        sb.draw(image, this.x, this.y, 96.0F, 96.0F, 0, 0, image.getWidth(), image.getHeight(), false, false);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
