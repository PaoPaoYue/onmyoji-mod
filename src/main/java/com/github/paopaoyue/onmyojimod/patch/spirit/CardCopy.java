package com.github.paopaoyue.onmyojimod.patch.spirit;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.github.paopaoyue.onmyojimod.card.AbstractKamiCard;
import com.github.paopaoyue.onmyojimod.object.spirit.AbstractSpirit;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(
        clz = AbstractCard.class,
        method = "makeStatEquivalentCopy"
)
public class CardCopy {

    public CardCopy() {
    }

    @SpireInsertPatch(
            rloc = 1,
            localvars = {"card"}
    )
    public static void Insert(AbstractCard __instance, AbstractCard card) {
        AbstractSpirit spirit = (AbstractSpirit) SpiritField.spirit.get(__instance);
        if (spirit != null) {
            SpiritField.spirit.set(card, spirit);
        }
        if (__instance instanceof AbstractKamiCard) {
            AbstractKamiCard kamiCard = (AbstractKamiCard) card;
            kamiCard.setBaseHp(((AbstractKamiCard) __instance).getBaseHp());
            kamiCard.setHp(((AbstractKamiCard) __instance).getHp());
            kamiCard.setHpUpgraded(((AbstractKamiCard) __instance).isHpUpgraded());
            kamiCard.setHpModified(((AbstractKamiCard) __instance).isHpModified());
        }
    }


}
