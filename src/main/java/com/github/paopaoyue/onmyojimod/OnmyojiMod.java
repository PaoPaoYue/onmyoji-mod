package com.github.paopaoyue.onmyojimod;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.github.paopaoyue.onmyojimod.card.KamiHp;
import com.github.paopaoyue.onmyojimod.character.Sanme;
import com.github.paopaoyue.onmyojimod.patch.AbstractCardEnum;
import com.github.paopaoyue.onmyojimod.patch.PlayerClassEnum;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@SpireInitializer
@SuppressWarnings("unused")
public class OnmyojiMod implements EditCharactersSubscriber, EditStringsSubscriber, EditKeywordsSubscriber, EditRelicsSubscriber, EditCardsSubscriber, OnStartBattleSubscriber, PostBattleSubscriber, AddAudioSubscriber {


    public static final String MOD_ID = "Onmyoji";
    public static final HashMap<String, Keyword> MOD_DICTIONARY = new HashMap<>();

    private static final Logger logger = LogManager.getLogger(OnmyojiMod.class);

    public OnmyojiMod() {
        logger.info("instantiating OnmyojiMod");
        BaseMod.subscribe(this);
        BaseMod.addColor(AbstractCardEnum.ONMYOJI_COLOR, Color.SKY, Color.SKY, Color.SKY, Color.SKY, Color.SKY, Color.SKY, Color.SKY,
                "image/512/bg_attack.png", "image/512/bg_skill.png", "image/512/bg_power.png",
                "image/512/energy_orb.png", "image/1024/bg_attack.png",
                "image/1024/bg_skill.png", "image/1024/bg_power.png",
                "image/1024/energy_orb.png", "image/icon/small_energy_orb.png");
    }

    public static void initialize() {
        new OnmyojiMod();
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new KamiHp());
        new AutoAdd(MOD_ID)
                .setDefaultSeen(true)
                .cards();
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new Sanme(),
                "image/character_select/button.png",
                "image/character_select/portrait.png", PlayerClassEnum.ONMYOJI);
    }

    @Override
    public void receiveEditKeywords() {
        if (MOD_DICTIONARY != null) {
            for (Keyword keyword : MOD_DICTIONARY.values()) {
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
        BaseMod.loadCustomStringsFile(CardStrings.class, "localization/" + language + "/onmyoji_cards.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, "localization/" + language + "/onmyoji_powers.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, "localization/" + language + "/onmyoji_relics.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, "localization/" + language + "/onmyoji_ui.json");
        BaseMod.loadCustomStringsFile(OrbStrings.class, "localization/" + language + "/onmyoji_orbs.json");

//        BaseMod.loadCustomStringsFile(EventStrings.class, "localization/" + language + "/Event-Strings.json");

        Gson gson = new Gson();
        String json = Gdx.files.internal("localization/" + language + "/onmyoji_keywords.json").readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);
        for (Keyword keyword : keywords) {
            MOD_DICTIONARY.put(keyword.ID, keyword);
        }
    }


    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {

    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {

    }

    @Override
    public void receiveAddAudio() {
        BaseMod.addAudio("ONMYOJI:ROLL_DICE", "audio/roll_dice.mp3");
    }
}
