package com.intertrust.wasabi.drm;

public enum TransactionType
{
  static
  {
    PERSONALIZATION = new TransactionType("PERSONALIZATION", 1);
    USER_REGISTRATION = new TransactionType("USER_REGISTRATION", 2);
    LINK_ACQUISITION = new TransactionType("LINK_ACQUISITION", 3);
    LINK_RELEASE = new TransactionType("LINK_RELEASE", 4);
    SECURITY_DATA_UPDATE = new TransactionType("SECURITY_DATA_UPDATE", 5);
    SECURITY_DATA_CERTIFICATION = new TransactionType("SECURITY_DATA_CERTIFICATION", 6);
    SUSPENSION_LIST_UPDATE = new TransactionType("SUSPENSION_LIST_UPDATE", 7);
    METERING_DATA_UPDATE = new TransactionType("METERING_DATA_UPDATE", 8);
    LICENSE_ACQUISITION = new TransactionType("LICENSE_ACQUISITION", 9);
    CRL_UPDATE = new TransactionType("CRL_UPDATE", 10);
    TransactionType[] arrayOfTransactionType = new TransactionType[11];
    arrayOfTransactionType[0] = SERVICE_TOKEN_PROCESSING;
    arrayOfTransactionType[1] = PERSONALIZATION;
    arrayOfTransactionType[2] = USER_REGISTRATION;
    arrayOfTransactionType[3] = LINK_ACQUISITION;
    arrayOfTransactionType[4] = LINK_RELEASE;
    arrayOfTransactionType[5] = SECURITY_DATA_UPDATE;
    arrayOfTransactionType[6] = SECURITY_DATA_CERTIFICATION;
    arrayOfTransactionType[7] = SUSPENSION_LIST_UPDATE;
    arrayOfTransactionType[8] = METERING_DATA_UPDATE;
    arrayOfTransactionType[9] = LICENSE_ACQUISITION;
    arrayOfTransactionType[10] = CRL_UPDATE;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.drm.TransactionType
 * JD-Core Version:    0.6.2
 */