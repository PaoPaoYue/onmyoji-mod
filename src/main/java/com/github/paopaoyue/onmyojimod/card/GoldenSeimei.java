package com.github.paopaoyue.onmyojimod.card;

import basemod.abstracts.CustomCard;
import com.github.paopaoyue.onmyojimod.action.ProjectileAction;
import com.github.paopaoyue.onmyojimod.patch.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GoldenSeimei extends CustomCard {
    public static final String ID = "Onmyoji:Golden Seimei";
    private static final CardStrings cardStrings;
    private static final int BASE_DAMAGE = 30;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public GoldenSeimei() {
        super(ID, cardStrings.NAME, Util.getImagePath(ID), -2, cardStrings.DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.ONMYOJI_COLOR, CardRarity.SPECIAL, CardTarget.ENEMY);
        this.baseMagicNumber = BASE_DAMAGE;
        this.magicNumber = baseMagicNumber;
    }

    public void triggerWhenDrawn() {
        this.addToBot(new ProjectileAction(this.magicNumber));
        this.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }


    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(10);
        }
    }

    public AbstractCard makeCopy() {
        return new GoldenSeimei();
    }
}
