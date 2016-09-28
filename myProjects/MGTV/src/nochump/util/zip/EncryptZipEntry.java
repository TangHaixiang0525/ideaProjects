package nochump.util.zip;

import java.util.Date;
import java.util.zip.ZipEntry;

public class EncryptZipEntry extends ZipEntry
{
  String comment;
  long crc = -1L;
  long csize = -1L;
  byte[] extra;
  int flag;
  int method = -1;
  String name;
  long offset;
  long size = -1L;
  long time = -1L;
  int version;

  public EncryptZipEntry(String paramString)
  {
    super(paramString);
    this.name = paramString;
  }

  private static long javaToDosTime(long paramLong)
  {
    Date localDate = new Date(paramLong);
    int i = 1900 + localDate.getYear();
    if (i < 1980)
      return 2162688L;
    return i - 1980 << 25 | 1 + localDate.getMonth() << 21 | localDate.getDate() << 16 | localDate.getHours() << 11 | localDate.getMinutes() << 5 | localDate.getSeconds() >> 1;
  }

  public void setTime(long paramLong)
  {
    this.time = javaToDosTime(paramLong);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     nochump.util.zip.EncryptZipEntry
 * JD-Core Version:    0.6.2
 */