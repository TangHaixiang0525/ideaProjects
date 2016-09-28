package com.starcor.data.processor;

import com.starcor.data.model.DataModel;
import com.starcor.data.parser.xml.IXmlParser;
import com.starcor.data.parser.xml.PullXmlParser;
import java.io.InputStream;
import java.io.OutputStream;

public class XmlProcessor
  implements IProcessor
{
  private IXmlParser mParser = new PullXmlParser();

  public void generateData(OutputStream paramOutputStream, DataModel paramDataModel)
  {
  }

  public void parseData(InputStream paramInputStream, DataModel paramDataModel)
  {
    DataModel.processModel(paramDataModel, this.mParser.parse(paramInputStream), 1);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.data.processor.XmlProcessor
 * JD-Core Version:    0.6.2
 */