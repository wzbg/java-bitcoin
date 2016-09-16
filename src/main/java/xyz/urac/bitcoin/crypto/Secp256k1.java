package xyz.urac.bitcoin.crypto;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.math.ec.ECPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Secp256k1 {
  private final static Logger logger = LoggerFactory.getLogger(Secp256k1.class);

  private final static String RANDOM_NUMBER_ALGORITHM = "SHA1PRNG";
  private final static String RANDOM_NUMBER_ALGORITHM_PROVIDER = "SUN";

  private final static BigInteger MAX_PRIVATE_KEY = new BigInteger(
      "00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364140", 16);

  public static boolean privateKeyVerify(byte[] privateKey) {
    BigInteger privateKeyCheck = new BigInteger(1, privateKey);
    return privateKeyCheck.compareTo(BigInteger.ZERO) == 1 && privateKeyCheck.compareTo(MAX_PRIVATE_KEY) == -1;
  }

  /**
   * Generate a random private key that can be used with Secp256k1.(k)
   */
  public static byte[] privateKeyCreate() {
    SecureRandom secureRandom;
    try {
      secureRandom = SecureRandom.getInstance(RANDOM_NUMBER_ALGORITHM, RANDOM_NUMBER_ALGORITHM_PROVIDER);
    } catch (Exception e) {
      logger.warn(e.getMessage(), e);
      secureRandom = new SecureRandom();
    }
    byte[] privateKey = new byte[32];
    while (!privateKeyVerify(privateKey)) {
      secureRandom.nextBytes(privateKey);
    }
    return privateKey;
  }

  /**
   * Converts a private key into its corresponding public key.(K)
   */
  public static byte[] publicKeyCreate(byte[] privateKey) {
    return publicKeyCreate(privateKey, true);
  }

  public static byte[] publicKeyCreate(byte[] privateKey, boolean compressed) {
    ECNamedCurveParameterSpec spec = ECNamedCurveTable.getParameterSpec("secp256k1");
    ECPoint pointQ = spec.getG().multiply(new BigInteger(1, privateKey));
    return pointQ.getEncoded(compressed);
  }
}
