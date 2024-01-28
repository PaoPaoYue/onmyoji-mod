package com.github.paopaoyue.onmyojimod.object.spirit;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.github.paopaoyue.onmyojimod.OnmyojiMod;
import com.github.paopaoyue.onmyojimod.action.HealLossHPAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class Knot extends AbstractSpirit {

    private static final Keyword spiritString = OnmyojiMod.MOD_DICTIONARY.get("Onmyoji:Knot");
    private static final String texturePath = "image/512/spirit_knot.png";
    private static final Texture texture = ImageMaster.loadImage(texturePath);


    public Knot() {
        this.id = spiritString.ID;
        this.name = spiritString.NAMES[0];
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public void onDraw() {
        AbstractDungeon.actionManager.addToBottom(new HealLossHPAction(2));
    }

    @Override
    public void onUse() {
    }
}
