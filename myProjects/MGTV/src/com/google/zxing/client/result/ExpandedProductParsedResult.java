package com.google.zxing.client.result;

import java.util.Map;

public final class ExpandedProductParsedResult extends ParsedResult
{
  public static final String KILOGRAM = "KG";
  public static final String POUND = "LB";
  private final String bestBeforeDate;
  private final String expirationDate;
  private final String lotNumber;
  private final String packagingDate;
  private final String price;
  private final String priceCurrency;
  private final String priceIncrement;
  private final String productID;
  private final String productionDate;
  private final String sscc;
  private final Map<String, String> uncommonAIs;
  private final String weight;
  private final String weightIncrement;
  private final String weightType;

  public ExpandedProductParsedResult(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, Map<String, String> paramMap)
  {
    super(ParsedResultType.PRODUCT);
    this.productID = paramString1;
    this.sscc = paramString2;
    this.lotNumber = paramString3;
    this.productionDate = paramString4;
    this.packagingDate = paramString5;
    this.bestBeforeDate = paramString6;
    this.expirationDate = paramString7;
    this.weight = paramString8;
    this.weightType = paramString9;
    this.weightIncrement = paramString10;
    this.price = paramString11;
    this.priceIncrement = paramString12;
    this.priceCurrency = paramString13;
    this.uncommonAIs = paramMap;
  }

  private static boolean equalsOrNull(Object paramObject1, Object paramObject2)
  {
    if (paramObject1 == null)
      return paramObject2 == null;
    return paramObject1.equals(paramObject2);
  }

  private static int hashNotNull(Object paramObject)
  {
    if (paramObject == null)
      return 0;
    return paramObject.hashCode();
  }

  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ExpandedProductParsedResult));
    ExpandedProductParsedResult localExpandedProductParsedResult;
    do
    {
      return false;
      localExpandedProductParsedResult = (ExpandedProductParsedResult)paramObject;
    }
    while ((!equalsOrNull(this.productID, localExpandedProductParsedResult.productID)) || (!equalsOrNull(this.sscc, localExpandedProductParsedResult.sscc)) || (!equalsOrNull(this.lotNumber, localExpandedProductParsedResult.lotNumber)) || (!equalsOrNull(this.productionDate, localExpandedProductParsedResult.productionDate)) || (!equalsOrNull(this.bestBeforeDate, localExpandedProductParsedResult.bestBeforeDate)) || (!equalsOrNull(this.expirationDate, localExpandedProductParsedResult.expirationDate)) || (!equalsOrNull(this.weight, localExpandedProductParsedResult.weight)) || (!equalsOrNull(this.weightType, localExpandedProductParsedResult.weightType)) || (!equalsOrNull(this.weightIncrement, localExpandedProductParsedResult.weightIncrement)) || (!equalsOrNull(this.price, localExpandedProductParsedResult.price)) || (!equalsOrNull(this.priceIncrement, localExpandedProductParsedResult.priceIncrement)) || (!equalsOrNull(this.priceCurrency, localExpandedProductParsedResult.priceCurrency)) || (!equalsOrNull(this.uncommonAIs, localExpandedProductParsedResult.uncommonAIs)));
    return true;
  }

  public String getBestBeforeDate()
  {
    return this.bestBeforeDate;
  }

  public String getDisplayResult()
  {
    return String.valueOf(this.productID);
  }

  public String getExpirationDate()
  {
    return this.expirationDate;
  }

  public String getLotNumber()
  {
    return this.lotNumber;
  }

  public String getPackagingDate()
  {
    return this.packagingDate;
  }

  public String getPrice()
  {
    return this.price;
  }

  public String getPriceCurrency()
  {
    return this.priceCurrency;
  }

  public String getPriceIncrement()
  {
    return this.priceIncrement;
  }

  public String getProductID()
  {
    return this.productID;
  }

  public String getProductionDate()
  {
    return this.productionDate;
  }

  public String getSscc()
  {
    return this.sscc;
  }

  public Map<String, String> getUncommonAIs()
  {
    return this.uncommonAIs;
  }

  public String getWeight()
  {
    return this.weight;
  }

  public String getWeightIncrement()
  {
    return this.weightIncrement;
  }

  public String getWeightType()
  {
    return this.weightType;
  }

  public int hashCode()
  {
    return 0x0 ^ hashNotNull(this.productID) ^ hashNotNull(this.sscc) ^ hashNotNull(this.lotNumber) ^ hashNotNull(this.productionDate) ^ hashNotNull(this.bestBeforeDate) ^ hashNotNull(this.expirationDate) ^ hashNotNull(this.weight) ^ hashNotNull(this.weightType) ^ hashNotNull(this.weightIncrement) ^ hashNotNull(this.price) ^ hashNotNull(this.priceIncrement) ^ hashNotNull(this.priceCurrency) ^ hashNotNull(this.uncommonAIs);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.client.result.ExpandedProductParsedResult
 * JD-Core Version:    0.6.2
 */