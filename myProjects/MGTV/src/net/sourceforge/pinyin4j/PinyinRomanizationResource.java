package net.sourceforge.pinyin4j;

import com.hp.hpl.sparta.Document;
import com.hp.hpl.sparta.ParseException;
import com.hp.hpl.sparta.Parser;
import java.io.FileNotFoundException;
import java.io.IOException;

class PinyinRomanizationResource
{
  private Document pinyinMappingDoc;

  private PinyinRomanizationResource()
  {
    initializeResource();
  }

  static PinyinRomanizationResource getInstance()
  {
    return PinyinRomanizationSystemResourceHolder.theInstance;
  }

  private void initializeResource()
  {
    try
    {
      setPinyinMappingDoc(Parser.parse("", ResourceHelper.getResourceInputStream("/pinyindb/pinyin_mapping.xml")));
      return;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      localFileNotFoundException.printStackTrace();
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      return;
    }
    catch (ParseException localParseException)
    {
      localParseException.printStackTrace();
    }
  }

  private void setPinyinMappingDoc(Document paramDocument)
  {
    this.pinyinMappingDoc = paramDocument;
  }

  Document getPinyinMappingDoc()
  {
    return this.pinyinMappingDoc;
  }

  private static class PinyinRomanizationSystemResourceHolder
  {
    static final PinyinRomanizationResource theInstance = new PinyinRomanizationResource(null);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     net.sourceforge.pinyin4j.PinyinRomanizationResource
 * JD-Core Version:    0.6.2
 */