package com.wimpy.util;

import com.wimpy.util.acid.AcidRollback;
import com.wimpy.util.acid.MultiAcidRollback;
import com.wimpy.util.acid.Result;
import org.junit.Test;

import java.util.Arrays;
import java.util.function.Supplier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MultiAcidRollbackTest {

    @Test
    public void run_listOfAllWorkingCommands_expectSuccess() {

        MultiAcidRollback multiAcidRollback = new MultiAcidRollback(Arrays.asList(working(), working()));

        Result actual = multiAcidRollback.run();


        assertThat(actual.isSuccess()).isTrue();
    }

    @Test
    public void run_oneCommandFails() {

        MultiAcidRollback multiAcidRollback = new MultiAcidRollback(Arrays.asList(working(), onlyRollbackWorks()));

        Result actual = multiAcidRollback.run();


        assertThat(actual.isSuccess()).isFalse();
        assertThat(actual.isRollbackSuccess()).isTrue();
    }

    @Test
    public void run_rollbacksFail() {

        MultiAcidRollback multiAcidRollback = new MultiAcidRollback(Arrays.asList(bothFail(), bothFail()));

        Result actual = multiAcidRollback.run();


        assertThat(actual.isSuccess()).isFalse();
        assertThat(actual.isRollbackSuccess()).isTrue();
    }


    private AcidRollback working() {

        Supplier<Void> working =
                () -> {
                    System.out.println("rollback method");
                    return null;
                };

        Supplier<Void> rollback =
                () -> {
                    System.out.println("rollback method");
                    return null;
                };

        return new AcidRollback(working, rollback);
    }

    private AcidRollback onlyRollbackWorks() {

        Supplier<Void> working =
                () -> {
                    throw new RuntimeException("I MUST FAIL");
                };

        Supplier<Void> rollback =
                () -> {
                    System.out.println("rollback method");
                    return null;
                };

        return new AcidRollback(working, rollback);
    }

    private AcidRollback bothFail() {

        Supplier<Void> working =
                () -> {
                    throw new RuntimeException("I MUST FAIL");
                };

        Supplier<Void> rollback =
                () -> {
                    throw new RuntimeException("I MUST FAIL");
                };

        return new AcidRollback(working, rollback);
    }
}