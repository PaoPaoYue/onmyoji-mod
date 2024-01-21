package com.github.paopaoyue.onmyojimod.patch.spirit;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.github.paopaoyue.onmyojimod.object.spirit.AbstractSpirit;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "useCard"
)
public class CardUse {

    public CardUse() {
    }

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"c"}
    )
    public static void Insert(AbstractPlayer __instance, AbstractCard c) {
        AbstractSpirit spirit = (AbstractSpirit) SpiritField.spirit.get(c);
        if (spirit != null) {
            spirit.onUse();
        }
    }

    private static class Locator extends SpireInsertLocator {
        private Locator() {
        }

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "use");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}
