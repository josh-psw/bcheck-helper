package data.bcheck;

import data.ItemFilter;

public class BCheckFilter implements ItemFilter<BCheck> {
    @Override
    public boolean filter(BCheck item, String searchTerm) {
        return item.name().toLowerCase().contains(searchTerm) ||
                item.author().toLowerCase().contains(searchTerm) ||
                item.description().toLowerCase().contains(searchTerm) ||
                item.tags().contains(searchTerm);
    }
}