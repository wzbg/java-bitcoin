package xyz.urac.bitcoin.encoding;

import org.bouncycastle.util.encoders.Hex;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Base58Tests {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final byte[] SOURCE = Hex.decode("00086eaa677895f92d4a6c5ef740c168932b5e3f442b14dbdb");

  @Test
  public void test() {
    Base58 base58 = new Base58();
    String digits = base58.encode(SOURCE);
    logger.info("digits: {}", digits);
    byte[] source = base58.decode(digits);
    Assert.assertArrayEquals(SOURCE, source);
  }
}
