package data;

public interface ItemParser<T extends Item> {
    T parse(String fileName, String filePath, String fileContent);
}