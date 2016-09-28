package com.intertrust.wasabi;

public final class ErrorCodeExplanation
{
  private Recommendation recommendation = Recommendation.values()[paramInt];
  private String recommendationText;
  private String text;

  ErrorCodeExplanation(int paramInt, String paramString1, String paramString2)
  {
    this.text = paramString1;
    this.recommendationText = paramString2;
  }

  public Recommendation getRecommendation()
  {
    return this.recommendation;
  }

  public String getRecommendationText()
  {
    return this.recommendationText;
  }

  public String getText()
  {
    return this.text;
  }

  static enum Recommendation
  {
    static
    {
      RECOMMEND_REINSTALL = new Recommendation("RECOMMEND_REINSTALL", 1);
      RECOMMEND_CHECK_NETWORK = new Recommendation("RECOMMEND_CHECK_NETWORK", 2);
      RECOMMEND_CONTACT_SUPPORT = new Recommendation("RECOMMEND_CONTACT_SUPPORT", 3);
      RECOMMEND_REFRESH_CONTENT = new Recommendation("RECOMMEND_REFRESH_CONTENT", 4);
      RECOMMEND_FIX_DRM = new Recommendation("RECOMMEND_FIX_DRM", 5);
      RECOMMEND_GOTO_BROWSER = new Recommendation("RECOMMEND_GOTO_BROWSER", 6);
      RECOMMEND_REBOOT = new Recommendation("RECOMMEND_REBOOT", 7);
      Recommendation[] arrayOfRecommendation = new Recommendation[8];
      arrayOfRecommendation[0] = RECOMMEND_TRY_AGAIN;
      arrayOfRecommendation[1] = RECOMMEND_REINSTALL;
      arrayOfRecommendation[2] = RECOMMEND_CHECK_NETWORK;
      arrayOfRecommendation[3] = RECOMMEND_CONTACT_SUPPORT;
      arrayOfRecommendation[4] = RECOMMEND_REFRESH_CONTENT;
      arrayOfRecommendation[5] = RECOMMEND_FIX_DRM;
      arrayOfRecommendation[6] = RECOMMEND_GOTO_BROWSER;
      arrayOfRecommendation[7] = RECOMMEND_REBOOT;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.ErrorCodeExplanation
 * JD-Core Version:    0.6.2
 */