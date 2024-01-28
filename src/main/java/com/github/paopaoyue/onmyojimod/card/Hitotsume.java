package com.github.paopaoyue.onmyojimod.card;

import com.github.paopaoyue.onmyojimod.action.SwitchKamiAction;
import com.github.paopaoyue.onmyojimod.patch.AbstractCardEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Hitotsume extends AbstractKamiCard {
    public static final String ID = "Onmyoji:Hitotsume";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public Hitotsume() {
        super(ID, cardStrings.NAME, null, 2, cardStrings.DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.ONMYOJI_COLOR, CardRarity.UNCOMMON, CardTarget.SELF,
                new com.github.paopaoyue.onmyojimod.object.kami.Hitotsume(), 12);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SwitchKamiAction(this, getKami(), getHp()));
    }


    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeHp(4);
        }
    }

    public AbstractCard makeCopy() {
        return new Hitotsume();
    }
}
