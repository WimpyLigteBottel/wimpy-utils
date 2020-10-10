package com.wimpy.util;

import com.wimpy.util.acid.AcidRollback;
import com.wimpy.util.acid.Result;
import org.junit.Test;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class AcidRollbackTest {

    @Test
    public void run_expectSuccess() {

        Supplier<Void> working =
                () -> {
                    System.out.println("normal method");
                    return null;
                };

        Supplier<Void> rollback =
                () -> {
                    System.out.println("rollback method");
                    return null;
                };

        AcidRollback acidRollback = new AcidRollback(working, rollback);

        Result run = acidRollback.run();

        assertThat(run.isSuccess()).isTrue();
    }

    @Test
    public void run_rollbackWorks_expectFailed() {

        Supplier<Void> working =
                () -> {
                    throw new RuntimeException("I MUST FAIL");
                };

        Supplier<Void> rollback =
                () -> {
                    System.out.println("rollback method");
                    return null;
                };

        AcidRollback acidRollback = new AcidRollback(working, rollback);

        Result run = acidRollback.run();

        assertThat(run.isSuccess()).isFalse();
        assertThat(run.isRollbackSuccess()).isTrue();
    }

    @Test
    public void run_rollbackDoesNotWork_expectFailed() {

        Supplier<Void> working =
                () -> {
                    throw new RuntimeException("I MUST FAIL");
                };

        Supplier<Void> rollback =
                () -> {
                    throw new RuntimeException("I MUST FAIL");
                };

        AcidRollback acidRollback = new AcidRollback(working, rollback);

        Result run = acidRollback.run();

        assertThat(run.isSuccess()).isFalse();
        assertThat(run.isRollbackSuccess()).isFalse();
    }
}
