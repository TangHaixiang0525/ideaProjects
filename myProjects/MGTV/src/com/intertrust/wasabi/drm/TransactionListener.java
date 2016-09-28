package com.intertrust.wasabi.drm;

public abstract interface TransactionListener
{
  public abstract void onLicenseDataReceived(byte[] paramArrayOfByte);

  public abstract void onTransactionBegin(TransactionType paramTransactionType);

  public abstract void onTransactionEnd(TransactionType paramTransactionType, int paramInt, String paramString1, String paramString2);

  public abstract void onTransactionProgress(TransactionType paramTransactionType, int paramInt1, int paramInt2);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.drm.TransactionListener
 * JD-Core Version:    0.6.2
 */