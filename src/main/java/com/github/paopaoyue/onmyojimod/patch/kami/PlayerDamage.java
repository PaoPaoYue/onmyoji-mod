package com.github.paopaoyue.onmyojimod.patch.kami;

import com.evacipated.cardcrawl.mod.stslib.vfx.combat.TempDamageNumberEffect;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.github.paopaoyue.onmyojimod.character.OnmyojiCharacter;
import com.github.paopaoyue.onmyojimod.object.kami.KamiManager;
import com.github.paopaoyue.onmyojimod.utility.Inject;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.vfx.combat.DamageImpactLineEffect;
import com.megacrit.cardcrawl.vfx.combat.StrikeEffect;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "damage"
)
public class PlayerDamage {

    public PlayerDamage() {
    }

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"damageAmount", "hadBlock"}
    )
    public static void Insert(AbstractCreature __instance, DamageInfo info, int damageAmount, boolean hadBlock) {
        if (damageAmount > 0 && AbstractDungeon.player instanceof OnmyojiCharacter) {

            KamiManager kamiManager = ((OnmyojiCharacter) AbstractDungeon.player).getKamiManager();
            int tempHp = kamiManager.getHp();
            if (tempHp > 0) {

                for (int i = 0; i < 18; ++i) {
                    AbstractDungeon.effectsQueue.add(new DamageImpactLineEffect(__instance.hb.cX, __instance.hb.cY));
                }

                CardCrawlGame.screenShake.shake(ShakeIntensity.MED, ShakeDur.SHORT, false);
                if (tempHp > damageAmount) {
                    AbstractDungeon.effectsQueue.add(new TempDamageNumberEffect(__instance, __instance.hb.cX, __instance.hb.cY, damageAmount));
                    kamiManager.setHp(tempHp - damageAmount);
                    damageAmount = 0;
                } else {
                    AbstractDungeon.effectsQueue.add(new TempDamageNumberEffect(__instance, __instance.hb.cX, __instance.hb.cY, tempHp));
                    kamiManager.setHp(0);
                    kamiManager.onDead();
                    damageAmount -= tempHp;
                }

            }
        }
    }

    @SpireInsertPatch(
            locator = StrikeEffectLocator.class
    )
    public static SpireReturn<Void> Insert(AbstractCreature __instance, DamageInfo info) {
        if (AbstractDungeon.player instanceof OnmyojiCharacter) {
            KamiManager kamiManager = ((OnmyojiCharacter) AbstractDungeon.player).getKamiManager();
            return kamiManager.getHp() > 0 ? SpireReturn.Return((Void) null) : SpireReturn.Continue();
        }
        return SpireReturn.Continue();
    }

    private static class Locator extends SpireInsertLocator {
        private Locator() {
        }

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "decrementBlock");
            return Inject.insertAfter(LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher));
        }
    }

    private static class StrikeEffectLocator extends SpireInsertLocator {
        private StrikeEffectLocator() {
        }

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.NewExprMatcher(StrikeEffect.class);
            int[] all = LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
            return new int[]{all[all.length - 1]};
        }
    }
}
