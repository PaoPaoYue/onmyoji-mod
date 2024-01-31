package com.github.paopaoyue.onmyojimod.object.spirit;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.github.paopaoyue.onmyojimod.OnmyojiMod;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class Eye extends AbstractSpirit {

    private static final Keyword spiritString = OnmyojiMod.MOD_DICTIONARY.get("Onmyoji:Eye");
    private static final String texturePath = "image/512/spirit_eye.png";
    private static final Texture texture = ImageMaster.loadImage(texturePath);


    public Eye() {
        this.id = spiritString.ID;
        this.name = spiritString.NAMES[0];
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public void onDraw() {
        AbstractDungeon.actionManager.addToBottom(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, 1, false));
    }

    @Override
    public void onUse() {
    }

    @Override
    public AbstractSpirit makeCopy() {
        return new Eye();
    }

}
