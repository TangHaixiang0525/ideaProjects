package net.sourceforge.pinyin4j;

import com.hp.hpl.sparta.Document;
import com.hp.hpl.sparta.ParseException;
import com.hp.hpl.sparta.Parser;
import java.io.FileNotFoundException;
import java.io.IOException;

class GwoyeuRomatzyhResource
{
  private Document pinyinToGwoyeuMappingDoc;

  private GwoyeuRomatzyhResource()
  {
    initializeResource();
  }

  static GwoyeuRomatzyhResource getInstance()
  {
    return GwoyeuRomatzyhSystemResourceHolder.theInstance;
  }

  private void initializeResource()
  {
    try
    {
      setPinyinToGwoyeuMappingDoc(Parser.parse("", ResourceHelper.getResourceInputStream("/pinyindb/pinyin_gwoyeu_mapping.xml")));
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

  private void setPinyinToGwoyeuMappingDoc(Document paramDocument)
  {
    this.pinyinToGwoyeuMappingDoc = paramDocument;
  }

  Document getPinyinToGwoyeuMappingDoc()
  {
    return this.pinyinToGwoyeuMappingDoc;
  }

  private static class GwoyeuRomatzyhSystemResourceHolder
  {
    static final GwoyeuRomatzyhResource theInstance = new GwoyeuRomatzyhResource(null);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     net.sourceforge.pinyin4j.GwoyeuRomatzyhResource
 * JD-Core Version:    0.6.2
 */