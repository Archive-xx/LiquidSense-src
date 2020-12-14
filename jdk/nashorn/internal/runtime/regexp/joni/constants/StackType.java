package jdk.nashorn.internal.runtime.regexp.joni.constants;

public interface StackType {
   int INVALID_STACK_INDEX = -1;
   int ALT = 1;
   int LOOK_BEHIND_NOT = 2;
   int POS_NOT = 3;
   int MEM_START = 256;
   int MEM_END = 33280;
   int REPEAT_INC = 768;
   int STATE_CHECK_MARK = 4096;
   int NULL_CHECK_START = 12288;
   int NULL_CHECK_END = 20480;
   int MEM_END_MARK = 33792;
   int POS = 1280;
   int STOP_BT = 1536;
   int REPEAT = 1792;
   int CALL_FRAME = 2048;
   int RETURN = 2304;
   int VOID = 2560;
   int MASK_POP_USED = 255;
   int MASK_TO_VOID_TARGET = 4351;
   int MASK_MEM_END_OR_MARK = 32768;
}
