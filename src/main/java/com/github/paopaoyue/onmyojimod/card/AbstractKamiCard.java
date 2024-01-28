package com.github.paopaoyue.onmyojimod.card;

import basemod.abstracts.CustomCard;
import com.github.paopaoyue.onmyojimod.object.kami.AbstractKami;
import com.github.paopaoyue.onmyojimod.object.spirit.AbstractSpirit;
import com.github.paopaoyue.onmyojimod.object.spirit.Enhancement;
import com.github.paopaoyue.onmyojimod.patch.spirit.SpiritField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractKamiCard extends CustomCard {

    private static final Logger logger = LogManager.getLogger(AbstractKamiCard.class.getName());

    private AbstractKami kami;

    private int baseHp;

    private int hp;

    private boolean hpUpgraded;

    private boolean hpModified;


    public AbstractKamiCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target, AbstractKami kami, int baseKamiHp) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
        this.kami = kami;
        this.baseHp = baseKamiHp;
        this.hp = this.baseHp;
    }

    public AbstractKami getKami() {
        return kami;
    }

    public int getBaseHp() {
        return baseHp;
    }

    public int getHp() {
        return hp;
    }

    public boolean isHpUpgraded() {
        return hpUpgraded;
    }

    public boolean isHpModified() {
        return hpModified;
    }

    public void setHpModified(boolean v) {
        this.hpModified = v;
    }

    public void modifyBaseHp(int amount) {
        this.baseHp = this.baseHp + amount;
        this.hp = this.baseHp;
        this.hpModified = true;
    }

    public void modifyHp(int amount) {
        this.hp = this.baseHp + amount;
        this.hpModified = true;
    }

    public void upgradeKami() {
        this.kami.upgrade();
    }

    public void upgradeHp(int amount) {
        this.baseHp += amount;
        this.hp = this.baseHp;
        this.hpUpgraded = true;
    }

    @Override
    public void applyPowers() {
        AbstractSpirit spirit = SpiritField.spirit.get(this);
        if (spirit instanceof Enhancement) {
            modifyHp(Enhancement.HP_BUFF_AMOUNT);
        }
        super.applyPowers();
    }

}