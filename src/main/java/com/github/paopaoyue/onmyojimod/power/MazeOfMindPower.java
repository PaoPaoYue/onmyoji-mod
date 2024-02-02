package com.github.paopaoyue.onmyojimod.power;

import com.badlogic.gdx.graphics.Texture;
import com.github.paopaoyue.onmyojimod.card.AbstractKamiCard;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class MazeOfMindPower extends AbstractPower {
    public static final String POWER_ID = "Onmyoji:Maze of Mind";
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = ImageMaster.loadImage("image/icon/maze_of_mind.png");


    public MazeOfMindPower(AbstractCreature owner) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = IMG;
        this.type = PowerType.BUFF;
        this.amount = -1;
        this.priority = 0;
        this.canGoNegative = false;
        updateDescription();
    }

    @Override
    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_CONFUSION", 0.05f);
    }

    @Override
    public void onCardDraw(final AbstractCard card) {
        if (card instanceof AbstractKamiCard) {
            card.cost = AbstractDungeon.cardRandomRng.random(0, 3);
            card.costForTurn = card.cost;
            card.isCostModified = true;
            card.freeToPlayOnce = false;
        }
    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (card instanceof AbstractKamiCard) {
            this.flash();
            final ArrayList<AbstractCard> groupCopy = new ArrayList<AbstractCard>();
            for (final AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.cost > 0 && c.costForTurn > 0 && !c.freeToPlayOnce) {
                    groupCopy.add(c);
                }
            }
            for (final CardQueueItem i : AbstractDungeon.actionManager.cardQueue) {
                if (i.card != null) {
                    groupCopy.remove(i.card);
                }
            }
            AbstractCard c2 = null;
            if (!groupCopy.isEmpty()) {
                c2 = groupCopy.get(AbstractDungeon.cardRandomRng.random(0, groupCopy.size() - 1));
            }
            if (c2 != null) {
                c2.setCostForTurn(0);
            }
        }
    }


    public void updateDescription() {
        this.description = strings.DESCRIPTIONS[0];
    }

}
