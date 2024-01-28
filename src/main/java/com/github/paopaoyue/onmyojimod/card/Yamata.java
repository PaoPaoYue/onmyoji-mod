package com.github.paopaoyue.onmyojimod.card;

import com.github.paopaoyue.onmyojimod.action.SwitchKamiAction;
import com.github.paopaoyue.onmyojimod.patch.AbstractCardEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Yamata extends AbstractKamiCard {
    public static final String ID = "Onmyoji:Yamata";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public Yamata() {
        super(ID, cardStrings.NAME, null, 0, cardStrings.DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.ONMYOJI_COLOR, CardRarity.SPECIAL, CardTarget.SELF,
                new com.github.paopaoyue.onmyojimod.object.kami.Yamata(), 3);
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SwitchKamiAction(this, getKami(), getHp()));
    }


    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeHp(1);
        }
    }

    public AbstractCard makeCopy() {
        return new Yamata();
    }
}
