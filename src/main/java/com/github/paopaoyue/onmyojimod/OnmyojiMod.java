package com.github.paopaoyue.onmyojimod;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.github.paopaoyue.onmyojimod.character.OnmyojiCharacter;
import com.github.paopaoyue.onmyojimod.patch.AbstractCardEnum;
import com.github.paopaoyue.onmyojimod.patch.PlayerClassEnum;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;

@SpireInitializer
@SuppressWarnings("unused")
public class OnmyojiMod implements EditCharactersSubscriber, EditStringsSubscriber, EditKeywordsSubscriber, EditRelicsSubscriber, EditCardsSubscriber, OnStartBattleSubscriber, PostBattleSubscriber {

    public static final Logger logger = LogManager.getLogger(OnmyojiMod.class);

    public static final String MOD_ID = "Onmyoji";

    public OnmyojiMod() {
        logger.info("instantiating OnmyojiMod");
        BaseMod.subscribe(this);
        BaseMod.addColor(AbstractCardEnum.ONMYOJI_COLOR, Color.SKY, Color.SKY, Color.SKY, Color.SKY, Color.SKY, Color.SKY, Color.SKY,
                "image/512/bg_attack.png", "image/512/bg_skill.png", "image/512/bg_power.png",
                "image/512/energy_orb.png", "image/1024/bg_attack.png",
                "image/1024/bg_skill.png", "image/1024/bg_power.png",
                "image/1024/energy_orb.png");
    }

    public static void initialize() {
        new OnmyojiMod();
    }

    @Override
    public void receiveEditCards() {
        new AutoAdd(MOD_ID)
                .setDefaultSeen(true)
                .cards();
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new OnmyojiCharacter(),
                "image/character_select/button.png",
                "image/character_select/portrait.png", PlayerClassEnum.ONMYOJI);
    }

    @Override
    public void receiveEditKeywords() {
        String language;
        switch (Settings.language) {
            case ZHS:
                language = "zhs";
                break;
            default:
                language = "eng";
                break;
        }
        Gson gson = new Gson();
        String json = Gdx.files.internal("localization/" + language + "/onmyoji_keywords.json").readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = (Keyword[]) gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                logger.info("Adding Keyword - " + keyword.PROPER_NAME + " | " + keyword.NAMES[0]);
                BaseMod.addKeyword(keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(MOD_ID)
                .any(CustomRelic.class, (info, relic) -> {
                    BaseMod.addRelicToCustomPool(relic, AbstractCardEnum.ONMYOJI_COLOR);
                    if (info.seen) {
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                    }
                });
    }

    @Override
    public void receiveEditStrings() {
        String language;
        switch (Settings.language) {
            case ZHS:
                language = "zhs";
                break;
            default:
                language = "eng";
                break;
        }
        BaseMod.loadCustomStringsFile(CharacterStrings.class, "localization/" + language + "/onmyoji_characters.json");
        BaseMod.loadCustomStringsFile(StanceStrings.class, "localization/" + language + "/onmyoji_kami.json");
        BaseMod.loadCustomStringsFile(CardStrings.class, "localization/" + language + "/onmyoji_cards.json");
//        BaseMod.loadCustomStringsFile(PowerStrings.class, "localization/" + language + "/Power-Strings.json");
//        BaseMod.loadCustomStringsFile(OrbStrings.class, "localization/" + language + "/Orb-Strings.json");
//        BaseMod.loadCustomStringsFile(RelicStrings.class, "localization/" + language + "/Relic-Strings.json");
//        BaseMod.loadCustomStringsFile(UIStrings.class, "localization/" + language + "/UI-Strings.json");
//        BaseMod.loadCustomStringsFile(EventStrings.class, "localization/" + language + "/Event-Strings.json");
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {

    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {

    }

}
