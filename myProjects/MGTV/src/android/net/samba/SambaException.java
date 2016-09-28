package android.net.samba;

public class SambaException extends Exception
{
  public static final int ERR_NULLPOINTER = 2;
  public static final int ERR_UNSUPPORTTYPE = 3;
  public static final int SMBERR_UNKNOWN = 1;

  public int getErr()
  {
    throw new RuntimeException("stub");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     android.net.samba.SambaException
 * JD-Core Version:    0.6.2
 */