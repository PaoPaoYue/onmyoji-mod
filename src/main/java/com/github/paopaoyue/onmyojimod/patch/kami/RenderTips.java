package com.github.paopaoyue.onmyojimod.patch.kami;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.github.paopaoyue.onmyojimod.object.kami.AbstractKami;
import com.github.paopaoyue.onmyojimod.object.kami.KamiManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.PowerTip;

import java.util.ArrayList;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "renderPowerTips"
)
public class RenderTips {
    public RenderTips() {
    }

    @SpireInsertPatch(
            rloc = 2, localvars = {"tips"}
    )
    public static void Insert(AbstractPlayer __instance, ArrayList<PowerTip> tips) {
        KamiManager kamiManager = (KamiManager) KamiManagerField.kamiManager.get(__instance);
        if (kamiManager.isHasKami()) {
            AbstractKami kami = kamiManager.getCurrentKami();
            kami.updateDescription();
            tips.add(new PowerTip(kami.getName(), kami.getDescription()));
        }
    }
}
