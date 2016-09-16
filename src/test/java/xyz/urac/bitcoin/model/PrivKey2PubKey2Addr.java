package xyz.urac.bitcoin.model;

import java.util.Map;

import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xyz.urac.bitcoin.enums.KeyFormat;

public class PrivKey2PubKey2Addr {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final byte[] PRIVATE_KEY = Hex.decode("1a3cf5e11d8de9ff4e5cd61c4c37a2e4e543042c4279db3f56326040cf0c10f9");

  @Test
  public void testGenPrivKey() {
    String side = "";
    do {
      side += '1';
    } while (side.length() != 20);
    boolean compressed = false;
    logger.info(side + "testGenPrivKey start" + side);
    byte[] privateKey = Key.genPrivKey(compressed);
    logger.info("{} Private Key", privateKey);
    byte[] publicKey = Key.getPubKey(privateKey, compressed);
    logger.info("{} Public Key", publicKey);
    String address = Address.getPubAddr(publicKey);
    logger.info("{} Address", address);
    logger.info(side + "-testGenPrivKey end-" + side);
    System.out.println();
  }

  @Test
  public void testGenPrivKeyByCompressed() {
    String side = "";
    do {
      side += '2';
    } while (side.length() != 20);
    logger.info(side + "testGenPrivKeyByCompressed start" + side);
    byte[] privateKey = Key.genPrivKey();
    logger.info("{} Private Key", privateKey);
    byte[] publicKey = Key.getPubKey(privateKey);
    logger.info("{} Public Key", publicKey);
    String address = Address.getPubAddr(publicKey);
    logger.info("{} Address", address);
    logger.info(side + "-testGenPrivKeyByCompressed end-" + side);
    System.out.println();
  }

  @Test
  public void testGetPrivKeyHex() {
    String side = "";
    do {
      side += '3';
    } while (side.length() != 20);
    KeyFormat format = KeyFormat.Hex;
    logger.info(side + "testGetPrivKeyHex start" + side);
    String privateKey = Key.getPrivKey(PRIVATE_KEY, format);
    logger.info("{} Private Key", privateKey);
    String publicKey = Key.getPubKey(privateKey, format);
    logger.info("{} Public Key", publicKey);
    String address = Address.getPubAddr(publicKey);
    logger.info("{} Address", address);
    logger.info(side + "-testGetPrivKeyHex end-" + side);
    System.out.println();
  }

  @Test
  public void testGetPrivKeyHexyByCompressed() {
    String side = "";
    do {
      side += '4';
    } while (side.length() != 20);
    KeyFormat format = KeyFormat.Hex.compress();
    logger.info(side + "testGetPrivKeyHexyByCompressed start" + side);
    String privateKey = Key.getPrivKey(PRIVATE_KEY, format);
    logger.info("{} Private Key", privateKey);
    String publicKey = Key.getPubKey(privateKey, format);
    logger.info("{} Public Key", publicKey);
    String address = Address.getPubAddr(publicKey);
    logger.info("{} Address", address);
    logger.info(side + "-testGetPrivKeyHexyByCompressed end-" + side);
    System.out.println();
  }

  @Test
  public void testGetPrivKeyWIF() {
    String side = "";
    do {
      side += '5';
    } while (side.length() != 20);
    KeyFormat format = KeyFormat.WIF;
    logger.info(side + "testGetPrivKeyWIF start" + side);
    String privateKey = Key.getPrivKey(PRIVATE_KEY, format);
    logger.info("{} Private Key", privateKey);
    String publicKey = Key.getPubKey(privateKey, format);
    logger.info("{} Public Key", publicKey);
    String address = Address.getPubAddr(publicKey);
    logger.info("{} Address", address);
    logger.info(side + "-testGetPrivKeyWIF end-" + side);
    System.out.println();
  }

  @Test
  public void testGetPrivKeyWIFByCompressed() {
    String side = "";
    do {
      side += '6';
    } while (side.length() != 20);
    KeyFormat format = KeyFormat.WIF.compress();
    logger.info(side + "testGetPrivKeyWIFByCompressed start" + side);
    String privateKey = Key.getPrivKey(PRIVATE_KEY, format);
    logger.info("{} Private Key", privateKey);
    String publicKey = Key.getPubKey(privateKey, format);
    logger.info("{} Public Key", publicKey);
    String address = Address.getPubAddr(publicKey);
    logger.info("{} Address", address);
    logger.info(side + "-testGetPrivKeyWIFByCompressed end-" + side);
    System.out.println();
  }

  @Test
  public void testGenKeys() {
    String side = "";
    do {
      side += '7';
    } while (side.length() != 20);
    logger.info(side + "testGenKeys start" + side);
    Map<String, byte[]> map = Key.genKeys();
    logger.info("privKey:{}, pubKey:{}", map.get("privKey"), map.get("pubKey"));
    map = Key.genKeys(false);
    logger.info("privKey:{}, pubKey:{}", map.get("privKey"), map.get("pubKey"));
    logger.info(side + "-testGenKeys end-" + side);
    System.out.println();
  }

  @Test
  public void testGenPubAddr() {
    String side = "";
    do {
      side += '8';
    } while (side.length() != 20);
    logger.info(side + "testGenPubAddr start" + side);
    Map<String, Object> map = Address.genPubAddr(KeyFormat.Hex.compress());
    logger.info("privKey:{}, pubKey:{}, addr:{}", map.get("privKey"), map.get("pubKey"), map.get("addr"));
    map = Address.genPubAddr(KeyFormat.WIF.compress());
    logger.info("privKey:{}, pubKey:{}, addr:{}", map.get("privKey"), map.get("pubKey"), map.get("addr"));
    logger.info(side + "-testGenPubAddr end-" + side);
    System.out.println();
  }
}
