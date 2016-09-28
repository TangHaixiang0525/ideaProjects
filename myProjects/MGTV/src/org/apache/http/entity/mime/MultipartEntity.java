package org.apache.http.entity.mime;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.message.BasicHeader;

public class MultipartEntity
  implements HttpEntity
{
  private static final char[] MULTIPART_CHARS = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
  private final Header contentType;
  private volatile boolean dirty;
  private long length;
  private final HttpMultipart multipart;

  public MultipartEntity()
  {
    this(HttpMultipartMode.STRICT, null, null);
  }

  public MultipartEntity(HttpMultipartMode paramHttpMultipartMode)
  {
    this(paramHttpMultipartMode, null, null);
  }

  public MultipartEntity(HttpMultipartMode paramHttpMultipartMode, String paramString, Charset paramCharset)
  {
    if (paramString == null)
      paramString = generateBoundary();
    if (paramHttpMultipartMode == null)
      paramHttpMultipartMode = HttpMultipartMode.STRICT;
    this.multipart = new HttpMultipart("form-data", paramCharset, paramString, paramHttpMultipartMode);
    this.contentType = new BasicHeader("Content-Type", generateContentType(paramString, paramCharset));
    this.dirty = true;
  }

  public void addPart(String paramString, ContentBody paramContentBody)
  {
    addPart(new FormBodyPart(paramString, paramContentBody));
  }

  public void addPart(FormBodyPart paramFormBodyPart)
  {
    this.multipart.addBodyPart(paramFormBodyPart);
    this.dirty = true;
  }

  public void consumeContent()
    throws IOException, UnsupportedOperationException
  {
    if (isStreaming())
      throw new UnsupportedOperationException("Streaming entity does not implement #consumeContent()");
  }

  protected String generateBoundary()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Random localRandom = new Random();
    int i = 30 + localRandom.nextInt(11);
    for (int j = 0; j < i; j++)
      localStringBuilder.append(MULTIPART_CHARS[localRandom.nextInt(MULTIPART_CHARS.length)]);
    return localStringBuilder.toString();
  }

  protected String generateContentType(String paramString, Charset paramCharset)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("multipart/form-data; boundary=");
    localStringBuilder.append(paramString);
    if (paramCharset != null)
    {
      localStringBuilder.append("; charset=");
      localStringBuilder.append(paramCharset.name());
    }
    return localStringBuilder.toString();
  }

  public InputStream getContent()
    throws IOException, UnsupportedOperationException
  {
    throw new UnsupportedOperationException("Multipart form entity does not implement #getContent()");
  }

  public Header getContentEncoding()
  {
    return null;
  }

  public long getContentLength()
  {
    if (this.dirty)
    {
      this.length = this.multipart.getTotalLength();
      this.dirty = false;
    }
    return this.length;
  }

  public Header getContentType()
  {
    return this.contentType;
  }

  public boolean isChunked()
  {
    return !isRepeatable();
  }

  public boolean isRepeatable()
  {
    Iterator localIterator = this.multipart.getBodyParts().iterator();
    while (localIterator.hasNext())
      if (((FormBodyPart)localIterator.next()).getBody().getContentLength() < 0L)
        return false;
    return true;
  }

  public boolean isStreaming()
  {
    return !isRepeatable();
  }

  public void writeTo(OutputStream paramOutputStream)
    throws IOException
  {
    this.multipart.writeTo(paramOutputStream);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.apache.http.entity.mime.MultipartEntity
 * JD-Core Version:    0.6.2
 */