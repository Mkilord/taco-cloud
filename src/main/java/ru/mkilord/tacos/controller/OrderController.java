package ru.mkilord.tacos.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import ru.mkilord.tacos.data.OrderRepository;
import ru.mkilord.tacos.entites.TacoOrder;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
@AllArgsConstructor
public class OrderController {
    private final OrderRepository orderRepo;

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus) {
        log.info(errors.toString());
        if (errors.hasErrors()) return "/orderForm";
        orderRepo.save(order);
        sessionStatus.setComplete();
        return "redirect:/order_created";
    }
}
