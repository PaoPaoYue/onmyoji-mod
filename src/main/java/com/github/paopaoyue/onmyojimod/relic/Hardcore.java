package com.github.paopaoyue.onmyojimod.relic;

import basemod.abstracts.CustomRelic;
import com.github.paopaoyue.onmyojimod.character.Sanme;
import com.github.paopaoyue.onmyojimod.object.kami.KamiManager;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Hardcore extends CustomRelic {
    public static final String ID = "Onmyoji:Hardcore";
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final RelicTier TIER = RelicTier.STARTER;
    private static final LandingSound SOUND = LandingSound.HEAVY;


    public Hardcore() {
        super(ID, ImageMaster.loadImage("image/icon/hardcore.png"), TIER, SOUND);
    }

    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStart() {
        if (AbstractDungeon.player instanceof Sanme) {
            KamiManager kamiManager = ((Sanme) AbstractDungeon.player).getKamiManager();
            if (kamiManager.isHasKami()) {
                kamiManager.setHp(kamiManager.getBaseHp());
            }
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Hardcore();
    }
}
