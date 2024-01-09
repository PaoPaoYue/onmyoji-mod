package com.github.paopaoyue.onmyojimod.character;

import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.github.paopaoyue.onmyojimod.patch.AbstractCardEnum;
import com.github.paopaoyue.onmyojimod.patch.PlayerClassEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Bash;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import java.util.ArrayList;

public class OnmyojiCharacter extends CustomPlayer {
    public static final String CHARACTER_ID = "sanme";
    public static final int ENERGY_PER_TURN = 3; // how much energy you get every turn
    public static final String CHARACTER_SHOULDER_2 = "image/character/shoulder.png"; // campfire pose
    public static final String CHARACTER_SHOULDER_1 = "image/character/shoulder.png"; // another campfire pose
    public static final String CHARACTER_CORPSE = "image/character/corpse.png"; // dead corpse
    public static final String CHARACTER_SKELETON_ATLAS = "character/sanme/skeleton.atlas"; // spine animation atlas
    public static final String CHARACTER_SKELETON_JSON = "character/sanme/skeleton.json"; // spine animation json
    public static final int STARTING_HP = 75;
    public static final int MAX_HP = 75;
    public static final int MAX_ORB_SLOT = 0;
    public static final int STARTING_GOLD = 99;
    public static final int HAND_SIZE = 5;
    public static CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString("Onmyoji:Sanme");

    public OnmyojiCharacter() {
        super(CHARACTER_ID, PlayerClassEnum.ONMYOJI, null, null, (String) null, null);

        this.dialogX = (this.drawX + 0.0F * Settings.scale); // set location for text bubbles
        this.dialogY = (this.drawY + 220.0F * Settings.scale); // you can just copy these values

        initializeClass(null, CHARACTER_SHOULDER_2, // required call to load textures and setup energy/loadout
                CHARACTER_SHOULDER_1,
                CHARACTER_CORPSE,
                getLoadout(), 0F, 0F, 300.0F, 300.0F, new EnergyManager(ENERGY_PER_TURN));

        this.SwitchCharacterAnimation(name);

    }

    public ArrayList<String> getStartingDeck() { // starting deck 'nuff said
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add("Strike_R");
        retVal.add("Strike_R");
        retVal.add("Strike_R");
        retVal.add("Strike_R");
        retVal.add("Strike_R");
        retVal.add("Defend_R");
        retVal.add("Defend_R");
        retVal.add("Defend_R");
        retVal.add("Defend_R");
        retVal.add("Onmyoji:Menreiki");
        return retVal;
    }

    public ArrayList<String> getStartingRelics() { // starting relics - also simple
        ArrayList<String> retVal = new ArrayList<>();
        return retVal;
    }

    public CharSelectInfo getLoadout() { // the rest of the character loadout so includes your character select screen info plus hp and starting gold
        return new CharSelectInfo(characterStrings.NAMES[0], characterStrings.TEXT[0],
                STARTING_HP, MAX_HP, MAX_ORB_SLOT, STARTING_GOLD, HAND_SIZE,
                this, getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return characterStrings.NAMES[0];
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return AbstractCardEnum.ONMYOJI_COLOR;
    }

    @Override
    public Color getCardRenderColor() {
        return Color.SKY;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Bash();
    }

    @Override
    public Color getCardTrailColor() {
        return Color.SKY;
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 6;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("SELECT_WATCHER", MathUtils.random(-0.15F, 0.15F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "SELECT_WATCHER";
    }

    @Override
    public String getLocalizedCharacterName() {
        return characterStrings.NAMES[0];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new OnmyojiCharacter();
    }

    @Override
    public String getSpireHeartText() {
        return characterStrings.TEXT[1];
    }

    @Override
    public Color getSlashAttackColor() {
        return Color.SKY;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.BLUNT_LIGHT, AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.BLUNT_LIGHT, AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.BLUNT_LIGHT};
    }

    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[1];
    }

    public void SwitchCharacterAnimation(String characterID) {
        String atlasPath = "character/" + characterID + "/skeleton.atlas";
        String jsonPath = "character/" + characterID + "/skeleton.json";
        loadAnimation(atlasPath, jsonPath, 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

}