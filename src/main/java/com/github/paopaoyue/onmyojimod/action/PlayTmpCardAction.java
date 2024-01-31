package com.github.paopaoyue.onmyojimod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PlayTmpCardAction extends AbstractGameAction {

    private AbstractCard card;
    private boolean isOtherCardInCenter;
    private boolean exhaustCards;

    public PlayTmpCardAction(AbstractCard card, boolean isOtherCardInCenter, boolean exhausts) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.card = card;
        this.isOtherCardInCenter = isOtherCardInCenter;
        this.exhaustCards = exhausts;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (card == null) {
                this.isDone = true;
                return;
            }
            if (!AbstractDungeon.player.drawPile.isEmpty()) {
                card.exhaustOnUseOnce = this.exhaustCards;
                AbstractDungeon.player.limbo.group.add(card);
                card.current_y = -200.0f * Settings.scale;
                card.target_x = Settings.WIDTH / 2.0f + (isOtherCardInCenter ? (25.0F + AbstractCard.IMG_WIDTH) * Settings.xScale : 0F);
                card.target_y = Settings.HEIGHT / 2.0f;
                card.targetAngle = 0.0f;
                card.lighten(false);
                card.drawScale = 0.12f;
                card.targetDrawScale = 0.75f;
                card.applyPowers();
                this.addToTop(new NewQueueCardAction(card, true, false, true));
                this.addToTop(new UnlimboAction(card));
                if (!Settings.FAST_MODE) {
                    this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                } else {
                    this.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                }
            }
            this.isDone = true;
        }
    }
}
