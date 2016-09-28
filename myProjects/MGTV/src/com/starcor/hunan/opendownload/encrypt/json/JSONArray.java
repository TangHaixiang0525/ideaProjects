package com.starcor.hunan.opendownload.encrypt.json;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONTokener;

public class JSONArray
{
  private final ArrayList<Object> myArrayList = new ArrayList();

  public JSONArray()
  {
  }

  public JSONArray(Object paramObject)
    throws JSONException
  {
    this();
    if (paramObject.getClass().isArray())
    {
      int i = Array.getLength(paramObject);
      for (int j = 0; j < i; j++)
        put(JSONObject.wrap(Array.get(paramObject, j)));
    }
    throw new JSONException("JSONArray initial value should be a string or collection or array.");
  }

  public JSONArray(String paramString)
    throws JSONException
  {
    this(new JSONTokener(paramString));
  }

  public JSONArray(Collection<Object> paramCollection)
  {
    if (paramCollection != null)
    {
      Iterator localIterator = paramCollection.iterator();
      while (localIterator.hasNext())
        this.myArrayList.add(JSONObject.wrap(localIterator.next()));
    }
  }

  public JSONArray(JSONTokener paramJSONTokener)
    throws JSONException
  {
    this();
    if (paramJSONTokener.nextClean() != '[')
      throw paramJSONTokener.syntaxError("A JSONArray text must start with '['");
    if (paramJSONTokener.nextClean() != ']')
      paramJSONTokener.back();
    while (true)
    {
      if (paramJSONTokener.nextClean() == ',')
      {
        paramJSONTokener.back();
        this.myArrayList.add(JSONObject.NULL);
      }
      while (true)
        switch (paramJSONTokener.nextClean())
        {
        default:
          throw paramJSONTokener.syntaxError("Expected a ',' or ']'");
          paramJSONTokener.back();
          this.myArrayList.add(paramJSONTokener.nextValue());
        case ',':
        case ']':
        }
      if (paramJSONTokener.nextClean() == ']')
        return;
      paramJSONTokener.back();
    }
  }

  public Object get(int paramInt)
    throws JSONException
  {
    Object localObject = opt(paramInt);
    if (localObject == null)
      throw new JSONException("JSONArray[" + paramInt + "] not found.");
    return localObject;
  }

  public boolean getBoolean(int paramInt)
    throws JSONException
  {
    Object localObject = get(paramInt);
    if ((localObject.equals(Boolean.FALSE)) || (((localObject instanceof String)) && (((String)localObject).equalsIgnoreCase("false"))))
      return false;
    if ((localObject.equals(Boolean.TRUE)) || (((localObject instanceof String)) && (((String)localObject).equalsIgnoreCase("true"))))
      return true;
    throw new JSONException("JSONArray[" + paramInt + "] is not a boolean.");
  }

  public double getDouble(int paramInt)
    throws JSONException
  {
    Object localObject = get(paramInt);
    try
    {
      if ((localObject instanceof Number))
        return ((Number)localObject).doubleValue();
      double d = Double.parseDouble((String)localObject);
      return d;
    }
    catch (Exception localException)
    {
    }
    throw new JSONException("JSONArray[" + paramInt + "] is not a number.");
  }

  public int getInt(int paramInt)
    throws JSONException
  {
    Object localObject = get(paramInt);
    try
    {
      if ((localObject instanceof Number))
        return ((Number)localObject).intValue();
      int i = Integer.parseInt((String)localObject);
      return i;
    }
    catch (Exception localException)
    {
    }
    throw new JSONException("JSONArray[" + paramInt + "] is not a number.");
  }

  public JSONArray getJSONArray(int paramInt)
    throws JSONException
  {
    Object localObject = get(paramInt);
    if ((localObject instanceof JSONArray))
      return (JSONArray)localObject;
    throw new JSONException("JSONArray[" + paramInt + "] is not a JSONArray.");
  }

  public JSONObject getJSONObject(int paramInt)
    throws JSONException
  {
    Object localObject = get(paramInt);
    if ((localObject instanceof JSONObject))
      return (JSONObject)localObject;
    throw new JSONException("JSONArray[" + paramInt + "] is not a JSONObject.");
  }

  public long getLong(int paramInt)
    throws JSONException
  {
    Object localObject = get(paramInt);
    try
    {
      if ((localObject instanceof Number))
        return ((Number)localObject).longValue();
      long l = Long.parseLong((String)localObject);
      return l;
    }
    catch (Exception localException)
    {
    }
    throw new JSONException("JSONArray[" + paramInt + "] is not a number.");
  }

  public String getString(int paramInt)
    throws JSONException
  {
    Object localObject = get(paramInt);
    if ((localObject instanceof String))
      return (String)localObject;
    throw new JSONException("JSONArray[" + paramInt + "] not a string.");
  }

  public boolean isNull(int paramInt)
  {
    return JSONObject.NULL.equals(opt(paramInt));
  }

  public String join(String paramString)
    throws JSONException
  {
    int i = length();
    StringBuilder localStringBuilder = new StringBuilder();
    for (int j = 0; j < i; j++)
    {
      if (j > 0)
        localStringBuilder.append(paramString);
      localStringBuilder.append(JSONObject.valueToString(this.myArrayList.get(j)));
    }
    return localStringBuilder.toString();
  }

  public int length()
  {
    return this.myArrayList.size();
  }

  public Object opt(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= length()))
      return null;
    return this.myArrayList.get(paramInt);
  }

  public boolean optBoolean(int paramInt)
  {
    return optBoolean(paramInt, false);
  }

  public boolean optBoolean(int paramInt, boolean paramBoolean)
  {
    try
    {
      boolean bool = getBoolean(paramInt);
      return bool;
    }
    catch (Exception localException)
    {
    }
    return paramBoolean;
  }

  public double optDouble(int paramInt)
  {
    return optDouble(paramInt, (0.0D / 0.0D));
  }

  public double optDouble(int paramInt, double paramDouble)
  {
    try
    {
      double d = getDouble(paramInt);
      return d;
    }
    catch (Exception localException)
    {
    }
    return paramDouble;
  }

  public int optInt(int paramInt)
  {
    return optInt(paramInt, 0);
  }

  public int optInt(int paramInt1, int paramInt2)
  {
    try
    {
      int i = getInt(paramInt1);
      return i;
    }
    catch (Exception localException)
    {
    }
    return paramInt2;
  }

  public JSONArray optJSONArray(int paramInt)
  {
    Object localObject = opt(paramInt);
    if ((localObject instanceof JSONArray))
      return (JSONArray)localObject;
    return null;
  }

  public JSONObject optJSONObject(int paramInt)
  {
    Object localObject = opt(paramInt);
    if ((localObject instanceof JSONObject))
      return (JSONObject)localObject;
    return null;
  }

  public long optLong(int paramInt)
  {
    return optLong(paramInt, 0L);
  }

  public long optLong(int paramInt, long paramLong)
  {
    try
    {
      long l = getLong(paramInt);
      return l;
    }
    catch (Exception localException)
    {
    }
    return paramLong;
  }

  public String optString(int paramInt)
  {
    return optString(paramInt, "");
  }

  public String optString(int paramInt, String paramString)
  {
    Object localObject = opt(paramInt);
    if (JSONObject.NULL.equals(localObject))
      return paramString;
    return localObject.toString();
  }

  public JSONArray put(double paramDouble)
    throws JSONException
  {
    Double localDouble = new Double(paramDouble);
    JSONObject.testValidity(localDouble);
    put(localDouble);
    return this;
  }

  public JSONArray put(int paramInt)
  {
    put(new Integer(paramInt));
    return this;
  }

  public JSONArray put(int paramInt, double paramDouble)
    throws JSONException
  {
    put(paramInt, new Double(paramDouble));
    return this;
  }

  public JSONArray put(int paramInt1, int paramInt2)
    throws JSONException
  {
    put(paramInt1, new Integer(paramInt2));
    return this;
  }

  public JSONArray put(int paramInt, long paramLong)
    throws JSONException
  {
    put(paramInt, new Long(paramLong));
    return this;
  }

  public JSONArray put(int paramInt, Object paramObject)
    throws JSONException
  {
    JSONObject.testValidity(paramObject);
    if (paramInt < 0)
      throw new JSONException("JSONArray[" + paramInt + "] not found.");
    if (paramInt < length())
    {
      this.myArrayList.set(paramInt, paramObject);
      return this;
    }
    while (paramInt != length())
      put(JSONObject.NULL);
    put(paramObject);
    return this;
  }

  public JSONArray put(int paramInt, Collection<Object> paramCollection)
    throws JSONException
  {
    put(paramInt, new JSONArray(paramCollection));
    return this;
  }

  public JSONArray put(int paramInt, Map<String, Object> paramMap)
    throws JSONException
  {
    put(paramInt, new JSONObject(paramMap));
    return this;
  }

  public JSONArray put(int paramInt, boolean paramBoolean)
    throws JSONException
  {
    if (paramBoolean);
    for (Boolean localBoolean = Boolean.TRUE; ; localBoolean = Boolean.FALSE)
    {
      put(paramInt, localBoolean);
      return this;
    }
  }

  public JSONArray put(long paramLong)
  {
    put(new Long(paramLong));
    return this;
  }

  public JSONArray put(Object paramObject)
  {
    this.myArrayList.add(paramObject);
    return this;
  }

  public JSONArray put(Collection<Object> paramCollection)
  {
    put(new JSONArray(paramCollection));
    return this;
  }

  public JSONArray put(Map<String, Object> paramMap)
  {
    put(new JSONObject(paramMap));
    return this;
  }

  public JSONArray put(boolean paramBoolean)
  {
    if (paramBoolean);
    for (Boolean localBoolean = Boolean.TRUE; ; localBoolean = Boolean.FALSE)
    {
      put(localBoolean);
      return this;
    }
  }

  public Object remove(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < length()))
      return this.myArrayList.remove(paramInt);
    return null;
  }

  public boolean similar(Object paramObject)
    throws JSONException
  {
    if (!(paramObject instanceof JSONArray))
      return false;
    int i = length();
    if (i != ((JSONArray)paramObject).length())
      return false;
    for (int j = 0; j < i; j++)
    {
      Object localObject1 = get(j);
      Object localObject2 = ((JSONArray)paramObject).get(j);
      if ((localObject1 instanceof JSONObject))
      {
        if (!((JSONObject)localObject1).similar(localObject2))
          return false;
      }
      else if ((localObject1 instanceof JSONArray))
      {
        if (!((JSONArray)localObject1).similar(localObject2))
          return false;
      }
      else if (!localObject1.equals(localObject2))
        return false;
    }
    return true;
  }

  public JSONObject toJSONObject(JSONArray paramJSONArray)
    throws JSONException
  {
    JSONObject localJSONObject;
    if ((paramJSONArray == null) || (paramJSONArray.length() == 0) || (length() == 0))
      localJSONObject = null;
    while (true)
    {
      return localJSONObject;
      localJSONObject = new JSONObject();
      for (int i = 0; i < paramJSONArray.length(); i++)
        localJSONObject.put(paramJSONArray.getString(i), opt(i));
    }
  }

  public String toString()
  {
    try
    {
      String str = toString(0);
      return str;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public String toString(int paramInt)
    throws JSONException
  {
    StringWriter localStringWriter = new StringWriter();
    synchronized (localStringWriter.getBuffer())
    {
      String str = write(localStringWriter, paramInt, 0).toString();
      return str;
    }
  }

  public Writer write(Writer paramWriter)
    throws JSONException
  {
    return write(paramWriter, 0, 0);
  }

  Writer write(Writer paramWriter, int paramInt1, int paramInt2)
    throws JSONException
  {
    while (true)
    {
      int i;
      int m;
      int k;
      int j;
      try
      {
        i = length();
        paramWriter.write(91);
        if (i == 1)
        {
          JSONObject.writeValue(paramWriter, this.myArrayList.get(0), paramInt1, paramInt2);
          paramWriter.write(93);
          return paramWriter;
          if (m < i)
          {
            if (k != 0)
              paramWriter.write(44);
            if (paramInt1 > 0)
              paramWriter.write(10);
            JSONObject.indent(paramWriter, j);
            JSONObject.writeValue(paramWriter, this.myArrayList.get(m), paramInt1, j);
            k = 1;
            m++;
            continue;
          }
          if (paramInt1 > 0)
            paramWriter.write(10);
          JSONObject.indent(paramWriter, paramInt2);
          continue;
        }
      }
      catch (IOException localIOException)
      {
        throw new JSONException(localIOException.getMessage());
      }
      if (i != 0)
      {
        j = paramInt2 + paramInt1;
        k = 0;
        m = 0;
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.opendownload.encrypt.json.JSONArray
 * JD-Core Version:    0.6.2
 */