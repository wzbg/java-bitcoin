package xyz.urac.bitcoin.encoding;

import java.math.BigInteger;

import org.bouncycastle.util.encoders.Hex;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseXTests {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final String ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
  private final byte[] SOURCE = Hex.decode("00086eaa677895f92d4a6c5ef740c168932b5e3f442b14dbdb");

  @Test
  public void testBase10() {
    BaseX baseX = new BaseX();
    String decimal = baseX.encode(SOURCE);
    logger.info("decimal: {}", decimal);
    Assert.assertEquals(new BigInteger(1, SOURCE), new BigInteger(decimal));
    byte[] source = baseX.decode(decimal);
    Assert.assertArrayEquals(SOURCE, source);
  }

  @Test
  public void testBase58() {
    BaseX baseX = new BaseX(ALPHABET);
    String digits = baseX.encode(SOURCE);
    logger.info("digits: {}", digits);
    byte[] source = baseX.decode(digits);
    Assert.assertArrayEquals(SOURCE, source);
  }
}
