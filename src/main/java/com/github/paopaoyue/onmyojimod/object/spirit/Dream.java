package com.github.paopaoyue.onmyojimod.object.spirit;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.github.paopaoyue.onmyojimod.OnmyojiMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;

public class Dream extends AbstractSpirit {

    private static final Keyword spiritString = OnmyojiMod.MOD_DICTIONARY.get("Onmyoji:Dream");
    private static final String texturePath = "image/512/spirit_dream.png";
    private static final Texture texture = ImageMaster.loadImage(texturePath);


    public Dream() {
        this.id = spiritString.ID;
        this.name = spiritString.NAMES[0];
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public void onDraw() {
        AbstractMonster mo = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster) null, true, AbstractDungeon.cardRandomRng);
        CardCrawlGame.sound.play("ORB_DARK_EVOKE", 0.1f);
        AbstractDungeon.effectsQueue.add(new DarkOrbActivateEffect(mo.hb.cX, mo.hb.cY));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(mo, new DamageInfo(AbstractDungeon.player, 6, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void onUse() {
    }

    @Override
    public AbstractSpirit makeCopy() {
        return new Dream();
    }
}
