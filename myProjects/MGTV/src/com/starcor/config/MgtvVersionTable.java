package com.starcor.config;

import com.starcor.core.utils.Logger;
import java.util.ArrayList;
import java.util.Iterator;

public class MgtvVersionTable
{
  private static final String TAG = "MgtvVersionTable";
  private static final ArrayList<MgtvVersionInfo> mgtvVersionTable = new ArrayList();

  static
  {
    mgtvVersionTable.add(new MgtvVersionInfo(900000000, MgtvBossPolicy.MG_TONGYONG, "2", "MG", "0", "0", "http://interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900000001, MgtvBossPolicy.KAIFANG, "2", MgtvVersion.mgtvChannelName, "0", "0", "http://interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900060000, MgtvBossPolicy.USEREXPERIENCE, "2", "TY", "0", "0", "http://interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900010000, MgtvBossPolicy.TCL_TV, "2", "TC", "0", "0", "http://tclepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900010002, MgtvBossPolicy.TCL_TV, "1", "TC", "0", "0", "http://tclepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900010201, MgtvBossPolicy.TCL_TV, "2", "TC", "2", "1", "http://tclepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900010203, MgtvBossPolicy.TCL_TV, "1", "TC", "2", "1", "http://tclepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900010102, MgtvBossPolicy.TCL_TV, "2", "TC", "1", "1", "http://tclepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900010105, MgtvBossPolicy.TCL_TV, "2", "TC", "1", "6", "http://tclepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900010101, MgtvBossPolicy.TCL_TV, "2", "TC", "1", "3", "http://tclepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900010103, MgtvBossPolicy.TCL_TV, "2", "TC", "1", "2", "http://tclepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900010104, MgtvBossPolicy.TCL_TV, "1", "TC", "1", "2", "http://tclepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900010100, MgtvBossPolicy.TCL_TV, "2", "TC", "1", "0", "http://tclepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900010200, MgtvBossPolicy.TCL_TV, "2", "TC", "2", "0", "http://tclepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900010001, MgtvBossPolicy.TCL_TV, "2", "TC", "8", "1", "http://tclepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900010202, MgtvBossPolicy.TCL_TV, "2", "TC", "2", "2", "http://tclepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900010301, MgtvBossPolicy.TCL_BOX, "2", "TC", "4", "2", "http://tcl7vepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900010401, MgtvBossPolicy.TCL_MANGO_TV, "2", "TC", "8", "1", "http://tclimgotvepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900010402, MgtvBossPolicy.TCL_MANGO_TV, "2", "TC", "1", "6", "http://tclimgotvepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900010403, MgtvBossPolicy.TCL_MANGO_TV, "2", "TC", "21", "1", "http://tclimgotvepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900010406, MgtvBossPolicy.TCL_TV, "2", "TC", "8", "6", "http://lehuaepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900011002, MgtvBossPolicy.CHANGEHONG, "2", "CH", "1", "1", "http://changhongepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900011001, MgtvBossPolicy.CHANGEHONG, "2", "CH", "1", "3", "http://changhongepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900011003, MgtvBossPolicy.CHANGEHONG, "2", "CH", "2", "3", "http://changhongepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900011004, MgtvBossPolicy.CHANGEHONG, "2", "CH", "2", "4", "http://changhongepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900011007, MgtvBossPolicy.CHANGEHONG, "1", "CH", "2", "4", "http://changhongepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900011005, MgtvBossPolicy.CHANGEHONG, "2", "CH", "2", "2", "http://changhongepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900011008, MgtvBossPolicy.CHANGEHONG, "2", "CH", "2", "5", "http://changhongepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900011009, MgtvBossPolicy.CHANGEHONG, "2", "CH", "1", "8", "http://changhongepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900011010, MgtvBossPolicy.CHANGEHONG, "2", "CH", "8", "6", "http://changhongepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900011011, MgtvBossPolicy.CHANGEHONG, "2", "CH", "2", "9", "http://changhongepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900012001, MgtvBossPolicy.MG_TONGYONG, "2", "HX", "1", "2", "http://hisenseepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900012002, MgtvBossPolicy.MG_TONGYONG, "2", "HX", "1", "1", "http://hisenseepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900018001, MgtvBossPolicy.SUOJIA, "2", "SJ", "1", "2", "http://sogoodepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900018002, MgtvBossPolicy.SUOJIA, "1", "SJ", "1", "2", "http://sogoodepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900018003, MgtvBossPolicy.SUOJIA, "2", "SJ", "1", "5", "http://sogoodepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900013001, MgtvBossPolicy.JIUSHI, "2", "JS", "1", "2", "http://jiushiepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900013002, MgtvBossPolicy.JIUSHI, "2", "JS", "17", "1", "http://jiushiepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(909900000, MgtvBossPolicy.MG_TONGYONG, "2", "SC", "4", "1", "http://interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900015001, MgtvBossPolicy.MG_TONGYONG, "2", "DX", "16", "1", "http://interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900016000, MgtvBossPolicy.HIMEDIA, "2", "HMD", "0", "0", "http://himediaepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900016001, MgtvBossPolicy.HIMEDIA, "2", "HMD", "3", "2", "http://himediaepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900016201, MgtvBossPolicy.HIMEDIA, "2", "HMD", "17", "1", "http://himediaepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900016202, MgtvBossPolicy.HIMEDIA, "2", "HMD", "17", "2", "http://himediaepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900016200, MgtvBossPolicy.HIMEDIA, "2", "HMD", "17", "0", "http://himediaepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900016205, MgtvBossPolicy.HIMEDIA, "2", "HMD", "3", "4", "http://himediaepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900017001, MgtvBossPolicy.HUAWEI_MANGGUOPAI, "1", "HW", "3", "1", "http://huaweiepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900019001, MgtvBossPolicy.OTEC, "2", "OTEC", "18", "1", "http://otecepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900020001, MgtvBossPolicy.YINGFEIKE, "2", "YFK", "17", "1", "http://inphicepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900021001, MgtvBossPolicy.YIGERUI, "2", "YGR", "19", "1", "http://egreatepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900021003, MgtvBossPolicy.YIGERUI, "2", "YGR", "17", "2", "http://egreatepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900021002, MgtvBossPolicy.YIGERUI, "2", "YGR", "17", "2", "http://egreatepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900021004, MgtvBossPolicy.YIGERUI, "2", "YGR", "17", "2", "http://egreatepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900022001, MgtvBossPolicy.TPST, "2", "TPST", "17", "1", "http://topsatfactory.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900022002, MgtvBossPolicy.TPST, "2", "TPST", "22", "1", "http://topsatfactory.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900023000, MgtvBossPolicy.YINGCHI, "2", "YC", "4", "2", "http://galaxyepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900023001, MgtvBossPolicy.YINGCHI, "2", "YC", "4", "2", "http://galaxyepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900023002, MgtvBossPolicy.YINGCHI, "2", "YC", "4", "3", "http://galaxyepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900024001, MgtvBossPolicy.BAIDU, "2", "BD", "4", "3", "http://baiduepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900024002, MgtvBossPolicy.BAIDU, "2", "BD", "4", "4", "http://baiduepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900026001, MgtvBossPolicy.PULIER, "2", "PLE", "17", "1", "http://pulierepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900025001, MgtvBossPolicy.JINYUN, "2", "JY", "20", "1", "http://jinyunepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900025002, MgtvBossPolicy.JINYUN, "2", "JY", "17", "1", "http://jinyunepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900027001, MgtvBossPolicy.SAIWEITE, "2", "SWT", "17", "0", "http://saiweiteepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900027002, MgtvBossPolicy.SAIWEITE, "2", "SWT", "17", "2", "http://saiweiteepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900027003, MgtvBossPolicy.SAIWEITE, "2", "SWT", "3", "7", "http://saiweiteepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900028001, MgtvBossPolicy.BOSHILIAN, "2", "BSL", "17", "1", "http://booslinkepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(901001001, MgtvBossPolicy.DINGXI_JIUDIAN, "2", "DXJD", "17", "1", "http://epg.dingxitech.tv/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900029000, MgtvBossPolicy.THTF, "2", "THTF", "4", "2", "http://qhtfepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900029001, MgtvBossPolicy.THTF, "2", "THTF", "4", "2", "http://qhtfepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900029002, MgtvBossPolicy.THTF, "2", "THTF", "4", "0", "http://qhtfepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900031001, MgtvBossPolicy.YISHIJIE, "2", "YSJ", "17", "2", "http://ysjepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900030001, MgtvBossPolicy.JIALIANWANG, "2", "JLW", "18", "2", "http://jlwepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900044000, MgtvBossPolicy.KUAILEWEISHI, "2", "KLWS", "2", "6", "http://qlwsepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900044001, MgtvBossPolicy.KUAILEWEISHI, "2", "KLWS", "4", "4", "http://qlwsepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900047000, MgtvBossPolicy.DFIM, "2", "DF", "18", "3", "http://dfepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900047001, MgtvBossPolicy.DFIM, "2", "DF", "18", "0", "http://dfepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900048000, MgtvBossPolicy.ZHIWO, "2", "ZW", "4", "3", "http://zivooepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900048001, MgtvBossPolicy.ZHIWO, "2", "ZW", "4", "4", "http://zivooepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900040001, MgtvBossPolicy.TAIJIE, "2", "TJ", "4", "2", "http://togicepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900040002, MgtvBossPolicy.TAIJIE, "2", "TJ", "4", "5", "http://togicepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900043001, MgtvBossPolicy.MG_TEST, "2", "QZ", "17", "0", "http://qzepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(909901102, MgtvBossPolicy.STARCOR, "2", "SC", "17", "2", "http://qzepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900051000, MgtvBossPolicy.KONKA, "2", "KJ", "25", "1", "http://konkaepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900034003, MgtvBossPolicy.PHILIP, "2", "PHI", "1", "8", "http://philipepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900034004, MgtvBossPolicy.PHILIP, "2", "PHI", "1", "6", "http://philipepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900042002, MgtvBossPolicy.FENGHUO, "2", "FHTX", "3", "7", "http://fhtxepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900052000, MgtvBossPolicy.XIANHAISHU, "2", "HS", "0", "0", "http://himediaepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900053000, MgtvBossPolicy.MAILE, "2", "ML", "17", "0", "http://mlepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900038002, MgtvBossPolicy.KAIBOER, "2", "KBE", "17", "0", "http://kbeepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900038003, MgtvBossPolicy.KAIBOER, "2", "KBE", "18", "2", "http://kbeepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900054000, MgtvBossPolicy.DYMT, "2", "DYMT", "5", "1", "http://dymtepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900010407, MgtvBossPolicy.XIANFENG, "2", "XF", "1", "6", "http://xianfengepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900010408, MgtvBossPolicy.TCL_TONGSHUAI, "2", "HTS", "1", "6", "http://tongshuaiepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900046000, MgtvBossPolicy.SKYWORTH, "2", "CW", "4", "4", "http://skyworthboxepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900046002, MgtvBossPolicy.SKYWORTH_TV, "2", "CWTV", "3", "8", "http://cwtvepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900055000, MgtvBossPolicy.WOBO, "2", "WB", "18", "4", "http://woboepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900055001, MgtvBossPolicy.WOBO, "2", "WB", "17", "3", "http://woboepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900039002, MgtvBossPolicy.HUOLE, "2", "HL", "24", "1", "http://holatekepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900037002, MgtvBossPolicy.TIANSHANG, "2", "TS", "3", "7", "http://tsepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900056000, MgtvBossPolicy.SWXX, "2", "SWXX", "3", "7", "http://himediaepg.interface.hifuntv.com/mgtv/STBindex"));
    mgtvVersionTable.add(new MgtvVersionInfo(900057000, MgtvBossPolicy.XIAOMI, "2", "MI", "2", "8", "http://xiaomiepg.interface.hifuntv.com/mgtv/STBindex"));
  }

  public static void dumpData()
  {
  }

  public static MgtvVersionInfo getMgtvVersionInfo(int paramInt)
  {
    Iterator localIterator = mgtvVersionTable.iterator();
    while (localIterator.hasNext())
    {
      MgtvVersionInfo localMgtvVersionInfo = (MgtvVersionInfo)localIterator.next();
      if (localMgtvVersionInfo.factory == paramInt)
        return localMgtvVersionInfo;
    }
    Logger.e("MgtvVersionTable", "getMgtvVersionInfo not found version of factory:" + paramInt);
    return null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.config.MgtvVersionTable
 * JD-Core Version:    0.6.2
 */