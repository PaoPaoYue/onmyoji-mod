package com.github.paopaoyue.onmyojimod.power;

import com.badlogic.gdx.graphics.Texture;
import com.github.paopaoyue.onmyojimod.character.Sanme;
import com.github.paopaoyue.onmyojimod.object.kami.KamiManager;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class UnyieldingPower extends AbstractPower {
    public static final String POWER_ID = "Onmyoji:Unyielding";
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = ImageMaster.loadImage("image/icon/unyielding.png");

    public UnyieldingPower(AbstractCreature owner, int amount) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = IMG;
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.canGoNegative = false;
        updateDescription();
    }

    public int onKamiDamaged(int damage, DamageInfo.DamageType damageType) {
        if (this.owner instanceof Sanme) {
            KamiManager kamiManager = ((Sanme) this.owner).getKamiManager();
            if (kamiManager.getHp() <= damage) {
                this.addToTop(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
                return kamiManager.getHp() > 1 ? kamiManager.getHp() - 1 : damage;
            }
        }
        return damage;
    }

    public void updateDescription() {
        this.description = strings.DESCRIPTIONS[0];
    }

}
