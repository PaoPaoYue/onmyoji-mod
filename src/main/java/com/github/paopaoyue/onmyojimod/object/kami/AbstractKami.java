package com.github.paopaoyue.onmyojimod.object.kami;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;

public abstract class AbstractKami {

    public static final AbstractKami ONMYOJI_NONE = null;

    protected String id;
    protected String name;
    protected Faction faction;

    protected String description;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Faction getFaction() {
        return faction;
    }

    public String getDescription() {
        return description;
    }

    public void atStartOfTurn() {
    }

    public void onEndOfTurn() {
    }

    public void onEnter() {
    }

    public void onExit() {
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return damage;
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        return this.atDamageGive(damage, type);
    }

    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        return damage;
    }

    public void onPlayCard(AbstractCard card) {
    }

    public abstract String getCharacterImage();

    public abstract void updateDescription();

    public abstract void upgrade();

    @Override
    public String toString() {
        return "AbstractKami{" +
                "ID='" + id + '\'' +
                ", faction=" + faction +
                "} " + super.toString();
    }
}
