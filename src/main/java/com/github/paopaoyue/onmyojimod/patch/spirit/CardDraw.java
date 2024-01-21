package com.github.paopaoyue.onmyojimod.patch.spirit;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.github.paopaoyue.onmyojimod.object.spirit.AbstractSpirit;
import com.github.paopaoyue.onmyojimod.utility.Inject;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "draw",
        paramtypez = {
                int.class
        }
)
public class CardDraw {

    public CardDraw() {
    }

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"c"}
    )
    public static void Insert(AbstractPlayer __instance, AbstractCard c) {
        AbstractSpirit spirit = (AbstractSpirit) SpiritField.spirit.get(c);
        if (spirit != null) {
            spirit.onDraw();
        }
    }

    private static class Locator extends SpireInsertLocator {
        private Locator() {
        }

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(CardGroup.class, "removeTopCard");
            return Inject.insertAfter(LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher));
        }
    }
}
