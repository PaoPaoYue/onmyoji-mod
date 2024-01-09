package com.github.paopaoyue.onmyojimod.patch.kami;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

@SpirePatch(
        clz = AbstractRoom.class,
        method = "endBattle"
)
public class BattleEnd {
    public BattleEnd() {
    }

    public static void Prefix(AbstractRoom __instance) {
        KamiManagerField.kamiManager.get(AbstractDungeon.player).onBattleEnd();
    }
}
