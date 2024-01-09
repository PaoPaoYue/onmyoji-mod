package com.github.paopaoyue.onmyojimod.card;

import basemod.abstracts.CustomCard;
import com.github.paopaoyue.onmyojimod.kami.AbstractKami;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractKamiCard extends CustomCard {

    private static final Logger logger = LogManager.getLogger(AbstractKamiCard.class.getName());

    private AbstractKami kami;

    public AbstractKamiCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target, AbstractKami kami, int baseKamiHp) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
        this.kami = kami;
        this.baseMagicNumber = baseKamiHp;
        this.magicNumber = this.baseMagicNumber;
    }

    public AbstractKami getKami() {
        return kami;
    }

    public void setKami(AbstractKami kami) {
        this.kami = kami;
    }

    public int getBaseKamiHp() {
        return this.magicNumber;
    }

    public void setBaseKamiHp(int baseKamiHp) {
        this.magicNumber = baseKamiHp;
    }

    protected void upgradeKami() {
        this.kami.upgrade();
    }

    protected void upgradeBaseKamiHp(int amount) {
        this.upgradeMagicNumber(amount);
    }

}