package com.github.paopaoyue.onmyojimod.action;

import com.github.paopaoyue.onmyojimod.character.Sanme;
import com.github.paopaoyue.onmyojimod.effect.RollDiceEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RollDiceAction extends AbstractGameAction {
    public RollDiceAction() {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player, amount);
        this.startDuration = Settings.ACTION_DUR_LONG;
        this.duration = this.startDuration;
        this.actionType = ActionType.SPECIAL;

        amount = AbstractDungeon.cardRng.random(1, 6);
        if (this.target instanceof Sanme && ((Sanme) this.target).getKamiManager().isHasKami()) {
            amount = ((Sanme) this.target).getKamiManager().getCurrentKami().onRollDice(amount);
        }

        if (amount == 6) {
            for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
                if (card instanceof com.github.paopaoyue.onmyojimod.card.Yamausagi) {
                    ((com.github.paopaoyue.onmyojimod.card.Yamausagi) card).modifyBaseHp(card.magicNumber);
                }
            }
            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card instanceof com.github.paopaoyue.onmyojimod.card.Yamausagi) {
                    ((com.github.paopaoyue.onmyojimod.card.Yamausagi) card).modifyBaseHp(card.magicNumber);
                }
            }
            for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
                if (card instanceof com.github.paopaoyue.onmyojimod.card.Yamausagi) {
                    ((com.github.paopaoyue.onmyojimod.card.Yamausagi) card).modifyBaseHp(card.magicNumber);
                }
            }
            for (AbstractCard card : ((Sanme) AbstractDungeon.player).getKamiManager().getKamiCardGroup().group) {
                if (card instanceof com.github.paopaoyue.onmyojimod.card.Yamausagi) {
                    ((com.github.paopaoyue.onmyojimod.card.Yamausagi) card).modifyBaseHp(card.magicNumber);
                }
            }
        }
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            AbstractDungeon.effectList.add(new RollDiceEffect(this.target.hb.cX, this.target.hb.cY, amount));
        }
        this.tickDuration();
    }
}
