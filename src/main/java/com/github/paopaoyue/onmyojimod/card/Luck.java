package com.github.paopaoyue.onmyojimod.card;

import basemod.abstracts.CustomCard;
import com.github.paopaoyue.onmyojimod.action.PlayTmpCardAction;
import com.github.paopaoyue.onmyojimod.action.RollDiceAction;
import com.github.paopaoyue.onmyojimod.patch.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Luck extends CustomCard {
    public static final String ID = "Onmyoji:Luck";
    private static final CardStrings cardStrings;
    private static final int BASE_DAMAGE = 16;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public Luck() {
        super(ID, cardStrings.NAME, Util.getImagePath(ID), 2, cardStrings.DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.ONMYOJI_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = BASE_DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        if (!p.hand.isEmpty()) {
            RollDiceAction action = new RollDiceAction();
            this.addToBot(action);
            if (action.amount >= 4) {
                this.addToBot(new ExhaustAction(1, !upgraded, false, false));
                this.addToBot(new PlayTmpCardAction(this.makeStatEquivalentCopy(), m, true, true));
            }
        }
    }


    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public AbstractCard makeCopy() {
        return new Luck();
    }
}
