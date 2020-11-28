package org.ametyst.budgeting.register;

import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("{uuid}/recharge")
    public ResponseEntity<Register> rechargeRegister(@PathVariable("uuid") UUID uuid, @RequestBody @Valid RegisterRechargeDto registerRechargeDto) {
        return ResponseEntity.ok(registerService.rechargeRegister(uuid, registerRechargeDto.getAmount()));
    }

    @PostMapping("{uuid}/transfer")
    public ResponseEntity<Register> transfer(@PathVariable("uuid") UUID uuid, @RequestBody @Valid RegisterTransferDto registerTransferDto) {
        Register transfer = registerService.transfer(uuid, registerTransferDto.getTargetRegisterUUID(), registerTransferDto.getAmount());
        return ResponseEntity.ok(transfer);
    }
}
