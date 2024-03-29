package com.github.paopaoyue.onmyojimod.card;

import com.github.paopaoyue.onmyojimod.action.SwitchKamiAction;
import com.github.paopaoyue.onmyojimod.object.kami.AbstractKami;
import com.github.paopaoyue.onmyojimod.patch.AbstractCardEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Menreiki extends AbstractKamiCard {
    public static final String ID = "Onmyoji:Menreiki";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public Menreiki() {
        super(ID, cardStrings.NAME, Util.getImagePath(ID), 1, cardStrings.DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.ONMYOJI_COLOR, CardRarity.BASIC, CardTarget.SELF,
                new com.github.paopaoyue.onmyojimod.object.kami.Menreiki(), 3);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractKami kami;
        switch (AbstractDungeon.cardRandomRng.random(3)) {
            case 0:
                kami = new com.github.paopaoyue.onmyojimod.object.kami.Menreiki.Red();
                break;
            case 1:
                kami = new com.github.paopaoyue.onmyojimod.object.kami.Menreiki.Green();
                break;
            case 2:
                kami = new com.github.paopaoyue.onmyojimod.object.kami.Menreiki.Blue();
                break;
            default:
                kami = new com.github.paopaoyue.onmyojimod.object.kami.Menreiki.Purple();
        }
        this.addToBot(new SwitchKamiAction(this, kami, getHp()));
    }


    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    public AbstractCard makeCopy() {
        return new Menreiki();
    }
}
