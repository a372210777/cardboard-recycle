package com.br.modules.biz.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExcelListener <T> extends AnalysisEventListener<T> {

        List<T> list = new ArrayList<T>();

        /**
        * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。这里我们传入自己的function作为参数
        */
        ExcelConsumer<T> consumer;

        public ExcelListener() {
        }

        /**
        * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
        */
        public ExcelListener(ExcelConsumer consumer) {
                this.consumer = consumer;
        }


        @Override
        public void invoke(T t, AnalysisContext analysisContext) {
                try {
                        list.add(t);
                } catch (Exception e) {
                        throw new RuntimeException("第" + (analysisContext.getCurrentRowNum() + 1) + "行数据错误! "+e.getMessage());
                }

        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                //执行函数
                consumer.execute(list);
        }

}
