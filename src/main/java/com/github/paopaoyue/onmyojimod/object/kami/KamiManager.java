package com.github.paopaoyue.onmyojimod.object.kami;

import com.github.paopaoyue.onmyojimod.character.OnmyojiCharacter;
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
        return kamiSequenceInBattle.size();
    }

    public int getKamiSwitchCountInTurn() {
        return kamiSequenceInTurn.size();
    }

    public int getDiffKamiSwitchCountInBattle() {
        Set<String> kamiSet = kamiSequenceInBattle.stream().map(AbstractKami::getId).collect(Collectors.toSet());
        return kamiSet.size();
    }

    public int getDiffKamiSwitchCountInTurn() {
        Set<String> kamiSet = kamiSequenceInTurn.stream().map(AbstractKami::getId).collect(Collectors.toSet());
        return kamiSet.size();
    }

    public List<AbstractKami> getKamiSequenceInBattle() {
        return kamiSequenceInBattle;
    }

    public List<AbstractKami> getKamiSequenceInTurn() {
        return kamiSequenceInTurn;
    }

    public Faction getCurrentFaction() {
        if (!hasKami) {
            return Faction.NONE;
        }
        return kamiSequenceInBattle.get(kamiSequenceInBattle.size() - 1).getFaction();
    }

    public Faction getPreviousFaction() {
        if (kamiSequenceInBattle.size() <= 1) {
            return Faction.NONE;
        }
        return kamiSequenceInBattle.get(kamiSequenceInBattle.size() - 2).getFaction();
    }

    public int getFactionSwitchCountInBattle() {
        return kamiSequenceInBattle.size();
    }

    public int getFactionSwitchCountInTurn() {
        return kamiSequenceInTurn.size();
    }

    public int getDiffFactionSwitchCountInBattle() {
        Set<Faction> factionSet = kamiSequenceInBattle.stream().map(AbstractKami::getFaction).collect(Collectors.toSet());
        return factionSet.size();
    }

    public int getDiffFactionSwitchCountInTurn() {
        Set<Faction> factionSet = kamiSequenceInTurn.stream().map(AbstractKami::getFaction).collect(Collectors.toSet());
        return factionSet.size();
    }

    public void onSwitch(AbstractCard card, AbstractKami kami) {
        if (hasKami) {
            this.getCurrentKami().onExit();
        }

        hasKami = true;

        kamiSequenceInBattle.add(kami);
        kamiSequenceInTurn.add(kami);

        kami.onEnter();

        if (!kamiCardGroup.isEmpty()) {
            AbstractCard discardCard = kamiCardGroup.getTopCard();
            kamiCardGroup.moveToDiscardPile(discardCard);
        }
        if (!card.exhaust && !card.exhaustOnUseOnce) {
            kamiCardGroup.addToBottom(card);
            AbstractDungeon.player.hand.empower(card);
        }

    }

    public void onDead() {
        if (hasKami) {
            this.getCurrentKami().onExit();
        }

        hasKami = false;

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

        OnmyojiCharacter player = (OnmyojiCharacter) AbstractDungeon.player;
        player.SwitchCharacterImage(OnmyojiCharacter.CHARACTER_IMG);
    }


}
