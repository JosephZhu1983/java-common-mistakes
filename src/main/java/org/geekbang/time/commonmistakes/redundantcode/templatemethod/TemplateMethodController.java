package org.geekbang.time.commonmistakes.redundantcode.templatemethod;

import lombok.extern.slf4j.Slf4j;
import org.geekbang.time.commonmistakes.redundantcode.templatemethod.right.AbstractCart;
import org.geekbang.time.commonmistakes.redundantcode.templatemethod.wrong.InternalUserCart;
import org.geekbang.time.commonmistakes.redundantcode.templatemethod.wrong.NormalUserCart;
import org.geekbang.time.commonmistakes.redundantcode.templatemethod.wrong.VipUserCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("templatemethod")
public class TemplateMethodController {

    private static Map<Long, Integer> items = new HashMap<>();

    static {
        items.put(1L, 2);
        items.put(2L, 4);
    }

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("wrong")
    public Cart wrong(@RequestParam("userId") int userId) {
        String userCategory = Db.getUserCategory(userId);

        if (userCategory.equals("Normal")) {
            NormalUserCart normalUserCart = new NormalUserCart();
            return normalUserCart.process(userId, items);
        }

        if (userCategory.equals("Vip")) {
            VipUserCart vipUserCart = new VipUserCart();
            return vipUserCart.process(userId, items);
        }

        if (userCategory.equals("Internal")) {
            InternalUserCart internalUserCart = new InternalUserCart();
            return internalUserCart.process(userId, items);
        }

        return null;
    }

    @GetMapping("right")
    public Cart right(@RequestParam("userId") int userId) {
        String userCategory = Db.getUserCategory(userId);
        AbstractCart cart = (AbstractCart) applicationContext.getBean(userCategory + "UserCart");
        return cart.process(userId, items);
    }
}
