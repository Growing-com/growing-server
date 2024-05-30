package org.sarangchurch.growing.__e2e__;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.sarangchurch.growing.DatabaseCleanup;
import org.sarangchurch.growing.DatabaseInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {
    @Value("${local.server.port}")
    int port;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private DatabaseInit databaseInit;

    @BeforeEach
    protected void setUp() {
        RestAssured.port = port;

        databaseCleanup.execute();
        databaseInit.execute();
    }
}

