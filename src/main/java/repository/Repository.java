package repository;

import bcheck.BCheck;

import java.util.List;

public interface Repository {
    List<BCheck> loadAllBChecks();
}
