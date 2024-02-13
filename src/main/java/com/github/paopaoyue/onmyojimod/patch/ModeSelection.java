package com.github.paopaoyue.onmyojimod.patch;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.github.paopaoyue.onmyojimod.OnmyojiMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;

public class ModeSelection {
    private static final String SELECT_MODIFIER_PROMPT;
    private static final int MODE_NUM = 2;
    private static final String[] MODE_NAME = new String[MODE_NUM];
    private static final String[] MODE_DESC = new String[MODE_NUM];
    public static int modifierIndex = -1;
    private static Hitbox arrowLeft;
    private static Hitbox arrowRight;

    static {
        SELECT_MODIFIER_PROMPT = CardCrawlGame.languagePack.getUIString("Onmyoji:ModeSelectionPrompt").TEXT[0];
        final UIStrings normalModeStrings = CardCrawlGame.languagePack.getUIString("Onmyoji:NormalMode");
        final UIStrings hardcoreModeStrings = CardCrawlGame.languagePack.getUIString("Onmyoji:HardcoreMode");
        MODE_NAME[0] = normalModeStrings.TEXT[0];
        MODE_NAME[1] = hardcoreModeStrings.TEXT[0];
        MODE_DESC[0] = normalModeStrings.TEXT[1];
        MODE_DESC[1] = hardcoreModeStrings.TEXT[1];
    }

    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "initialize"
    )
    public static class InitializeButtonsPatch {
        public static SpireReturn<Void> Prefix(CharacterSelectScreen _inst) {
            ModeSelection.modifierIndex = 0;
            ModeSelection.arrowLeft = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
            ModeSelection.arrowRight = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "renderAscensionMode"
    )
    public static class RenderButtonPatch {
        public static SpireReturn<Void> Prefix(CharacterSelectScreen _inst, SpriteBatch sb) {
            if (CardCrawlGame.chosenCharacter == PlayerClassEnum.ONMYOJI && (Boolean) ReflectionHacks.getPrivate(_inst, CharacterSelectScreen.class, "anySelected")) {
                FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, ModeSelection.SELECT_MODIFIER_PROMPT, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT - 50.0F, Color.WHITE, 1.25F);
                FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, ModeSelection.MODE_NAME[ModeSelection.modifierIndex], (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT - 100.0F, Settings.RED_TEXT_COLOR);
                FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, ModeSelection.MODE_DESC[ModeSelection.modifierIndex], (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT - 150.0F, Settings.BLUE_TEXT_COLOR);
                sb.setColor(Color.SCARLET);
                sb.draw(ImageMaster.CF_LEFT_ARROW, ModeSelection.arrowLeft.cX - 24.0F, ModeSelection.arrowLeft.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
                sb.draw(ImageMaster.CF_RIGHT_ARROW, ModeSelection.arrowRight.cX - 24.0F, ModeSelection.arrowRight.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
                ModeSelection.arrowLeft.render(sb);
                ModeSelection.arrowRight.render(sb);
                sb.setColor(Color.WHITE);
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "updateAscensionToggle"
    )
    public static class UpdateButtonPatch {
        public static SpireReturn<Void> Prefix(CharacterSelectScreen _inst) {
            if (CardCrawlGame.chosenCharacter == PlayerClassEnum.ONMYOJI) {
                ModeSelection.arrowLeft.update();
                ModeSelection.arrowRight.update();
                if (ModeSelection.arrowLeft.clicked) {
                    ModeSelection.arrowLeft.clicked = false;
                    ModeSelection.modifierIndex = (ModeSelection.MODE_NAME.length + ModeSelection.modifierIndex - 1) % ModeSelection.MODE_NAME.length;
                    OnmyojiMod.mode = OnmyojiMod.Mode.values()[ModeSelection.modifierIndex];
                }

                if (ModeSelection.arrowRight.clicked) {
                    ModeSelection.arrowRight.clicked = false;
                    ModeSelection.modifierIndex = (ModeSelection.modifierIndex + 1) % ModeSelection.MODE_NAME.length;
                    OnmyojiMod.mode = OnmyojiMod.Mode.values()[ModeSelection.modifierIndex];
                }


                if (InputHelper.justClickedLeft) {
                    if (ModeSelection.arrowLeft.hovered) {
                        ModeSelection.arrowLeft.clickStarted = true;
                    }

                    if (ModeSelection.arrowRight.hovered) {
                        ModeSelection.arrowRight.clickStarted = true;
                    }
                }
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "update"
    )
    public static class NewButtonPatch {
        public static SpireReturn<Void> Prefix(CharacterSelectScreen _inst) {
            if (CardCrawlGame.chosenCharacter == PlayerClassEnum.ONMYOJI && ModeSelection.arrowLeft != null && ModeSelection.arrowRight != null) {
                ModeSelection.arrowLeft.move((float) Settings.WIDTH / 2.0F - 220.0F * Settings.scale, (float) Settings.HEIGHT - 100.0F);
                ModeSelection.arrowRight.move((float) Settings.WIDTH / 2.0F + 220.0F * Settings.scale, (float) Settings.HEIGHT - 100.0F);
            }

            return SpireReturn.Continue();
        }
    }
}