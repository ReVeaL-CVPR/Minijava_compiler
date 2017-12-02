package MiniJava.utils;

/** Token Manager. */
public class MiniJavaParserTokenManager implements MiniJavaParserConstants
{

  /** Debug output. */
  public static  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public static  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private static final int jjStopStringLiteralDfa_0(int pos, long active0)
{
   switch (pos)
   {
      case 0:
         if ((active0 & 0x7ffff800000L) != 0L)
         {
            jjmatchedKind = 44;
            return 4;
         }
         return -1;
      case 1:
         if ((active0 & 0x20000000L) != 0L)
            return 4;
         if ((active0 & 0x7ffdf800000L) != 0L)
         {
            jjmatchedKind = 44;
            jjmatchedPos = 1;
            return 4;
         }
         return -1;
      case 2:
         if ((active0 & 0x482000000L) != 0L)
            return 4;
         if ((active0 & 0x7fb5d800000L) != 0L)
         {
            if (jjmatchedPos != 2)
            {
               jjmatchedKind = 44;
               jjmatchedPos = 2;
            }
            return 4;
         }
         return -1;
      case 3:
         if ((active0 & 0x58204000000L) != 0L)
            return 4;
         if ((active0 & 0x2795b800000L) != 0L)
         {
            jjmatchedKind = 44;
            jjmatchedPos = 3;
            return 4;
         }
         return -1;
      case 4:
         if ((active0 & 0x51000000L) != 0L)
            return 4;
         if ((active0 & 0x2790a800000L) != 0L)
         {
            jjmatchedKind = 44;
            jjmatchedPos = 4;
            return 4;
         }
         return -1;
      case 5:
         if ((active0 & 0x7900000000L) != 0L)
            return 4;
         if ((active0 & 0x2000a800000L) != 0L)
         {
            jjmatchedKind = 44;
            jjmatchedPos = 5;
            return 4;
         }
         return -1;
      case 6:
         if ((active0 & 0x20000000000L) != 0L)
         {
            if (jjmatchedPos < 5)
            {
               jjmatchedKind = 44;
               jjmatchedPos = 5;
            }
            return -1;
         }
         if ((active0 & 0x8800000L) != 0L)
            return 4;
         if ((active0 & 0x2000000L) != 0L)
         {
            jjmatchedKind = 44;
            jjmatchedPos = 6;
            return 4;
         }
         return -1;
      case 7:
         if ((active0 & 0x20000000000L) != 0L)
         {
            if (jjmatchedPos < 5)
            {
               jjmatchedKind = 44;
               jjmatchedPos = 5;
            }
            return -1;
         }
         if ((active0 & 0x2000000L) != 0L)
         {
            jjmatchedKind = 44;
            jjmatchedPos = 7;
            return 4;
         }
         return -1;
      case 8:
         if ((active0 & 0x20000000000L) != 0L)
         {
            if (jjmatchedPos < 5)
            {
               jjmatchedKind = 44;
               jjmatchedPos = 5;
            }
            return -1;
         }
         if ((active0 & 0x2000000L) != 0L)
            return 4;
         return -1;
      case 9:
         if ((active0 & 0x20000000000L) != 0L)
         {
            if (jjmatchedPos < 5)
            {
               jjmatchedKind = 44;
               jjmatchedPos = 5;
            }
            return -1;
         }
         return -1;
      case 10:
         if ((active0 & 0x20000000000L) != 0L)
         {
            if (jjmatchedPos < 5)
            {
               jjmatchedKind = 44;
               jjmatchedPos = 5;
            }
            return -1;
         }
         return -1;
      case 11:
         if ((active0 & 0x20000000000L) != 0L)
         {
            if (jjmatchedPos < 5)
            {
               jjmatchedKind = 44;
               jjmatchedPos = 5;
            }
            return -1;
         }
         return -1;
      case 12:
         if ((active0 & 0x20000000000L) != 0L)
         {
            if (jjmatchedPos < 5)
            {
               jjmatchedKind = 44;
               jjmatchedPos = 5;
            }
            return -1;
         }
         return -1;
      case 13:
         if ((active0 & 0x20000000000L) != 0L)
         {
            if (jjmatchedPos < 5)
            {
               jjmatchedKind = 44;
               jjmatchedPos = 5;
            }
            return -1;
         }
         return -1;
      case 14:
         if ((active0 & 0x20000000000L) != 0L)
         {
            if (jjmatchedPos < 5)
            {
               jjmatchedKind = 44;
               jjmatchedPos = 5;
            }
            return -1;
         }
         return -1;
      case 15:
         if ((active0 & 0x20000000000L) != 0L)
         {
            if (jjmatchedPos < 5)
            {
               jjmatchedKind = 44;
               jjmatchedPos = 5;
            }
            return -1;
         }
         return -1;
      case 16:
         if ((active0 & 0x20000000000L) != 0L)
         {
            if (jjmatchedPos < 5)
            {
               jjmatchedKind = 44;
               jjmatchedPos = 5;
            }
            return -1;
         }
         return -1;
      default :
         return -1;
   }
}
private static final int jjStartNfa_0(int pos, long active0)
{
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
static private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
static private int jjMoveStringLiteralDfa0_0()
{
   switch(curChar)
   {
      case 33:
         return jjStopAtPos(0, 22);
      case 38:
         return jjMoveStringLiteralDfa1_0(0x200000L);
      case 40:
         return jjStopAtPos(0, 9);
      case 41:
         return jjStopAtPos(0, 10);
      case 42:
         return jjStopAtPos(0, 48);
      case 43:
         return jjStopAtPos(0, 19);
      case 44:
         return jjStopAtPos(0, 47);
      case 45:
         return jjStopAtPos(0, 20);
      case 46:
         return jjStopAtPos(0, 16);
      case 59:
         return jjStopAtPos(0, 15);
      case 60:
         return jjStopAtPos(0, 18);
      case 61:
         return jjStopAtPos(0, 17);
      case 83:
         return jjMoveStringLiteralDfa1_0(0x24000000000L);
      case 91:
         return jjStopAtPos(0, 11);
      case 93:
         return jjStopAtPos(0, 12);
      case 98:
         return jjMoveStringLiteralDfa1_0(0x800000L);
      case 99:
         return jjMoveStringLiteralDfa1_0(0x1000000L);
      case 101:
         return jjMoveStringLiteralDfa1_0(0xc000000L);
      case 102:
         return jjMoveStringLiteralDfa1_0(0x10000000L);
      case 105:
         return jjMoveStringLiteralDfa1_0(0xa2000000L);
      case 108:
         return jjMoveStringLiteralDfa1_0(0x100000000L);
      case 109:
         return jjMoveStringLiteralDfa1_0(0x200000000L);
      case 110:
         return jjMoveStringLiteralDfa1_0(0x400000000L);
      case 112:
         return jjMoveStringLiteralDfa1_0(0x800000000L);
      case 114:
         return jjMoveStringLiteralDfa1_0(0x1000000000L);
      case 115:
         return jjMoveStringLiteralDfa1_0(0x2000000000L);
      case 116:
         return jjMoveStringLiteralDfa1_0(0x18000000000L);
      case 118:
         return jjMoveStringLiteralDfa1_0(0x40000000000L);
      case 119:
         return jjMoveStringLiteralDfa1_0(0x40000000L);
      case 123:
         return jjStopAtPos(0, 13);
      case 125:
         return jjStopAtPos(0, 14);
      default :
         return jjMoveNfa_0(0, 0);
   }
}
static private int jjMoveStringLiteralDfa1_0(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 38:
         if ((active0 & 0x200000L) != 0L)
            return jjStopAtPos(1, 21);
         break;
      case 97:
         return jjMoveStringLiteralDfa2_0(active0, 0x210000000L);
      case 101:
         return jjMoveStringLiteralDfa2_0(active0, 0x1500000000L);
      case 102:
         if ((active0 & 0x20000000L) != 0L)
            return jjStartNfaWithStates_0(1, 29, 4);
         break;
      case 104:
         return jjMoveStringLiteralDfa2_0(active0, 0x8040000000L);
      case 108:
         return jjMoveStringLiteralDfa2_0(active0, 0x5000000L);
      case 110:
         return jjMoveStringLiteralDfa2_0(active0, 0x82000000L);
      case 111:
         return jjMoveStringLiteralDfa2_0(active0, 0x40000800000L);
      case 114:
         return jjMoveStringLiteralDfa2_0(active0, 0x10000000000L);
      case 116:
         return jjMoveStringLiteralDfa2_0(active0, 0x6000000000L);
      case 117:
         return jjMoveStringLiteralDfa2_0(active0, 0x800000000L);
      case 120:
         return jjMoveStringLiteralDfa2_0(active0, 0x8000000L);
      case 121:
         return jjMoveStringLiteralDfa2_0(active0, 0x20000000000L);
      default :
         break;
   }
   return jjStartNfa_0(0, active0);
}
static private int jjMoveStringLiteralDfa2_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(0, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(1, active0);
      return 2;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa3_0(active0, 0x2001000000L);
      case 98:
         return jjMoveStringLiteralDfa3_0(active0, 0x800000000L);
      case 105:
         return jjMoveStringLiteralDfa3_0(active0, 0x48240000000L);
      case 108:
         return jjMoveStringLiteralDfa3_0(active0, 0x10000000L);
      case 110:
         return jjMoveStringLiteralDfa3_0(active0, 0x100000000L);
      case 111:
         return jjMoveStringLiteralDfa3_0(active0, 0x800000L);
      case 114:
         return jjMoveStringLiteralDfa3_0(active0, 0x4000000000L);
      case 115:
         return jjMoveStringLiteralDfa3_0(active0, 0x20004000000L);
      case 116:
         if ((active0 & 0x80000000L) != 0L)
         {
            jjmatchedKind = 31;
            jjmatchedPos = 2;
         }
         return jjMoveStringLiteralDfa3_0(active0, 0x100a000000L);
      case 117:
         return jjMoveStringLiteralDfa3_0(active0, 0x10000000000L);
      case 119:
         if ((active0 & 0x400000000L) != 0L)
            return jjStartNfaWithStates_0(2, 34, 4);
         break;
      default :
         break;
   }
   return jjStartNfa_0(1, active0);
}
static private int jjMoveStringLiteralDfa3_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(1, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(2, active0);
      return 3;
   }
   switch(curChar)
   {
      case 100:
         if ((active0 & 0x40000000000L) != 0L)
            return jjStartNfaWithStates_0(3, 42, 4);
         break;
      case 101:
         if ((active0 & 0x4000000L) != 0L)
            return jjStartNfaWithStates_0(3, 26, 4);
         else if ((active0 & 0x10000000000L) != 0L)
            return jjStartNfaWithStates_0(3, 40, 4);
         return jjMoveStringLiteralDfa4_0(active0, 0xa000000L);
      case 103:
         return jjMoveStringLiteralDfa4_0(active0, 0x100000000L);
      case 105:
         return jjMoveStringLiteralDfa4_0(active0, 0x4000000000L);
      case 108:
         return jjMoveStringLiteralDfa4_0(active0, 0x840800000L);
      case 110:
         if ((active0 & 0x200000000L) != 0L)
            return jjStartNfaWithStates_0(3, 33, 4);
         break;
      case 115:
         if ((active0 & 0x8000000000L) != 0L)
            return jjStartNfaWithStates_0(3, 39, 4);
         return jjMoveStringLiteralDfa4_0(active0, 0x11000000L);
      case 116:
         return jjMoveStringLiteralDfa4_0(active0, 0x22000000000L);
      case 117:
         return jjMoveStringLiteralDfa4_0(active0, 0x1000000000L);
      default :
         break;
   }
   return jjStartNfa_0(2, active0);
}
static private int jjMoveStringLiteralDfa4_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(2, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(3, active0);
      return 4;
   }
   switch(curChar)
   {
      case 101:
         if ((active0 & 0x10000000L) != 0L)
            return jjStartNfaWithStates_0(4, 28, 4);
         else if ((active0 & 0x40000000L) != 0L)
            return jjStartNfaWithStates_0(4, 30, 4);
         return jjMoveStringLiteralDfa5_0(active0, 0x20000800000L);
      case 105:
         return jjMoveStringLiteralDfa5_0(active0, 0x2800000000L);
      case 110:
         return jjMoveStringLiteralDfa5_0(active0, 0x4008000000L);
      case 114:
         return jjMoveStringLiteralDfa5_0(active0, 0x1002000000L);
      case 115:
         if ((active0 & 0x1000000L) != 0L)
            return jjStartNfaWithStates_0(4, 24, 4);
         break;
      case 116:
         return jjMoveStringLiteralDfa5_0(active0, 0x100000000L);
      default :
         break;
   }
   return jjStartNfa_0(3, active0);
}
static private int jjMoveStringLiteralDfa5_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(3, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(4, active0);
      return 5;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa6_0(active0, 0x800000L);
      case 99:
         if ((active0 & 0x800000000L) != 0L)
            return jjStartNfaWithStates_0(5, 35, 4);
         else if ((active0 & 0x2000000000L) != 0L)
            return jjStartNfaWithStates_0(5, 37, 4);
         break;
      case 100:
         return jjMoveStringLiteralDfa6_0(active0, 0x8000000L);
      case 102:
         return jjMoveStringLiteralDfa6_0(active0, 0x2000000L);
      case 103:
         if ((active0 & 0x4000000000L) != 0L)
            return jjStartNfaWithStates_0(5, 38, 4);
         break;
      case 104:
         if ((active0 & 0x100000000L) != 0L)
            return jjStartNfaWithStates_0(5, 32, 4);
         break;
      case 109:
         return jjMoveStringLiteralDfa6_0(active0, 0x20000000000L);
      case 110:
         if ((active0 & 0x1000000000L) != 0L)
            return jjStartNfaWithStates_0(5, 36, 4);
         break;
      default :
         break;
   }
   return jjStartNfa_0(4, active0);
}
static private int jjMoveStringLiteralDfa6_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(4, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(5, active0);
      return 6;
   }
   switch(curChar)
   {
      case 46:
         return jjMoveStringLiteralDfa7_0(active0, 0x20000000000L);
      case 97:
         return jjMoveStringLiteralDfa7_0(active0, 0x2000000L);
      case 110:
         if ((active0 & 0x800000L) != 0L)
            return jjStartNfaWithStates_0(6, 23, 4);
         break;
      case 115:
         if ((active0 & 0x8000000L) != 0L)
            return jjStartNfaWithStates_0(6, 27, 4);
         break;
      default :
         break;
   }
   return jjStartNfa_0(5, active0);
}
static private int jjMoveStringLiteralDfa7_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(5, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(6, active0);
      return 7;
   }
   switch(curChar)
   {
      case 99:
         return jjMoveStringLiteralDfa8_0(active0, 0x2000000L);
      case 111:
         return jjMoveStringLiteralDfa8_0(active0, 0x20000000000L);
      default :
         break;
   }
   return jjStartNfa_0(6, active0);
}
static private int jjMoveStringLiteralDfa8_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(6, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(7, active0);
      return 8;
   }
   switch(curChar)
   {
      case 101:
         if ((active0 & 0x2000000L) != 0L)
            return jjStartNfaWithStates_0(8, 25, 4);
         break;
      case 117:
         return jjMoveStringLiteralDfa9_0(active0, 0x20000000000L);
      default :
         break;
   }
   return jjStartNfa_0(7, active0);
}
static private int jjMoveStringLiteralDfa9_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(7, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(8, active0);
      return 9;
   }
   switch(curChar)
   {
      case 116:
         return jjMoveStringLiteralDfa10_0(active0, 0x20000000000L);
      default :
         break;
   }
   return jjStartNfa_0(8, active0);
}
static private int jjMoveStringLiteralDfa10_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(8, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(9, active0);
      return 10;
   }
   switch(curChar)
   {
      case 46:
         return jjMoveStringLiteralDfa11_0(active0, 0x20000000000L);
      default :
         break;
   }
   return jjStartNfa_0(9, active0);
}
static private int jjMoveStringLiteralDfa11_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(9, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(10, active0);
      return 11;
   }
   switch(curChar)
   {
      case 112:
         return jjMoveStringLiteralDfa12_0(active0, 0x20000000000L);
      default :
         break;
   }
   return jjStartNfa_0(10, active0);
}
static private int jjMoveStringLiteralDfa12_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(10, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(11, active0);
      return 12;
   }
   switch(curChar)
   {
      case 114:
         return jjMoveStringLiteralDfa13_0(active0, 0x20000000000L);
      default :
         break;
   }
   return jjStartNfa_0(11, active0);
}
static private int jjMoveStringLiteralDfa13_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(11, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(12, active0);
      return 13;
   }
   switch(curChar)
   {
      case 105:
         return jjMoveStringLiteralDfa14_0(active0, 0x20000000000L);
      default :
         break;
   }
   return jjStartNfa_0(12, active0);
}
static private int jjMoveStringLiteralDfa14_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(12, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(13, active0);
      return 14;
   }
   switch(curChar)
   {
      case 110:
         return jjMoveStringLiteralDfa15_0(active0, 0x20000000000L);
      default :
         break;
   }
   return jjStartNfa_0(13, active0);
}
static private int jjMoveStringLiteralDfa15_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(13, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(14, active0);
      return 15;
   }
   switch(curChar)
   {
      case 116:
         return jjMoveStringLiteralDfa16_0(active0, 0x20000000000L);
      default :
         break;
   }
   return jjStartNfa_0(14, active0);
}
static private int jjMoveStringLiteralDfa16_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(14, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(15, active0);
      return 16;
   }
   switch(curChar)
   {
      case 108:
         return jjMoveStringLiteralDfa17_0(active0, 0x20000000000L);
      default :
         break;
   }
   return jjStartNfa_0(15, active0);
}
static private int jjMoveStringLiteralDfa17_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(15, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(16, active0);
      return 17;
   }
   switch(curChar)
   {
      case 110:
         if ((active0 & 0x20000000000L) != 0L)
            return jjStopAtPos(17, 41);
         break;
      default :
         break;
   }
   return jjStartNfa_0(16, active0);
}
static private int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
static final long[] jjbitVec0 = {
   0x1ff00000fffffffeL, 0xffffffffffffc000L, 0xffffffffL, 0x600000000000000L
};
static final long[] jjbitVec2 = {
   0x0L, 0x0L, 0x0L, 0xff7fffffff7fffffL
};
static final long[] jjbitVec3 = {
   0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL, 0xffffffffffffffffL
};
static final long[] jjbitVec4 = {
   0xffffffffffffffffL, 0xffffffffffffffffL, 0xffffL, 0x0L
};
static final long[] jjbitVec5 = {
   0xffffffffffffffffL, 0xffffffffffffffffL, 0x0L, 0x0L
};
static final long[] jjbitVec6 = {
   0x3fffffffffffL, 0x0L, 0x0L, 0x0L
};
static final long[] jjbitVec7 = {
   0xfffffffffffffffeL, 0xffffffffffffffffL, 0xffffffffffffffffL, 0xffffffffffffffffL
};
static final long[] jjbitVec8 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
static private int jjMoveNfa_0(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 24;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0x3fe000000000000L & l) != 0L)
                  {
                     if (kind > 43)
                        kind = 43;
                     jjCheckNAdd(1);
                  }
                  else if (curChar == 47)
                     jjAddStates(0, 2);
                  else if (curChar == 36)
                  {
                     if (kind > 44)
                        kind = 44;
                     jjCheckNAdd(4);
                  }
                  else if (curChar == 48)
                  {
                     if (kind > 43)
                        kind = 43;
                  }
                  break;
               case 1:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 43)
                     kind = 43;
                  jjCheckNAdd(1);
                  break;
               case 2:
                  if (curChar == 48 && kind > 43)
                     kind = 43;
                  break;
               case 3:
                  if (curChar != 36)
                     break;
                  if (kind > 44)
                     kind = 44;
                  jjCheckNAdd(4);
                  break;
               case 4:
                  if ((0x3ff001000000000L & l) == 0L)
                     break;
                  if (kind > 44)
                     kind = 44;
                  jjCheckNAdd(4);
                  break;
               case 5:
                  if (curChar == 47)
                     jjAddStates(0, 2);
                  break;
               case 6:
                  if (curChar == 47)
                     jjCheckNAddStates(3, 5);
                  break;
               case 7:
                  if ((0xffffffffffffdbffL & l) != 0L)
                     jjCheckNAddStates(3, 5);
                  break;
               case 8:
                  if ((0x2400L & l) != 0L && kind > 6)
                     kind = 6;
                  break;
               case 9:
                  if (curChar == 10 && kind > 6)
                     kind = 6;
                  break;
               case 10:
                  if (curChar == 13)
                     jjstateSet[jjnewStateCnt++] = 9;
                  break;
               case 11:
                  if (curChar == 42)
                     jjCheckNAddTwoStates(12, 13);
                  break;
               case 12:
                  if ((0xfffffbffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(12, 13);
                  break;
               case 13:
                  if (curChar == 42)
                     jjCheckNAddStates(6, 8);
                  break;
               case 14:
                  if ((0xffff7bffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(15, 13);
                  break;
               case 15:
                  if ((0xfffffbffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(15, 13);
                  break;
               case 16:
                  if (curChar == 47 && kind > 7)
                     kind = 7;
                  break;
               case 17:
                  if (curChar == 42)
                     jjstateSet[jjnewStateCnt++] = 11;
                  break;
               case 18:
                  if (curChar == 42)
                     jjCheckNAddTwoStates(19, 20);
                  break;
               case 19:
                  if ((0xfffffbffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(19, 20);
                  break;
               case 20:
                  if (curChar == 42)
                     jjCheckNAddStates(9, 11);
                  break;
               case 21:
                  if ((0xffff7bffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(22, 20);
                  break;
               case 22:
                  if ((0xfffffbffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(22, 20);
                  break;
               case 23:
                  if (curChar == 47 && kind > 8)
                     kind = 8;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
               case 4:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 44)
                     kind = 44;
                  jjCheckNAdd(4);
                  break;
               case 7:
                  jjAddStates(3, 5);
                  break;
               case 12:
                  jjCheckNAddTwoStates(12, 13);
                  break;
               case 14:
               case 15:
                  jjCheckNAddTwoStates(15, 13);
                  break;
               case 19:
                  jjCheckNAddTwoStates(19, 20);
                  break;
               case 21:
               case 22:
                  jjCheckNAddTwoStates(22, 20);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int hiByte = (int)(curChar >> 8);
         int i1 = hiByte >> 6;
         long l1 = 1L << (hiByte & 077);
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
               case 4:
                  if (!jjCanMove_0(hiByte, i1, i2, l1, l2))
                     break;
                  if (kind > 44)
                     kind = 44;
                  jjCheckNAdd(4);
                  break;
               case 7:
                  if (jjCanMove_1(hiByte, i1, i2, l1, l2))
                     jjAddStates(3, 5);
                  break;
               case 12:
                  if (jjCanMove_1(hiByte, i1, i2, l1, l2))
                     jjCheckNAddTwoStates(12, 13);
                  break;
               case 14:
               case 15:
                  if (jjCanMove_1(hiByte, i1, i2, l1, l2))
                     jjCheckNAddTwoStates(15, 13);
                  break;
               case 19:
                  if (jjCanMove_1(hiByte, i1, i2, l1, l2))
                     jjCheckNAddTwoStates(19, 20);
                  break;
               case 21:
               case 22:
                  if (jjCanMove_1(hiByte, i1, i2, l1, l2))
                     jjCheckNAddTwoStates(22, 20);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 24 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
static final int[] jjnextStates = {
   6, 17, 18, 7, 8, 10, 13, 14, 16, 20, 21, 23, 
};
private static final boolean jjCanMove_0(int hiByte, int i1, int i2, long l1, long l2)
{
   switch(hiByte)
   {
      case 0:
         return ((jjbitVec2[i2] & l2) != 0L);
      case 48:
         return ((jjbitVec3[i2] & l2) != 0L);
      case 49:
         return ((jjbitVec4[i2] & l2) != 0L);
      case 51:
         return ((jjbitVec5[i2] & l2) != 0L);
      case 61:
         return ((jjbitVec6[i2] & l2) != 0L);
      default :
         if ((jjbitVec0[i1] & l1) != 0L)
            return true;
         return false;
   }
}
private static final boolean jjCanMove_1(int hiByte, int i1, int i2, long l1, long l2)
{
   switch(hiByte)
   {
      case 0:
         return ((jjbitVec8[i2] & l2) != 0L);
      default :
         if ((jjbitVec7[i1] & l1) != 0L)
            return true;
         return false;
   }
}

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, null, null, null, "\50", "\51", "\133", 
"\135", "\173", "\175", "\73", "\56", "\75", "\74", "\53", "\55", "\46\46", "\41", 
"\142\157\157\154\145\141\156", "\143\154\141\163\163", "\151\156\164\145\162\146\141\143\145", 
"\145\154\163\145", "\145\170\164\145\156\144\163", "\146\141\154\163\145", "\151\146", 
"\167\150\151\154\145", "\151\156\164", "\154\145\156\147\164\150", "\155\141\151\156", 
"\156\145\167", "\160\165\142\154\151\143", "\162\145\164\165\162\156", 
"\163\164\141\164\151\143", "\123\164\162\151\156\147", "\164\150\151\163", "\164\162\165\145", 
"\123\171\163\164\145\155\56\157\165\164\56\160\162\151\156\164\154\156", "\166\157\151\144", null, null, null, null, "\54", "\52", };

/** Lexer state names. */
public static final String[] lexStateNames = {
   "DEFAULT",
};
static final long[] jjtoToken = {
   0x19ffffffffe01L, 
};
static final long[] jjtoSkip = {
   0x1feL, 
};
static final long[] jjtoSpecial = {
   0x1c0L, 
};
static protected JavaCharStream input_stream;
static private final int[] jjrounds = new int[24];
static private final int[] jjstateSet = new int[48];
static protected char curChar;
/** Constructor. */
public MiniJavaParserTokenManager(JavaCharStream stream){
   if (input_stream != null)
      throw new TokenMgrError("ERROR: Second call to constructor of static lexer. You must use ReInit() to initialize the static variables.", TokenMgrError.STATIC_LEXER_ERROR);
   input_stream = stream;
}

/** Constructor. */
public MiniJavaParserTokenManager(JavaCharStream stream, int lexState){
   this(stream);
   SwitchTo(lexState);
}

/** Reinitialise parser. */
static public void ReInit(JavaCharStream stream)
{
   jjmatchedPos = jjnewStateCnt = 0;
   curLexState = defaultLexState;
   input_stream = stream;
   ReInitRounds();
}
static private void ReInitRounds()
{
   int i;
   jjround = 0x80000001;
   for (i = 24; i-- > 0;)
      jjrounds[i] = 0x80000000;
}

/** Reinitialise parser. */
static public void ReInit(JavaCharStream stream, int lexState)
{
   ReInit(stream);
   SwitchTo(lexState);
}

/** Switch to specified lex state. */
static public void SwitchTo(int lexState)
{
   if (lexState >= 1 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
   else
      curLexState = lexState;
}

static protected Token jjFillToken()
{
   final Token t;
   final String curTokenImage;
   final int beginLine;
   final int endLine;
   final int beginColumn;
   final int endColumn;
   String im = jjstrLiteralImages[jjmatchedKind];
   curTokenImage = (im == null) ? input_stream.GetImage() : im;
   beginLine = input_stream.getBeginLine();
   beginColumn = input_stream.getBeginColumn();
   endLine = input_stream.getEndLine();
   endColumn = input_stream.getEndColumn();
   t = Token.newToken(jjmatchedKind, curTokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn = endColumn;

   return t;
}

static int curLexState = 0;
static int defaultLexState = 0;
static int jjnewStateCnt;
static int jjround;
static int jjmatchedPos;
static int jjmatchedKind;

/** Get the next Token. */
public static Token getNextToken() 
{
  Token specialToken = null;
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {
   try
   {
      curChar = input_stream.BeginToken();
   }
   catch(java.io.IOException e)
   {
      jjmatchedKind = 0;
      matchedToken = jjFillToken();
      matchedToken.specialToken = specialToken;
      return matchedToken;
   }

   try { input_stream.backup(0);
      while (curChar <= 32 && (0x100003600L & (1L << curChar)) != 0L)
         curChar = input_stream.BeginToken();
   }
   catch (java.io.IOException e1) { continue EOFLoop; }
   jjmatchedKind = 0x7fffffff;
   jjmatchedPos = 0;
   curPos = jjMoveStringLiteralDfa0_0();
   if (jjmatchedKind != 0x7fffffff)
   {
      if (jjmatchedPos + 1 < curPos)
         input_stream.backup(curPos - jjmatchedPos - 1);
      if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
      {
         matchedToken = jjFillToken();
         matchedToken.specialToken = specialToken;
         return matchedToken;
      }
      else
      {
         if ((jjtoSpecial[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
         {
            matchedToken = jjFillToken();
            if (specialToken == null)
               specialToken = matchedToken;
            else
            {
               matchedToken.specialToken = specialToken;
               specialToken = (specialToken.next = matchedToken);
            }
         }
         continue EOFLoop;
      }
   }
   int error_line = input_stream.getEndLine();
   int error_column = input_stream.getEndColumn();
   String error_after = null;
   boolean EOFSeen = false;
   try { input_stream.readChar(); input_stream.backup(1); }
   catch (java.io.IOException e1) {
      EOFSeen = true;
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
      if (curChar == '\n' || curChar == '\r') {
         error_line++;
         error_column = 0;
      }
      else
         error_column++;
   }
   if (!EOFSeen) {
      input_stream.backup(1);
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
   }
   throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

static private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
static private void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
static private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

static private void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}

}