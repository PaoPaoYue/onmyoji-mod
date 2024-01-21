package com.github.paopaoyue.onmyojimod.object.kami;

import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.github.paopaoyue.onmyojimod.OnmyojiMod;

public class Menreiki extends AbstractKami {

    private static final Keyword kamiString = OnmyojiMod.MOD_DICTIONARY.get("Onmyoji:Menreiki");

    public Menreiki() {
        this.id = kamiString.ID;
        this.name = kamiString.NAMES[0];
        this.faction = Faction.BLUE;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = kamiString.DESCRIPTION;
    }

    @Override
    public void upgrade() {

    }

    public static class Red extends Menreiki {
        private static final Keyword kamiString = OnmyojiMod.MOD_DICTIONARY.get("Onmyoji:Menreiki Red");

        public Red() {
            this.id = kamiString.ID;
            this.name = kamiString.NAMES[0];
            this.faction = Faction.RED;
            this.updateDescription();
        }
    }

    public static class Green extends Menreiki {
        private static final Keyword kamiString = OnmyojiMod.MOD_DICTIONARY.get("Onmyoji:Menreiki Green");

        public Green() {
            this.id = kamiString.ID;
            this.name = kamiString.NAMES[0];
            this.faction = Faction.GREEN;
            this.updateDescription();
        }
    }

    public static class Blue extends Menreiki {
        private static final Keyword kamiString = OnmyojiMod.MOD_DICTIONARY.get("Onmyoji:Menreiki Blue");

        public Blue() {
            this.id = kamiString.ID;
            this.name = kamiString.NAMES[0];
            this.faction = Faction.BLUE;
            this.updateDescription();
        }
    }

    public static class Purple extends Menreiki {
        private static final Keyword kamiString = OnmyojiMod.MOD_DICTIONARY.get("Onmyoji:Menreiki Purple");

        public Purple() {
            this.id = kamiString.ID;
            this.name = kamiString.NAMES[0];
            this.faction = Faction.PURPLE;
            this.updateDescription();
        }
    }
}
