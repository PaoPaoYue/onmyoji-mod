package com.github.paopaoyue.onmyojimod.card;

import basemod.abstracts.CustomCard;
import com.github.paopaoyue.onmyojimod.patch.AbstractCardEnum;
import com.github.paopaoyue.onmyojimod.patch.kami.KamiManager;
import com.github.paopaoyue.onmyojimod.patch.kami.KamiManagerField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SevenSides extends CustomCard {
    public static final String ID = "Onmyoji:Seven Sides";
    private static final CardStrings cardStrings;
    private static final int BASE_DAMAGE = 7;
    private static final int BASE_DAMAGE_ADD_ON = 4;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Onmyoji:Seven Sides");
    }

    public SevenSides() {
        super(ID, cardStrings.NAME, "image/card/menreiki.png", 1, cardStrings.DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.ONMYOJI_COLOR, CardRarity.BASIC, CardTarget.ENEMY);
        this.baseDamage = 7;
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        KamiManager kamiManager = (KamiManager) KamiManagerField.kamiManager.get(AbstractDungeon.player);
        int kamiNum = kamiManager.getDiffKamiSwitchCountInBattle();
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    public void applyPowers() {
        KamiManager kamiManager = (KamiManager) KamiManagerField.kamiManager.get(AbstractDungeon.player);
        int kamiNum = kamiManager.getDiffKamiSwitchCountInBattle();
        if (this.upgraded) {
            this.baseDamage = BASE_DAMAGE + 1 + (BASE_DAMAGE_ADD_ON + 2) * kamiNum;
        } else {
            this.baseDamage = BASE_DAMAGE + BASE_DAMAGE_ADD_ON * kamiNum;
        }
        super.applyPowers();
    }


    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(1);
            this.upgradeMagicNumber(2);
        }
    }

    public AbstractCard makeCopy() {
        return new SevenSides();
    }
}
