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
    private final boolean hideVisual;
    private AbstractSpirit spirit;
    private Predicate<AbstractCard> predicate;
    private boolean fast;

    public RandomAttachSpiritAction(AbstractSpirit spirit, int amount, Predicate<AbstractCard> predicate, boolean hideVisual, boolean fast) {
        setValues(AbstractDungeon.player, AbstractDungeon.player, amount);
        this.spirit = spirit;
        this.predicate = predicate;
        this.duration = fast ? Settings.ACTION_DUR_FAST : Settings.ACTION_DUR_LONG;
        this.actionType = ActionType.SPECIAL;
        this.hideVisual = hideVisual;
        this.fast = fast;
    }

    public void update() {
        if (this.duration == (fast ? Settings.ACTION_DUR_FAST : Settings.ACTION_DUR_LONG)) {
            ArrayList<AbstractCard> rngPool = new ArrayList<>();
            for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
                if (predicate.test(card) && SpiritField.spirit.get(card) == null) {
                    rngPool.add(card);
                }
            }
            for (int i = 0; i < amount && rngPool.size() > 0; i++) {
                AbstractCard card = rngPool.get(AbstractDungeon.cardRandomRng.random(rngPool.size() - 1));
                spirit.attachToCard(card);
                if (!hideVisual) {
                    AbstractDungeon.effectList.add(new SpiritAttachedEffect(card));
                }
                rngPool.remove((card));
            }
        }

        this.tickDuration();
    }

}
