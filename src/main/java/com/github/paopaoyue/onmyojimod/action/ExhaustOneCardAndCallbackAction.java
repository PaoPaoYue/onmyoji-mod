package com.github.paopaoyue.onmyojimod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class ExhaustOneCardAndCallbackAction extends AbstractGameAction {
    public static final String[] TEXT;
    private static final UIStrings uiStrings;

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
        TEXT = uiStrings.TEXT;
    }

    private Consumer<AbstractCard> callback;

    private Predicate<AbstractCard> predicate;

    public ExhaustOneCardAndCallbackAction(Consumer<AbstractCard> callback) {
        this.target = AbstractDungeon.player;
        this.source = AbstractDungeon.player;
        this.callback = callback;
        this.amount = 1;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.EXHAUST;
    }

    public ExhaustOneCardAndCallbackAction(Consumer<AbstractCard> callback, Predicate<AbstractCard> predicate) {
        this.target = AbstractDungeon.player;
        this.source = AbstractDungeon.player;
        this.callback = callback;
        this.amount = 1;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.EXHAUST;
        this.predicate = predicate;
    }


    public void update() {
        if (this.duration == this.startDuration) {
            CardGroup tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                if (predicate == null || predicate.test(card)) {
                    tmpGroup.group.add(card);
                }
            }

            if (tmpGroup.group.size() == 0) {
                this.isDone = true;
                return;
            }

            if (tmpGroup.group.size() == 1) {
                AbstractCard card = tmpGroup.getTopCard();
                AbstractDungeon.player.hand.moveToExhaustPile(AbstractDungeon.player.hand.getTopCard());
                this.callback.accept(card);
                CardCrawlGame.dungeon.checkForPactAchievement();
                return;
            }

            AbstractDungeon.gridSelectScreen.open(tmpGroup, this.amount, TEXT[0], false, false);
            this.tickDuration();
        }

        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {

            for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards) {
                AbstractDungeon.player.hand.moveToExhaustPile(card);
                this.callback.accept(card);
            }
            CardCrawlGame.dungeon.checkForPactAchievement();
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }

        this.tickDuration();
    }


}
