package com.github.paopaoyue.onmyojimod.object.kami;

import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.github.paopaoyue.onmyojimod.OnmyojiMod;
import com.github.paopaoyue.onmyojimod.action.HealLossHPAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Momo extends AbstractKami {

    public static final String ID = "Onmyoji:Momo";

    public static final String CHARACTER_IMG = "image/character/momo.png";
    private static final Keyword kamiString = OnmyojiMod.MOD_DICTIONARY.get(ID);

    public Momo() {
        this.id = kamiString.ID;
        this.name = kamiString.NAMES[0];
        this.faction = Faction.RED;
        this.updateDescription();
    }

    @Override
    public void onEnter() {
        AbstractDungeon.actionManager.addToBottom(new HealLossHPAction(upgraded ? 3 : 2));
    }

    @Override
    public void onHeal(int amount) {
        if (amount <= 0) return;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1)));
    }

    @Override
    public String getCharacterImage() {
        return CHARACTER_IMG;
    }

    @Override
    public void updateDescription() {
        String[] description = kamiString.DESCRIPTION.split("\\|");
        if (this.upgraded) {
            this.description = description[1];
        } else {
            this.description = description[0];
        }
    }

}
