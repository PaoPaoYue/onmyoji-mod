package com.github.paopaoyue.onmyojimod.card;

import basemod.abstracts.CustomCard;
import com.github.paopaoyue.onmyojimod.character.Sanme;
import com.github.paopaoyue.onmyojimod.object.kami.AbstractKami;
import com.github.paopaoyue.onmyojimod.object.kami.KamiManager;
import com.github.paopaoyue.onmyojimod.patch.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.stream.Collectors;

public class SevenSides extends CustomCard {
    public static final String ID = "Onmyoji:Seven Sides";
    private static final CardStrings cardStrings;
    private static final int BASE_DAMAGE = 8;
    private static final int BASE_DAMAGE_ADD_ON = 4;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public SevenSides() {
        super(ID, cardStrings.NAME, (String) null, 1, cardStrings.DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.ONMYOJI_COLOR, CardRarity.BASIC, CardTarget.ENEMY);
        this.baseDamage = BASE_DAMAGE;
        this.baseMagicNumber = BASE_DAMAGE_ADD_ON;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    public void applyPowers() {
        if (AbstractDungeon.player instanceof Sanme) {
            KamiManager kamiManager = ((Sanme) AbstractDungeon.player).getKamiManager();
            int kamiNum = kamiManager.getKamiSequenceInBattle().stream().filter(x -> x != AbstractKami.ONMYOJI_NONE && x.getId().startsWith(Menreiki.ID)).collect(Collectors.toSet()).size();
            if (this.upgraded) {
                this.baseDamage = BASE_DAMAGE + (BASE_DAMAGE_ADD_ON + 1) * kamiNum;
            } else {
                this.baseDamage = BASE_DAMAGE + BASE_DAMAGE_ADD_ON * kamiNum;
            }
        }
        super.applyPowers();
    }


    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(1);
            this.upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy() {
        return new SevenSides();
    }
}
