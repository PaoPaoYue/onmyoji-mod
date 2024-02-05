package com.github.paopaoyue.onmyojimod.object.kami;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public abstract class AbstractKami {

    public static final AbstractKami ONMYOJI_NONE = null;

    protected String id;
    protected String name;
    protected Faction faction;

    protected String description;

    protected boolean upgraded = false;

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

    public boolean isUpgraded() {
        return upgraded;
    }

    public void onEndOfTurn() {
    }

    public void onEnter() {
    }

    public void onExit(AbstractKami nextKami) {
    }

    public int onRollDice(int dice) {
        return dice;
    }

    public void onDivine(int amount) {
    }

    public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
        return damage;
    }

    public void onDamaged(int damage, DamageInfo.DamageType damageType) {
    }

    public void onHeal(int amount) {
    }

    public int onAttackPreDecrementBlock(DamageInfo info, int damageAmount, AbstractCreature target) {
        return damageAmount;
    }

    public abstract String getCharacterImage();

    public abstract void updateDescription();

    public void upgrade() {
        this.upgraded = true;
        updateDescription();
    }

    @Override
    public String toString() {
        return "AbstractKami{" +
                "ID='" + id + '\'' +
                ", faction=" + faction +
                "} " + super.toString();
    }
}
