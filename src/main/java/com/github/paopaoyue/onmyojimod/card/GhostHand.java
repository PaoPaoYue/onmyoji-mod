package com.github.paopaoyue.onmyojimod.card;

import basemod.abstracts.CustomCard;
import com.github.paopaoyue.onmyojimod.action.GhostHandAction;
import com.github.paopaoyue.onmyojimod.patch.AbstractCardEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GhostHand extends CustomCard {
    public static final String ID = "Onmyoji:Ghost Hand";
    private static final CardStrings cardStrings;
    private static final int BASE_DAMAGE = 10;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    private boolean once;

    public GhostHand() {
        super(ID, cardStrings.NAME, Util.getImagePath(ID), 1, cardStrings.DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.ONMYOJI_COLOR, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = BASE_DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GhostHandAction(m, p, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
    }


    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
        }
    }

    public AbstractCard makeCopy() {
        return new GhostHand();
    }
}
