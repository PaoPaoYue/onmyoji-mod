package com.github.paopaoyue.onmyojimod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import static com.github.paopaoyue.onmyojimod.card.Util.kamiCardList;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;

public class BookAction extends AbstractGameAction {
    private boolean retrieveCard;
    private boolean setCost;

    public BookAction(boolean setCost) {
        this.retrieveCard = false;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = 1;
        this.setCost = setCost;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.customCombatOpen(this.generateCardChoices(), CardRewardScreen.TEXT[1], false);
            this.tickDuration();
            return;
        }
        if (!this.retrieveCard) {
            if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                final AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                if (AbstractDungeon.player.hasPower("MasterRealityPower")) {
                    disCard.upgrade();
                }
                disCard.current_x = -1000.0f * Settings.xScale;
                if (this.amount == 1) {
                    if (AbstractDungeon.player.hand.size() < 10) {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
                    } else {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
                    }
                }
                AbstractDungeon.cardRewardScreen.discoveryCard = null;
            }
            this.retrieveCard = true;
        }
        this.tickDuration();
    }

    private ArrayList<AbstractCard> generateCardChoices() {
        ArrayList<AbstractCard> choices = new ArrayList<AbstractCard>();
        for (int i = 0; i < 3; i++) {
            AbstractCard card;
            AtomicReference<AbstractCard> ptr = new AtomicReference<>();
            do {
                card = kamiCardList.get(cardRandomRng.random(kamiCardList.size() - 1));
                ptr.set(card);
            } while (choices.stream().anyMatch(x -> x.cardID.equals(ptr.get().cardID)));
            card = card.makeStatEquivalentCopy();
            if (this.setCost) {
                card.setCostForTurn(0);
            }
            choices.add(card);
        }
        return choices;
    }
}
