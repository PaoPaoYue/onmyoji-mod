package com.github.paopaoyue.onmyojimod.card;

import com.github.paopaoyue.onmyojimod.action.SwitchKamiAction;
import com.github.paopaoyue.onmyojimod.patch.AbstractCardEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Bokku extends AbstractKamiCard {
    public static final String ID = "Onmyoji:Bokku";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public Bokku() {
        super(ID, cardStrings.NAME, null, 1, cardStrings.DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.ONMYOJI_COLOR, CardRarity.RARE, CardTarget.SELF,
                new com.github.paopaoyue.onmyojimod.object.kami.Bokku(), 5);
        this.cardsToPreview = new GoldenSeimei();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SwitchKamiAction(this, getKami(), getHp()));
    }


    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeKami();
            this.cardsToPreview.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public AbstractCard makeCopy() {
        return new Bokku();
    }
}
