package jdk.nashorn.internal.runtime.regexp.joni;

final class OptEnvironment {
   final MinMaxLen mmd = new MinMaxLen();
   int options;
   int caseFoldFlag;
   ScanEnvironment scanEnv;

   void copy(OptEnvironment other) {
      this.mmd.copy(other.mmd);
      this.options = other.options;
      this.caseFoldFlag = other.caseFoldFlag;
      this.scanEnv = other.scanEnv;
   }
}
