package com.github.paopaoyue.onmyojimod.card;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class KamiHp extends DynamicVariable {

    public KamiHp() {
    }

    @Override
    public String key() {
        return "K";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractKamiCard) {
            return ((AbstractKamiCard) card).isHpModified();
        }
        return false;
    }

    @Override
    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractKamiCard) {
            ((AbstractKamiCard) card).setHpModified(v);
        }
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractKamiCard) {
            return ((AbstractKamiCard) card).getHp();
        }
        return 0;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractKamiCard) {
            return ((AbstractKamiCard) card).getBaseHp();
        }
        return 0;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractKamiCard) {
            return ((AbstractKamiCard) card).isHpUpgraded();
        }
        return false;
    }
}
