package com.github.paopaoyue.onmyojimod.action;

import com.github.paopaoyue.onmyojimod.character.Sanme;
import com.github.paopaoyue.onmyojimod.object.kami.AbstractKami;
import com.github.paopaoyue.onmyojimod.object.kami.KamiManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SwitchKamiAction extends AbstractGameAction {
    private AbstractKami newKami;
    private AbstractCard card;

    public SwitchKamiAction(AbstractCard card, AbstractKami kami, int kamiHp) {
        setValues(AbstractDungeon.player, AbstractDungeon.player, kamiHp);
        this.card = card;
        this.newKami = kami;
        this.duration = Settings.ACTION_DUR_MED;
        this.actionType = ActionType.SPECIAL;
    }

    public void update() {
        if (!(this.target instanceof Sanme)) {
            this.isDone = true;
            return;
        }
        if (this.duration == Settings.ACTION_DUR_MED) {
            KamiManager kamiManager = ((Sanme) AbstractDungeon.player).getKamiManager();
            kamiManager.setHp(amount + kamiManager.getHp());
            kamiManager.setBaseHp(amount);
            kamiManager.onSwitch(card, newKami);
            if (this.amount > 0) {
                this.target.healthBarUpdatedEvent();
            }
        } else if (this.duration <= 0.1F) {
            if (card.exhaust) {
                Sanme player = (Sanme) AbstractDungeon.player;
                player.SwitchCharacterImage(newKami.getCharacterImage());
            }
        }


        this.tickDuration();
    }
}
