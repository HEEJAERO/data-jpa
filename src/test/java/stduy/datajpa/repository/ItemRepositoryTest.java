package stduy.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import stduy.datajpa.entity.Item;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;
    @Test
    public void save(){
        Item item = new Item("A");
        // 이와 같이 id를 generateValue 를 통해 생성하는것이 아닌 값을 할당해주는 형태를 사용하게 된다면
        //SimpleJpaRepository의 구조상 ,새로운 데이터가 아니라고 판단 -> merge 가 동작하게됨
        // db에서 A 를 찾게됨 (select 문 실행)-> 없다는것이 판단되어 이후에 insert -> 비효율적
        // item 부분에 implements  Persistable<String> 하고 isNew 부분을 작성하여 해결

        itemRepository.save(item);
    }
}