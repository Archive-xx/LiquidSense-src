package jdk.nashorn.internal.runtime.regexp.joni;

import java.io.PrintStream;

public interface Config {
   int CHAR_TABLE_SIZE = 256;
   boolean VANILLA = false;
   int INTERNAL_ENC_CASE_FOLD_MULTI_CHAR = 1073741824;
   int ENC_CASE_FOLD_MIN = 1073741824;
   int ENC_CASE_FOLD_DEFAULT = 1073741824;
   boolean USE_MONOMANIAC_CHECK_CAPTURES_IN_ENDLESS_REPEAT = true;
   boolean USE_NEWLINE_AT_END_OF_STRING_HAS_EMPTY_LINE = true;
   boolean USE_WARNING_REDUNDANT_NESTED_REPEAT_OPERATOR = false;
   boolean CASE_FOLD_IS_APPLIED_INSIDE_NEGATIVE_CCLASS = true;
   boolean USE_MATCH_RANGE_MUST_BE_INSIDE_OF_SPECIFIED_RANGE = false;
   boolean USE_VARIABLE_META_CHARS = true;
   boolean USE_WORD_BEGIN_END = true;
   boolean USE_POSIX_API_REGION_OPTION = false;
   boolean USE_FIND_LONGEST_SEARCH_ALL_OF_RANGE = true;
   int NREGION = 10;
   int MAX_BACKREF_NUM = 1000;
   int MAX_REPEAT_NUM = 100000;
   int MAX_MULTI_BYTE_RANGES_NUM = 10000;
   boolean USE_WARN = true;
   boolean USE_PARSE_TREE_NODE_RECYCLE = true;
   boolean USE_OP_PUSH_OR_JUMP_EXACT = true;
   boolean USE_SHARED_CCLASS_TABLE = false;
   boolean USE_QTFR_PEEK_NEXT = true;
   int INIT_MATCH_STACK_SIZE = 64;
   int DEFAULT_MATCH_STACK_LIMIT_SIZE = 0;
   int NUMBER_OF_POOLED_STACKS = 4;
   boolean DONT_OPTIMIZE = false;
   boolean USE_STRING_TEMPLATES = true;
   boolean NON_UNICODE_SDW = true;
   PrintStream log = System.out;
   PrintStream err = System.err;
   boolean DEBUG_ALL = false;
   boolean DEBUG = false;
   boolean DEBUG_PARSE_TREE = false;
   boolean DEBUG_PARSE_TREE_RAW = true;
   boolean DEBUG_COMPILE = false;
   boolean DEBUG_COMPILE_BYTE_CODE_INFO = false;
   boolean DEBUG_SEARCH = false;
   boolean DEBUG_MATCH = false;
}
