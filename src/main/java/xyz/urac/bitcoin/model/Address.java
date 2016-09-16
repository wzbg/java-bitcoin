package xyz.urac.bitcoin.model;

import java.security.MessageDigest;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xyz.urac.bitcoin.encoding.Base58Check;
import xyz.urac.bitcoin.enums.KeyFormat;

/**
 * 地址
 * 
 * @author zyc ￠幻冰
 * @date 2016年9月16日 下午7:15:27
 */
public class Address {
  private final static Logger logger = LoggerFactory.getLogger(Key.class);

  private static MessageDigest sha256;
  private static MessageDigest ripemd160;

  static {
    try {
      Security.addProvider(new BouncyCastleProvider());
      sha256 = MessageDigest.getInstance("SHA-256");
      ripemd160 = MessageDigest.getInstance("RipeMD160");
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
  }

  /**
   * 获取地址
   * 
   * @param pubKey
   * @return
   */
  public static String getPubAddr(String pubKey) {
    return getPubAddr(Hex.decode(pubKey));
  }

  public static String getPubAddr(byte[] pubKey) {
    byte[] hash = sha256.digest(pubKey);
    byte[] data = ripemd160.digest(hash);
    return Base58Check.encode(data);
  }

  /**
   * 生成地址
   * 
   * @param format
   * @return
   */
  public static Map<String, Object> genPubAddr(KeyFormat format) {
    Map<String, Object> map = new HashMap<>();
    String privKey = Key.genPrivKey(format);
    String pubKey = Key.getPubKey(privKey, format);
    String addr = getPubAddr(pubKey);
    map.put("privKey", privKey);
    map.put("pubKey", pubKey);
    map.put("addr", addr);
    return map;
  }

  public static Map<String, Object> genPubAddr(boolean compressed) {
    Map<String, Object> map = new HashMap<>();
    byte[] privKey = Key.genPrivKey(compressed);
    byte[] pubKey = Key.getPubKey(privKey, compressed);
    String addr = getPubAddr(pubKey);
    map.put("privKey", privKey);
    map.put("pubKey", pubKey);
    map.put("addr", addr);
    return map;
  }

  public static Map<String, Object> genPubAddr() {
    return genPubAddr(true);
  }
}
