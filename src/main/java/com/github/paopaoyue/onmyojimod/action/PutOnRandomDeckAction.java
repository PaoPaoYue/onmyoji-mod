package com.github.paopaoyue.onmyojimod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class PutOnRandomDeckAction extends AbstractGameAction {
    public static final String[] TEXT;
    private static final UIStrings uiStrings;

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("PutOnRandomDeckAction");
        TEXT = PutOnRandomDeckAction.uiStrings.TEXT;
    }

    private AbstractPlayer p;

    public PutOnRandomDeckAction(int amount) {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player, amount);
        this.p = (AbstractPlayer) target;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        if (this.duration == 0.5f) {
            if (this.p.hand.size() < this.amount) {
                this.amount = this.p.hand.size();
            }

            if (this.p.hand.group.size() > this.amount) {
                AbstractDungeon.handCardSelectScreen.open(PutOnRandomDeckAction.TEXT[0], this.amount, false);
                this.tickDuration();
                return;
            }
            for (int i = 0; i < this.p.hand.size(); ++i) {
                this.p.hand.moveToDeck(this.p.hand.getRandomCard(AbstractDungeon.cardRandomRng), true);
            }
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (final AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                this.p.hand.moveToDeck(c, true);
            }
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        this.tickDuration();
    }

}
