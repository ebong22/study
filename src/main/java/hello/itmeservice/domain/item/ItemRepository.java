package hello.itmeservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository //repository 안에 @component 있어서 component scan의 대상이 됨
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>(); // static
    private static long sequence = 0L; // static - 스프링이 싱글톤으로 관리해주긴 하지만 일단 넣는다함
    // 실무에서 동시에 여러 쓰레드가 접근하는거엔 HashMap을 쓰면안되고 사용하고 싶으면 ConcurrentHashMAp 사용해야하고,
    // long도 여러곳에서 접근하면 다른거 사용해야함 (ex. Atomic:Long)

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
        //store를 그냥 반환해도 되는데 ArrayList로 한번 감싸서 반환하게되면 이 arrayList에 다른 값을 넣어도 실제 store는 변하지 않기 때문에 안전하게 한번 감싼것
    }

    public void update(Long itemId, Item updateParam) { //정석으로 하려면 Item updateParam으로 할게아니라 updatePAram이라는 클래스하나 더 만들어야함 (왜냐면 param은 id를 안쓸기 때문에 name, price, quntity만 명확하게 사용하는거로 하나 만들어주는게 맞음)
        // 편한거, 명확성 고민되면 무조건 명확성을 따라라
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore(){   //테스트용
        store.clear(); // store의 해쉬맵 데이터 비우기
    }
}
