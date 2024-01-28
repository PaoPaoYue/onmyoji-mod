package com.github.paopaoyue.onmyojimod.patch.kami;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.github.paopaoyue.onmyojimod.character.Sanme;
import com.github.paopaoyue.onmyojimod.object.kami.KamiManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(
        clz = AbstractCard.class,
        method = "applyPowers"
)
public class CardApplyPowersMulti {
    public CardApplyPowersMulti() {
    }

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"tmp"}
    )
    public static void Insert(AbstractCard __instance, DamageInfo.DamageType ___damageTypeForTurn, float[] tmp) {
        if (AbstractDungeon.player instanceof Sanme) {
            KamiManager kamiManager = ((Sanme) AbstractDungeon.player).getKamiManager();
            if (kamiManager.isHasKami()) {
                for (int i = 0; i < tmp.length; ++i) {
                    tmp[i] = kamiManager.getCurrentKami().atDamageFinalGive(tmp[i], ___damageTypeForTurn);
                }
            }
        }
    }


    private static class Locator extends SpireInsertLocator {
        private Locator() {
        }

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPower.class, "atDamageFinalGive");
            return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher)[1] - 2};
        }
    }
}
