package com.github.paopaoyue.onmyojimod.action;

import com.github.paopaoyue.onmyojimod.card.AbstractKamiCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PeachBlossomAction extends AbstractGameAction {
    public static final String[] TEXT;

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT;
    }

    private AbstractPlayer player;
    private int numberOfCards;
    private int reducedCost;

    public PeachBlossomAction(int numberOfCards, int reducedCost) {
        this.reducedCost = reducedCost;
        this.actionType = ActionType.CARD_MANIPULATION;
        float action_DUR_FAST = Settings.ACTION_DUR_FAST;
        this.startDuration = action_DUR_FAST;
        this.duration = action_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
    }

    @Override
    public void update() {
        if (this.duration != this.startDuration) {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (final AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards) {
                    cardToHand(card);
                    card.unhover();
                }
                for (final AbstractCard c : this.player.discardPile.group) {
                    c.unhover();
                    c.target_x = (float) CardGroup.DISCARD_PILE_X;
                    c.target_y = 0.0f;
                }
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }
            this.tickDuration();
            if (this.isDone) {
                for (final AbstractCard c : this.player.hand.group) {
                    c.applyPowers();
                }
            }
            return;
        }
        if (this.player.discardPile.isEmpty() || this.numberOfCards <= 0) {
            this.isDone = true;
            return;
        }
        CardGroup tmpCardGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (final AbstractCard card : this.player.discardPile.group) {
            if (card instanceof AbstractKamiCard)
                tmpCardGroup.group.add(card);
        }
        if (tmpCardGroup.group.size() <= this.numberOfCards) {
            for (final AbstractCard card : tmpCardGroup.group) {
                cardToHand(card);
            }
            this.isDone = true;
            return;
        }
        AbstractDungeon.gridSelectScreen.open(tmpCardGroup, this.numberOfCards, BetterDiscardPileToHandAction.TEXT[1] + this.numberOfCards + BetterDiscardPileToHandAction.TEXT[2], false);
        this.tickDuration();
    }

    private void cardToHand(AbstractCard card) {
        if (this.player.hand.size() < 10) {
            this.player.hand.addToHand(card);
            card.setCostForTurn(card.cost - reducedCost);
            this.player.discardPile.removeCard(card);
        }
        card.lighten(false);
        card.applyPowers();
    }


}
