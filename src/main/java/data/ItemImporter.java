package data;

public interface ItemImporter<T extends Item> {
    void importItem(T item);
}
