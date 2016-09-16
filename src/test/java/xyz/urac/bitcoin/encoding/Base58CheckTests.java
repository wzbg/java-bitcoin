package xyz.urac.bitcoin.encoding;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Base58CheckTests {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final String DATA = "086eaa677895f92d4a6c5ef740c168932b5e3f44";
  private final String PRIVATE_KEY = "1a3cf5e11d8de9ff4e5cd61c4c37a2e4e543042c4279db3f56326040cf0c10f9";

  @Test
  public void testAddr() {
    String string = Base58Check.doEncode(DATA);
    logger.info(string);
    Map<String, String> map = Base58Check.doDecode(string);
    Assert.assertEquals("00", map.get("prefix"));
    Assert.assertEquals(DATA, map.get("data"));
  }

  @Test
  public void testPrivKey() {
    String string = Base58Check.doEncode(PRIVATE_KEY, "80");
    logger.info(string);
    Map<String, String> map = Base58Check.doDecode(string);
    Assert.assertEquals("80", map.get("prefix"));
    Assert.assertEquals(PRIVATE_KEY, map.get("data"));
  }
}
