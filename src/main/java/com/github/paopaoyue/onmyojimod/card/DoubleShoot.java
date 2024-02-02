package com.github.paopaoyue.onmyojimod.card;

import basemod.abstracts.CustomCard;
import com.github.paopaoyue.onmyojimod.action.PlayTmpCardAction;
import com.github.paopaoyue.onmyojimod.patch.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;

public class DoubleShoot extends CustomCard {
    public static final String ID = "Onmyoji:Double Shoot";
    private static final CardStrings cardStrings;
    private static final int BASE_DAMAGE = 4;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    private boolean once;

    public DoubleShoot() {
        super(ID, cardStrings.NAME, (String) null, 1, cardStrings.DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.ONMYOJI_COLOR, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = BASE_DAMAGE;
        this.baseMagicNumber = 5;
        this.magicNumber = baseMagicNumber;
        this.once = false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new VFXAction(new ThrowDaggerEffect(m.hb.cX, m.hb.cY)));
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
        if (!once) {
            DoubleShoot copy = (DoubleShoot) this.makeStatEquivalentCopy();
            copy.once = true;
            this.addToBot(new PlayTmpCardAction(copy, m, true));
        }
    }


    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
        }
    }

    public AbstractCard makeCopy() {
        return new DoubleShoot();
    }
}
