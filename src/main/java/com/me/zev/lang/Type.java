package com.me.zev.lang;

public interface Type<T> extends Parseable {

    T convert(String code);

}
