package com.github.paopaoyue.onmyojimod.object.kami;

import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.github.paopaoyue.onmyojimod.OnmyojiMod;
import com.github.paopaoyue.onmyojimod.character.Sanme;
import com.github.paopaoyue.onmyojimod.orb.PineconeOrb;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Objects;

public class Komatsu extends AbstractKami {

    public static final String ID = "Onmyoji:Komatsu";

    public static final String CHARACTER_IMG = "image/character/komatsu.png";
    private static final Keyword kamiString = OnmyojiMod.MOD_DICTIONARY.get(ID);

    public Komatsu() {
        this.id = kamiString.ID;
        this.name = kamiString.NAMES[0];
        this.faction = Faction.GREEN;
        this.updateDescription();
    }

    @Override
    public void onExit(AbstractKami nextKami) {
        if (nextKami == AbstractKami.ONMYOJI_NONE || Objects.equals(nextKami.id, ID)) {
            return;
        }
        AbstractCard kamiCard = null;
        if (!((Sanme) AbstractDungeon.player).getKamiManager().getKamiCardGroup().isEmpty()) {
            kamiCard = ((Sanme) AbstractDungeon.player).getKamiManager().getKamiCardGroup().group.get(0);
        }
        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new PineconeOrb(1, kamiCard)));
    }

    @Override
    public String getCharacterImage() {
        return CHARACTER_IMG;
    }

    @Override
    public void updateDescription() {
        this.description = kamiString.DESCRIPTION;
    }

}
