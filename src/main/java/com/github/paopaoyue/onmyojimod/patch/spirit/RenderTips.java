package com.github.paopaoyue.onmyojimod.patch.spirit;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.github.paopaoyue.onmyojimod.object.spirit.AbstractSpirit;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.TipHelper;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(
        clz = AbstractCard.class,
        method = "renderCardTip"
)
public class RenderTips {
    public RenderTips() {
    }

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"sb"}
    )
    public static void Insert(AbstractCard __instance, SpriteBatch sb) {
        AbstractSpirit spirit = (AbstractSpirit) SpiritField.spirit.get(__instance);
        if (spirit != null) {
            ArrayList<String> keywords = new ArrayList<>(__instance.keywords);
            keywords.add(spirit.getName());
            TipHelper.renderTipForCard(__instance, sb, keywords);
        }
    }

    private static class Locator extends SpireInsertLocator {
        private Locator() {
        }

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "keywords");
            int[] findResult = LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
            return new int[]{findResult[findResult.length - 1]};
        }
    }
}
