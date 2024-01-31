package com.github.paopaoyue.onmyojimod.object.spirit;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.github.paopaoyue.onmyojimod.OnmyojiMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class Curse extends AbstractSpirit {

    private static final Keyword spiritString = OnmyojiMod.MOD_DICTIONARY.get("Onmyoji:Curse");
    private static final String texturePath = "image/512/spirit_curse.png";
    private static final Texture texture = ImageMaster.loadImage(texturePath);


    public Curse() {
        this.id = spiritString.ID;
        this.name = spiritString.NAMES[0];
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public void onUse() {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, 3, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public void onDraw() {
    }

    @Override
    public AbstractSpirit makeCopy() {
        return new Curse();
    }
}
