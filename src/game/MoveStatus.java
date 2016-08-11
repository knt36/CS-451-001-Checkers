package game;

import static java.util.Arrays.asList;

/**
 *
 */
public enum MoveStatus {
    ADJ, JMP, JMP_INCOMPLETE, JMP_WRONG_DISK, INVALID_MOVE, OUT_OF_BOARD, WRONG_TURN, EMPTY_SRC, INVALID_STATE;

    /**
     * Checks if the move was successful
     *
     * @return True if the move was successful and the board updated.
     */
    public Boolean success() {
        return complete() || asList(JMP_INCOMPLETE).contains(this);
    }

    /**
     * Checks if the move was complete and the turn was changed
     *
     * @return True if the move succeeded and is complete (that is, not waiting for more jumps)
     */
    public Boolean complete() {
        return asList(ADJ, JMP).contains(this);
    }

    /**
     * Checks if the move was a failure. Alias for !success()
     *
     * @return True if the move failed.
     */
    public Boolean failure() {
        return !this.success();
    }

    /**
     * Checks if the failure was serious (that is, should not possibly happen if the UI is well constructed)
     *
     * @return True if a serious failure occurred.
     */
    public Boolean serious() {
        return asList(OUT_OF_BOARD, INVALID_STATE).contains(this);
    }
}
