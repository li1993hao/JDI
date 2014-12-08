package com.tiptimes.tp.common;

/**
 * Created by haoli on 14/11/4.
 */
public interface DataParser {
    public void parse(String result, ActionBundle actionBundle,ActionInfo actionInfo) throws Exception;
}
