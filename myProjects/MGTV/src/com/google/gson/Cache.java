package com.google.gson;

abstract interface Cache<K, V>
{
  public abstract void addElement(K paramK, V paramV);

  public abstract V getElement(K paramK);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.gson.Cache
 * JD-Core Version:    0.6.2
 */