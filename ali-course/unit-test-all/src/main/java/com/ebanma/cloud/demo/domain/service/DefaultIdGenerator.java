package com.ebanma.cloud.demo.domain.service;

import org.springframework.stereotype.Component;

/**
 * @author duym
 * @version $ Id: DefaultIdGenerator, v 0.1 2023/05/22 9:50 duym Exp $
 */
@Component
public class DefaultIdGenerator implements IdGenerator{
    @Override
    public Long next() {
        return null;
    }
}
