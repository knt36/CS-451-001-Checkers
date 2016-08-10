package game;

import static java.util.Arrays.asList;

/**
 *
 */
public enum MoveStatus {
    ADJ, JMP, OUT_OF_BOARD, INVALID_DISTANCE, WRONG_TURN, NONEMPTY_DEST, WRONG_DIRECTION, WRONG_JMP_COLOR, FAIL;

    /**
     * Checks if the move was successful
     *
     * @return True if type is SUCCESS
     */
    public Boolean success() {
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
        return asList(OUT_OF_BOARD, FAIL).contains(this);
    }
}
