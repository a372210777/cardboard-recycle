package cn.com.qjun.cardboard.utils;

import lombok.RequiredArgsConstructor;
import me.zhengjie.utils.RedisUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @author RenQiang
 * @date 2022/6/21
 */
@Component
@RequiredArgsConstructor
public class SerialNumberGenerator {
    /**
     * 生成流水号的redis key
     */
    private static final String SERIAL_NUMBER_HASH_KEY = "cardboard:serialNumber",
            SERIAL_NUMBER_HASH_ITEM_STOCK_IN_ORDER = "stockInOrder",
            SERIAL_NUMBER_HASH_ITEM_STOCK_OUT_ORDER = "stockOutOrder",
            SERIAL_NUMBER_HASH_ITEM_QUALITY_CHECK = "qualityCheck",
            SERIAL_NUMBER_HASH_ITEM_WAYBILL = "waybill",
            SERIAL_NUMBER_HASH_ITEM_STATEMENT = "statement";
    /**
     * 流水号前缀
     */
    private static final String SERIAL_NUMBER_PREFIX_STOCK_IN_ORDER = "IO",
            SERIAL_NUMBER_PREFIX_STOCK_OUT_ORDER = "OO",
            SERIAL_NUMBER_PREFIX_QUALITY_CHECK = "CO",
            SERIAL_NUMBER_PREFIX_WAYBILL = "TO",
            SERIAL_NUMBER_PREFIX_STATEMENT = "BO";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final RedisUtils redisUtils;

    public String generateStockInOrderId(LocalDate date) {
        return generateSerialNumber(SERIAL_NUMBER_HASH_ITEM_STOCK_IN_ORDER, SERIAL_NUMBER_PREFIX_STOCK_IN_ORDER, date);
    }

    public String generateStockOutOrderId(LocalDate date) {
        return generateSerialNumber(SERIAL_NUMBER_HASH_ITEM_STOCK_OUT_ORDER, SERIAL_NUMBER_PREFIX_STOCK_OUT_ORDER, date);
    }

    public String generateQuantityCheckCertId(LocalDate date) {
        return generateSerialNumber(SERIAL_NUMBER_HASH_ITEM_QUALITY_CHECK, SERIAL_NUMBER_PREFIX_QUALITY_CHECK, date);
    }

    public String generateWaybillId(LocalDate date) {
        return generateSerialNumber(SERIAL_NUMBER_HASH_ITEM_WAYBILL, SERIAL_NUMBER_PREFIX_WAYBILL, date);
    }

    public String generateStatementId(LocalDate date) {
        return generateSerialNumber(SERIAL_NUMBER_HASH_ITEM_STATEMENT, SERIAL_NUMBER_PREFIX_STATEMENT, date);
    }

    private String generateSerialNumber(String hashItem, String prefix, LocalDate date) {
        double incrResult = redisUtils.hincr(getRedisHashKey(date), hashItem, 1);
        int serialNumber = Double.valueOf(incrResult).intValue();
        if (serialNumber == 1) {
            redisUtils.expire(getRedisHashKey(date), 1, TimeUnit.DAYS);
        }
        return String.format("%s-%s-%06d", prefix, date.format(DATE_FORMATTER), serialNumber);
    }

    private static String getRedisHashKey(LocalDate date) {
        return String.format("%s:%s", SERIAL_NUMBER_HASH_KEY, date.format(DATE_FORMATTER));
    }
}
