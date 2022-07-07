/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cn.com.qjun.cardboard.service.impl;

import cn.com.qjun.cardboard.common.SystemConstant;
import cn.com.qjun.cardboard.domain.StockInOrder;
import cn.com.qjun.cardboard.service.dto.StockReportDto;
import cn.com.qjun.cardboard.utils.SerialNumberGenerator;
import com.google.common.collect.Lists;
import me.zhengjie.utils.*;
import lombok.RequiredArgsConstructor;
import cn.com.qjun.cardboard.repository.StockInOrderRepository;
import cn.com.qjun.cardboard.service.StockInOrderService;
import cn.com.qjun.cardboard.service.dto.StockInOrderDto;
import cn.com.qjun.cardboard.service.dto.StockInOrderQueryCriteria;
import cn.com.qjun.cardboard.service.mapstruct.StockInOrderMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
 * @author RenQiang
 * @website https://el-admin.vip
 * @description 服务实现
 * @date 2022-06-18
 **/
@Service
@RequiredArgsConstructor
public class StockInOrderServiceImpl implements StockInOrderService {

    private final StockInOrderRepository stockInOrderRepository;
    private final StockInOrderMapper stockInOrderMapper;
    private final SerialNumberGenerator serialNumberGenerator;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Map<String, Object> queryAll(StockInOrderQueryCriteria criteria, Pageable pageable) {
        Page<StockInOrder> page = stockInOrderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(stockInOrderMapper::toDto));
    }

    @Override
    public List<StockInOrderDto> queryAll(StockInOrderQueryCriteria criteria) {
        List<StockInOrder> all = stockInOrderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder));
        return stockInOrderMapper.toDto(new ArrayList<>(new HashSet<>(all)));
    }

    @Override
    @Transactional
    public StockInOrderDto findById(String id) {
        StockInOrder stockInOrder = stockInOrderRepository.findById(id).orElseGet(StockInOrder::new);
        ValidationUtil.isNull(stockInOrder.getId(), "StockInOrder", "id", id);
        return stockInOrderMapper.toDto(stockInOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StockInOrderDto create(StockInOrder resources) {
        LocalDate date = resources.getStockInTime().toLocalDateTime().toLocalDate();
        resources.setId(serialNumberGenerator.generateStockInOrderId(date));
        resources.setDeleted(SystemConstant.DEL_FLAG_0);
        resources.getOrderItems()
                .forEach(item -> item.setStockInOrder(resources));
        return stockInOrderMapper.toDto(stockInOrderRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StockInOrder resources) {
        StockInOrder stockInOrder = stockInOrderRepository.findById(resources.getId()).orElseGet(StockInOrder::new);
        ValidationUtil.isNull(stockInOrder.getId(), "StockInOrder", "id", resources.getId());
        stockInOrder.copy(resources);
        stockInOrderRepository.save(stockInOrder);
    }

    @Override
    public void deleteAll(String[] ids) {
        List<StockInOrder> allById = stockInOrderRepository.findAllById(Arrays.asList(ids));
        for (StockInOrder stockInOrder : allById) {
            stockInOrder.setDeleted(SystemConstant.DEL_FLAG_1);
        }
        stockInOrderRepository.saveAll(allById);
    }

    @Override
    public void download(List<StockInOrderDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StockInOrderDto stockInOrder : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("入库时间", stockInOrder.getStockInTime());
            map.put("制单人", stockInOrder.getCreateBy());
            map.put("制单时间", stockInOrder.getCreateTime());
            map.put("更新人", stockInOrder.getUpdateBy());
            map.put("更新时间", stockInOrder.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public List<Map<String, Object>> groupingStatistics(LocalDate beginDate, LocalDate endDate) {
        return stockInOrderRepository.groupingStatistics(beginDate, endDate);
    }

    @Override
    public Map<String, Object> report(String reportType, StockInOrderQueryCriteria criteria, Integer pageNumber, Integer pageSize) {
        LocalDateTime startTime = criteria.getStockInTime().get(0).toLocalDateTime();
        LocalDateTime endTime = criteria.getStockInTime().get(1).toLocalDateTime();
        String resultSelect = String.join(" ", "select '入库' as `orderType`,",
                "daily".equals(reportType) ? "date(o.stock_in_time) as `date`," : String.format("'%s ~ %s' as `date`, ",
                        DateUtil.DFY_MD.format(startTime),
                        DateUtil.DFY_MD.format(endTime)),
                "w.name_ as `warehouseName`,", "m.name_ as `materialName`,", "dd.label as `materialCategory`,",
                "sum(oi.quantity) as `quantity`,",
                "sum(ifnull(oi.unit_price, 0) * oi.quantity) as `money`");
        String from = String.join(" ", "from biz_stock_in_order o",
                "join basic_warehouse w on o.warehouse_id = w.id", "join biz_stock_in_order_item oi on o.id = oi.stock_in_order_id",
                "join basic_material m on oi.material_id = m.id", "join sys_dict_detail dd on m.category = dd.`value`");
        StringBuilder where = new StringBuilder("where o.deleted = 0 and o.stock_in_time between ? and ?");
        List<Object> params = Lists.newArrayList(startTime, endTime);
        if (criteria.getWarehouseId() != null) {
            where.append(" and o.warehouse_id = ?");
            params.add(criteria.getWarehouseId());
        }
        if (StringUtils.isNotEmpty(criteria.getMaterialCategory())) {
            where.append(" and m.category = ?");
            params.add(criteria.getMaterialCategory());
        }
        if (criteria.getMaterialId() != null) {
            where.append(" and oi.material_id = ?");
            params.add(criteria.getMaterialId());
        }
        StringBuilder group = new StringBuilder("group by w.id, m.id");
        StringBuilder order = new StringBuilder("order by w.id, m.id");
        if ("daily".equals(reportType)) {
            group.insert(9, "date(o.stock_in_time), ");
            order.insert(9, "date(o.stock_in_time), ");
        }
        String countSql = String.format("select count(*) from (%s) t", String.join(" ", resultSelect, from, where.toString(), group.toString()));
        Long total = jdbcTemplate.queryForObject(countSql, Long.class, params.toArray());
        String limit = "limit ?, ?";
        params.add((pageNumber - 1) * pageSize);
        params.add(pageSize);
        List<StockReportDto> content = jdbcTemplate.query(String.join(" ", resultSelect, from, where.toString(), group.toString(), order.toString(), limit),
                StockReportDto.ROW_MAPPER, params.toArray());
        return PageUtil.toPage(content, total);
    }
}