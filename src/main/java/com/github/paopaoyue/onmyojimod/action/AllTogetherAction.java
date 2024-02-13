package com.github.paopaoyue.onmyojimod.action;

import com.github.paopaoyue.onmyojimod.object.spirit.Knot;
import com.github.paopaoyue.onmyojimod.patch.spirit.SpiritField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class AllTogetherAction extends AbstractGameAction {
    public static final String[] TEXT;
    private static final UIStrings uiStrings;
    public static int numPlaced;

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Onmyoji:AllTogetherAction");
        TEXT = uiStrings.TEXT;
    }

    private AbstractPlayer p;
    private boolean isRandom;

    public AllTogetherAction(final AbstractCreature target, final AbstractCreature source, final int amount, final boolean isRandom) {
        this.target = target;
        this.p = (AbstractPlayer) target;
        this.setValues(target, source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.isRandom = isRandom;
    }

    @Override
    public void update() {
        if (this.duration == 0.5f) {
            if (this.p.hand.size() < this.amount) {
                this.amount = this.p.hand.size();
            }
            if (this.isRandom) {
                for (int i = 0; i < this.amount; ++i) {
                    AbstractCard c = this.p.hand.getRandomCard(AbstractDungeon.cardRandomRng);
                    attachToCard(c);
                    this.p.hand.moveToDeck(c, true);
                }
            } else {
                if (this.p.hand.group.size() > this.amount) {
                    AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, false);
                    this.tickDuration();
                    return;
                }
                for (int i = 0; i < this.p.hand.size(); ++i) {
                    AbstractCard c = this.p.hand.getRandomCard(AbstractDungeon.cardRandomRng);
                    attachToCard(c);
                    this.p.hand.moveToDeck(c, true);
                }
            }
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (final AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                attachToCard(c);
                this.p.hand.moveToDeck(c, true);
            }
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        this.tickDuration();
    }

    private void attachToCard(AbstractCard card) {
        if (SpiritField.spirit.get(card) == null) {
            new Knot().attachToCard(card);
        }
    }


}
