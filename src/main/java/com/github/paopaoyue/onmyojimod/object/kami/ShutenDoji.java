package com.github.paopaoyue.onmyojimod.object.kami;

import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.github.paopaoyue.onmyojimod.OnmyojiMod;
import com.github.paopaoyue.onmyojimod.power.UnyieldingPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class ShutenDoji extends AbstractKami {

    public static final String ID = "Onmyoji:Shuten Doji";

    public static final String CHARACTER_IMG = "image/character/shuten_doji.png";
    private static final Keyword kamiString = OnmyojiMod.MOD_DICTIONARY.get(ID);

    public ShutenDoji() {
        this.id = kamiString.ID;
        this.name = kamiString.NAMES[0];
        this.faction = Faction.RED;
        this.updateDescription();
    }

    @Override
    public void onEnter() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new UnyieldingPower(AbstractDungeon.player, 1)));
    }

    @Override
    public void onDamaged(int damage, DamageInfo.DamageType damageType) {
        if (damage >= 3) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, upgraded ? 2 : 1)));
        }
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
