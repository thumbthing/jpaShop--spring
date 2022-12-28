package jpabook.jpashop.service;

import jpabook.jpashop.domain.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 적용 범위 : 만드는 모든 메소드에 전부 적용
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    // 읽기만 하는게 아니어서 한번 더 적어서 덮어 씌우는 것
    @Transactional
    public Long saveItem(Item item) {
        itemRepository.save(item);
        return item.getId();
    }

    public Item findItem(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
