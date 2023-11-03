package bcheck;

import fetcher.BCheckFetcher;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.*;
import static java.util.Comparator.comparing;

public class BCheckManager {
    public enum State {
        START, INITIAL_LOAD, REFRESH, ERROR;

        State nextState() {
            return switch (this) {
                case START -> INITIAL_LOAD;
                case INITIAL_LOAD, REFRESH, ERROR -> REFRESH;
            };
        }
    }

    private final BCheckFetcher onlineBCheckFetcher;
    private final List<BCheck> allAvailableBChecks;

    private State state;

    public BCheckManager(BCheckFetcher onlineBCheckFetcher) {
        this.onlineBCheckFetcher = onlineBCheckFetcher;
        this.state = State.START;
        this.allAvailableBChecks = synchronizedList(new ArrayList<>());
    }

    public void loadData() {
        List<BCheck> bChecks;

        try {
            bChecks = onlineBCheckFetcher.fetchAllBChecks();
            state = state.nextState();
        } catch (Exception e) {
            bChecks = emptyList();
            state = State.ERROR;
        }

        allAvailableBChecks.clear();
        allAvailableBChecks.addAll(bChecks);
        allAvailableBChecks.sort(comparing(BCheck::name));
    }

    public List<BCheck> availableBChecks() {
        return unmodifiableList(allAvailableBChecks);
    }

    public int numberOfBChecks() {
        return allAvailableBChecks.size();
    }

    public State state() {
        return state;
    }
}
