package com.github.paopaoyue.onmyojimod.card;

import basemod.abstracts.CustomCard;
import com.github.paopaoyue.onmyojimod.patch.AbstractCardEnum;
import com.github.paopaoyue.onmyojimod.power.PenetratedPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.ScreenOnFireEffect;

public class SnakeFire extends CustomCard {
    public static final String ID = "Onmyoji:Snake Fire";
    private static final CardStrings cardStrings;
    private static final int BASE_DAMAGE = 1;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public SnakeFire() {
        super(ID, cardStrings.NAME, (String) null, -1, cardStrings.DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.ONMYOJI_COLOR, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.baseDamage = BASE_DAMAGE;
        this.baseMagicNumber = 3;
        this.magicNumber = baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }
        if (p.hasRelic("Chemical X")) {
            effect += 2;
            p.getRelic("Chemical X").flash();
        }
        if (effect > 0) {
            this.addToBot(new VFXAction(p, new ScreenOnFireEffect(), 1.0f));
            for (int i = 0; i < effect; ++i) {
                for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (m.isDead) continue;
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new PenetratedPower(mo, this.magicNumber)));
                }
                this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
            }
        }
    }


    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(1);
        }
    }

    public AbstractCard makeCopy() {
        return new SnakeFire();
    }
}
