package com.github.paopaoyue.onmyojimod.card;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public static final List<AbstractCard> kamiCardList = new ArrayList<>();

    static {
        kamiCardList.add(new Kiyohime());
        kamiCardList.add(new Hitotsume());
        kamiCardList.add(new Akaname());
        kamiCardList.add(new Enmusubi());
        kamiCardList.add(new Yamausagi());
        kamiCardList.add(new Kusa());
        kamiCardList.add(new Komatsu());
        kamiCardList.add(new Itsumade());
        kamiCardList.add(new ShutenDoji());
        kamiCardList.add(new YotoHime());
        kamiCardList.add(new Momo());
        kamiCardList.add(new Shiranui());
        kamiCardList.add(new Bokku());
        kamiCardList.add(new Onikiri());
        kamiCardList.add(new Hangan());
        kamiCardList.add(new Dodomeki());
    }

    static String getImagePath(String cardID) {
        cardID = cardID.replace("Onmyoji:", "");
        StringBuilder sb = new StringBuilder();
        sb.append("image/card/");
        for (int i = 0; i < cardID.length(); i++) {
            if (!Character.isLetter(cardID.charAt(i))) continue;
            if (Character.isUpperCase(cardID.charAt(i))) {
                if (i > 0) {
                    sb.append("_");
                }
                sb.append(Character.toLowerCase(cardID.charAt(i)));
            } else {
                sb.append(cardID.charAt(i));
            }
        }
        sb.append(".png");
        return sb.toString();
    }
}
