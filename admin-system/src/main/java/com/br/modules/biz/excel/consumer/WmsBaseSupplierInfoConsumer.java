package com.br.modules.biz.excel.consumer;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import com.br.common.C;
import com.br.common.DC;
import com.br.common.enums.RoleEnum;
import com.br.common.enums.StatusEnum;
import com.br.modules.biz.domain.WmsBaseSupplierInfo;
import com.br.modules.biz.excel.ExcelConsumer;
import com.br.modules.biz.excel.excelBean.WmsBaseSupplierInfoEB;
import com.br.modules.biz.repository.WmsBaseSupplierInfoRepository;
import com.br.modules.system.domain.DictDetail;
import com.br.modules.system.domain.User;
import com.br.modules.system.repository.UserRepository;
import com.br.modules.system.service.DictDetailService;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class WmsBaseSupplierInfoConsumer implements ExcelConsumer<WmsBaseSupplierInfoEB> {

    private final WmsBaseSupplierInfoRepository wmsBaseSupplierInfoRepository;
    private final DictDetailService dictDetailService;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void execute(List<WmsBaseSupplierInfoEB> list) {

        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        ArrayList<WmsBaseSupplierInfo> datas = Lists.newArrayList();
        for (int i=0; i < list.size(); i++) {
            WmsBaseSupplierInfoEB eb = list.get(i);
            int no = i+2;
            Assert.hasText(eb.getCode(), String.format("第%d行的供应商编码必填", no));
            Assert.hasText(eb.getShortName(), String.format("第%d行的供应商名称必填", no));
            Assert.hasText(eb.getFullName(), String.format("第%d行的供应商全称必填", no));

            WmsBaseSupplierInfo data = new WmsBaseSupplierInfo();
            if (StringUtils.hasText(eb.getSignType())) {
                DictDetail dictDetail = dictDetailService.findByDictNameAndLabel(DC.Supplier.SIGN_TYPE, eb.getSignType());
                Assert.notNull(dictDetail, String.format("第%d行的签约类型填写错误", no));
                data.setSignType(Integer.parseInt(dictDetail.getValue()));
            }

            if (StringUtils.hasText(eb.getSettlementType())) {
                DictDetail dictDetail = dictDetailService.findByDictNameAndLabel(DC.Supplier.SETTLEMENT_TYPE, eb.getSettlementType());
                Assert.notNull(dictDetail, String.format("第%d行的结算方式填写错误", no));
                data.setSettlementType(Integer.parseInt(dictDetail.getValue()));
            }

            if (StringUtils.hasText(eb.getUsername())) {
                User user = userRepository.findByUsername(eb.getUsername());
                Assert.notNull(user, String.format("系统未找到第%d行的责任客服[%s]信息", no, eb.getUsername()));
                boolean isCustomerServiceRole = user.getRoles().stream().anyMatch(r ->  r.getName().equals(RoleEnum.CUSTOMER_SERVICE.getName()));
                Assert.isTrue(isCustomerServiceRole, String.format("第%d行的责任客服[%s]不是客服角色！", no, eb.getUsername()));
                data.setUser(user);
            }

            BeanUtil.copyProperties(eb, data, CopyOptions.create().setIgnoreProperties("signType", "settlementType"));
            data.setStatus(StatusEnum.STATUS_ENABLE.getValue());
            datas.add(data);
        }

        wmsBaseSupplierInfoRepository.saveAll(datas);

    }
}
