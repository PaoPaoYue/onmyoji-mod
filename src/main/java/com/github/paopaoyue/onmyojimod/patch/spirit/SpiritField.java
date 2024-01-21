package com.github.paopaoyue.onmyojimod.patch.spirit;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.github.paopaoyue.onmyojimod.object.spirit.AbstractSpirit;

@SpirePatch(
        cls = "com.megacrit.cardcrawl.cards.AbstractCard",
        method = "<class>"
)
public class SpiritField {
    public static SpireField<AbstractSpirit> spirit = new SpireField<>(() -> null);

    public SpiritField() {
    }
}