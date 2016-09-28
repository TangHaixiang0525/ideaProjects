package com.xiaomi.mitv.payment.thirdsdk.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface IMiTVPaymentThirdManagerService extends IInterface
{
  public abstract String CreateOrderAndPay(String paramString1, String paramString2, long paramLong, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements IMiTVPaymentThirdManagerService
  {
    private static final String DESCRIPTOR = "com.xiaomi.mitv.payment.thirdsdk.aidl.IMiTVPaymentThirdManagerService";
    static final int TRANSACTION_CreateOrderAndPay = 1;

    public Stub()
    {
      attachInterface(this, "com.xiaomi.mitv.payment.thirdsdk.aidl.IMiTVPaymentThirdManagerService");
    }

    public static IMiTVPaymentThirdManagerService asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.xiaomi.mitv.payment.thirdsdk.aidl.IMiTVPaymentThirdManagerService");
      if ((localIInterface != null) && ((localIInterface instanceof IMiTVPaymentThirdManagerService)))
        return (IMiTVPaymentThirdManagerService)localIInterface;
      return new Proxy(paramIBinder);
    }

    public IBinder asBinder()
    {
      return this;
    }

    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      switch (paramInt1)
      {
      default:
        return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
      case 1598968902:
        paramParcel2.writeString("com.xiaomi.mitv.payment.thirdsdk.aidl.IMiTVPaymentThirdManagerService");
        return true;
      case 1:
      }
      paramParcel1.enforceInterface("com.xiaomi.mitv.payment.thirdsdk.aidl.IMiTVPaymentThirdManagerService");
      String str = CreateOrderAndPay(paramParcel1.readString(), paramParcel1.readString(), paramParcel1.readLong(), paramParcel1.readString(), paramParcel1.readString(), paramParcel1.readString(), paramParcel1.readString(), paramParcel1.readString(), paramParcel1.readString());
      paramParcel2.writeNoException();
      paramParcel2.writeString(str);
      return true;
    }

    private static class Proxy
      implements IMiTVPaymentThirdManagerService
    {
      private IBinder mRemote;

      Proxy(IBinder paramIBinder)
      {
        this.mRemote = paramIBinder;
      }

      public String CreateOrderAndPay(String paramString1, String paramString2, long paramLong, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.xiaomi.mitv.payment.thirdsdk.aidl.IMiTVPaymentThirdManagerService");
          localParcel1.writeString(paramString1);
          localParcel1.writeString(paramString2);
          localParcel1.writeLong(paramLong);
          localParcel1.writeString(paramString3);
          localParcel1.writeString(paramString4);
          localParcel1.writeString(paramString5);
          localParcel1.writeString(paramString6);
          localParcel1.writeString(paramString7);
          localParcel1.writeString(paramString8);
          this.mRemote.transact(1, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public IBinder asBinder()
      {
        return this.mRemote;
      }

      public String getInterfaceDescriptor()
      {
        return "com.xiaomi.mitv.payment.thirdsdk.aidl.IMiTVPaymentThirdManagerService";
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.xiaomi.mitv.payment.thirdsdk.aidl.IMiTVPaymentThirdManagerService
 * JD-Core Version:    0.6.2
 */