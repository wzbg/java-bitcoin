package xyz.urac.bitcoin.crypto;

import org.bouncycastle.util.encoders.Hex;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Secp256k1Tests {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final byte[] PRIVATE_KEY = Hex.decode("1a3cf5e11d8de9ff4e5cd61c4c37a2e4e543042c4279db3f56326040cf0c10f9");

  @Test
  public void testPrivateKeyVerify() {
    Assert.assertTrue(Secp256k1.privateKeyVerify(PRIVATE_KEY));
  }

  @Test
  public void testPrivateKeyCreate() {
    byte[] privateKey = Secp256k1.privateKeyCreate();
    String privateKeyHex = Hex.toHexString(privateKey);
    logger.info("Private Key (Hex): {}", privateKeyHex);
    Assert.assertEquals(32, privateKey.length);
    Assert.assertEquals(64, privateKeyHex.length());
  }

  @Test
  public void testPublicKeyCreate() {
    byte[] publicKey = Secp256k1.publicKeyCreate(PRIVATE_KEY, false);
    String publicKeyHex = Hex.toHexString(publicKey);
    logger.info("Public Key: {}", publicKeyHex);
    Assert.assertEquals(65, publicKey.length);
    Assert.assertEquals(130, publicKeyHex.length());
  }

  @Test
  public void testPublicKeyCreateCompressed() {
    byte[] publicKey = Secp256k1.publicKeyCreate(PRIVATE_KEY);
    String publicKeyHex = Hex.toHexString(publicKey);
    logger.info("Public Key (Compressed): {}", publicKeyHex);
    Assert.assertEquals(33, publicKey.length);
    Assert.assertEquals(66, publicKeyHex.length());
  }
}
