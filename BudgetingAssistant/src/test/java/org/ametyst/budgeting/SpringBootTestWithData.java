package org.ametyst.budgeting;

import java.util.UUID;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SpringBootTestWithData {

    protected static final Integer DEFAULT_REGISTER_AMOUNT = 4;
    protected static final Integer TEST_REGISTER_AMOUNT = 2;
    protected static final UUID TEST_REGISTER_UUID = UUID.fromString("6a8096a6-29f3-11eb-adc1-0242ac120002");
    protected static final UUID TEST_REGISTER_UUID_2 = UUID.fromString("40e35958-7b15-41ee-aca9-ec36904f0d18");
}
