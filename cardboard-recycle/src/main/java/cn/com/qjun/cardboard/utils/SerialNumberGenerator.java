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

    public String generateStockInOrderId() {
        return generateSerialNumber(SERIAL_NUMBER_HASH_ITEM_STOCK_IN_ORDER, SERIAL_NUMBER_PREFIX_STOCK_IN_ORDER);
    }

    public String generateStockOutOrderId() {
        return generateSerialNumber(SERIAL_NUMBER_HASH_ITEM_STOCK_OUT_ORDER, SERIAL_NUMBER_PREFIX_STOCK_OUT_ORDER);
    }

    public String generateQuantityCheckCertId() {
        return generateSerialNumber(SERIAL_NUMBER_HASH_ITEM_QUALITY_CHECK, SERIAL_NUMBER_PREFIX_QUALITY_CHECK);
    }

    public String generateWaybillId() {
        return generateSerialNumber(SERIAL_NUMBER_HASH_ITEM_WAYBILL, SERIAL_NUMBER_PREFIX_WAYBILL);
    }

    public String generateStatementId() {
        return generateSerialNumber(SERIAL_NUMBER_HASH_ITEM_STATEMENT, SERIAL_NUMBER_PREFIX_STATEMENT);
    }

    private String generateSerialNumber(String hashItem, String prefix) {
        double incrResult = redisUtils.hincr(getRedisHashKey(), hashItem, 1);
        int serialNumber = Double.valueOf(incrResult).intValue();
        if (serialNumber == 1) {
            redisUtils.expire(getRedisHashKey(), 1, TimeUnit.DAYS);
        }
        return String.format("%s-%s-%06d", prefix, LocalDate.now().format(DATE_FORMATTER), serialNumber);
    }

    private static String getRedisHashKey() {
        return String.format("%s:%s", SERIAL_NUMBER_HASH_KEY, LocalDate.now().format(DATE_FORMATTER));
    }
}
