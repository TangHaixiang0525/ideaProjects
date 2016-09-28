package u.aly;

import java.util.BitSet;

public final class dm extends da
{
  public dm(du paramdu)
  {
    super(paramdu);
  }

  public static BitSet a(byte[] paramArrayOfByte)
  {
    BitSet localBitSet = new BitSet();
    for (int i = 0; i < 8 * paramArrayOfByte.length; i++)
      if ((paramArrayOfByte[(-1 + (paramArrayOfByte.length - i / 8))] & 1 << i % 8) > 0)
        localBitSet.set(i);
    return localBitSet;
  }

  public static byte[] b(BitSet paramBitSet, int paramInt)
  {
    byte[] arrayOfByte = new byte[(int)Math.ceil(paramInt / 8.0D)];
    for (int i = 0; i < paramBitSet.length(); i++)
      if (paramBitSet.get(i))
      {
        int j = -1 + (arrayOfByte.length - i / 8);
        arrayOfByte[j] = ((byte)(arrayOfByte[j] | 1 << i % 8));
      }
    return arrayOfByte;
  }

  public Class<? extends do> D()
  {
    return dr.class;
  }

  public void a(BitSet paramBitSet, int paramInt)
    throws cn
  {
    byte[] arrayOfByte = b(paramBitSet, paramInt);
    int i = arrayOfByte.length;
    for (int j = 0; j < i; j++)
      a(arrayOfByte[j]);
  }

  public BitSet b(int paramInt)
    throws cn
  {
    int i = (int)Math.ceil(paramInt / 8.0D);
    byte[] arrayOfByte = new byte[i];
    for (int j = 0; j < i; j++)
      arrayOfByte[j] = u();
    return a(arrayOfByte);
  }

  public static class a
    implements di
  {
    public dg a(du paramdu)
    {
      return new dm(paramdu);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.dm
 * JD-Core Version:    0.6.2
 */