package com.mstar.android.tvapi.common.exception;

public abstract interface TvExceptionConstant
{
  public static final String EXCEPTION_MSG_COMMON = "Exception happened ";
  public static final String EXCEPTION_MSG_IPC_FAIL = "Exception happened in ipc!! ";
  public static final String EXCEPTION_MSG_JNI_FAIL = "Exception happened in jni!! ";
  public static final String EXCEPTION_MSG_NATIVE_CALL_FAIL = "Exception happened in native call!! ";
  public static final String EXCEPTION_MSG_OUT_OF_BOUND = "Exception happened in bound";
  public static final short EXCEPTION_TYPE_IPC_FAIL = 2;
  public static final short EXCEPTION_TYPE_JNI_FAIL = 3;
  public static final short EXCEPTION_TYPE_NATIVE_CALL_FAIL = 1;
  public static final short EXCEPTION_TYPE_NATIVE_COMMON;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.common.exception.TvExceptionConstant
 * JD-Core Version:    0.6.2
 */