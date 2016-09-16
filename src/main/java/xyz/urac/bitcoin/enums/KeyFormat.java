package xyz.urac.bitcoin.enums;

/**
 * 私钥格式
 * 
 * @author zyc ￠幻冰
 * @date 2016年9月17日 上午2:58:34
 */
public enum KeyFormat {
  Hex(false), WIF(false);

  private boolean compressed;

  KeyFormat(boolean compressed) {
    this.compressed = compressed;
  }

  public boolean isCompressed() {
    return compressed;
  }

  public KeyFormat compress() {
    this.compressed = true;
    return this;
  }
}
