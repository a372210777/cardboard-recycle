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
package com.br.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import com.br.annotation.DataPermission;
import com.br.annotation.Query;
import lombok.val;

import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.util.*;

/**
 *
 * @date 2019-6-4 14:59:48
 */
@Slf4j
@SuppressWarnings({"unchecked","all"})
public class QueryHelp {

    public static final String START_TIME_COLUMN = "startTime";
    public static final String END_TIME_COLUMN = "endTime";
    public static final String RANGE_DAY_TYPE_COLUMN = "rangeDayType";
    public static final String EFFECTIVE_DAYS_COLUMN = "effectiveDays";


    public static <R, Q> Predicate getPredicate(Root<R> root, Q query, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<>();
        if(query == null){
            return cb.and(list.toArray(new Predicate[0]));
        }
        // 数据权限验证
        DataPermission permission = query.getClass().getAnnotation(DataPermission.class);
        if(permission != null){
            // 获取数据权限
            List<Long> dataScopes = SecurityUtils.getCurrentUserDataScope();
            if(CollectionUtil.isNotEmpty(dataScopes)){
                if(StringUtils.isNotBlank(permission.joinName()) && StringUtils.isNotBlank(permission.fieldName())) {
                    Join join = root.join(permission.joinName(), JoinType.LEFT);
                    list.add(getExpression(permission.fieldName(),join, root).in(dataScopes));
                } else if (StringUtils.isBlank(permission.joinName()) && StringUtils.isNotBlank(permission.fieldName())) {
                    list.add(getExpression(permission.fieldName(),null, root).in(dataScopes));
                }
            }
        }
        try {
            List<Field> fields = getAllFields(query.getClass(), new ArrayList<>());
            for (Field field : fields) {
                boolean accessible = field.isAccessible();
                // 设置对象的访问权限，保证对private的属性的访问
                field.setAccessible(true);
                Query q = field.getAnnotation(Query.class);
                if (q != null) {
                    String propName = q.propName();
                    String joinName = q.joinName();
                    String blurry = q.blurry();
                    String attributeName = isBlank(propName) ? field.getName() : propName;
                    Class<?> fieldType = field.getType();
                    Object val = field.get(query);
                    if (ObjectUtil.isNull(val) || "".equals(val)) {
                        continue;
                    }
                    Join join = null;
                    // 模糊多字段
                    if (ObjectUtil.isNotEmpty(blurry)) {
                        String[] blurrys = blurry.split(",");
                        List<Predicate> orPredicate = new ArrayList<>();
                        for (String s : blurrys) {
                            orPredicate.add(cb.like(root.get(s).as(String.class), "%" + val.toString() + "%"));
                        }
                        Predicate[] p = new Predicate[orPredicate.size()];
                        list.add(cb.or(orPredicate.toArray(p)));
                        continue;
                    }
                    if (ObjectUtil.isNotEmpty(joinName)) {
                        String[] joinNames = joinName.split(">");
                        for (String name : joinNames) {
                            switch (q.join()) {
                                case LEFT:
                                    if(ObjectUtil.isNotNull(join) && ObjectUtil.isNotNull(val)){
                                        join = join.join(name, JoinType.LEFT);
                                    } else {
                                        join = root.join(name, JoinType.LEFT);
                                    }
                                    break;
                                case RIGHT:
                                    if(ObjectUtil.isNotNull(join) && ObjectUtil.isNotNull(val)){
                                        join = join.join(name, JoinType.RIGHT);
                                    } else {
                                        join = root.join(name, JoinType.RIGHT);
                                    }
                                    break;
                                case INNER:
                                    if(ObjectUtil.isNotNull(join) && ObjectUtil.isNotNull(val)){
                                        join = join.join(name, JoinType.INNER);
                                    } else {
                                        join = root.join(name, JoinType.INNER);
                                    }
                                    break;
                                default: break;
                            }
                        }
                    }
                    switch (q.type()) {
                        case EQUAL:
                            list.add(cb.equal(getExpression(attributeName,join,root)
                                    .as((Class<? extends Comparable>) fieldType),val));
                            break;
                        case GREATER_THAN:
                            list.add(cb.greaterThanOrEqualTo(getExpression(attributeName,join,root)
                                    .as((Class<? extends Comparable>) fieldType), (Comparable) val));
                            break;
                        case LESS_THAN:
                            list.add(cb.lessThanOrEqualTo(getExpression(attributeName,join,root)
                                    .as((Class<? extends Comparable>) fieldType), (Comparable) val));
                            break;
                        case LESS_THAN_NQ:
                            list.add(cb.lessThan(getExpression(attributeName,join,root)
                                    .as((Class<? extends Comparable>) fieldType), (Comparable) val));
                            break;
                        case INNER_LIKE:
                            list.add(cb.like(getExpression(attributeName,join,root)
                                    .as(String.class), "%" + val.toString() + "%"));
                            break;
                        case LEFT_LIKE:
                            list.add(cb.like(getExpression(attributeName,join,root)
                                    .as(String.class), "%" + val.toString()));
                            break;
                        case RIGHT_LIKE:
                            list.add(cb.like(getExpression(attributeName,join,root)
                                    .as(String.class), val.toString() + "%"));
                            break;
                        case IN:
                            if (CollUtil.isNotEmpty((Collection<Long>)val)) {
                                list.add(getExpression(attributeName,join,root).in((Collection<Long>) val));
                            }
                            break;
                        case NOT_EQUAL:
                            list.add(cb.notEqual(getExpression(attributeName,join,root), val));
                            break;
                        case NOT_NULL:
                            list.add(cb.isNotNull(getExpression(attributeName,join,root)));
                            break;
                        case IS_NULL:
                            list.add(cb.isNull(getExpression(attributeName,join,root)));
                            break;
                        case BETWEEN:
                            List<Object> between = new ArrayList<>((List<Object>)val);
                            list.add(cb.between(getExpression(attributeName, join, root).as((Class<? extends Comparable>) between.get(0).getClass()),
                                    (Comparable) between.get(0), (Comparable) between.get(1)));
                            break;
                        case COUPON_STATUS:
                            queryCouponStatus(val.toString(), list, root, cb);
                            break;
                        default: break;
                    }
                }
                field.setAccessible(accessible);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        int size = list.size();
        return cb.and(list.toArray(new Predicate[size]));
    }

    private static void queryCouponStatus(String status, List<Predicate> list, Root root, CriteriaBuilder cb) {
        Date now = new Date();
        switch (status) {

            case "NEW":
                // 开始和结束时间都大于now
                list.add(cb.greaterThan(root.get(START_TIME_COLUMN).as(Date.class),  now));
                list.add(cb.greaterThan(root.get(END_TIME_COLUMN).as(Date.class),  now));
                break;
            case "START":

                List<Predicate> orPredicate = new ArrayList<>();
                // 开始时间小于等于now， 结束时间大于等于now
                Predicate startPredicate = cb.lessThanOrEqualTo(root.get(START_TIME_COLUMN).as(Date.class), now);
                Predicate endPredicate = cb.greaterThanOrEqualTo(root.get(END_TIME_COLUMN).as(Date.class), now);

                // 或者时间范围类型是动态时，effective_days大于0
                Predicate typePredicate = cb.equal(root.get(RANGE_DAY_TYPE_COLUMN).as(String.class), "DYNAMICTIME");
                Predicate effectivePredicate = cb.greaterThan(root.get(EFFECTIVE_DAYS_COLUMN).as(Integer.class), 0);

                Predicate condition1 = cb.and(startPredicate, endPredicate);
                Predicate condition2 = cb.and(typePredicate, effectivePredicate);
                orPredicate.add(condition1);
                orPredicate.add(condition2);

                Predicate[] p = new Predicate[orPredicate.size()];
                list.add(cb.or(orPredicate.toArray(p)));

                break;
            case "END":
                // 开始和结束时间都小于now
                list.add(cb.lessThan(root.get(START_TIME_COLUMN).as(Date.class),  now));
                list.add(cb.lessThan(root.get(END_TIME_COLUMN).as(Date.class),  now));
                break;
            case "CLOSE":

                List<Predicate> orPredicateClose = new ArrayList<>();

                // 时间范围类型是动态时，effective_days小于等于0
                Predicate dynPredicate = cb.equal(root.get(RANGE_DAY_TYPE_COLUMN).as(String.class), "DYNAMICTIME");
                Predicate effectivePredicate1 = cb.lessThanOrEqualTo(root.get(EFFECTIVE_DAYS_COLUMN).as(Integer.class), 0);

                // 时间范围类型是固定时，开始和结束时间为空
                Predicate fixedPredicate = cb.equal(root.get(RANGE_DAY_TYPE_COLUMN).as(String.class), "FIXEDTIME");
                Predicate startNullPredicate = cb.isNull(root.get(START_TIME_COLUMN));
                Predicate endNullPredicate = cb.isNull(root.get(END_TIME_COLUMN));

                Predicate condition3 = cb.and(dynPredicate, effectivePredicate1);
                Predicate condition4 = cb.and(fixedPredicate, startNullPredicate, endNullPredicate);
                orPredicateClose.add(condition3);
                orPredicateClose.add(condition4);

                Predicate[] p1 = new Predicate[orPredicateClose.size()];
                list.add(cb.or(orPredicateClose.toArray(p1)));

                break;
            default:
        }
    }

    @SuppressWarnings("unchecked")
    private static <T, R> Expression<T> getExpression(String attributeName, Join join, Root<R> root) {
        if (ObjectUtil.isNotEmpty(join)) {
            return join.get(attributeName);
        } else {
            return root.get(attributeName);
        }
    }

    private static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static List<Field> getAllFields(Class clazz, List<Field> fields) {
        if (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            getAllFields(clazz.getSuperclass(), fields);
        }
        return fields;
    }
}
