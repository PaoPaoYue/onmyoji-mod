package com.github.paopaoyue.onmyojimod.character;

import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.github.paopaoyue.onmyojimod.object.kami.AbstractKami;
import com.github.paopaoyue.onmyojimod.object.kami.KamiManager;
import com.github.paopaoyue.onmyojimod.object.spirit.AbstractSpirit;
import com.github.paopaoyue.onmyojimod.orb.AbstractCountdownOrb;
import com.github.paopaoyue.onmyojimod.patch.AbstractCardEnum;
import com.github.paopaoyue.onmyojimod.patch.PlayerClassEnum;
import com.github.paopaoyue.onmyojimod.patch.spirit.SpiritField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.AnimateOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.red.Bash;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;

public class OnmyojiCharacter extends CustomPlayer {

    public static final String CHARACTER_ID = "sanme";
    public static final int ENERGY_PER_TURN = 3; // how much energy you get every turn
    public static final String CHARACTER_SHOULDER_2 = "image/character/shoulder.png"; // campfire pose
    public static final String CHARACTER_SHOULDER_1 = "image/character/shoulder.png"; // another campfire pose
    public static final String CHARACTER_CORPSE = "image/character/corpse.png"; // dead corpse
    public static final int STARTING_HP = 75;
    public static final int MAX_HP = 75;
    public static final int MAX_ORB_SLOT = 0;
    public static final int STARTING_GOLD = 99;
    public static final int HAND_SIZE = 5;
    private static final Logger logger = LogManager.getLogger(OnmyojiCharacter.class.getName());
    private static final float CHARACTER_IMAG_SWITCH_MAX_DURATION = 0.2f;
    public static String CHARACTER_IMG = "image/character/sanme.png";
    public static CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString("Onmyoji:Sanme");
    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("Countdown");
    private final KamiManager kamiManager;
    private float characterImgSwitchDuration = 0f;

    public OnmyojiCharacter() {
        super(CHARACTER_ID, PlayerClassEnum.ONMYOJI, null, null, (String) null, null);

        this.dialogX = (this.drawX + 0.0F * Settings.scale); // set location for text bubbles
        this.dialogY = (this.drawY + 220.0F * Settings.scale); // you can just copy these values

        initializeClass(CHARACTER_IMG, CHARACTER_SHOULDER_2, // required call to load textures and setup energy/loadout
                CHARACTER_SHOULDER_1,
                CHARACTER_CORPSE,
                getLoadout(), 0F, 0F, 300.0F, 300.0F, new EnergyManager(ENERGY_PER_TURN));

        this.kamiManager = new KamiManager();
    }

    public KamiManager getKamiManager() {
        return this.kamiManager;
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
        retVal.add("Onmyoji:Seven Sides");
        return retVal;
    }

    public ArrayList<String> getStartingRelics() { // starting relics - also simple
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add("Onmyoji:Sachet");
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


    @Override
    public void render(SpriteBatch sb) {
        this.stance.render(sb);
        if ((AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT || AbstractDungeon.getCurrRoom() instanceof MonsterRoom) && !this.isDead) {
            this.renderHealth(sb);

            AbstractCountdownOrb.updateTimerAnimation();
            if (!this.orbs.isEmpty()) {
                for (AbstractOrb o : this.orbs) {
                    o.render(sb);
                }
            }
        }

        if (!(AbstractDungeon.getCurrRoom() instanceof RestRoom)) {
            float opacity = 1;
            if (this.characterImgSwitchDuration > 0) {
                opacity = MathUtils.lerp(0F, 1F, 1 - this.characterImgSwitchDuration / CHARACTER_IMAG_SWITCH_MAX_DURATION);
                this.characterImgSwitchDuration -= Gdx.graphics.getDeltaTime();
                if (this.characterImgSwitchDuration < 0.01F) {
                    this.characterImgSwitchDuration = 0;
                }
            }
            sb.setColor(1, 1, 1, opacity);
            sb.draw(this.img, this.drawX - (float) this.img.getWidth() * Settings.scale / 2.0F + this.animX, this.drawY, (float) this.img.getWidth() * Settings.scale, (float) this.img.getHeight() * Settings.scale, 0, 0, this.img.getWidth(), this.img.getHeight(), this.flipHorizontal, this.flipVertical);
            sb.setColor(Color.WHITE);
            this.hb.render(sb);
            this.healthHb.render(sb);
        } else {
            sb.setColor(Color.WHITE);
            this.renderShoulderImg(sb);
        }
    }

    @Override
    public void renderPowerTips(SpriteBatch sb) {
        final ArrayList<PowerTip> tips = new ArrayList<>();
        if (kamiManager.isHasKami()) {
            AbstractKami kami = kamiManager.getCurrentKami();
            kami.updateDescription();
            tips.add(new PowerTip(kami.getName(), kami.getDescription()));
        }
        if (!this.stance.ID.equals("Neutral")) {
            tips.add(new PowerTip(this.stance.name, this.stance.description));
        }
        for (final AbstractPower p : this.powers) {
            if (p.region48 != null) {
                tips.add(new PowerTip(p.name, p.description, p.region48));
            } else {
                tips.add(new PowerTip(p.name, p.description, p.img));
            }
        }
        if (!tips.isEmpty()) {
            if (this.hb.cX + this.hb.width / 2.0f < AbstractPlayer.TIP_X_THRESHOLD) {
                TipHelper.queuePowerTips(this.hb.cX + this.hb.width / 2.0f + AbstractPlayer.TIP_OFFSET_R_X, this.hb.cY + TipHelper.calculateAdditionalOffset(tips, this.hb.cY), tips);
            } else {
                TipHelper.queuePowerTips(this.hb.cX - this.hb.width / 2.0f + AbstractPlayer.TIP_OFFSET_L_X, this.hb.cY + TipHelper.calculateAdditionalOffset(tips, this.hb.cY), tips);
            }
        }
    }

    public void SwitchCharacterImage(String img) {
        this.img = ImageMaster.loadImage(img);
        this.characterImgSwitchDuration = CHARACTER_IMAG_SWITCH_MAX_DURATION;
    }

    public void useCard(AbstractCard c, AbstractMonster monster, int energyOnUse) {
        if (c.type == AbstractCard.CardType.ATTACK) {
            this.useFastAttackAnimation();
        }

        c.calculateCardDamage(monster);
        if (c.cost == -1 && EnergyPanel.totalCount < energyOnUse && !c.ignoreEnergyOnUse) {
            c.energyOnUse = EnergyPanel.totalCount;
        }

        if (c.cost == -1 && c.isInAutoplay) {
            c.freeToPlayOnce = true;
        }

        AbstractSpirit spirit = SpiritField.spirit.get(c);
        if (spirit != null) {
            spirit.onUse();
        }

        this.countdown();

        c.use(this, monster);
        AbstractDungeon.actionManager.addToBottom(new UseCardAction(c, monster));
        if (!c.dontTriggerOnUseCard) {
            this.hand.triggerOnOtherCardPlayed(c);
        }

        this.hand.removeCard(c);
        this.cardInUse = c;
        c.target_x = (float) (Settings.WIDTH / 2);
        c.target_y = (float) (Settings.HEIGHT / 2);
        if (c.costForTurn > 0 && !c.freeToPlay() && !c.isInAutoplay && (!this.hasPower("Corruption") || c.type != AbstractCard.CardType.SKILL)) {
            this.energy.use(c.costForTurn);
        }

        if (!this.hand.canUseAnyCard() && !this.endTurnQueued) {
            AbstractDungeon.overlayMenu.endTurnButton.isGlowing = true;
        }
    }

    public void draw(int numCards) {
        for (int i = 0; i < numCards; ++i) {
            if (this.drawPile.isEmpty()) {
                logger.info("ERROR: How did this happen? No cards in draw pile?? Player.java");
            } else {
                AbstractCard c = this.drawPile.getTopCard();
                c.current_x = CardGroup.DRAW_PILE_X;
                c.current_y = CardGroup.DRAW_PILE_Y;
                c.setAngle(0.0F, true);
                c.lighten(false);
                c.drawScale = 0.12F;
                c.targetDrawScale = 0.75F;
                c.triggerWhenDrawn();
                this.hand.addToHand(c);
                this.drawPile.removeTopCard();

                AbstractSpirit spirit = SpiritField.spirit.get(c);
                if (spirit != null) {
                    spirit.onDraw();
                }

                for (AbstractPower power : this.powers) {
                    power.onCardDraw(c);
                }

                for (AbstractRelic relic : this.relics) {
                    relic.onCardDraw(c);
                }
            }
        }
    }

    public void applyEndOfTurnTriggers() {
        this.kamiManager.onTurnEnd();
        super.applyEndOfTurnTriggers();
    }

    public void onVictory() {
        this.kamiManager.onBattleEnd();
        super.onVictory();
    }

    public void evokeOrb() {
        if (!this.orbs.isEmpty() && !(this.orbs.get(0) instanceof EmptyOrbSlot)) {
            this.orbs.get(0).onEvoke();
            for (int i = 1; i < this.orbs.size(); ++i) {
                Collections.swap(this.orbs, i, i - 1);
            }
            this.decreaseMaxOrbSlots(1);
        }

    }

    public void removeNextOrb() {
        if (!this.orbs.isEmpty() && !(this.orbs.get(0) instanceof EmptyOrbSlot)) {
            for (int i = 1; i < this.orbs.size(); ++i) {
                Collections.swap(this.orbs, i, i - 1);
            }
            this.decreaseMaxOrbSlots(1);
        }

    }

    public void channelOrb(AbstractOrb orbToSet) {
        if (this.maxOrbs == 10) {
            AbstractDungeon.effectList.add(new ThoughtBubble(this.dialogX, this.dialogY, 3.0F, uiStrings.TEXT[0], true));
            return;
        }
        if (!(orbToSet instanceof AbstractCountdownOrb)) {
            AbstractDungeon.effectList.add(new ThoughtBubble(this.dialogX, this.dialogY, 3.0F, uiStrings.TEXT[1], true));
            return;
        }
        CardCrawlGame.sound.play("ORB_SLOT_GAIN", 0.1F);
        this.maxOrbs += 1;
        this.orbs.add(orbToSet);
        for (int i = this.orbs.size() - 1; i >= 0; i--) {
            if (((AbstractCountdownOrb) orbToSet).getCountdown() < ((AbstractCountdownOrb) this.orbs.get(i)).getCountdown()) {
                Collections.swap(this.orbs, i + 1, i);
            }
        }
        for (int i = 0; i < this.orbs.size(); i++) {
            this.orbs.get(i).setSlot(i, this.maxOrbs);
        }
        orbToSet.cX = AbstractDungeon.player.drawX + AbstractDungeon.player.hb_x;
        orbToSet.cY = AbstractDungeon.player.drawY + AbstractDungeon.player.hb_y + AbstractDungeon.player.hb_h / 2.0F;
        orbToSet.playChannelSFX();
        for (final AbstractPower p : this.powers) {
            p.onChannel(orbToSet);
        }
        AbstractDungeon.actionManager.orbsChanneledThisCombat.add(orbToSet);
        AbstractDungeon.actionManager.orbsChanneledThisTurn.add(orbToSet);
        orbToSet.applyFocus();
    }

    public void applyStartOfTurnOrbs() {
        this.countdown();
    }

    public void countdown() {
        int orbToEvoke = 0;
        for (AbstractOrb orb : this.orbs) {
            orb.onStartOfTurn();
            if (orb instanceof AbstractCountdownOrb) {
                ((AbstractCountdownOrb) orb).countdown();
                orbToEvoke += ((AbstractCountdownOrb) orb).isTimeout() ? 1 : 0;
            }
        }
        if (orbToEvoke > 0) {
            AbstractDungeon.actionManager.addToBottom(new AnimateOrbAction(orbToEvoke));
            AbstractDungeon.actionManager.addToBottom(new EvokeOrbAction(orbToEvoke));
        }
    }


}