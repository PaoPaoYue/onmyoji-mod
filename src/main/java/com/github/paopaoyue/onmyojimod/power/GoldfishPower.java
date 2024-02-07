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
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class GoldfishPower extends AbstractPower {
    public static final String POWER_ID = "Onmyoji:Goldfish";
    private static final int[] probArray = new int[]{2, 5, 8, 10};
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = ImageMaster.loadImage("image/icon/goldfish.png");
    private int count;
    private int cardUseCount;

    public GoldfishPower(AbstractCreature owner, int amount) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = IMG;
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.count = amount;
        this.cardUseCount = 0;
        this.canGoNegative = false;
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        this.count = this.amount;
        this.cardUseCount = 0;
        updateDescription();
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) return;
        if (count <= 0 || card.rarity == AbstractCard.CardRarity.UNCOMMON || card.rarity == AbstractCard.CardRarity.RARE)
            return;
        if (getTriggered()) {
            this.count--;
            this.cardUseCount = 0;
            updateDescription();
            AbstractCard c = getRandomCard().makeStatEquivalentCopy();
            this.addToBot(new PlayTmpCardAction(c, false, true));
        }

        this.cardUseCount = (cardUseCount + 1) % probArray.length;

    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        this.count += stackAmount;
    }

    public void updateDescription() {
        this.description = strings.DESCRIPTIONS[0] + strings.DESCRIPTIONS[1] + this.count + strings.DESCRIPTIONS[2];
    }

    private boolean getTriggered() {
        int num = AbstractDungeon.cardRandomRng.random(1, 10);
        return probArray[this.cardUseCount] >= num;
    }

    private AbstractCard getRandomCard() {
        AbstractCard card;
        do {
            card = AbstractDungeon.getCard(AbstractCard.CardRarity.COMMON, AbstractDungeon.cardRandomRng).makeCopy();
        } while ((card instanceof AbstractKamiCard) || card.type == AbstractCard.CardType.POWER);
        return card;
    }

}
