package com.google.zxing.oned.rss.expanded.decoders;

final class BlockParsedResult
{
  private final DecodedInformation decodedInformation;
  private final boolean finished;

  BlockParsedResult(DecodedInformation paramDecodedInformation, boolean paramBoolean)
  {
    this.finished = paramBoolean;
    this.decodedInformation = paramDecodedInformation;
  }

  BlockParsedResult(boolean paramBoolean)
  {
    this(null, paramBoolean);
  }

  DecodedInformation getDecodedInformation()
  {
    return this.decodedInformation;
  }

  boolean isFinished()
  {
    return this.finished;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.rss.expanded.decoders.BlockParsedResult
 * JD-Core Version:    0.6.2
 */