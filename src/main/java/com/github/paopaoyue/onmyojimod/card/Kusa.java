package com.github.paopaoyue.onmyojimod.card;

import com.github.paopaoyue.onmyojimod.action.SwitchKamiAction;
import com.github.paopaoyue.onmyojimod.patch.AbstractCardEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Kusa extends AbstractKamiCard {
    public static final String ID = "Onmyoji:Kusa";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public Kusa() {
        super(ID, cardStrings.NAME, null, 1, cardStrings.DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.ONMYOJI_COLOR, CardRarity.UNCOMMON, CardTarget.SELF,
                new com.github.paopaoyue.onmyojimod.object.kami.Kusa(), 1);
        this.baseMagicNumber = 2;
        this.magicNumber = baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SwitchKamiAction(this, getKami(), getHp()));
    }


    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeKami();
            this.upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy() {
        return new Kusa();
    }
}
