package com.br.modules.biz.excel;

import java.util.List;

@FunctionalInterface
public interface ExcelConsumer<E> {
    void execute(List<E> e);
}
