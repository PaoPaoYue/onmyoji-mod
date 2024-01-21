package com.github.paopaoyue.onmyojimod.object.spirit;

import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.github.paopaoyue.onmyojimod.OnmyojiMod;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class Enhancement extends AbstractSpirit {

    private static final Keyword spiritString = OnmyojiMod.MOD_DICTIONARY.get("Onmyoji:Enhancement");
    private static final String texturePath = "image/512/spirit_enhancement.png";

    static {
        texture = ImageMaster.loadImage(texturePath);
    }

    public Enhancement() {
        this.id = spiritString.ID;
        this.name = spiritString.NAMES[0];
    }

    @Override
    public void onDraw() {

    }

    @Override
    public void onUse() {
    }
}
