package com.github.paopaoyue.onmyojimod.card;

import basemod.abstracts.CustomCard;
import com.github.paopaoyue.onmyojimod.patch.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Taiji extends CustomCard {
    public static final String ID = "Onmyoji:Tai ji";
    private static final CardStrings cardStrings;
    private static final int BASE_DAMAGE = 6;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public Taiji() {
        super(ID, cardStrings.NAME, (String) null, 2, cardStrings.DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.ONMYOJI_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = BASE_DAMAGE;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster mo) {
        this.calculateCardDamage(mo);
        this.addToBot(new DamageAction(mo, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
        if (mo != null && (mo.intent == AbstractMonster.Intent.ATTACK || mo.intent == AbstractMonster.Intent.ATTACK_BUFF || mo.intent == AbstractMonster.Intent.ATTACK_DEBUFF || mo.intent == AbstractMonster.Intent.ATTACK_DEFEND)) {
            mo.setIntentBaseDmg(this.baseDamage);
        }
    }

    @Override
    public void calculateCardDamage(final AbstractMonster mo) {
        if (mo != null && (mo.intent == AbstractMonster.Intent.ATTACK || mo.intent == AbstractMonster.Intent.ATTACK_BUFF || mo.intent == AbstractMonster.Intent.ATTACK_DEBUFF || mo.intent == AbstractMonster.Intent.ATTACK_DEFEND)) {
            int realBaseDamage = this.baseDamage;
            this.baseDamage = mo.getIntentBaseDmg();
            super.calculateCardDamage(mo);
            this.baseDamage = realBaseDamage;
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(-4);
        }
    }

    public AbstractCard makeCopy() {
        return new Taiji();
    }
}
