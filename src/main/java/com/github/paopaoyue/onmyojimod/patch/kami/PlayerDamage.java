package com.github.paopaoyue.onmyojimod.patch.kami;

import basemod.Pair;
import com.evacipated.cardcrawl.mod.stslib.vfx.combat.TempDamageNumberEffect;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.github.paopaoyue.onmyojimod.card.Taiji;
import com.github.paopaoyue.onmyojimod.character.Sanme;
import com.github.paopaoyue.onmyojimod.object.kami.KamiManager;
import com.github.paopaoyue.onmyojimod.power.BreakthroughPower;
import com.github.paopaoyue.onmyojimod.power.IceShieldPower;
import com.github.paopaoyue.onmyojimod.power.UnyieldingPower;
import com.github.paopaoyue.onmyojimod.utility.Inject;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.powers.AbstractPower;
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
            locator = BeforeDecrementBlockLocator.class,
            localvars = {"damageAmount"}
    )
    public static void Insert(AbstractCreature __instance, DamageInfo info, @ByRef int[] damageAmount) {
        Integer replacedDamage = Taiji.getDamageReplacement().get(new Pair<>(info.owner, info.base));
        if (replacedDamage != null) {
            damageAmount[0] = replacedDamage;
        }
        if (damageAmount[0] > 0 && __instance.hasPower("IntangiblePlayer")) {
            damageAmount[0] = 1;
        }
    }

    @SpireInsertPatch(
            locator = AfterDecrementLocator.class,
            localvars = {"damageAmount", "hadBlock"}
    )
    public static void Insert(AbstractCreature __instance, DamageInfo info, @ByRef int[] damageAmount, boolean hadBlock) {
        for (AbstractPower power : __instance.powers) {
            if (power instanceof IceShieldPower) {
                ((IceShieldPower) power).onAttacked(damageAmount[0], info, hadBlock);
            }
        }
        if (damageAmount[0] > 0 && info.type != DamageInfo.DamageType.HP_LOSS && AbstractDungeon.player instanceof Sanme) {

            KamiManager kamiManager = ((Sanme) AbstractDungeon.player).getKamiManager();
            int tempHp = kamiManager.getHp();
            if (tempHp > 0) {

                for (AbstractPower power : __instance.powers) {
                    if (power instanceof BreakthroughPower) {
                        AbstractDungeon.actionManager.addToTop(new ReducePowerAction(__instance, __instance, power.ID, 1));
                        return;
                    }
                    if (power instanceof IceShieldPower) {
                        ((IceShieldPower) power).onAttacked(damageAmount[0], info, hadBlock);
                    }
                }

                for (int i = 0; i < 18; ++i) {
                    AbstractDungeon.effectsQueue.add(new DamageImpactLineEffect(__instance.hb.cX, __instance.hb.cY));
                }

                for (AbstractPower power : __instance.powers) {
                    if (power instanceof UnyieldingPower) {
                        damageAmount[0] = ((UnyieldingPower) power).onKamiDamaged(damageAmount[0], info.type);
                    }
                }

                CardCrawlGame.screenShake.shake(ShakeIntensity.MED, ShakeDur.SHORT, false);
                if (tempHp > damageAmount[0]) {
                    AbstractDungeon.effectsQueue.add(new TempDamageNumberEffect(__instance, __instance.hb.cX, __instance.hb.cY, damageAmount[0]));
                    kamiManager.setHp(tempHp - damageAmount[0]);
                    kamiManager.getCurrentKami().onDamaged(damageAmount[0], info.type);
                    damageAmount[0] = 0;
                } else {
                    int damage = tempHp;
                    for (AbstractPower power : __instance.powers) {
                        if (power instanceof UnyieldingPower) {
                            if (tempHp > 1) {
                                damage = tempHp - 1;
                                damageAmount[0] = 0;
                            }
                            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(__instance, __instance, power.ID, 1));
                            break;
                        }
                    }

                    AbstractDungeon.effectsQueue.add(new TempDamageNumberEffect(__instance, __instance.hb.cX, __instance.hb.cY, damage));
                    kamiManager.setHp(tempHp - damage);
                    kamiManager.getCurrentKami().onDamaged(damage, info.type);
                    if (tempHp == damage) {
                        kamiManager.onDead();
                        damageAmount[0] -= damage;
                    }
                }
            }
        }
    }

    @SpireInsertPatch(
            locator = StrikeEffectLocator.class
    )
    public static SpireReturn<Void> Insert(AbstractCreature __instance, DamageInfo info) {
        if (AbstractDungeon.player instanceof Sanme) {
            KamiManager kamiManager = ((Sanme) AbstractDungeon.player).getKamiManager();
            return kamiManager.getHp() > 0 ? SpireReturn.Return((Void) null) : SpireReturn.Continue();
        }
        return SpireReturn.Continue();
    }

    private static class AfterDecrementLocator extends SpireInsertLocator {
        private AfterDecrementLocator() {
        }

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "decrementBlock");
            return Inject.insertAfter(LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher), 1);
        }
    }

    private static class BeforeDecrementBlockLocator extends SpireInsertLocator {
        private BeforeDecrementBlockLocator() {
        }

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(DamageInfo.class, "output");
            return Inject.insertAfter(LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher), 1);
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
