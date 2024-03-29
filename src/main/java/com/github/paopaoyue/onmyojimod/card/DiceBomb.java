package com.github.paopaoyue.onmyojimod.card;

import basemod.abstracts.CustomCard;
import com.github.paopaoyue.onmyojimod.action.RollDiceAction;
import com.github.paopaoyue.onmyojimod.patch.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlickCoinEffect;

public class DiceBomb extends CustomCard {
    public static final String ID = "Onmyoji:Dice Bomb";
    private static final CardStrings cardStrings;
    private static final int BASE_DAMAGE = 0;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public DiceBomb() {
        super(ID, cardStrings.NAME, Util.getImagePath(ID), 1, cardStrings.DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.ONMYOJI_COLOR, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = BASE_DAMAGE;
        this.baseMagicNumber = 3;
        this.magicNumber = baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        RollDiceAction action = new RollDiceAction();
        this.addToBot(action);

        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * action.amount;
        super.calculateCardDamage(m);
        this.baseDamage = realBaseDamage;
        this.addToBot(new VFXAction(new FlickCoinEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY), 0.3f));
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
    }


    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy() {
        return new DiceBomb();
    }
}
