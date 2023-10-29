package bcheck;

import fetcher.BCheckFetcher;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.*;
import static java.util.Comparator.comparing;

public class BCheckManager {
    private final BCheckFetcher onlineBCheckFetcher;

    private final List<BCheck> allAvailableBChecks;

    public BCheckManager(BCheckFetcher onlineBCheckFetcher) {
        this.onlineBCheckFetcher = onlineBCheckFetcher;

        this.allAvailableBChecks = synchronizedList(new ArrayList<>());

        loadData();
    }

    private void loadData() {
        List<BCheck> bChecks;

        try {
            bChecks = onlineBCheckFetcher.fetchAllBChecks();
        } catch (Exception e) {
            bChecks = emptyList();
        }

        allAvailableBChecks.clear();
        allAvailableBChecks.addAll(bChecks);
        allAvailableBChecks.sort(comparing(BCheck::name));
    }

    public void refresh() {
        loadData();
    }

    public List<BCheck> availableBChecks() {
        return unmodifiableList(allAvailableBChecks);
    }
}
