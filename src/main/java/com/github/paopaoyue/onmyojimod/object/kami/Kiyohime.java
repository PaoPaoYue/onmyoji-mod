package com.github.paopaoyue.onmyojimod.object.kami;

import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.github.paopaoyue.onmyojimod.OnmyojiMod;
import com.github.paopaoyue.onmyojimod.power.PenetratedPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class Kiyohime extends AbstractKami {

    public static final String ID = "Onmyoji:Kiyohime";

    public static final String CHARACTER_IMG = "image/character/kiyohime.png";
    private static final Keyword kamiString = OnmyojiMod.MOD_DICTIONARY.get(ID);


    public Kiyohime() {
        this.id = kamiString.ID;
        this.name = kamiString.NAMES[0];
        this.faction = Faction.PURPLE;
        this.updateDescription();
    }

    public int onAttackPreDecrementBlock(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (target != null && info.type == DamageInfo.DamageType.NORMAL) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new PenetratedPower(target, damageAmount)));
        }
        return 0;
    }

    @Override
    public String getCharacterImage() {
        return CHARACTER_IMG;
    }

    @Override
    public void updateDescription() {
        description = kamiString.DESCRIPTION;
    }

}
