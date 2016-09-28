package com.starcor.remote_key;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TestActivity extends Activity
{
  private static final String TAG = TestActivity.class.getSimpleName();
  Button btnStart;
  Button btnStop;
  Button btnTest;

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.test_activity);
    this.btnStart = ((Button)findViewById(R.id.btnStart));
    this.btnStart.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        TestActivity.this.startService(new Intent(TestActivity.this, RemoteKeyService.class));
      }
    });
    this.btnStop = ((Button)findViewById(R.id.btnStop));
    this.btnStop.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        TestActivity.this.stopService(new Intent(TestActivity.this, RemoteKeyService.class));
      }
    });
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.remote_key.TestActivity
 * JD-Core Version:    0.6.2
 */