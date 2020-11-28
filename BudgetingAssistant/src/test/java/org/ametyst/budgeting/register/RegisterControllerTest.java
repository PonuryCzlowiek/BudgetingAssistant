package org.ametyst.budgeting.register;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class RegisterControllerTest {
    protected static final UUID TEST_REGISTER_UUID = UUID.fromString("6a8096a6-29f3-11eb-adc1-0242ac120002");
    protected static final UUID TEST_REGISTER_UUID_2 = UUID.fromString("40e35958-7b15-41ee-aca9-ec36904f0d18");
    
    @MockBean
    private RegisterDao registerDao;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldGetAllRegisters() {
        // arrange
        when(registerDao.findAll()).thenReturn(Arrays.asList(new Register(), new Register()));

        // act
        List<Register> registers = this.restTemplate.getForObject("http://localhost:" + port + "/registers", List.class);

        // assert
        assertEquals(2, registers.size());
    }

    @Test
    void shouldChargeRegisterWithGivenAmount() {
        // arrange
        RegisterChargeDto request = new RegisterChargeDto();
        request.setAmount(50.0);
        when(registerDao.findById(TEST_REGISTER_UUID)).thenReturn(Optional.of(getRegister(TEST_REGISTER_UUID, "Charge", 100.0)));

        // act
        Register register = this.restTemplate
            .postForObject("http://localhost:" + port + "/registers/" + TEST_REGISTER_UUID + "/charge", request, Register.class);

        // assert
        assertEquals(150.0, register.getBalance());
        assertEquals(TEST_REGISTER_UUID, register.getUuid());
    }

    @Test
    void shouldNotChargeRegisterWithNegativeAmount() {
        // arrange
        RegisterChargeDto request = new RegisterChargeDto();
        request.setAmount(-50.0);

        // act
        ResponseEntity<String> responseEntity = this.restTemplate
            .postForEntity("http://localhost:" + port + "/registers/" + TEST_REGISTER_UUID + "/charge", request, String.class);

        // assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void shouldTransferMoneyBetweenAccount() {
        // arrange
        RegisterTransferDto registerTransferDto = new RegisterTransferDto();
        registerTransferDto.setAmount(300.0);
        registerTransferDto.setTargetRegisterUUID(TEST_REGISTER_UUID_2);
        Register fromRegister = getRegister(TEST_REGISTER_UUID, "From", 300.0);
        Register toRegister = getRegister(TEST_REGISTER_UUID_2, "To", 100.0);
        when(registerDao.findById(TEST_REGISTER_UUID)).thenReturn(Optional.of(fromRegister));
        when(registerDao.findById(TEST_REGISTER_UUID_2)).thenReturn(Optional.of(toRegister));

        // act
        ResponseEntity<Register> responseEntity = this.restTemplate
            .postForEntity("http://localhost:" + port + "/registers/" + TEST_REGISTER_UUID + "/transfer", registerTransferDto, Register.class);

        // assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0.0, responseEntity.getBody().getBalance());
        assertEquals(400.0, toRegister.getBalance());
    }

    @Test
    void shouldNotAllowToTransferNegativeAmountOfMoney() {
        // arrange
        RegisterTransferDto registerTransferDto = new RegisterTransferDto();
        registerTransferDto.setAmount(-5.0);
        registerTransferDto.setTargetRegisterUUID(TEST_REGISTER_UUID_2);
        Register fromRegister = getRegister(TEST_REGISTER_UUID, "From", 300.0);
        Register toRegister = getRegister(TEST_REGISTER_UUID_2, "To", 100.0);
        when(registerDao.findById(TEST_REGISTER_UUID)).thenReturn(Optional.of(fromRegister));
        when(registerDao.findById(TEST_REGISTER_UUID_2)).thenReturn(Optional.of(toRegister));

        // act
        ResponseEntity<Register> responseEntity = this.restTemplate
            .postForEntity("http://localhost:" + port + "/registers/" + TEST_REGISTER_UUID + "/transfer", registerTransferDto, Register.class);

        // assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void shouldNotAllowToTransferFromNonexistentRegister() {
        // arrange
        RegisterTransferDto registerTransferDto = new RegisterTransferDto();
        registerTransferDto.setAmount(15.0);
        registerTransferDto.setTargetRegisterUUID(TEST_REGISTER_UUID_2);
        Register toRegister = getRegister(TEST_REGISTER_UUID_2, "To", 100.0);
        when(registerDao.findById(TEST_REGISTER_UUID)).thenReturn(Optional.empty());
        when(registerDao.findById(TEST_REGISTER_UUID_2)).thenReturn(Optional.of(toRegister));

        // act
        ResponseEntity<Register> responseEntity = this.restTemplate
            .postForEntity("http://localhost:" + port + "/registers/" + TEST_REGISTER_UUID + "/transfer", registerTransferDto, Register.class);

        // assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void shouldNotAllowToTransferToNonexistentRegister() {
        // arrange
        RegisterTransferDto registerTransferDto = new RegisterTransferDto();
        registerTransferDto.setAmount(15.0);
        registerTransferDto.setTargetRegisterUUID(TEST_REGISTER_UUID_2);
        Register fromRegister = getRegister(TEST_REGISTER_UUID, "From", 300.0);
        when(registerDao.findById(TEST_REGISTER_UUID)).thenReturn(Optional.of(fromRegister));
        when(registerDao.findById(TEST_REGISTER_UUID_2)).thenReturn(Optional.empty());

        // act
        ResponseEntity<Register> responseEntity = this.restTemplate
            .postForEntity("http://localhost:" + port + "/registers/" + TEST_REGISTER_UUID + "/transfer", registerTransferDto, Register.class);

        // assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void shouldNotAllowToTransferMoreThanCurrentBalance() {
        // arrange
        RegisterTransferDto registerTransferDto = new RegisterTransferDto();
        registerTransferDto.setAmount(150.0);
        registerTransferDto.setTargetRegisterUUID(TEST_REGISTER_UUID_2);
        Register fromRegister = getRegister(TEST_REGISTER_UUID, "From", 140.0);
        Register toRegister = getRegister(TEST_REGISTER_UUID_2, "To", 100.0);
        when(registerDao.findById(TEST_REGISTER_UUID)).thenReturn(Optional.of(fromRegister));
        when(registerDao.findById(TEST_REGISTER_UUID_2)).thenReturn(Optional.of(toRegister));

        // act
        ResponseEntity<Register> responseEntity = this.restTemplate
            .postForEntity("http://localhost:" + port + "/registers/" + TEST_REGISTER_UUID + "/transfer", registerTransferDto, Register.class);

        // assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    private Register getRegister(UUID registerUuid, String name, double balance) {
        Register fromRegister = new Register();
        fromRegister.setUuid(registerUuid);
        fromRegister.setName(name);
        fromRegister.setBalance(balance);
        return fromRegister;
    }
}