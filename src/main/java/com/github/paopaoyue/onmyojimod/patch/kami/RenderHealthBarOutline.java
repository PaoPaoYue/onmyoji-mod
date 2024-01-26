package com.github.paopaoyue.onmyojimod.patch.kami;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.github.paopaoyue.onmyojimod.character.OnmyojiCharacter;
import com.github.paopaoyue.onmyojimod.object.kami.KamiManager;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import javassist.CtBehavior;

@SpirePatch(
        clz = AbstractCreature.class,
        method = "renderHealth"
)
public class RenderHealthBarOutline {
    private static float HEALTH_BAR_HEIGHT = -1.0F;
    private static float HEALTH_BAR_OFFSET_Y = -1.0F;

    public RenderHealthBarOutline() {
    }

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"x", "y"}
    )
    public static void Insert(AbstractCreature __instance, SpriteBatch sb, float x, float y) {
        if (HEALTH_BAR_HEIGHT == -1.0F) {
            HEALTH_BAR_HEIGHT = 20.0F * Settings.scale;
            HEALTH_BAR_OFFSET_Y = -28.0F * Settings.scale;
        }

        if (!Gdx.input.isKeyPressed(36) && (__instance instanceof OnmyojiCharacter) && __instance.hbAlpha > 0.0F) {
            KamiManager kamiManager = ((OnmyojiCharacter) __instance).getKamiManager();
            if (kamiManager.isHasKami()) {
                sb.setColor(Color.WHITE);
                sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
                sb.draw(ImageMaster.BLOCK_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
                sb.draw(ImageMaster.BLOCK_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, __instance.hb.width, HEALTH_BAR_HEIGHT);
                sb.draw(ImageMaster.BLOCK_BAR_R, x + __instance.hb.width, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
                sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            }
        }

    }

    private static void renderHealthBarOutline(AbstractCreature creature, SpriteBatch sb, float x, float y) {

    }

    private static class Locator extends SpireInsertLocator {
        private Locator() {
        }

        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCreature.class, "currentBlock");
            return LineFinder.findInOrder(ctBehavior, finalMatcher);
        }
    }
}
