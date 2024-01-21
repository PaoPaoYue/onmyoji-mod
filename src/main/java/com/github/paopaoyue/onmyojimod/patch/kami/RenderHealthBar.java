package com.github.paopaoyue.onmyojimod.patch.kami;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.github.paopaoyue.onmyojimod.object.kami.KamiManager;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import javassist.CtBehavior;

import java.lang.reflect.Field;
import java.util.ArrayList;

@SpirePatch(
        clz = AbstractCreature.class,
        method = "renderHealth"
)
public class RenderHealthBar {
    private static float HEALTH_BAR_HEIGHT = -1.0F;
    private static float HEALTH_BAR_OFFSET_Y = -1.0F;

    private static Texture FACTION_RED_ICON = ImageMaster.loadImage("image/icon/faction_red.png");
    private static Texture FACTION_BLUE_ICON = ImageMaster.loadImage("image/icon/faction_blue.png");
    private static Texture FACTION_GREEN_ICON = ImageMaster.loadImage("image/icon/faction_green.png");
    private static Texture FACTION_PURPLE_ICON = ImageMaster.loadImage("image/icon/faction_purple.png");

    public RenderHealthBar() {
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

        if (!Gdx.input.isKeyPressed(36) && KamiManagerField.kamiManager.get(__instance).getHp() > 0 && __instance.hbAlpha > 0.0F) {
            renderKamiHPAndValue(__instance, sb, x, y);
        }

    }

    private static <O, T> T getPrivate(Class<O> cls, Object obj, String varName, Class<T> type) {
        try {
            Field f = cls.getDeclaredField(varName);
            f.setAccessible(true);
            return type.cast(f.get(obj));
        } catch (IllegalAccessException | NoSuchFieldException var5) {
            var5.printStackTrace();
            return null;
        }
    }

    private static <O, T> T getPrivate(Class<O> obj, String varName, Class<T> type) {
        return getPrivate(obj, (Object) null, varName, type);
    }

    private static void renderKamiHPAndValue(AbstractCreature creature, SpriteBatch sb, float x, float y) {
        sb.setColor(Color.WHITE);
        Texture icon = null;
        KamiManager kamiManager = KamiManagerField.kamiManager.get(creature);
        switch (kamiManager.getCurrentFaction()) {
            case RED:
                icon = FACTION_RED_ICON;
                break;
            case BLUE:
                icon = FACTION_BLUE_ICON;
                break;
            case GREEN:
                icon = FACTION_GREEN_ICON;
                break;
            case PURPLE:
                icon = FACTION_PURPLE_ICON;
                break;
        }
        sb.draw(icon, x + (Float) getPrivate(AbstractCreature.class, "BLOCK_ICON_X", Float.class) - 16.0F + creature.hb.width, y + (Float) getPrivate(AbstractCreature.class, "BLOCK_ICON_Y", Float.class) - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        FontHelper.renderFontCentered(sb, FontHelper.blockInfoFont, String.valueOf(kamiManager.getHp()), x + (Float) getPrivate(AbstractCreature.class, "BLOCK_ICON_X", Float.class) + 16.0F + creature.hb.width, y - 16.0F * Settings.scale, Settings.CREAM_COLOR, 1.0F);
    }

    private static class Locator extends SpireInsertLocator {
        private Locator() {
        }

        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCreature.class, "currentBlock");
            ArrayList<Matcher> matchers = new ArrayList();
            matchers.add(finalMatcher);
            return LineFinder.findInOrder(ctBehavior, matchers, finalMatcher);
        }
    }
}

