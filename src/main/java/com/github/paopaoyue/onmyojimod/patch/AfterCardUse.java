package com.github.paopaoyue.onmyojimod.patch;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.github.paopaoyue.onmyojimod.card.Trick;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(
        clz = UseCardAction.class,
        method = "update"
)
public class AfterCardUse {

    public AfterCardUse() {
    }

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"target"}
    )
    public static void Insert(UseCardAction __instance, AbstractCard ___targetCard, AbstractCreature target) {
        for (final AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c instanceof Trick && !___targetCard.dontTriggerOnUseCard) {
                ((Trick) c).onAfterCardUse(___targetCard, target);
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        private Locator() {
        }

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "freeToPlayOnce");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}
