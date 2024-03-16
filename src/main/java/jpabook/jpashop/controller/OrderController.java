package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model) {

        // 만들어 놓은 모든 멤버를 가져옴
        List<Member> members = memberService.findMembers();
        // findItems로 item들을 모두 가져옴
        List<Item> items = itemService.findItems();

        // 가져온 것들을 model에 담아서 orderForm에 넘김
        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    // @RequestParam은 Form submit에서 선택된 value가 넘어오게 됨.
    // Form에 있는 name으로 넘어오게 될 것임
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {

        // 예제를 단순화하기 위해 하나의 상품만 주문하도록 함
        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }
}
