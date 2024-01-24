package com.github.paopaoyue.onmyojimod.patch.kami;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.github.paopaoyue.onmyojimod.card.AbstractKamiCard;
import com.github.paopaoyue.onmyojimod.character.OnmyojiCharacter;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(
        clz = Soul.class,
        method = "update"
)
public class SwitchAnime {

    public SwitchAnime() {
    }

    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void Insert(Soul __instance) {
        if (__instance.card instanceof AbstractKamiCard) {
            OnmyojiCharacter player = (OnmyojiCharacter) AbstractDungeon.player;
            player.SwitchCharacterImage(((AbstractKamiCard) __instance.card).getKami().getCharacterImage());
        }
    }

    private static class Locator extends SpireInsertLocator {
        private Locator() {
        }

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "effectList");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}
