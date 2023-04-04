package com.wddmg.concurrent.n2;

import com.wddmg.concurrent.Constants;
import com.wddmg.concurrent.n2.util.FileReader;
import lombok.extern.slf4j.Slf4j;


/**
 * 同步等待
 * @author duym
 * @version $ Id: Sync, v 0.1 2023/04/03 9:18 duym Exp $
 */
@Slf4j(topic = "c.Sync")
public class Sync {

    public static void main(String[] args) {
        FileReader.read(Constants.BOOK_FULL_PATH);
        log.debug("do other things ...");

    }
}
