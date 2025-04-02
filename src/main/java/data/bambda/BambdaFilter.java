package data.bambda;

import data.ItemFilter;

public class BambdaFilter implements ItemFilter<Bambda> {
    @Override
    public boolean filter(Bambda item, String searchTerm) {
        return item.name().toLowerCase().contains(searchTerm) ||
                item.author().toLowerCase().contains(searchTerm) ||
                item.description().toLowerCase().contains(searchTerm) ||
                item.tags().contains(searchTerm);
    }
}