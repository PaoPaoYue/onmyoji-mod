package com.github.paopaoyue.onmyojimod.object.spirit;

import com.badlogic.gdx.graphics.Texture;
import com.github.paopaoyue.onmyojimod.patch.spirit.SpiritField;
import com.megacrit.cardcrawl.cards.AbstractCard;

public abstract class AbstractSpirit {
    public static final AbstractSpirit SPIRIT_NONE = null;
    protected static Texture texture = null;
    protected String id;
    protected String name;
    private AbstractCard attachedCard;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Texture getTexture() {
        return texture;
    }

    public AbstractCard getAttachedCard() {
        return attachedCard;
    }

    public void attachToCard(AbstractCard card) {
        SpiritField.spirit.set(card, this);
        this.attachedCard = card;
    }

    public abstract void onDraw();

    public abstract void onUse();

    @Override
    public String toString() {
        return "AbstractSpirit{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
