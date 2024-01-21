package com.github.paopaoyue.onmyojimod.action;

import com.github.paopaoyue.onmyojimod.effect.SpiritAttachedEffect;
import com.github.paopaoyue.onmyojimod.object.spirit.AbstractSpirit;
import com.github.paopaoyue.onmyojimod.patch.spirit.SpiritField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.function.Predicate;

public class RandomAttachSpiritAction extends AbstractGameAction {
    private AbstractSpirit spirit;
    private Predicate<AbstractCard> predicate;
    private boolean isFast;

    public RandomAttachSpiritAction(AbstractSpirit spirit, int amount, Predicate<AbstractCard> predicate, boolean isFast) {
        this.spirit = spirit;
        this.predicate = predicate;
        this.amount = amount;
        this.duration = isFast ? Settings.ACTION_DUR_FAST : Settings.ACTION_DUR_LONG;
        this.actionType = ActionType.SPECIAL;
        this.isFast = isFast;
    }

    public void update() {
        if (this.duration == (this.isFast ? Settings.ACTION_DUR_FAST : Settings.ACTION_DUR_LONG)) {
            System.out.println(AbstractDungeon.player.drawPile.group.size());
            ArrayList<AbstractCard> rngPool = new ArrayList<>();
            for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
                if (predicate.test(card) && SpiritField.spirit.get(card) == null) {
                    rngPool.add(card);
                }
            }
            for (int i = 0; i < amount && rngPool.size() > 0; i++) {
                AbstractCard card = rngPool.get(AbstractDungeon.cardRandomRng.random(rngPool.size() - 1));
                spirit.attachToCard(card);
                AbstractDungeon.effectList.add(new SpiritAttachedEffect(card));
                rngPool.remove((card));
            }
        }

        this.tickDuration();
    }

}
