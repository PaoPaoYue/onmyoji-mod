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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Taiji extends CustomCard {
    public static final String ID = "Onmyoji:Tai Ji";
    private static final CardStrings cardStrings;
    private static final int BASE_DAMAGE = 6;
    private static Method refreshDamageMethod;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    static {
        try {
            refreshDamageMethod = AbstractMonster.class.getDeclaredMethod("calculateDamage", int.class);
            refreshDamageMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public Taiji() {
        super(ID, cardStrings.NAME, Util.getImagePath(ID), 1, cardStrings.DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.ONMYOJI_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = BASE_DAMAGE;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster mo) {
        this.calculateCardDamage(mo);
        this.addToBot(new DamageAction(mo, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
        if (mo != null && (mo.intent == AbstractMonster.Intent.ATTACK || mo.intent == AbstractMonster.Intent.ATTACK_BUFF || mo.intent == AbstractMonster.Intent.ATTACK_DEBUFF || mo.intent == AbstractMonster.Intent.ATTACK_DEFEND)) {
            mo.setIntentBaseDmg(this.baseDamage);
            try {
                refreshDamageMethod.invoke(mo, this.baseDamage);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
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
