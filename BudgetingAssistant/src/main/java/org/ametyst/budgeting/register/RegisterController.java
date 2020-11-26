package org.ametyst.budgeting.register;

import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("registers")
@RestController
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @GetMapping
    public List<Register> getAllRegisters() {
        return registerService.getAllRegisters();
    }

    @PostMapping("{uuid}/charge")
    public Register chargeRegister(@PathVariable("uuid") UUID uuid, @RequestBody RegisterChargeDto registerChargeDto) {
        return registerService.chargeRegister(uuid, registerChargeDto.getAmount());
    }
}
