package com.github.paopaoyue.onmyojimod.action;

import com.github.paopaoyue.onmyojimod.card.Exorcism;
import com.github.paopaoyue.onmyojimod.character.Sanme;
import com.github.paopaoyue.onmyojimod.power.DivinedEyePower;
import com.github.paopaoyue.onmyojimod.relic.Talisman;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DivineAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("Onmyoji:DivineAction");

    private Consumer<DivineAction> callback;
    private List<AbstractCard> divinedCards;
    private List<AbstractCard> selectedCards;
    private boolean callbackDone;

    public DivineAction(int numCards) {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player, amount);
        this.amount = numCards;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.divinedCards = new ArrayList<>();
        this.selectedCards = new ArrayList<>();
        this.callbackDone = false;

        for (AbstractPower power : this.target.powers) {
            if (power instanceof DivinedEyePower) {
                this.amount = ((DivinedEyePower) power).onDivine(this.amount);
            }
        }
    }

    public DivineAction(int numCards, Consumer<DivineAction> callback) {
        this(numCards);
        this.callback = callback;
    }

    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
        } else {
            if (this.duration == Settings.ACTION_DUR_FAST) {

                if (AbstractDungeon.player.drawPile.isEmpty()) {
                    if (this.callback != null) this.callback.accept(this);
                    this.isDone = true;
                    return;
                }

                CardGroup tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                if (this.amount != -1) {
                    for (int i = 0; i < Math.min(this.amount, AbstractDungeon.player.drawPile.size()); ++i) {
                        tmpGroup.addToTop(AbstractDungeon.player.drawPile.group.get(AbstractDungeon.player.drawPile.size() - i - 1));
                    }
                } else {
                    for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
                        tmpGroup.addToBottom(card);
                    }
                }
                this.divinedCards.addAll(tmpGroup.group);
                AbstractDungeon.gridSelectScreen.open(tmpGroup, this.amount, true, uiStrings.TEXT[0]);
            } else {
                if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                    this.selectedCards.addAll(AbstractDungeon.gridSelectScreen.selectedCards);
                    for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards) {
                        AbstractDungeon.player.drawPile.removeCard(card);
                        AbstractDungeon.player.drawPile.addToBottom(card);
                        if (card instanceof Exorcism) ((Exorcism) card).onDivine();
                        card.stopGlowing();
                    }
                    AbstractDungeon.gridSelectScreen.selectedCards.clear();
                }
                if (!callbackDone) {
                    if (this.callback != null) {
                        this.callback.accept(this);
                    }
                    if (AbstractDungeon.player.hasRelic(Talisman.ID)) {
                        ((Talisman) AbstractDungeon.player.getRelic(Talisman.ID)).onDivine(this);
                    }
                    if (target instanceof Sanme && ((Sanme) this.target).getKamiManager().isHasKami()) {
                        ((Sanme) this.target).getKamiManager().getCurrentKami().onDivine(amount);
                    }
                    callbackDone = true;
                }
            }

            this.tickDuration();
        }
    }

    public List<AbstractCard> getDivinedCards() {
        return divinedCards;
    }

    public List<AbstractCard> getSelectedCards() {
        return selectedCards;
    }
}
