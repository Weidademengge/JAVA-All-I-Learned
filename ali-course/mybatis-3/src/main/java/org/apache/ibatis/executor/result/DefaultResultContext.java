/**
 *    Copyright 2009-2023 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.executor.result;

import org.apache.ibatis.session.ResultContext;

/**
 * 默认的 {@link ResultContext} 的实现类
 *
 * @author Clinton Begin
 */
public class DefaultResultContext<T> implements ResultContext<T> {

    /**
     * 当前结果对象
     */
    private T resultObject;
    /**
     * 总的结果对象的数量
     */
    private int resultCount;
    /**
     * 是否暂停
     */
    private boolean stopped;

    public DefaultResultContext() {
        resultObject = null;
        resultCount = 0;
        stopped = false; // 默认非暂停
    }

    @Override
    public T getResultObject() {
        return resultObject;
    }

    @Override
    public int getResultCount() {
        return resultCount;
    }

    @Override
    public boolean isStopped() {
        return stopped;
    }

    /**
     * 当前结果对象
     *
     * @param resultObject 当前结果对象
     */
    public void nextResultObject(T resultObject) {
        // 数量 + 1
        resultCount++;
        // 当前结果对象
        this.resultObject = resultObject;
    }

    @Override
    public void stop() {
        this.stopped = true;
    }

}