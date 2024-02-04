package com.github.paopaoyue.onmyojimod.card;

import basemod.abstracts.CustomCard;
import com.github.paopaoyue.onmyojimod.patch.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.Objects;

public class Lotus extends CustomCard {
    public static final String ID = "Onmyoji:Lotus";
    private static final CardStrings cardStrings;
    private static final int MAGIC_NUMBER_ADD_ON = 2;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public Lotus() {
        super(ID, cardStrings.NAME, Util.getImagePath(ID), 1, cardStrings.DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.ONMYOJI_COLOR, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseMagicNumber = -6;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, this.magicNumber), this.magicNumber));
        if (m != null && !m.hasPower("Artifact")) {
            this.addToBot(new ApplyPowerAction(m, p, new GainStrengthPower(m, -this.magicNumber), -this.magicNumber));
        }
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (Objects.equals(c.cardID, this.cardID)) {
            this.baseMagicNumber += -MAGIC_NUMBER_ADD_ON;
            this.magicNumber = this.baseMagicNumber;
        }
        super.onPlayCard(c, m);
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }
    }

    public AbstractCard makeCopy() {
        return new Lotus();
    }
}
