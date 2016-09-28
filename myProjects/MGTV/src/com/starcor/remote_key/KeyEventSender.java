package com.starcor.remote_key;

public abstract class KeyEventSender
{
  public static final int ACTION_CLICK = 0;
  public static final int ACTION_DOWN = 1;
  public static final int ACTION_UP = 2;
  public static final int KEY_FLAG_ALT = 262144;
  public static final int KEY_FLAG_CTRL = 131072;
  public static final int KEY_FLAG_LALT = 268697600;
  public static final int KEY_FLAG_LCTRL = 268566528;
  public static final int KEY_FLAG_LSHIFT = 268500992;
  public static final int KEY_FLAG_RALT = 537133056;
  public static final int KEY_FLAG_RCTRL = 537001984;
  public static final int KEY_FLAG_RSHIFT = 536936448;
  public static final int KEY_FLAG_SHIFT = 65536;
  public static final int META_CTRL_LEFT_ON = 8192;
  public static final int META_CTRL_ON = 4096;
  public static final int META_CTRL_RIGHT_ON = 16384;

  public abstract boolean sendKey(int paramInt1, int paramInt2);

  public abstract boolean sendString(String paramString);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.remote_key.KeyEventSender
 * JD-Core Version:    0.6.2
 */