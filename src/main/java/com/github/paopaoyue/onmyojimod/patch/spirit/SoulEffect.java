package com.github.paopaoyue.onmyojimod.patch.spirit;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.github.paopaoyue.onmyojimod.object.spirit.AbstractSpirit;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.Soul;

@SpirePatch(
        clz = Soul.class,
        method = "isCarryingCard"
)
public class SoulEffect {

    public SoulEffect() {
    }

    public static SpireReturn<Boolean> Prefix(Soul __instance, AbstractCard ___card) {
        AbstractSpirit spirit = (AbstractSpirit) SpiritField.spirit.get(___card);
        if (spirit != null) {
            return SpireReturn.Return(true);
        }
        return SpireReturn.Continue();
    }


}
