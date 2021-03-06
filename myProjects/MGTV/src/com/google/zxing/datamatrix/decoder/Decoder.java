package com.google.zxing.datamatrix.decoder;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;

public final class Decoder
{
  private final ReedSolomonDecoder rsDecoder = new ReedSolomonDecoder(GenericGF.DATA_MATRIX_FIELD_256);

  private void correctErrors(byte[] paramArrayOfByte, int paramInt)
    throws ChecksumException
  {
    int i = paramArrayOfByte.length;
    int[] arrayOfInt = new int[i];
    for (int j = 0; j < i; j++)
      arrayOfInt[j] = (0xFF & paramArrayOfByte[j]);
    int k = paramArrayOfByte.length - paramInt;
    try
    {
      this.rsDecoder.decode(arrayOfInt, k);
      for (int m = 0; m < paramInt; m++)
        paramArrayOfByte[m] = ((byte)arrayOfInt[m]);
    }
    catch (ReedSolomonException localReedSolomonException)
    {
      throw ChecksumException.getChecksumInstance();
    }
  }

  public DecoderResult decode(BitMatrix paramBitMatrix)
    throws FormatException, ChecksumException
  {
    BitMatrixParser localBitMatrixParser = new BitMatrixParser(paramBitMatrix);
    Version localVersion = localBitMatrixParser.getVersion();
    DataBlock[] arrayOfDataBlock = DataBlock.getDataBlocks(localBitMatrixParser.readCodewords(), localVersion);
    int i = arrayOfDataBlock.length;
    int j = 0;
    for (int k = 0; k < i; k++)
      j += arrayOfDataBlock[k].getNumDataCodewords();
    byte[] arrayOfByte1 = new byte[j];
    for (int m = 0; m < i; m++)
    {
      DataBlock localDataBlock = arrayOfDataBlock[m];
      byte[] arrayOfByte2 = localDataBlock.getCodewords();
      int n = localDataBlock.getNumDataCodewords();
      correctErrors(arrayOfByte2, n);
      for (int i1 = 0; i1 < n; i1++)
        arrayOfByte1[(m + i1 * i)] = arrayOfByte2[i1];
    }
    return DecodedBitStreamParser.decode(arrayOfByte1);
  }

  public DecoderResult decode(boolean[][] paramArrayOfBoolean)
    throws FormatException, ChecksumException
  {
    int i = paramArrayOfBoolean.length;
    BitMatrix localBitMatrix = new BitMatrix(i);
    for (int j = 0; j < i; j++)
      for (int k = 0; k < i; k++)
        if (paramArrayOfBoolean[j][k] != 0)
          localBitMatrix.set(k, j);
    return decode(localBitMatrix);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.datamatrix.decoder.Decoder
 * JD-Core Version:    0.6.2
 */