package com.github.paopaoyue.onmyojimod.card;

import basemod.abstracts.CustomCard;
import com.github.paopaoyue.onmyojimod.action.ExhaustOneCardAndCallbackAction;
import com.github.paopaoyue.onmyojimod.patch.AbstractCardEnum;
import com.github.paopaoyue.onmyojimod.power.CatWuwanPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class CatWuwan extends CustomCard {
    public static final String ID = "Onmyoji:Cat Wuwan";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public CatWuwan() {
        super(ID, cardStrings.NAME, Util.getImagePath(ID), 1, cardStrings.DESCRIPTION, CardType.POWER,
                AbstractCardEnum.ONMYOJI_COLOR, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ExhaustOneCardAndCallbackAction((card) -> {
            for (AbstractPower power : p.powers) {
                if (power instanceof CatWuwanPower) {
                    ((CatWuwanPower) power).setCard(card);
                }
            }
            this.addToTop(new ApplyPowerAction(p, p, new CatWuwanPower(p, card, this.magicNumber)));
        }, (card) -> card.rarity == CardRarity.SPECIAL));

    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        for (AbstractCard card : p.hand.group) {
            if (card.rarity == CardRarity.SPECIAL) {
                return true;
            }
        }
        return false;
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(2);
        }
    }

    public AbstractCard makeCopy() {
        return new CatWuwan();
    }
}
