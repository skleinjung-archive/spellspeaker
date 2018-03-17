package com.thrashplay.spellspeaker.model;

import com.thrashplay.spellspeaker.config.GameRules;
import com.thrashplay.spellspeaker.view.CardView;
import com.thrashplay.spellspeaker.view.GameClientView;
import com.thrashplay.spellspeaker.view.PlayerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sean Kleinjung
 */
public class SpellspeakerGame {

    private long id;

    private GameRules rules;

    private Player bluePlayer;
    private Player redPlayer;

    private DiscardPile discardPile;
    private Library library;
    private Market market;

    private int currentTick = 0;

    public SpellspeakerGame(GameRules rules, CardFactory cardFactory, long bluePlayerUserId, long redPlayerUserId) {
        this.rules = rules;

        bluePlayer = new Player(bluePlayerUserId);
        bluePlayer.setHealth(40);
        bluePlayer.setMana(30);

        redPlayer = new Player(redPlayerUserId);
        redPlayer.setHealth(40);
        redPlayer.setMana(30);

        discardPile = new DiscardPile();

        library = new Library(discardPile);
        List<Card> cards = cardFactory.createCards();
        for (Card card : cards) {
            library.add(card);
        }
        library.shuffle();

        market = new Market(rules, discardPile, library);
        market.refresh();

        for (int i = 0; i < rules.getInitialHandSize(); i++) {
            bluePlayer.getHand().add(library.draw());
            redPlayer.getHand().add(library.draw());
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Player getBluePlayer() {
        return bluePlayer;
    }

    public Player getRedPlayer() {
        return redPlayer;
    }

    public GameClientView toClientView(final User requestingUser) {
        final Player currentUserPlayer;
        if (requestingUser != null && requestingUser.getId() == bluePlayer.getUserId()) {
            currentUserPlayer = bluePlayer;
        } else if (requestingUser != null && requestingUser.getId() == redPlayer.getUserId()) {
            currentUserPlayer = redPlayer;
        } else {
            currentUserPlayer = null;
        }

        return new GameClientView() {
            @Override
            public long getId() {
                return id;
            }

            @Override
            public int getNumberOfCardsInLibrary() {
                return library.size();
            }

            @Override
            public int getCurrentTick() {
                return currentTick;
            }

            @Override
            public PlayerView getBluePlayer() {
                return new PlayerView(requestingUser, bluePlayer);
            }

            @Override
            public PlayerView getRedPlayer() {
                return new PlayerView(requestingUser, redPlayer);
            }

            @Override
            public List<CardView> getMarket() {
                return convertToCardViews(market.getCards());
            }

            @Override
            public List<CardView> getHand() {
                return currentUserPlayer == null ? null : convertToCardViews(currentUserPlayer.getHand().getCards());
            }

            private List<CardView> convertToCardViews(List<Card> cards) {
                List<CardView> views = new ArrayList<>(cards.size());
                for (Card card : cards) {
                    CardView view = CardView.fromCard(card);
                    views.add(view);
                }
                return views;
            }
        };
    }
}
