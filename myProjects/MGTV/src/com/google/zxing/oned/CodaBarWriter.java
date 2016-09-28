package com.google.zxing.oned;

public class CodaBarWriter extends OneDimensionalCodeWriter
{
  public CodaBarWriter()
  {
    super(20);
  }

  public byte[] encode(String paramString)
  {
    if (!CodaBarReader.arrayContains(new char[] { 65, 66, 67, 68 }, Character.toUpperCase(paramString.charAt(0))))
      throw new IllegalArgumentException("Codabar should start with one of the following: 'A', 'B', 'C' or 'D'");
    if (!CodaBarReader.arrayContains(new char[] { 84, 78, 42, 69 }, Character.toUpperCase(paramString.charAt(-1 + paramString.length()))))
      throw new IllegalArgumentException("Codabar should end with one of the following: 'T', 'N', '*' or 'E'");
    int i = 20;
    char[] arrayOfChar = { 47, 58, 43, 46 };
    int j = 1;
    if (j < -1 + paramString.length())
    {
      if ((Character.isDigit(paramString.charAt(j))) || (paramString.charAt(j) == '-') || (paramString.charAt(j) == '$'))
        i += 9;
      while (true)
      {
        j++;
        break;
        if (!CodaBarReader.arrayContains(arrayOfChar, paramString.charAt(j)))
          break label202;
        i += 10;
      }
      label202: throw new IllegalArgumentException("Cannot encode : '" + paramString.charAt(j) + '\'');
    }
    byte[] arrayOfByte = new byte[i + (-1 + paramString.length())];
    int k = 0;
    for (int m = 0; m < paramString.length(); m++)
    {
      int n = Character.toUpperCase(paramString.charAt(m));
      int i1;
      label302: int i3;
      int i4;
      int i5;
      int i6;
      if (m == -1 + paramString.length())
      {
        if (n == 42)
          n = 67;
      }
      else
      {
        i1 = 0;
        int i2 = CodaBarReader.ALPHABET.length;
        i3 = 0;
        if (i1 < i2)
        {
          if (n != CodaBarReader.ALPHABET[i1])
            break label412;
          i3 = CodaBarReader.CHARACTER_ENCODINGS[i1];
        }
        i4 = 1;
        i5 = 0;
        i6 = 0;
      }
      while (true)
      {
        if (i6 >= 7)
          break label424;
        arrayOfByte[k] = i4;
        k++;
        if (((0x1 & i3 >> 6 - i6) == 0) || (i5 == 1))
        {
          i4 = (byte)(i4 ^ 0x1);
          i6++;
          i5 = 0;
          continue;
          if (n != 69)
            break;
          n = 68;
          break;
          label412: i1++;
          break label302;
        }
        i5++;
      }
      label424: if (m < -1 + paramString.length())
      {
        arrayOfByte[k] = 0;
        k++;
      }
    }
    return arrayOfByte;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.CodaBarWriter
 * JD-Core Version:    0.6.2
 */