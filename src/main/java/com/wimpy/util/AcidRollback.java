package com.wimpy.util;

import org.tinylog.Logger;

import java.util.Optional;
import java.util.function.Supplier;

public class AcidRollback {

    private final Supplier executeCommand;
    private final Supplier rollbackCommand;

    private int executeRetries;
    private int rollbackRetries;


    private final Result FAILED = new Result(false, Optional.empty(), false);

    public AcidRollback(Supplier executeCommand, Supplier rollbackCommand) {
        this.executeCommand = executeCommand;
        this.rollbackCommand = rollbackCommand;

        this.executeRetries = 3;
        this.rollbackRetries = 3;
    }


    public Result run() {

        Result result = FAILED;

        try {
            result = new Result(true, Optional.ofNullable(Retry.run(executeRetries, executeCommand)));
        } catch (Exception e) {

            Logger.info("starting rollback");

            try {
                Retry.run(rollbackRetries, rollbackCommand);
                result = new Result(false, Optional.empty(), true);
            } catch (Exception ex) {
                Logger.error(ex);
            }
        }

        return result;
    }

    public Result rollback() {

        Result result = FAILED;

        Logger.info("starting rollback");
        try {
            result = new Result(true, Optional.ofNullable(Retry.run(executeRetries, rollbackCommand)));
        } catch (Exception e) {
            Logger.error(e);
        }

        return result;
    }
}
