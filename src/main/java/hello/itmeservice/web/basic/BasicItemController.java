package hello.itmeservice.web.basic;


import hello.itmeservice.domain.item.Item;
import hello.itmeservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor //final인 애들 가지고 생성자 주입 자동 생성 (lombok)
public class BasicItemController {

    private final ItemRepository itemRepository;
//
//    @Autowired // 생성자가 하나면 생략해도 의존관계 주입됨
//    public BasicItemController(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    // RequestParam으로 받아오기
//    @PostMapping("/add")
//    public String addItemV1(@RequestParam String itemName
//                        , @RequestParam int price
//                        , @RequestParam Integer quantity
//                        , Model model) {
//        Item item = new Item();
//        item.setItemName(itemName);
//        item.setPrice(price);
//        item.setQuantity(quantity);
//
//        itemRepository.save(item);
//
//        model.addAttribute("item", item);
//
//        return "basic/item";
//    }


    // ModelAttribute로 사용햐기
    /*  modelAttribute의 역할 두가지
        1. 요청파라미터를 가져와줌
        2. modelAttribute로 지정한 객체를 model에 자동으로 담아줌
    * */
//    @PostMapping("/add")
//    public String addItemV2(@ModelAttribute("item") Item item, Model model) { //그래서 여기서 Model 파라미터도 사실 없어도됨 지워도되는건데 일단 남겨둠
//
//        itemRepository.save(item);
//
//        // ModelAttrebute 사용하면 해당이름으로 동일하게 알아서 addAttribute까지 해주기 때문에 생략 가능
////        model.addAttribute("item", item);
//
//        return "basic/item";
//    }


//    @PostMapping("/add")
//    public String addItemV3(@ModelAttribute Item item){
//        /*
//        *  modelAttribute의 name을 지정해주지 않으면
//        * 받아오는 클래스의 앞에만 소문자로 바꾼게 dlfault로 이름으로 지정됨 (Item-> item)
//        **/
//        itemRepository.save(item);
//
//        return "basic/item";
//    }


//    @PostMapping("/add")
//    public String addItemV4(Item item) { //modelAttribute의 어노테이션 생략 가능 // 하지만 개발자가 이해가 쉽도록 어노테이션 붙이는게 낫지 않을까? 무튼 최향
//        itemRepository.save(item);
//
//        return "basic/item";
//    }


    /*
    [ PRD / Post, Redirect, Get 패턴 ]
    리다이렉트 하지 않으면 /basic/add url이 호출된 채로 계속 남아
    새로고침 할 때마다 계속 add됨 ( 새로고침은 마지막에 한 행동을 다시 요청하는 것이기 때문에)
    그래서 리다이렉트를 해주어야함 -> 리다이렉트는 웹브라우저 입장에서 아예 새롭게 요청하는 것이기 때문에 이후에 새로고침 해봤자 페이지요청하는 GET 요청만 계속 나감
    * */
//    @PostMapping("/add")
//    public String addItemV5(Item item) { //modelAttribute의 어노테이션 생략 가능 // 하지만 개발자가 이해가 쉽도록 어노테이션 붙이는게 낫지 않을까? 무튼 최향
//        itemRepository.save(item);
//
//        return "redirect:/basic/items/" + item.getId(); // 이건 또 띄어쓰기나 그런게 있으면 문제가될 수있는데 이건 @RedirectAttribuete로 해결가능
//    }


    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) { //modelAttribute의 어노테이션 생략 가능 // 하지만 개발자가 이해가 쉽도록 어노테이션 붙이는게 낫지 않을까? 무튼 최향
        Item saveItem = itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", saveItem.getId());
        redirectAttributes.addAttribute("status",true); // return에서 치환되지 않는것은 쿼리 파라미터로 넘어감 //화면에서 true면 '저장되었습니다' 이런식으로 노출시켜주기 처리 가능
        return "redirect:/basic/items/{itemId}";
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item){
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }
        // 스프링에서 redirect 하는 법.
        // redirect 하면 /basic/items/1 이렇게 아예 새로운 경로가 나옴
        // 안하면 /basic/items/1/edit 이렇게 나옴





    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct // 해당 빈의 의존관계가 모두 주입되고 나면 초기화 용도로 호출된다. // 여기서는 테스트용 데이터를 넣기 위해 사용
    public void init() {
        itemRepository.save(new Item("testA", 10000, 10));
        itemRepository.save(new Item("testB", 20000, 20));
    }


}
