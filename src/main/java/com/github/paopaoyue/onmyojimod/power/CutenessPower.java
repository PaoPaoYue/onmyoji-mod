package com.github.paopaoyue.onmyojimod.power;

import com.badlogic.gdx.graphics.Texture;
import com.github.paopaoyue.onmyojimod.action.RollDiceAction;
import com.github.paopaoyue.onmyojimod.card.AbstractKamiCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static com.github.paopaoyue.onmyojimod.card.Util.kamiCardList;

public class CutenessPower extends AbstractPower {
    public static final String POWER_ID = "Onmyoji:Cuteness";
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = ImageMaster.loadImage("image/icon/cuteness.png");


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
                AbstractCard card = kamiCardList.get(AbstractDungeon.cardRandomRng.random(kamiCardList.size() - 1)).makeCopy();
                card.setCostForTurn(0);
                if (action.amount == 6) {
                    ((AbstractKamiCard) card).modifyBaseHp(((AbstractKamiCard) card).getBaseHp());
                }
                this.addToBot(new MakeTempCardInHandAction(card));
            }
        }
    }

    public void updateDescription() {
        this.description = strings.DESCRIPTIONS[0] + this.amount + strings.DESCRIPTIONS[1];
    }

}
