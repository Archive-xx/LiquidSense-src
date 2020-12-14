package jdk.nashorn.internal.objects.annotations;

public interface Attribute {
   int NOT_WRITABLE = 1;
   int NOT_ENUMERABLE = 2;
   int NOT_CONFIGURABLE = 4;
   int CONSTANT = 5;
   int NON_ENUMERABLE_CONSTANT = 7;
   int DEFAULT_ATTRIBUTES = 0;
}
