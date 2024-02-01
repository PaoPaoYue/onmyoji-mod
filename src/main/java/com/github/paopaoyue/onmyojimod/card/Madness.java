package com.github.paopaoyue.onmyojimod.card;

import basemod.abstracts.CustomCard;
import com.github.paopaoyue.onmyojimod.action.MadnessAction;
import com.github.paopaoyue.onmyojimod.patch.AbstractCardEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Madness extends CustomCard {
    public static final String ID = "Onmyoji:Madness";
    private static final CardStrings cardStrings;
    private static final int BASE_DAMAGE = 15;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public Madness() {
        super(ID, cardStrings.NAME, (String) null, 2, cardStrings.DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.ONMYOJI_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = BASE_DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new MadnessAction(m, p, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
    }


    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(4);
        }
    }

    public AbstractCard makeCopy() {
        return new Madness();
    }
}
