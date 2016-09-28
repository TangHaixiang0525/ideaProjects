package com.starcor.remote_key;

import java.util.HashMap;

public class KeyMap
{
  private static HashMap<String, Integer> keyCodeMap = new HashMap();

  static
  {
    keyCodeMap.put("0", Integer.valueOf(7));
    keyCodeMap.put("1", Integer.valueOf(8));
    keyCodeMap.put("2", Integer.valueOf(9));
    keyCodeMap.put("3", Integer.valueOf(10));
    keyCodeMap.put("4", Integer.valueOf(11));
    keyCodeMap.put("5", Integer.valueOf(12));
    keyCodeMap.put("6", Integer.valueOf(13));
    keyCodeMap.put("7", Integer.valueOf(14));
    keyCodeMap.put("8", Integer.valueOf(15));
    keyCodeMap.put("9", Integer.valueOf(16));
    keyCodeMap.put("A", Integer.valueOf(65565));
    keyCodeMap.put("B", Integer.valueOf(65566));
    keyCodeMap.put("C", Integer.valueOf(65567));
    keyCodeMap.put("D", Integer.valueOf(65568));
    keyCodeMap.put("E", Integer.valueOf(65569));
    keyCodeMap.put("F", Integer.valueOf(65570));
    keyCodeMap.put("G", Integer.valueOf(65571));
    keyCodeMap.put("H", Integer.valueOf(65572));
    keyCodeMap.put("I", Integer.valueOf(65573));
    keyCodeMap.put("J", Integer.valueOf(65574));
    keyCodeMap.put("K", Integer.valueOf(65575));
    keyCodeMap.put("L", Integer.valueOf(65576));
    keyCodeMap.put("M", Integer.valueOf(65577));
    keyCodeMap.put("N", Integer.valueOf(65578));
    keyCodeMap.put("O", Integer.valueOf(65579));
    keyCodeMap.put("P", Integer.valueOf(65580));
    keyCodeMap.put("Q", Integer.valueOf(65581));
    keyCodeMap.put("R", Integer.valueOf(65582));
    keyCodeMap.put("S", Integer.valueOf(65583));
    keyCodeMap.put("T", Integer.valueOf(65584));
    keyCodeMap.put("U", Integer.valueOf(65585));
    keyCodeMap.put("V", Integer.valueOf(65586));
    keyCodeMap.put("W", Integer.valueOf(65587));
    keyCodeMap.put("X", Integer.valueOf(65588));
    keyCodeMap.put("Y", Integer.valueOf(65589));
    keyCodeMap.put("Z", Integer.valueOf(65590));
    keyCodeMap.put("a", Integer.valueOf(29));
    keyCodeMap.put("b", Integer.valueOf(30));
    keyCodeMap.put("c", Integer.valueOf(31));
    keyCodeMap.put("d", Integer.valueOf(32));
    keyCodeMap.put("e", Integer.valueOf(33));
    keyCodeMap.put("f", Integer.valueOf(34));
    keyCodeMap.put("g", Integer.valueOf(35));
    keyCodeMap.put("h", Integer.valueOf(36));
    keyCodeMap.put("i", Integer.valueOf(37));
    keyCodeMap.put("j", Integer.valueOf(38));
    keyCodeMap.put("k", Integer.valueOf(39));
    keyCodeMap.put("l", Integer.valueOf(40));
    keyCodeMap.put("m", Integer.valueOf(41));
    keyCodeMap.put("n", Integer.valueOf(42));
    keyCodeMap.put("o", Integer.valueOf(43));
    keyCodeMap.put("p", Integer.valueOf(44));
    keyCodeMap.put("q", Integer.valueOf(45));
    keyCodeMap.put("r", Integer.valueOf(46));
    keyCodeMap.put("s", Integer.valueOf(47));
    keyCodeMap.put("t", Integer.valueOf(48));
    keyCodeMap.put("u", Integer.valueOf(49));
    keyCodeMap.put("v", Integer.valueOf(50));
    keyCodeMap.put("w", Integer.valueOf(51));
    keyCodeMap.put("x", Integer.valueOf(52));
    keyCodeMap.put("y", Integer.valueOf(53));
    keyCodeMap.put("z", Integer.valueOf(54));
    keyCodeMap.put("+", Integer.valueOf(81));
    keyCodeMap.put("-", Integer.valueOf(69));
    keyCodeMap.put("*", Integer.valueOf(17));
    keyCodeMap.put(",", Integer.valueOf(55));
    keyCodeMap.put(".", Integer.valueOf(56));
    keyCodeMap.put(";", Integer.valueOf(74));
    keyCodeMap.put("=", Integer.valueOf(70));
    keyCodeMap.put("\\", Integer.valueOf(73));
    keyCodeMap.put("/", Integer.valueOf(76));
    keyCodeMap.put(" ", Integer.valueOf(62));
    keyCodeMap.put("@", Integer.valueOf(77));
    keyCodeMap.put("[", Integer.valueOf(71));
    keyCodeMap.put("]", Integer.valueOf(72));
    keyCodeMap.put("'", Integer.valueOf(75));
    keyCodeMap.put("`", Integer.valueOf(68));
    keyCodeMap.put(")", Integer.valueOf(65543));
    keyCodeMap.put("!", Integer.valueOf(65544));
    keyCodeMap.put("#", Integer.valueOf(65546));
    keyCodeMap.put("$", Integer.valueOf(65547));
    keyCodeMap.put("%", Integer.valueOf(65548));
    keyCodeMap.put("^", Integer.valueOf(65549));
    keyCodeMap.put("&", Integer.valueOf(65550));
    keyCodeMap.put("*", Integer.valueOf(65551));
    keyCodeMap.put("(", Integer.valueOf(65552));
    keyCodeMap.put("_", Integer.valueOf(65605));
    keyCodeMap.put("|", Integer.valueOf(65609));
    keyCodeMap.put("<", Integer.valueOf(65591));
    keyCodeMap.put(">", Integer.valueOf(65592));
    keyCodeMap.put(":", Integer.valueOf(65610));
    keyCodeMap.put("?", Integer.valueOf(65612));
    keyCodeMap.put("{", Integer.valueOf(65607));
    keyCodeMap.put("}", Integer.valueOf(65608));
    keyCodeMap.put("\"", Integer.valueOf(65611));
    keyCodeMap.put("~", Integer.valueOf(65604));
    keyCodeMap.put("APOSTROPHE", Integer.valueOf(75));
    keyCodeMap.put("AT", Integer.valueOf(77));
    keyCodeMap.put("BACKSLASH", Integer.valueOf(73));
    keyCodeMap.put("COMMA", Integer.valueOf(55));
    keyCodeMap.put("EQUALS", Integer.valueOf(70));
    keyCodeMap.put("GRAVE", Integer.valueOf(68));
    keyCodeMap.put("MINUS", Integer.valueOf(69));
    keyCodeMap.put("PLUS", Integer.valueOf(81));
    keyCodeMap.put("PERIOD", Integer.valueOf(56));
    keyCodeMap.put("SLASH", Integer.valueOf(76));
    keyCodeMap.put("SEARCH", Integer.valueOf(84));
    keyCodeMap.put("SEMICOLON", Integer.valueOf(74));
    keyCodeMap.put("SPACE", Integer.valueOf(62));
    keyCodeMap.put("STAR", Integer.valueOf(17));
    keyCodeMap.put("ENTER", Integer.valueOf(66));
    keyCodeMap.put("RETURN", Integer.valueOf(4));
    keyCodeMap.put("HOME", Integer.valueOf(3));
    keyCodeMap.put("POWER", Integer.valueOf(26));
    keyCodeMap.put("VOL_UP", Integer.valueOf(24));
    keyCodeMap.put("VOL_DOWN", Integer.valueOf(25));
    keyCodeMap.put("MENU", Integer.valueOf(82));
    keyCodeMap.put("BACKSPACE", Integer.valueOf(67));
    keyCodeMap.put("DELETE", Integer.valueOf(67));
    keyCodeMap.put("DELETE_BACK", Integer.valueOf(65603));
    keyCodeMap.put("ERASE", Integer.valueOf(65603));
    keyCodeMap.put("CENTER", Integer.valueOf(23));
    keyCodeMap.put("UP", Integer.valueOf(19));
    keyCodeMap.put("DOWN", Integer.valueOf(20));
    keyCodeMap.put("LEFT", Integer.valueOf(21));
    keyCodeMap.put("RIGHT", Integer.valueOf(22));
    keyCodeMap.put("MEDIA_PLAY_PAUSE", Integer.valueOf(85));
    keyCodeMap.put("MEDIA_NEXT", Integer.valueOf(87));
    keyCodeMap.put("MEDIA_PREVIOUS", Integer.valueOf(88));
    keyCodeMap.put("MEDIA_FAST_FORWARD", Integer.valueOf(90));
    keyCodeMap.put("MEDIA_REWIND", Integer.valueOf(89));
    keyCodeMap.put("MEDIA_STOP", Integer.valueOf(86));
    keyCodeMap.put("CAMERA", Integer.valueOf(27));
    keyCodeMap.put("ENDCALL", Integer.valueOf(6));
    keyCodeMap.put("CALL", Integer.valueOf(5));
    keyCodeMap.put("ENVELOPE", Integer.valueOf(65));
    keyCodeMap.put("TAB", Integer.valueOf(61));
    keyCodeMap.put("EXPLORER", Integer.valueOf(64));
    keyCodeMap.put("LEFT_BRACKET", Integer.valueOf(71));
    keyCodeMap.put("RIGHT_BRACKET", Integer.valueOf(72));
    keyCodeMap.put("NUM", Integer.valueOf(78));
    keyCodeMap.put("MUTE", Integer.valueOf(91));
    keyCodeMap.put("POUND", Integer.valueOf(18));
    keyCodeMap.put("CUT", Integer.valueOf(131124));
    keyCodeMap.put("PASTE", Integer.valueOf(131122));
    keyCodeMap.put("COPY", Integer.valueOf(131103));
    keyCodeMap.put("SELECT_ALL", Integer.valueOf(131101));
  }

  public static int getKeyCode(String paramString)
  {
    Integer localInteger = (Integer)keyCodeMap.get(paramString);
    if (localInteger == null)
      return -1;
    return localInteger.intValue();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.remote_key.KeyMap
 * JD-Core Version:    0.6.2
 */