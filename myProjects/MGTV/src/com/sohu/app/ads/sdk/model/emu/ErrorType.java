package com.sohu.app.ads.sdk.model.emu;

public enum ErrorType
{
  static
  {
    NetError = new ErrorType("NetError", 2);
    ADSWITCH = new ErrorType("ADSWITCH", 3);
    RequestComponentError = new ErrorType("RequestComponentError", 4);
    RequestParamsError = new ErrorType("RequestParamsError", 5);
    AdOriginalError = new ErrorType("AdOriginalError", 6);
    RequestGroupViewError = new ErrorType("RequestGroupViewError", 7);
    RequestPlayerError = new ErrorType("RequestPlayerError", 8);
    ParamsParserError = new ErrorType("ParamsParserError", 9);
    ParamsServerUrlError = new ErrorType("ParamsServerUrlError", 10);
    ErrorType[] arrayOfErrorType = new ErrorType[11];
    arrayOfErrorType[0] = CONTEXT_ERROR;
    arrayOfErrorType[1] = REQUESTADDS_ERROR;
    arrayOfErrorType[2] = NetError;
    arrayOfErrorType[3] = ADSWITCH;
    arrayOfErrorType[4] = RequestComponentError;
    arrayOfErrorType[5] = RequestParamsError;
    arrayOfErrorType[6] = AdOriginalError;
    arrayOfErrorType[7] = RequestGroupViewError;
    arrayOfErrorType[8] = RequestPlayerError;
    arrayOfErrorType[9] = ParamsParserError;
    arrayOfErrorType[10] = ParamsServerUrlError;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.model.emu.ErrorType
 * JD-Core Version:    0.6.2
 */