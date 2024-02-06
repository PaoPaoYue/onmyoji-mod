package com.github.paopaoyue.onmyojimod.card;

import basemod.abstracts.CustomCard;
import com.github.paopaoyue.onmyojimod.patch.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;

import java.util.HashMap;

import static com.github.paopaoyue.onmyojimod.utility.Reflect.getPrivate;

public class Taiji extends CustomCard {
    public static final String ID = "Onmyoji:Tai Ji";
    private static final CardStrings cardStrings;
    private static final int BASE_DAMAGE = 6;
    private static final HashMap<AbstractCreature, Integer> damageReplacement = new HashMap<>();

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public Taiji() {
        super(ID, cardStrings.NAME, Util.getImagePath(ID), 1, cardStrings.DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.ONMYOJI_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = BASE_DAMAGE;
        this.exhaust = true;
    }

    public static void putDamageReplacement(AbstractCreature mo, int targetAmount) {
        damageReplacement.put(mo, targetAmount);
    }

    public static HashMap<AbstractCreature, Integer> getDamageReplacement() {
        return damageReplacement;
    }

    public void use(AbstractPlayer p, AbstractMonster mo) {
        this.calculateCardDamage(mo);
        this.addToBot(new DamageAction(mo, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
        if (mo != null && (mo.intent == AbstractMonster.Intent.ATTACK || mo.intent == AbstractMonster.Intent.ATTACK_BUFF || mo.intent == AbstractMonster.Intent.ATTACK_DEBUFF || mo.intent == AbstractMonster.Intent.ATTACK_DEFEND)) {
            EnemyMoveInfo move = getPrivate(AbstractMonster.class, mo, "move", EnemyMoveInfo.class);
            if (move != null) {
                move.baseDamage = this.baseDamage;
                mo.createIntent();
                putDamageReplacement(mo, mo.getIntentDmg());
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
