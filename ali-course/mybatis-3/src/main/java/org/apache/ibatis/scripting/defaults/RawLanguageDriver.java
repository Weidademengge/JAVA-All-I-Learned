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
package org.apache.ibatis.scripting.defaults;

import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

/**
 * As of 3.2.4 the default XML language is able to identify static statements
 * and create a {@link RawSqlSource}. So there is no need to use RAW unless you
 * want to make sure that there is not any dynamic tag for any reason.
 *
 * {@link RawSqlSource} 语言驱动器实现类，确保创建的 SqlSource 是 RawSqlSource 类。
 *
 * @since 3.2.0
 * @author Eduardo Macarron
 */
public class RawLanguageDriver extends XMLLanguageDriver {

    @Override
    public SqlSource createSqlSource(Configuration configuration, XNode script, Class<?> parameterType) {
        // 调用父类，创建 SqlSource 对象
        SqlSource source = super.createSqlSource(configuration, script, parameterType);
        // 校验创建的是 RawSqlSource 对象
        checkIsNotDynamic(source);
        return source;
    }

    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        // 调用父类，创建 SqlSource 对象
        SqlSource source = super.createSqlSource(configuration, script, parameterType);
        // 校验创建的是 RawSqlSource 对象
        checkIsNotDynamic(source);
        return source;
    }

    /**
     * 校验是 RawSqlSource 对象
     *
     * @param source 创建的 SqlSource 对象
     */
    private void checkIsNotDynamic(SqlSource source) {
        if (!RawSqlSource.class.equals(source.getClass())) {
            throw new BuilderException("Dynamic content is not allowed when using RAW language");
        }
    }

}