package com.github.paopaoyue.onmyojimod.patch.kami;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch(
        clz = GameActionManager.class,
        method = "callEndOfTurnActions"
)
public class TurnEnd {
    public TurnEnd() {
    }

    public static void Prefix(GameActionManager __instance) {
        KamiManagerField.kamiManager.get(AbstractDungeon.player).onTurnEnd();
    }
}