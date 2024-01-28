package com.github.paopaoyue.onmyojimod.patch.kami;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.github.paopaoyue.onmyojimod.character.Sanme;
import com.github.paopaoyue.onmyojimod.object.kami.KamiManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(
        clz = AbstractCard.class,
        method = "calculateCardDamage"
)
public class CardCalculateDamage {
    public CardCalculateDamage() {
    }

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"tmp"}
    )
    public static void Insert(AbstractCard __instance, AbstractMonster mo, DamageInfo.DamageType ___damageTypeForTurn, @ByRef float[] tmp) {
        if (AbstractDungeon.player instanceof Sanme) {
            KamiManager kamiManager = ((Sanme) AbstractDungeon.player).getKamiManager();
            if (kamiManager.isHasKami()) {
                tmp[0] = kamiManager.getCurrentKami().atDamageFinalGive(tmp[0], ___damageTypeForTurn);
            }
        }
    }


    private static class Locator extends SpireInsertLocator {
        private Locator() {
        }

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPower.class, "atDamageFinalGive");
            return new int[]{LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher)[0] - 1};
        }
    }
}