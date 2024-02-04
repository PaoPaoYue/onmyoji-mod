package com.github.paopaoyue.onmyojimod.object.kami;

import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.github.paopaoyue.onmyojimod.OnmyojiMod;
import com.github.paopaoyue.onmyojimod.action.YotoHimeAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class YotoHime extends AbstractKami {

    public static final String ID = "Onmyoji:Yoto Hime";

    public static final String CHARACTER_IMG = "image/character/yoto_hime.png";
    private static final Keyword kamiString = OnmyojiMod.MOD_DICTIONARY.get(ID);

    private com.github.paopaoyue.onmyojimod.card.YotoHime card;

    private AbstractMonster target;

    public YotoHime() {
        this.id = kamiString.ID;
        this.name = kamiString.NAMES[0];
        this.faction = Faction.RED;
        this.updateDescription();
        this.card = null;
        this.target = null;
    }

    public com.github.paopaoyue.onmyojimod.card.YotoHime getCard() {
        return card;
    }

    public void setCard(com.github.paopaoyue.onmyojimod.card.YotoHime card) {
        this.card = card;
    }

    public AbstractMonster getTarget() {
        return target;
    }

    public void setTarget(AbstractMonster target) {
        this.target = target;
    }

    @Override
    public void onEnter() {
        AbstractDungeon.actionManager.addToBottom(new YotoHimeAction(target, AbstractDungeon.player, card));
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
