package com.github.paopaoyue.onmyojimod.object.kami;

import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.github.paopaoyue.onmyojimod.OnmyojiMod;

public class Menreiki extends AbstractKami {

    public static final String ID = "Onmyoji:Menreiki";

    public static final String CHARACTER_IMG = "image/character/menreiki.png";
    private static final Keyword kamiString = OnmyojiMod.MOD_DICTIONARY.get(ID);

    public Menreiki() {
        this.id = kamiString.ID;
        this.name = kamiString.NAMES[0];
        this.faction = Faction.BLUE;
        this.updateDescription();
    }

    @Override
    public String getCharacterImage() {
        return CHARACTER_IMG;
    }

    @Override
    public void updateDescription() {
        this.description = kamiString.DESCRIPTION;
    }

    public static class Red extends Menreiki {

        public static final String ID = "Onmyoji:Menreiki Red";
        private static final Keyword kamiString = OnmyojiMod.MOD_DICTIONARY.get(ID);

        public Red() {
            this.id = kamiString.ID;
            this.name = kamiString.NAMES[0];
            this.faction = Faction.RED;
            this.updateDescription();
        }
    }

    public static class Green extends Menreiki {

        public static final String ID = "Onmyoji:Menreiki Green";
        private static final Keyword kamiString = OnmyojiMod.MOD_DICTIONARY.get(ID);

        public Green() {
            this.id = kamiString.ID;
            this.name = kamiString.NAMES[0];
            this.faction = Faction.GREEN;
            this.updateDescription();
        }
    }

    public static class Blue extends Menreiki {

        public static final String ID = "Onmyoji:Menreiki Blue";
        private static final Keyword kamiString = OnmyojiMod.MOD_DICTIONARY.get(ID);

        public Blue() {
            this.id = kamiString.ID;
            this.name = kamiString.NAMES[0];
            this.faction = Faction.BLUE;
            this.updateDescription();
        }
    }

    public static class Purple extends Menreiki {

        public static final String ID = "Onmyoji:Menreiki Purple";
        private static final Keyword kamiString = OnmyojiMod.MOD_DICTIONARY.get(ID);

        public Purple() {
            this.id = kamiString.ID;
            this.name = kamiString.NAMES[0];
            this.faction = Faction.PURPLE;
            this.updateDescription();
        }
    }
}
