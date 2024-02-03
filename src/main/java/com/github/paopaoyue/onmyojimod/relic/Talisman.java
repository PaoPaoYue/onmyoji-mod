package com.github.paopaoyue.onmyojimod.relic;

import basemod.abstracts.CustomRelic;
import com.github.paopaoyue.onmyojimod.action.DivineAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Talisman extends CustomRelic {
    public static final String ID = "Onmyoji:Talisman";
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final RelicTier TIER = RelicTier.RARE;
    private static final LandingSound SOUND = LandingSound.FLAT;

    public Talisman() {
        super(ID, ImageMaster.loadImage("image/icon/talisman.png"), TIER, SOUND);
    }

    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0];
    }

    public void onDivine(DivineAction action) {
        for (AbstractCard card : action.getSelectedCards()) {
            if (card.type == AbstractCard.CardType.CURSE || card.type == AbstractCard.CardType.STATUS) {
                card.current_x = CardGroup.DRAW_PILE_X;
                card.current_y = CardGroup.DRAW_PILE_Y;
                card.target_x = card.current_x;
                card.target_y = card.current_y;
                card.drawScale = 0.01F;
                card.targetDrawScale = 0.01F;
                this.addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.drawPile));
            }
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Talisman();
    }
}
