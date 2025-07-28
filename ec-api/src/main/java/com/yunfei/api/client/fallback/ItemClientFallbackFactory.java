package com.yunfei.api.client.fallback;

import com.yunfei.api.client.ItemClient;
import com.yunfei.api.dto.ItemDTO;
import com.yunfei.api.dto.OrderDetailDTO;
import com.yunfei.common.utils.CollUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.Collection;
import java.util.List;

@Slf4j
public class ItemClientFallbackFactory implements FallbackFactory<ItemClient> {

    @Override
    public ItemClient create(Throwable cause) {
        return new ItemClient() {
            @Override
            public List<ItemDTO> queryItemByIds(Collection<Long> ids) {
                log.error("get ItemClient#queryItemByIds exception，params：{}", ids, cause);
                // fail, return null;
                return CollUtils.emptyList();
            }

            @Override
            public void deductStock(Collection<OrderDetailDTO> items) {
                // fail, exception
                throw new RuntimeException(cause);
            }
        };
    }
}
