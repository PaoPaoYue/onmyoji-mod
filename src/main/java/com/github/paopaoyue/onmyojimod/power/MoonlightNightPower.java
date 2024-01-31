package com.github.paopaoyue.onmyojimod.power;

import com.badlogic.gdx.graphics.Texture;
import com.github.paopaoyue.onmyojimod.card.BlueForce;
import com.github.paopaoyue.onmyojimod.card.GreenForce;
import com.github.paopaoyue.onmyojimod.card.PurpleForce;
import com.github.paopaoyue.onmyojimod.card.RedForce;
import com.github.paopaoyue.onmyojimod.character.Sanme;
import com.github.paopaoyue.onmyojimod.object.kami.Faction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.HashSet;
import java.util.Set;

public class MoonlightNightPower extends AbstractPower {
    public static final String POWER_ID = "Onmyoji:Moonlight Night";
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = ImageMaster.loadImage("image/icon/moonlight_night.png");

    private Set<Faction> factionSet = new HashSet<>();

    public MoonlightNightPower(AbstractCreature owner) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = IMG;
        this.type = PowerType.BUFF;
        this.amount = -1;
        this.canGoNegative = false;
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
            if (owner instanceof Sanme && ((Sanme) owner).getKamiManager().isHasKami()) {
                switch (((Sanme) owner).getKamiManager().getCurrentFaction()) {
                    case RED:
                        if (!factionSet.contains(Faction.RED)) {
                            this.addToBot(new MakeTempCardInHandAction(new RedForce()));
                            factionSet.add(Faction.RED);
                        }
                        break;
                    case GREEN:
                        if (!factionSet.contains(Faction.GREEN)) {
                            this.addToBot(new MakeTempCardInHandAction(new GreenForce()));
                            factionSet.add(Faction.GREEN);
                        }
                        break;
                    case BLUE:
                        if (!factionSet.contains(Faction.BLUE)) {
                            this.addToBot(new MakeTempCardInHandAction(new BlueForce()));
                            factionSet.add(Faction.BLUE);
                        }
                        break;
                    case PURPLE:
                        if (!factionSet.contains(Faction.PURPLE)) {
                            this.addToBot(new MakeTempCardInHandAction(new PurpleForce()));
                            factionSet.add(Faction.PURPLE);
                        }
                        break;
                }
            }
        }
    }

    public void updateDescription() {
        this.description = strings.DESCRIPTIONS[0];
    }

}
