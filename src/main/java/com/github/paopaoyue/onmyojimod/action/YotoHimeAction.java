package com.github.paopaoyue.onmyojimod.action;

import com.github.paopaoyue.onmyojimod.card.YotoHime;
import com.github.paopaoyue.onmyojimod.character.Sanme;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class YotoHimeAction extends AbstractGameAction {
    private static final float DURATION = 0.0F;
    private DamageInfo info;
    private YotoHime card;

    public YotoHimeAction(AbstractCreature target, AbstractCreature source, YotoHime card) {
        this.setValues(target, source);
        this.source = source;
        this.actionType = ActionType.SPECIAL;
        this.duration = DURATION;
        this.card = card;
    }

    @Override
    public void update() {
        if (this.shouldCancelAction()) {
            this.isDone = true;
            return;
        }
        if (this.duration == DURATION && this.target != null) {
            if (this.target == null || this.card == null) return;
            card.calculateCardDamage((AbstractMonster) target);
            int count = 0;
            if (AbstractDungeon.player instanceof Sanme) {
                count += ((Sanme) AbstractDungeon.player).getKamiManager().getKamiSwitchCountInTurn();
            }
            for (int i = 0; i < count; i++) {
                AbstractDungeon.actionManager.addToTop(new DamageAction(target, new DamageInfo(AbstractDungeon.player, card.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            }
        }
        this.tickDuration();
    }
}
