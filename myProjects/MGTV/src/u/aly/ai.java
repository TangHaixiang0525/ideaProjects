package u.aly;

import org.json.JSONArray;

public class ai extends bg
{
  public ai()
  {
  }

  public ai(String paramString)
    throws Exception
  {
    a(new JSONArray(paramString));
  }

  public ai(JSONArray paramJSONArray)
    throws Exception
  {
    a(paramJSONArray);
  }

  private void a(JSONArray paramJSONArray)
    throws Exception
  {
    a(paramJSONArray.getString(0));
    a(paramJSONArray.getInt(1));
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.ai
 * JD-Core Version:    0.6.2
 */