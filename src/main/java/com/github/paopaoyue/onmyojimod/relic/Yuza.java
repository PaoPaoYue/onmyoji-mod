package com.github.paopaoyue.onmyojimod.relic;

import basemod.abstracts.CustomRelic;
import com.github.paopaoyue.onmyojimod.card.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class Yuza extends CustomRelic {
    public static final String ID = "Onmyoji:Yuza";
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final RelicTier TIER = RelicTier.BOSS;
    private static final LandingSound SOUND = LandingSound.FLAT;

    private static final int GOLD_AMOUNT = 80;
    private static final CardGroup cardGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    static {
        cardGroup.group.add(new Kiyohime());
        cardGroup.group.add(new Hitotsume());
        cardGroup.group.add(new Akaname());
        cardGroup.group.add(new Enmusubi());
        cardGroup.group.add(new Yamausagi());
        cardGroup.group.add(new Kusa());
        cardGroup.group.add(new Komatsu());
        cardGroup.group.add(new Itsumade());
        cardGroup.group.add(new ShutenDoji());
        cardGroup.group.add(new YotoHime());
        cardGroup.group.add(new Momo());
        cardGroup.group.add(new Shiranui());
        cardGroup.group.add(new Bokku());
        cardGroup.group.add(new Onikiri());
        cardGroup.group.add(new Hangan());
        cardGroup.group.add(new Dodomeki());
    }

    private boolean cardSelected = true;

    public Yuza() {
        super(ID, ImageMaster.loadImage("image/icon/yuza.png"), TIER, SOUND);
    }

    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0] + GOLD_AMOUNT + strings.DESCRIPTIONS[1];
    }

    @Override
    public void onEquip() {
        this.cardSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        AbstractDungeon.gridSelectScreen.open(cardGroup, 1, this.DESCRIPTIONS[2], false, false, false, false);
    }

    @Override
    public void update() {
        super.update();
        if (!this.cardSelected && AbstractDungeon.gridSelectScreen.selectedCards.size() == 1) {
            this.cardSelected = true;
            final AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0).makeStatEquivalentCopy();
            c.inBottleFlame = false;
            c.inBottleLightning = false;
            c.inBottleTornado = false;
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
            AbstractDungeon.player.gainGold(GOLD_AMOUNT);
            AbstractDungeon.effectList.add(new RainingGoldEffect(GOLD_AMOUNT * 2, true));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Yuza();
    }
}
