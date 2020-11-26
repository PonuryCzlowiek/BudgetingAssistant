package org.ametyst.budgeting.register;

import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("registers")
@RestController
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @GetMapping("/")
    public List<Register> getAllRegisters() {
        return registerService.getAllRegisters();
    }

    @PostMapping("{uuid}/charge")
    public void chargeRegister(@PathVariable("uuid") UUID uuid) {
        registerService.chargeRegister(uuid, 50.0);
    }
}
