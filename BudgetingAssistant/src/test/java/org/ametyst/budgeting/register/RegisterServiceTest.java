package org.ametyst.budgeting.register;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RegisterServiceTest {
    protected static final UUID TEST_REGISTER_UUID = UUID.fromString("6a8096a6-29f3-11eb-adc1-0242ac120002");
    protected static final UUID TEST_REGISTER_UUID_2 = UUID.fromString("40e35958-7b15-41ee-aca9-ec36904f0d18");

    private RegisterDao registerDao;
    private RegisterService registerService;

    @BeforeEach
    public void beforeEach() {
        registerDao = mock(RegisterDao.class);
        registerService = new RegisterService(registerDao);
    }

    @Test
    void shouldGetAllDefaultRegistersWithTestOne() {
        // arrange
        when(registerDao.findAll()).thenReturn(Arrays.asList(new Register(), new Register()));
        // act
        List<Register> allRegisters = registerService.getAllRegisters();

        // assert
        assertEquals(2, allRegisters.size());
    }

    @Test
    void shouldChargeSelectedRegister() {
        // arrange
        when(registerDao.findById(TEST_REGISTER_UUID_2)).thenReturn(Optional.of(getRegister(TEST_REGISTER_UUID_2, "Register", 100.0)));

        // act
        Register register = registerService.chargeRegister(TEST_REGISTER_UUID_2, 100.0);

        // assert
        assertEquals(200.0, register.getBalance());
    }
    
    @Test
    void shouldTransferMoneyBetweenRegisters() {
        // arrange
        Register fromRegister = getRegister(TEST_REGISTER_UUID, "FROM", 300.0);
        when(registerDao.findById(TEST_REGISTER_UUID)).thenReturn(Optional.of(fromRegister));
        Register toRegister = getRegister(TEST_REGISTER_UUID_2, "TO", 100.0);
        when(registerDao.findById(TEST_REGISTER_UUID_2)).thenReturn(Optional.of(toRegister));

        // act
        registerService.transfer(TEST_REGISTER_UUID, TEST_REGISTER_UUID_2, 300.0);

        // assert
        assertEquals(0, fromRegister.getBalance());
        assertEquals(400.0, toRegister.getBalance());
    }

    private Register getRegister(UUID registerUuid, String name, double balance) {
        Register fromRegister = new Register();
        fromRegister.setUuid(registerUuid);
        fromRegister.setName(name);
        fromRegister.setBalance(balance);
        return fromRegister;
    }
}