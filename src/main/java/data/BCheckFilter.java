package data;

public class BCheckFilter implements ItemFilter<BCheck> {
    @Override
    public boolean filter(BCheck item, String searchText) {
        return item.name().toLowerCase().contains(searchText) ||
                item.author().toLowerCase().contains(searchText) ||
                item.description().toLowerCase().contains(searchText) ||
                item.tags().contains(searchText);
    }
}