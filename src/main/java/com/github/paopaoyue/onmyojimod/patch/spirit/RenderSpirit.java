package com.github.paopaoyue.onmyojimod.patch.spirit;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.github.paopaoyue.onmyojimod.object.spirit.AbstractSpirit;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

@SpirePatch(
        clz = AbstractCard.class,
        method = "renderEnergy"
)
public class RenderSpirit {

    public RenderSpirit() {
    }

    @SpireInsertPatch(
            rloc = 2, localvars = {"sb"}
    )
    public static void Insert(AbstractCard __instance, SpriteBatch sb) {
        AbstractSpirit spirit = (AbstractSpirit) SpiritField.spirit.get(__instance);
        if (spirit != null) {
            Texture img = spirit.getTexture();
            sb.setColor(Color.WHITE);
            sb.draw(img, __instance.current_x - 256.0F, __instance.current_y - 256.0F, 256.0F, 256.0F, 512.0F, 512.0F, __instance.drawScale * Settings.scale, __instance.drawScale * Settings.scale, __instance.angle, 0, 0, 512, 512, false, false);
        }
    }
}
