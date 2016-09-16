package xyz.urac.bitcoin.encoding;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseX {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  private String ALPHABET;
  private int BASE;
  private char LEADER;

  BaseX() {
    this("0123456789");
  }

  BaseX(String ALPHABET) {
    this.ALPHABET = ALPHABET;
    BASE = ALPHABET.length();
    LEADER = ALPHABET.charAt(0);
  }

  public String encode(byte[] source) {
    if (source == null) {
      return "";
    }
    List<Character> digits = new ArrayList<>();
    for (int i = 0; i < source.length; i++) {
      int carry = 0xFF & source[i];
      for (int j = 0; j < digits.size(); j++) {
        carry += digits.get(j) << 8;
        digits.set(j, (char) (carry % BASE));
        carry = carry / BASE;
      }
      while (carry > 0) {
        digits.add((char) (carry % BASE));
        carry = carry / BASE;
      }
    }
    // deal with leading zeros
    for (int k = 0; k < source.length - 1 && source[k] == 0; k++) {
      digits.add((char) 0);
    }
    // convert digits to a string
    int jj = digits.size() - 1;
    for (int ii = 0; ii <= jj; ii++) {
      char tmp = ALPHABET.charAt(digits.get(ii));
      digits.set(ii, ALPHABET.charAt(digits.get(jj)));
      digits.set(jj--, tmp);
    }
    StringBuffer sb = new StringBuffer();
    for (char digit : digits) {
      sb.append(digit);
    }
    return sb.toString();
  }

  public byte[] decode(String digits) {
    if (digits == null) {
      return new byte[0];
    }
    List<Integer> source = new ArrayList<>();
    for (int i = 0; i < digits.length(); i++) {
      int carry = ALPHABET.indexOf(digits.charAt(i));
      if (carry == -1) {
        logger.error("Non-base" + BASE + " character");
        return new byte[0];
      }
      for (int j = 0; j < source.size(); j++) {
        carry += source.get(j) * BASE;
        source.set(j, (0xFF & carry));
        carry >>= 8;
      }
      while (carry > 0) {
        source.add((0xFF & carry));
        carry >>= 8;
      }
    }
    // deal with leading zeros
    for (int k = 0; k < digits.length() - 1 && digits.charAt(k) == LEADER; k++) {
      source.add(0);
    }
    byte[] bs = new byte[source.size()];
    for (int m = 0; m < bs.length; m++) {
      bs[m] = source.get(source.size() - 1 - m).byteValue();
    }
    return bs;
  }
}
