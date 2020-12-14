package org.apache.log4j.spi;

public interface ErrorCode {
   int GENERIC_FAILURE = 0;
   int WRITE_FAILURE = 1;
   int FLUSH_FAILURE = 2;
   int CLOSE_FAILURE = 3;
   int FILE_OPEN_FAILURE = 4;
   int MISSING_LAYOUT = 5;
   int ADDRESS_PARSE_FAILURE = 6;
}
