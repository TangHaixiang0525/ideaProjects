package com.google.zxing.oned.rss.expanded;

import com.google.zxing.oned.rss.DataCharacter;
import com.google.zxing.oned.rss.FinderPattern;

final class ExpandedPair
{
  private final FinderPattern finderPattern;
  private final DataCharacter leftChar;
  private final boolean mayBeLast;
  private final DataCharacter rightChar;

  ExpandedPair(DataCharacter paramDataCharacter1, DataCharacter paramDataCharacter2, FinderPattern paramFinderPattern, boolean paramBoolean)
  {
    this.leftChar = paramDataCharacter1;
    this.rightChar = paramDataCharacter2;
    this.finderPattern = paramFinderPattern;
    this.mayBeLast = paramBoolean;
  }

  FinderPattern getFinderPattern()
  {
    return this.finderPattern;
  }

  DataCharacter getLeftChar()
  {
    return this.leftChar;
  }

  DataCharacter getRightChar()
  {
    return this.rightChar;
  }

  boolean mayBeLast()
  {
    return this.mayBeLast;
  }

  public boolean mustBeLast()
  {
    return this.rightChar == null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.rss.expanded.ExpandedPair
 * JD-Core Version:    0.6.2
 */