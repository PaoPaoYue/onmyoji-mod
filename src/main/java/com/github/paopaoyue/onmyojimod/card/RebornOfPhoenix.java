package com.github.paopaoyue.onmyojimod.card;

import basemod.abstracts.CustomCard;
import com.github.paopaoyue.onmyojimod.character.Sanme;
import com.github.paopaoyue.onmyojimod.object.kami.KamiManager;
import com.github.paopaoyue.onmyojimod.patch.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class RebornOfPhoenix extends CustomCard {
    public static final String ID = "Onmyoji:Reborn of Phoenix";
    private static final CardStrings cardStrings;
    private static final int BASE_DAMAGE = 16;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public RebornOfPhoenix() {
        super(ID, cardStrings.NAME, (String) null, 3, cardStrings.DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.ONMYOJI_COLOR, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.baseDamage = BASE_DAMAGE;
        this.isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.calculateCardDamage(m);
        this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public void calculateCardDamage(final AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        if (AbstractDungeon.player instanceof Sanme) {
            KamiManager kamiManager = ((Sanme) AbstractDungeon.player).getKamiManager();
            if (kamiManager.hasDeadInBattle()) {
                this.baseDamage *= 2;
            }
        }
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
    }

    public void triggerOnGlowCheck() {
        if (AbstractDungeon.player instanceof Sanme) {
            KamiManager kamiManager = ((Sanme) AbstractDungeon.player).getKamiManager();
            if (kamiManager.hasDeadInBattle()) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
                return;
            }
        }
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
    }


    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(5);
        }
    }

    public AbstractCard makeCopy() {
        return new RebornOfPhoenix();
    }
}
