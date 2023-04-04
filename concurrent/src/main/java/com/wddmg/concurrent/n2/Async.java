package com.wddmg.concurrent.n2;

import com.wddmg.concurrent.Constants;
import com.wddmg.concurrent.n2.util.FileReader;
import lombok.extern.slf4j.Slf4j;

/**
 * @author duym
 * @version $ Id: Async, v 0.1 2023/04/03 9:29 duym Exp $
 */
@Slf4j(topic = "c.Async")
public class Async {

    public static void main(String[] args) {
        new Thread(()-> FileReader.read(Constants.BOOK_FULL_PATH)).start();
        log.debug("do other thing...");
    }
}
