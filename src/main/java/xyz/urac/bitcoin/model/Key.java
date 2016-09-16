package xyz.urac.bitcoin.model;

import java.util.HashMap;
import java.util.Map;

import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xyz.urac.bitcoin.crypto.Secp256k1;
import xyz.urac.bitcoin.encoding.Base58Check;
import xyz.urac.bitcoin.enums.KeyFormat;

/**
 * 密钥
 * 
 * @author zyc ￠幻冰
 * @date 2016年9月16日 下午7:15:19
 */
public class Key {
  private final static Logger logger = LoggerFactory.getLogger(Key.class);

  /**
   * 获取私钥
   * 
   * @param privKey
   * @param format
   * @return
   */
  public static String getPrivKey(byte[] privKey, KeyFormat format) {
    privKey = getPrivKey(privKey, format.isCompressed());
    switch (format) {
      case Hex:
        return Hex.toHexString(privKey);
      case WIF:
        return Base58Check.encode(privKey, "80");
      default:
        return "";
    }
  }

  private static byte[] getPrivKey(byte[] privKey, boolean compressed) {
    if (!Secp256k1.privateKeyVerify(privKey)) {
      logger.error("Invalid privateKey");
      return new byte[0];
    }
    if (compressed) {
      byte[] privKeyCompressed = new byte[privKey.length + 1];
      for (int i = 0; i < privKey.length; i++) {
        privKeyCompressed[i] = privKey[i];
      }
      privKeyCompressed[privKey.length] = new Byte("01");
      privKey = privKeyCompressed;
    }
    return privKey;
  }

  /**
   * 生成私钥
   * 
   * @param format
   * @return
   */
  public static String genPrivKey(KeyFormat format) {
    byte[] privKey = Secp256k1.privateKeyCreate();
    return getPrivKey(privKey, format);
  }

  public static byte[] genPrivKey(boolean compressed) {
    byte[] privKey = Secp256k1.privateKeyCreate();
    return getPrivKey(privKey, compressed);
  }

  public static byte[] genPrivKey() {
    return genPrivKey(true);
  }

  /**
   * 获取公钥
   * 
   * @param privKey
   * @param format
   * @return
   */
  public static String getPubKey(String privKeyHex, KeyFormat format) {
    byte[] privKey;
    switch (format) {
      case Hex:
        privKey = Hex.decode(privKeyHex);
        break;
      case WIF:
        privKey = (byte[]) Base58Check.decode(privKeyHex).get("data");
        break;
      default:
        logger.error("Invalid KeyFormat: " + format);
        return "";
    }
    byte[] pubKey = getPubKey(privKey, format.isCompressed());
    return Hex.toHexString(pubKey);
  }

  public static byte[] getPubKey(byte[] privKey, boolean compressed) {
    if (compressed) {
      byte[] privKeyUnCompressed = new byte[privKey.length - 1];
      for (int i = 0; i < privKeyUnCompressed.length; i++) {
        privKeyUnCompressed[i] = privKey[i];
      }
      privKey = privKeyUnCompressed;
    }
    byte[] pubKey = Secp256k1.publicKeyCreate(privKey, compressed);
    return pubKey;
  }

  public static byte[] getPubKey(byte[] privKey) {
    return getPubKey(privKey, true);
  }

  /**
   * 生成密钥对
   * 
   * @param format
   * @return
   */
  public static Map<String, String> genKeys(KeyFormat format) {
    Map<String, String> map = new HashMap<>();
    String privKey = genPrivKey(format);
    String pubKey = getPubKey(privKey, format);
    map.put("privKey", privKey);
    map.put("pubKey", pubKey);
    return map;
  }

  public static Map<String, byte[]> genKeys(boolean compressed) {
    Map<String, byte[]> map = new HashMap<>();
    byte[] privKey = genPrivKey(compressed);
    byte[] pubKey = getPubKey(privKey, compressed);
    map.put("privKey", privKey);
    map.put("pubKey", pubKey);
    return map;
  }

  public static Map<String, byte[]> genKeys() {
    return genKeys(true);
  }
}
