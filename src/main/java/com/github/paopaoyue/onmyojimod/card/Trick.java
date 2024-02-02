package com.github.paopaoyue.onmyojimod.card;

import basemod.abstracts.CustomCard;
import com.github.paopaoyue.onmyojimod.patch.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Trick extends CustomCard {
    public static final String ID = "Onmyoji:Trick";
    private static final CardStrings cardStrings;
    private static final int BASE_DAMAGE = 13;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public Trick() {
        super(ID, cardStrings.NAME, (String) null, 2, cardStrings.DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.ONMYOJI_COLOR, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = BASE_DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }

    public void onAfterCardUse(AbstractCard c, AbstractCreature target) {
        if (c.type == CardType.ATTACK && !AbstractDungeon.player.drawPile.isEmpty() && AbstractDungeon.player.drawPile.getTopCard() == this) {
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                return;
            }
            if (target == null) {
                target = AbstractDungeon.getRandomMonster();
            }
            this.addToTop(new PlayTopCardAction(target, false));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(5);
        }
    }

    public AbstractCard makeCopy() {
        return new Trick();
    }
}
