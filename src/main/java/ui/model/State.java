package ui.model;

public enum State {
    START, INITIAL_LOAD, REFRESH, ERROR;

    public State nextState() {
        return switch (this) {
            case START -> INITIAL_LOAD;
            case INITIAL_LOAD, REFRESH, ERROR -> REFRESH;
        };
    }
}
