package com.github.paopaoyue.onmyojimod.card;

import basemod.abstracts.CustomCard;
import com.github.paopaoyue.onmyojimod.patch.AbstractCardEnum;
import com.github.paopaoyue.onmyojimod.power.WindOfDragonPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WindOfDragon extends CustomCard {
    public static final String ID = "Onmyoji:Wind Of Dragon";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public WindOfDragon() {
        super(ID, cardStrings.NAME, Util.getImagePath(ID), 2, cardStrings.DESCRIPTION, CardType.POWER,
                AbstractCardEnum.ONMYOJI_COLOR, CardRarity.RARE, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new WindOfDragonPower(p)));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.isInnate = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public AbstractCard makeCopy() {
        return new WindOfDragon();
    }
}
