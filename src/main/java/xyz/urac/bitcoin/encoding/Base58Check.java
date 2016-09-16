package xyz.urac.bitcoin.encoding;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Base58Check {
  private final static Logger logger = LoggerFactory.getLogger(Base58Check.class);

  private static MessageDigest sha256;
  private static Base58 base58;

  static {
    try {
      sha256 = MessageDigest.getInstance("SHA-256");
      base58 = new Base58();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
  }

  public static String doEncode(String data) {
    return doEncode(data, "00");
  }

  public static String doEncode(String data, String prefix) {
    return doEncode(data, Hex.decode(prefix)[0]);
  }

  public static String doEncode(String data, byte prefix) {
    return encode(Hex.decode(data), prefix);
  }

  public static String encode(byte[] data, String prefix) {
    return encode(data, Hex.decode(prefix)[0]);
  }

  public static String encode(byte[] data) {
    return encode(data, (byte) 0);
  }

  public static String encode(byte[] data, byte prefix) {
    byte[] prefixData = new byte[1 + data.length];
    prefixData[0] = prefix;
    for (int i = 0; i < data.length; i++) {
      prefixData[1 + i] = data[i];
    }
    byte[] checksum = getCheck(data, prefix);
    byte[] prefixDataCheck = new byte[prefixData.length + checksum.length];
    for (int i = 0; i < prefixData.length; i++) {
      prefixDataCheck[i] = prefixData[i];
    }
    for (int i = 0; i < checksum.length; i++) {
      prefixDataCheck[prefixData.length + i] = checksum[i];
    }
    return base58.encode(prefixDataCheck);
  }

  private static byte[] getCheck(byte[] data, byte prefix) {
    byte[] prefixData = new byte[1 + data.length];
    prefixData[0] = prefix;
    for (int i = 0; i < data.length; i++) {
      prefixData[1 + i] = data[i];
    }
    byte[] checksum = sha256.digest(sha256.digest(prefixData));
    byte[] check = new byte[4];
    for (int i = 0; i < 4; i++) {
      check[i] = checksum[i];
    }
    return check;
  }

  public static Map<String, Object> decode(String string) {
    Map<String, Object> map = new HashMap<>();
    byte[] prefixDataCheck = base58.decode(string);
    byte prefix = prefixDataCheck[0];
    byte[] data = new byte[prefixDataCheck.length - 5];
    byte[] checksum = new byte[4];
    for (int i = 0; i < data.length; i++) {
      data[i] = prefixDataCheck[1 + i];
    }
    for (int i = 0; i < 4; i++) {
      checksum[i] = prefixDataCheck[1 + data.length + i];
    }
    byte[] newChecksum = getCheck(data, prefix);
    if (checksum.length != newChecksum.length) {
      logger.error("Invalid checksum length");
      return map;
    }
    for (int i = 0; i < 4; i++) {
      if (checksum[i] != newChecksum[i]) {
        logger.error("Invalid checksum data");
        return map;
      }
    }
    map.put("prefix", prefix);
    map.put("data", data);
    return map;
  }

  public static Map<String, String> doDecode(String string) {
    Map<String, String> newMap = new HashMap<>();
    Map<String, Object> map = decode(string);
    for (String key : map.keySet()) {
      byte[] data;
      Object value = map.get(key);
      if (value instanceof Byte) {
        data = new byte[] { (byte) value };
      } else {
        data = (byte[]) value;
      }
      newMap.put(key, Hex.toHexString(data));
    }
    return newMap;
  }
}
