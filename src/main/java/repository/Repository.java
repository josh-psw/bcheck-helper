package repository;

import bcheck.Item;

import java.util.List;

public interface Repository<T extends Item> {
    List<T> loadAllItems();
}
