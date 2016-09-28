package com.google.gson;

public enum LongSerializationPolicy
{
  private final Strategy strategy;

  static
  {
    LongSerializationPolicy[] arrayOfLongSerializationPolicy = new LongSerializationPolicy[2];
    arrayOfLongSerializationPolicy[0] = DEFAULT;
    arrayOfLongSerializationPolicy[1] = STRING;
  }

  private LongSerializationPolicy(Strategy paramStrategy)
  {
    this.strategy = paramStrategy;
  }

  public JsonElement serialize(Long paramLong)
  {
    return this.strategy.serialize(paramLong);
  }

  private static class DefaultStrategy
    implements LongSerializationPolicy.Strategy
  {
    public JsonElement serialize(Long paramLong)
    {
      return new JsonPrimitive(paramLong);
    }
  }

  private static abstract interface Strategy
  {
    public abstract JsonElement serialize(Long paramLong);
  }

  private static class StringStrategy
    implements LongSerializationPolicy.Strategy
  {
    public JsonElement serialize(Long paramLong)
    {
      return new JsonPrimitive(String.valueOf(paramLong));
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.gson.LongSerializationPolicy
 * JD-Core Version:    0.6.2
 */