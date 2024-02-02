package com.github.paopaoyue.onmyojimod.card;

import basemod.abstracts.CustomCard;
import com.github.paopaoyue.onmyojimod.character.Sanme;
import com.github.paopaoyue.onmyojimod.patch.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class GoodAndEvil extends CustomCard {
    public static final String ID = "Onmyoji:Good and Evil";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public GoodAndEvil() {
        super(ID, cardStrings.NAME, (String) null, 1, cardStrings.DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.ONMYOJI_COLOR, CardRarity.RARE, CardTarget.SELF);
        this.cardsToPreview = new Yamata();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p instanceof Sanme && ((Sanme) p).getKamiManager().isHasKami()) {
            this.addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));
            this.addToBot(new VFXAction(new LightningEffect(p.hb.cX, p.hb.cY)));
            this.addToBot(new DamageAction(p, new DamageInfo(p, ((Sanme) p).getKamiManager().getHp(), DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE));
            int amount = 11 - p.hand.size();
            if (amount > 0) {
                this.addToBot(new MakeTempCardInHandAction(new Yamata(), amount));
            }
        }
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return p instanceof Sanme && ((Sanme) p).getKamiManager().isHasKami();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    public AbstractCard makeCopy() {
        return new GoodAndEvil();
    }
}
