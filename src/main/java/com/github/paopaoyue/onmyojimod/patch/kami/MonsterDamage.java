package com.github.paopaoyue.onmyojimod.patch.kami;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.github.paopaoyue.onmyojimod.character.Sanme;
import com.github.paopaoyue.onmyojimod.object.kami.KamiManager;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(
        clz = AbstractMonster.class,
        method = "damage"
)
public class MonsterDamage {
    public MonsterDamage() {
    }

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"damageAmount"}
    )
    public static void Insert(AbstractMonster __instance, DamageInfo info, @ByRef int[] damageAmount) {
        if (info.owner == AbstractDungeon.player && info.owner instanceof Sanme) {
            KamiManager kamiManager = ((Sanme) info.owner).getKamiManager();
            if (kamiManager.isHasKami()) {
                damageAmount[0] = kamiManager.getCurrentKami().onAttackPreDecrementBlock(info, damageAmount[0], __instance);
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        private Locator() {
        }

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractMonster.class, "decrementBlock");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}
