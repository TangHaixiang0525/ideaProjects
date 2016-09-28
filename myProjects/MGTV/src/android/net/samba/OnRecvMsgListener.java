package android.net.samba;

public abstract interface OnRecvMsgListener
{
  public static final int MSG_UPDATE_DEVLIST_ADD = 3;
  public static final int MSG_UPDATE_DEVLIST_CANCEL = 1;
  public static final int MSG_UPDATE_DEVLIST_DONE = 2;

  public abstract void onRecvMsgListener(int paramInt);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     android.net.samba.OnRecvMsgListener
 * JD-Core Version:    0.6.2
 */