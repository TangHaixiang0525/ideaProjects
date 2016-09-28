package com.sohu.upload.service;

import android.location.Location;
import com.sohu.location.d;
import com.sohu.upload.a.a;

class c
  implements d
{
  c(b paramb)
  {
  }

  public void a(Location paramLocation)
  {
    CountService.a(this.a.a, paramLocation);
    a.a("��ȡ����Location:" + CountService.b(this.a.a));
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.upload.service.c
 * JD-Core Version:    0.6.2
 */