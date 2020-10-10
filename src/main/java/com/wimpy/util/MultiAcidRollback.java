package com.wimpy.util;

import java.util.List;
import java.util.Optional;

public class MultiAcidRollback {

    private final Result ROLLBACK_SUCCESS = new Result(false, Optional.empty(), true);
    private final Result ROLLBACK_FAILED = new Result(false, Optional.empty(), false);
    private final Result SUCCESS = new Result(true, Optional.empty(), true);


    private List<AcidRollback> acidRollbackList;

    public MultiAcidRollback(List<AcidRollback> acidRollbackList) {
        this.acidRollbackList = acidRollbackList;
    }

    public Result run() {
        int current = 0;

        boolean rollback = false;


        for (int i = 0; i < acidRollbackList.size(); i++) {
            AcidRollback acidRollback = acidRollbackList.get(i);


            Result run = acidRollback.run();

            if (!run.isSuccess()) {

                if (i > 0) {
                    rollback = true;
                    current = i;
                    break;
                }
                return ROLLBACK_SUCCESS;
            }

        }


        if (rollback) {
            try {
                for (int i = current; i > 0; i--) {
                    acidRollbackList.get(i).rollback();
                }
            } catch (Exception e) {
                return ROLLBACK_FAILED;
            }
            return ROLLBACK_SUCCESS;
        }


        return SUCCESS;

    }
}
