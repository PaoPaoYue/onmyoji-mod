package com.github.paopaoyue.onmyojimod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SpecificCardToHandAction extends AbstractGameAction {
    CardGroup cardGroup;

    AbstractCard card;

    private AbstractPlayer p;

    public SpecificCardToHandAction(CardGroup cardGroup, AbstractCard card) {
        this.setValues(this.p = AbstractDungeon.player, AbstractDungeon.player, 1);
        this.cardGroup = cardGroup;
        this.card = card;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        if (this.cardGroup.size() > 0 && this.cardGroup.contains(card)) {
            this.p.hand.addToHand(card);
            card.lighten(false);
            this.cardGroup.removeCard(card);
            this.p.hand.refreshHandLayout();
        }
        this.tickDuration();
        this.isDone = true;
    }
}


