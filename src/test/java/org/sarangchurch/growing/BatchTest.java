package org.sarangchurch.growing;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BatchTest {
    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private DatabaseInit databaseInit;

    @BeforeEach
    void beforeEach() {
        databaseCleanup.execute();
        databaseInit.execute();
    }
}
