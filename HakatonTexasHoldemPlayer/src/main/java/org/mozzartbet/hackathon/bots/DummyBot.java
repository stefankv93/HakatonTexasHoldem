package org.mozzartbet.hackathon.bots;

import java.util.List;
import java.util.Set;

import org.mozzartbet.hackathon.Card;
import org.mozzartbet.hackathon.Player;
import org.mozzartbet.hackathon.actions.Action;

/**
 * Dummy Texas Hold'em poker bot that always just checks or calls. <br />
 * <br />
 *
 * This bot allowed for perfectly predictable behavior.
 */
public class DummyBot implements Bot {

    /** {@inheritDoc} */
    @Override
    public void joinedTable(int bigBlind) {
        // Not implemented.
    }

    /** {@inheritDoc} */
    @Override
    public void handStarted(Player dealer) {
        // Not implemented.
    }

    /** {@inheritDoc} */
    @Override
    public void actorRotated(Player actor) {
        // Not implemented.
    }

    /** {@inheritDoc} */
    @Override
    public void playerUpdated(Player player) {
        // Not implemented.
    }

    /** {@inheritDoc} */
    @Override
    public void boardUpdated(List<Card> cards, int bet, int pot) {
        // Not implemented.
    }

    /** {@inheritDoc} */
    @Override
    public void playerActed(Player player) {
        // Not implemented.
    }

    /** {@inheritDoc} */
    @Override
    public Action act(int minBet, int currentBet, Set<Action> allowedActions, int currentAmount) {
        if (allowedActions.contains(Action.CHECK)) {
            return Action.CHECK;
        } else {
            return Action.CALL;
        }
    }

    @Override
    public String getName() {
        return "Dummy " + (int)(200*Math.random());
    }


}
