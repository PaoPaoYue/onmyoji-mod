package com.github.paopaoyue.onmyojimod.kami;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.StanceStrings;

public class Menreiki extends AbstractKami {

    private static final String ID = "Menreiki";
    private static final StanceStrings kamiString = CardCrawlGame.languagePack.getStanceString("Onmyoji:Menreiki");

    public Menreiki() {
        this.id = ID;
        this.name = kamiString.NAME;
        this.faction = Faction.BLUE;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = kamiString.DESCRIPTION[0];
    }

    @Override
    public void upgrade() {

    }

    public static class Red extends Menreiki {
        private static final String ID = "Menreiki Red";
        private static final StanceStrings kamiString = CardCrawlGame.languagePack.getStanceString("Onmyoji:Menreiki Red");

        public Red() {
            this.id = ID;
            this.name = kamiString.NAME;
            this.faction = Faction.RED;
            this.updateDescription();
        }
    }

    public static class Green extends Menreiki {
        private static final String ID = "Menreiki Green";
        private static final StanceStrings kamiString = CardCrawlGame.languagePack.getStanceString("Onmyoji:Menreiki Green");

        public Green() {
            this.id = ID;
            this.name = kamiString.NAME;
            this.faction = Faction.GREEN;
            this.updateDescription();
        }
    }

    public static class Blue extends Menreiki {
        private static final String ID = "Menreiki Blue";
        private static final StanceStrings kamiString = CardCrawlGame.languagePack.getStanceString("Onmyoji:Menreiki Blue");

        public Blue() {
            this.id = ID;
            this.name = kamiString.NAME;
            this.faction = Faction.BLUE;
            this.updateDescription();
        }
    }

    public static class Purple extends Menreiki {
        private static final String ID = "Menreiki Purple";
        private static final StanceStrings kamiString = CardCrawlGame.languagePack.getStanceString("Onmyoji:Menreiki Purple");

        public Purple() {
            this.id = ID;
            this.name = kamiString.NAME;
            this.faction = Faction.PURPLE;
            this.updateDescription();
        }
    }
}
