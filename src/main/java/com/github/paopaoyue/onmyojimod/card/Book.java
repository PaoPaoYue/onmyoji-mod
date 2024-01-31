package com.github.paopaoyue.onmyojimod.card;

import basemod.abstracts.CustomCard;
import com.github.paopaoyue.onmyojimod.patch.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;

public class Book extends CustomCard {
    public static final String ID = "Onmyoji:Book";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public Book() {
        super(ID, cardStrings.NAME, (String) null, 0, cardStrings.DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.ONMYOJI_COLOR, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        List<AbstractCard> list = new ArrayList<>(16);
        list.add(new Kiyohime());
        list.add(new Hitotsume());
        list.add(new Akaname());
        list.add(new Enmusubi());
        list.add(new Yamausagi());
        list.add(new Kusa());
        list.add(new Itsumade());
        list.add(new ShutenDoji());
        list.add(new YotoHime());
        list.add(new Momo());
        list.add(new Shiranui());
        list.add(new Bokku());
        list.add(new Onikiri());
        list.add(new Hangan());
        list.add(new Dodomeki());
        final AbstractCard c = list.get(cardRandomRng.random(list.size() - 1));
        if (upgraded)
            c.setCostForTurn(0);
        this.addToBot(new MakeTempCardInHandAction(c, true));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public AbstractCard makeCopy() {
        return new Book();
    }
}
