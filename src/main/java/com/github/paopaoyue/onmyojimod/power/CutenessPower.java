package com.github.paopaoyue.onmyojimod.power;

import com.badlogic.gdx.graphics.Texture;
import com.github.paopaoyue.onmyojimod.action.RollDiceAction;
import com.github.paopaoyue.onmyojimod.card.*;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.List;

public class CutenessPower extends AbstractPower {
    public static final String POWER_ID = "Onmyoji:Cuteness";
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = ImageMaster.loadImage("image/icon/cuteness.png");
    private static final List<AbstractCard> cardList = new ArrayList<>();

    static {
        cardList.add(new Kiyohime());
        cardList.add(new Hitotsume());
        cardList.add(new Akaname());
        cardList.add(new Enmusubi());
        cardList.add(new Yamausagi());
        cardList.add(new Kusa());
        cardList.add(new Komatsu());
        cardList.add(new Itsumade());
        cardList.add(new ShutenDoji());
        cardList.add(new YotoHime());
        cardList.add(new Momo());
        cardList.add(new Shiranui());
        cardList.add(new Bokku());
        cardList.add(new Onikiri());
        cardList.add(new Hangan());
        cardList.add(new Dodomeki());
    }


    public CutenessPower(AbstractCreature owner, int amount) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = IMG;
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.canGoNegative = false;
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
            RollDiceAction action = new RollDiceAction();
            this.addToBot(action);
            for (int i = 0; i < this.amount; ++i) {
                AbstractCard card = cardList.get(AbstractDungeon.cardRandomRng.random(cardList.size() - 1)).makeCopy();
                if (action.amount == 6) {
                    card.updateCost(-9);
                }
                this.addToBot(new MakeTempCardInHandAction(card));
            }
        }
    }

    public void updateDescription() {
        this.description = strings.DESCRIPTIONS[0] + this.amount + strings.DESCRIPTIONS[1];
    }

}
