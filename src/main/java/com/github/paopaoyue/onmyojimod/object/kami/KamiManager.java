package com.github.paopaoyue.onmyojimod.object.kami;

import com.github.paopaoyue.onmyojimod.character.Sanme;
import com.github.paopaoyue.onmyojimod.effect.KamiDeadEffect;
import com.github.paopaoyue.onmyojimod.patch.KamiCardGroup;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class KamiManager {

    private final List<AbstractKami> kamiSequenceInBattle;

    private final List<AbstractKami> kamiSequenceInTurn;

    private final CardGroup kamiCardGroup;

    private boolean hasKami;

    private int hp;

    private int baseHp;

    public KamiManager() {
        kamiSequenceInBattle = new ArrayList<>();
        kamiSequenceInTurn = new ArrayList<>();
        kamiCardGroup = new CardGroup(KamiCardGroup.KAMI_CARD);
    }

    public boolean isHasKami() {
        return hasKami;
    }

    public CardGroup getKamiCardGroup() {
        return kamiCardGroup;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getBaseHp() {
        return baseHp;
    }

    public void setBaseHp(int baseHp) {
        this.baseHp = baseHp;
    }

    public AbstractKami getCurrentKami() {
        if (!hasKami) {
            return AbstractKami.ONMYOJI_NONE;
        }
        return kamiSequenceInBattle.get(kamiSequenceInBattle.size() - 1);
    }

    public AbstractKami getPreviousKami() {
        if (kamiSequenceInBattle.size() <= 1) {
            return AbstractKami.ONMYOJI_NONE;
        }
        return kamiSequenceInBattle.get(kamiSequenceInBattle.size() - 2);
    }

    public int getKamiSwitchCountInBattle() {
        return (int) kamiSequenceInBattle.stream().filter(x -> x != AbstractKami.ONMYOJI_NONE).count();
    }

    public int getKamiSwitchCountInTurn() {
        return (int) kamiSequenceInTurn.stream().filter(x -> x != AbstractKami.ONMYOJI_NONE).count();
    }

    public int getDiffKamiSwitchCountInBattle() {
        Set<String> kamiSet = kamiSequenceInBattle.stream().filter(x -> x != AbstractKami.ONMYOJI_NONE).map(AbstractKami::getId).collect(Collectors.toSet());
        return kamiSet.size();
    }

    public int getDiffKamiSwitchCountInTurn() {
        Set<String> kamiSet = kamiSequenceInTurn.stream().filter(x -> x != AbstractKami.ONMYOJI_NONE).map(AbstractKami::getId).collect(Collectors.toSet());
        return kamiSet.size();
    }

    public int getDiffFactionSwitchCountInBattle() {
        Set<Faction> factionSet = kamiSequenceInBattle.stream().filter(x -> x != AbstractKami.ONMYOJI_NONE).map(AbstractKami::getFaction).collect(Collectors.toSet());
        return factionSet.size();
    }

    public int getDiffFationSwitchCountInTurn() {
        Set<Faction> factionSet = kamiSequenceInTurn.stream().filter(x -> x != AbstractKami.ONMYOJI_NONE).map(AbstractKami::getFaction).collect(Collectors.toSet());
        return factionSet.size();
    }

    public boolean hasDeadInBattle() {
        return getKamiSequenceInBattle().contains(AbstractKami.ONMYOJI_NONE);
    }

    public List<AbstractKami> getKamiSequenceInBattle() {
        return kamiSequenceInBattle;
    }

    public List<AbstractKami> getKamiSequenceInTurn() {
        return kamiSequenceInTurn;
    }

    public Faction getCurrentFaction() {
        if (!hasKami || getCurrentKami() == AbstractKami.ONMYOJI_NONE) {
            return Faction.NONE;
        }
        return kamiSequenceInBattle.get(kamiSequenceInBattle.size() - 1).getFaction();
    }

    public void onSwitch(AbstractCard card, AbstractKami kami) {
        if (hasKami) {
            this.getCurrentKami().onExit(kami);
        }

        hasKami = true;

        kamiSequenceInBattle.add(kami);
        kamiSequenceInTurn.add(kami);

        if (!kamiCardGroup.isEmpty()) {
            AbstractCard discardCard = kamiCardGroup.getTopCard();
            kamiCardGroup.moveToDiscardPile(discardCard);
        }
        if (!card.exhaust && !card.exhaustOnUseOnce && !card.purgeOnUse && !card.exhaustOnFire) {
            kamiCardGroup.addToBottom(card);
            AbstractDungeon.player.hand.empower(card);
        }

        kami.onEnter();

    }

    public void onDead() {
        if (hasKami) {
            this.getCurrentKami().onExit(AbstractKami.ONMYOJI_NONE);
        }

        hasKami = false;
        baseHp = 0;

        kamiSequenceInBattle.add(AbstractKami.ONMYOJI_NONE);
        kamiSequenceInTurn.add(AbstractKami.ONMYOJI_NONE);

        if (!kamiCardGroup.isEmpty()) {
            AbstractCard discardCard = kamiCardGroup.getTopCard();
            AbstractDungeon.actionManager.addToBottom(new DiscardSpecificCardAction(discardCard, kamiCardGroup));
        }

        AbstractDungeon.effectList.add(new KamiDeadEffect());
    }

    public void onTurnEnd() {
        if (hasKami) {
            this.getCurrentKami().onEndOfTurn();
        }
        kamiSequenceInTurn.clear();
    }

    public void onBattleEnd() {
        kamiSequenceInBattle.clear();
        kamiSequenceInTurn.clear();
        kamiCardGroup.clear();

        hasKami = false;
        hp = 0;
        baseHp = 0;

        Sanme player = (Sanme) AbstractDungeon.player;
        player.SwitchCharacterImage(Sanme.CHARACTER_IMG);
    }


}
