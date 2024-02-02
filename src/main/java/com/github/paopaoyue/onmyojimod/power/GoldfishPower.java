package com.github.paopaoyue.onmyojimod.power;

import com.badlogic.gdx.graphics.Texture;
import com.github.paopaoyue.onmyojimod.action.PlayTmpCardAction;
import com.github.paopaoyue.onmyojimod.card.AbstractKamiCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class GoldfishPower extends AbstractPower {
    public static final String POWER_ID = "Onmyoji:Goldfish";
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = ImageMaster.loadImage("image/icon/goldfish.png");


    public GoldfishPower(AbstractCreature owner, int amount) {
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
            for (int i = 0; i < this.amount; ++i) {
                AbstractCard card;
                do {
                    card = AbstractDungeon.getCard(AbstractCard.CardRarity.COMMON, AbstractDungeon.cardRandomRng).makeCopy();
                } while ((card instanceof AbstractKamiCard) || card.type == AbstractCard.CardType.POWER);
                this.addToBot(new PlayTmpCardAction(card, true));
            }
        }
    }

    public void updateDescription() {
        this.description = strings.DESCRIPTIONS[0] + this.amount + strings.DESCRIPTIONS[1];
    }

}
