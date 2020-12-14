package kotlin.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CharIterator;
import kotlin.collections.CollectionsKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000|\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\r\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\f\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0019\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0010\u0011\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u001b\u001a\u001c\u0010\t\u001a\u00020\n*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u001c\u0010\u000e\u001a\u00020\n*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u001f\u0010\u000f\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\f\u001a\u00020\rH\u0086\u0002\u001a\u001f\u0010\u000f\u001a\u00020\r*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\rH\u0086\u0002\u001a\u0015\u0010\u000f\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0013H\u0087\n\u001a\u001c\u0010\u0014\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u001c\u0010\u0014\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\r\u001a:\u0010\u0016\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\n\u0018\u00010\u0017*\u00020\u00022\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001aE\u0010\u0016\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\n\u0018\u00010\u0017*\u00020\u00022\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u00192\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\rH\u0002¢\u0006\u0002\b\u001c\u001a:\u0010\u001d\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\n\u0018\u00010\u0017*\u00020\u00022\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u0012\u0010\u001e\u001a\u00020\r*\u00020\u00022\u0006\u0010\u001f\u001a\u00020\u0006\u001a4\u0010 \u001a\u0002H!\"\f\b\u0000\u0010\"*\u00020\u0002*\u0002H!\"\u0004\b\u0001\u0010!*\u0002H\"2\f\u0010#\u001a\b\u0012\u0004\u0012\u0002H!0$H\u0087\b¢\u0006\u0002\u0010%\u001a4\u0010&\u001a\u0002H!\"\f\b\u0000\u0010\"*\u00020\u0002*\u0002H!\"\u0004\b\u0001\u0010!*\u0002H\"2\f\u0010#\u001a\b\u0012\u0004\u0012\u0002H!0$H\u0087\b¢\u0006\u0002\u0010%\u001a&\u0010'\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a;\u0010'\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010(\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u001b\u001a\u00020\rH\u0002¢\u0006\u0002\b)\u001a&\u0010'\u001a\u00020\u0006*\u00020\u00022\u0006\u0010*\u001a\u00020\n2\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a&\u0010+\u001a\u00020\u0006*\u00020\u00022\u0006\u0010,\u001a\u00020-2\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a,\u0010+\u001a\u00020\u0006*\u00020\u00022\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a\r\u0010.\u001a\u00020\r*\u00020\u0002H\u0087\b\u001a\r\u0010/\u001a\u00020\r*\u00020\u0002H\u0087\b\u001a\r\u00100\u001a\u00020\r*\u00020\u0002H\u0087\b\u001a \u00101\u001a\u00020\r*\u0004\u0018\u00010\u0002H\u0087\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a \u00102\u001a\u00020\r*\u0004\u0018\u00010\u0002H\u0087\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a\r\u00103\u001a\u000204*\u00020\u0002H\u0086\u0002\u001a&\u00105\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a&\u00105\u001a\u00020\u0006*\u00020\u00022\u0006\u0010*\u001a\u00020\n2\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a&\u00106\u001a\u00020\u0006*\u00020\u00022\u0006\u0010,\u001a\u00020-2\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a,\u00106\u001a\u00020\u0006*\u00020\u00022\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u0010\u00107\u001a\b\u0012\u0004\u0012\u00020\n08*\u00020\u0002\u001a\u0010\u00109\u001a\b\u0012\u0004\u0012\u00020\n0:*\u00020\u0002\u001a\u0015\u0010;\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0013H\u0087\f\u001a\u000f\u0010<\u001a\u00020\n*\u0004\u0018\u00010\nH\u0087\b\u001a\u001c\u0010=\u001a\u00020\u0002*\u00020\u00022\u0006\u0010>\u001a\u00020\u00062\b\b\u0002\u0010?\u001a\u00020\u0011\u001a\u001c\u0010=\u001a\u00020\n*\u00020\n2\u0006\u0010>\u001a\u00020\u00062\b\b\u0002\u0010?\u001a\u00020\u0011\u001a\u001c\u0010@\u001a\u00020\u0002*\u00020\u00022\u0006\u0010>\u001a\u00020\u00062\b\b\u0002\u0010?\u001a\u00020\u0011\u001a\u001c\u0010@\u001a\u00020\n*\u00020\n2\u0006\u0010>\u001a\u00020\u00062\b\b\u0002\u0010?\u001a\u00020\u0011\u001aG\u0010A\u001a\b\u0012\u0004\u0012\u00020\u000108*\u00020\u00022\u000e\u0010B\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0C2\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010D\u001a\u00020\u0006H\u0002¢\u0006\u0004\bE\u0010F\u001a=\u0010A\u001a\b\u0012\u0004\u0012\u00020\u000108*\u00020\u00022\u0006\u0010B\u001a\u00020-2\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010D\u001a\u00020\u0006H\u0002¢\u0006\u0002\bE\u001a4\u0010G\u001a\u00020\r*\u00020\u00022\u0006\u0010H\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010I\u001a\u00020\u00062\u0006\u0010>\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rH\u0000\u001a\u0012\u0010J\u001a\u00020\u0002*\u00020\u00022\u0006\u0010K\u001a\u00020\u0002\u001a\u0012\u0010J\u001a\u00020\n*\u00020\n2\u0006\u0010K\u001a\u00020\u0002\u001a\u001a\u0010L\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010(\u001a\u00020\u0006\u001a\u0012\u0010L\u001a\u00020\u0002*\u00020\u00022\u0006\u0010M\u001a\u00020\u0001\u001a\u001d\u0010L\u001a\u00020\n*\u00020\n2\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010(\u001a\u00020\u0006H\u0087\b\u001a\u0015\u0010L\u001a\u00020\n*\u00020\n2\u0006\u0010M\u001a\u00020\u0001H\u0087\b\u001a\u0012\u0010N\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0002\u001a\u0012\u0010N\u001a\u00020\n*\u00020\n2\u0006\u0010\u0015\u001a\u00020\u0002\u001a\u0012\u0010O\u001a\u00020\u0002*\u00020\u00022\u0006\u0010P\u001a\u00020\u0002\u001a\u001a\u0010O\u001a\u00020\u0002*\u00020\u00022\u0006\u0010K\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0002\u001a\u0012\u0010O\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u0002\u001a\u001a\u0010O\u001a\u00020\n*\u00020\n2\u0006\u0010K\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0002\u001a+\u0010Q\u001a\u00020\n*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0014\b\b\u0010R\u001a\u000e\u0012\u0004\u0012\u00020T\u0012\u0004\u0012\u00020\u00020SH\u0087\b\u001a\u001d\u0010Q\u001a\u00020\n*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010U\u001a\u00020\nH\u0087\b\u001a$\u0010V\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a$\u0010V\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a$\u0010X\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a$\u0010X\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a$\u0010Y\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a$\u0010Y\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a$\u0010Z\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a$\u0010Z\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001d\u0010[\u001a\u00020\n*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010U\u001a\u00020\nH\u0087\b\u001a\"\u0010\\\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010(\u001a\u00020\u00062\u0006\u0010U\u001a\u00020\u0002\u001a\u001a\u0010\\\u001a\u00020\u0002*\u00020\u00022\u0006\u0010M\u001a\u00020\u00012\u0006\u0010U\u001a\u00020\u0002\u001a%\u0010\\\u001a\u00020\n*\u00020\n2\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010(\u001a\u00020\u00062\u0006\u0010U\u001a\u00020\u0002H\u0087\b\u001a\u001d\u0010\\\u001a\u00020\n*\u00020\n2\u0006\u0010M\u001a\u00020\u00012\u0006\u0010U\u001a\u00020\u0002H\u0087\b\u001a=\u0010]\u001a\b\u0012\u0004\u0012\u00020\n0:*\u00020\u00022\u0012\u0010B\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0C\"\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010D\u001a\u00020\u0006¢\u0006\u0002\u0010^\u001a0\u0010]\u001a\b\u0012\u0004\u0012\u00020\n0:*\u00020\u00022\n\u0010B\u001a\u00020-\"\u00020\u00112\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010D\u001a\u00020\u0006\u001a/\u0010]\u001a\b\u0012\u0004\u0012\u00020\n0:*\u00020\u00022\u0006\u0010P\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010D\u001a\u00020\u0006H\u0002¢\u0006\u0002\b_\u001a%\u0010]\u001a\b\u0012\u0004\u0012\u00020\n0:*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010D\u001a\u00020\u0006H\u0087\b\u001a=\u0010`\u001a\b\u0012\u0004\u0012\u00020\n08*\u00020\u00022\u0012\u0010B\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0C\"\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010D\u001a\u00020\u0006¢\u0006\u0002\u0010a\u001a0\u0010`\u001a\b\u0012\u0004\u0012\u00020\n08*\u00020\u00022\n\u0010B\u001a\u00020-\"\u00020\u00112\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010D\u001a\u00020\u0006\u001a\u001c\u0010b\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u001c\u0010b\u001a\u00020\r*\u00020\u00022\u0006\u0010K\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\r\u001a$\u0010b\u001a\u00020\r*\u00020\u00022\u0006\u0010K\u001a\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u0012\u0010c\u001a\u00020\u0002*\u00020\u00022\u0006\u0010M\u001a\u00020\u0001\u001a\u001d\u0010c\u001a\u00020\u0002*\u00020\n2\u0006\u0010d\u001a\u00020\u00062\u0006\u0010e\u001a\u00020\u0006H\u0087\b\u001a\u001f\u0010f\u001a\u00020\n*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010(\u001a\u00020\u0006H\u0087\b\u001a\u0012\u0010f\u001a\u00020\n*\u00020\u00022\u0006\u0010M\u001a\u00020\u0001\u001a\u0012\u0010f\u001a\u00020\n*\u00020\n2\u0006\u0010M\u001a\u00020\u0001\u001a\u001c\u0010g\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001c\u0010g\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001c\u0010h\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001c\u0010h\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001c\u0010i\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001c\u0010i\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001c\u0010j\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001c\u0010j\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a\n\u0010k\u001a\u00020\u0002*\u00020\u0002\u001a!\u0010k\u001a\u00020\u0002*\u00020\u00022\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0SH\u0086\b\u001a\u0016\u0010k\u001a\u00020\u0002*\u00020\u00022\n\u0010,\u001a\u00020-\"\u00020\u0011\u001a\r\u0010k\u001a\u00020\n*\u00020\nH\u0087\b\u001a!\u0010k\u001a\u00020\n*\u00020\n2\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0SH\u0086\b\u001a\u0016\u0010k\u001a\u00020\n*\u00020\n2\n\u0010,\u001a\u00020-\"\u00020\u0011\u001a\n\u0010m\u001a\u00020\u0002*\u00020\u0002\u001a!\u0010m\u001a\u00020\u0002*\u00020\u00022\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0SH\u0086\b\u001a\u0016\u0010m\u001a\u00020\u0002*\u00020\u00022\n\u0010,\u001a\u00020-\"\u00020\u0011\u001a\r\u0010m\u001a\u00020\n*\u00020\nH\u0087\b\u001a!\u0010m\u001a\u00020\n*\u00020\n2\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0SH\u0086\b\u001a\u0016\u0010m\u001a\u00020\n*\u00020\n2\n\u0010,\u001a\u00020-\"\u00020\u0011\u001a\n\u0010n\u001a\u00020\u0002*\u00020\u0002\u001a!\u0010n\u001a\u00020\u0002*\u00020\u00022\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0SH\u0086\b\u001a\u0016\u0010n\u001a\u00020\u0002*\u00020\u00022\n\u0010,\u001a\u00020-\"\u00020\u0011\u001a\r\u0010n\u001a\u00020\n*\u00020\nH\u0087\b\u001a!\u0010n\u001a\u00020\n*\u00020\n2\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0SH\u0086\b\u001a\u0016\u0010n\u001a\u00020\n*\u00020\n2\n\u0010,\u001a\u00020-\"\u00020\u0011\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006o"},
   d2 = {"indices", "Lkotlin/ranges/IntRange;", "", "getIndices", "(Ljava/lang/CharSequence;)Lkotlin/ranges/IntRange;", "lastIndex", "", "getLastIndex", "(Ljava/lang/CharSequence;)I", "commonPrefixWith", "", "other", "ignoreCase", "", "commonSuffixWith", "contains", "char", "", "regex", "Lkotlin/text/Regex;", "endsWith", "suffix", "findAnyOf", "Lkotlin/Pair;", "strings", "", "startIndex", "last", "findAnyOf$StringsKt__StringsKt", "findLastAnyOf", "hasSurrogatePairAt", "index", "ifBlank", "R", "C", "defaultValue", "Lkotlin/Function0;", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "ifEmpty", "indexOf", "endIndex", "indexOf$StringsKt__StringsKt", "string", "indexOfAny", "chars", "", "isEmpty", "isNotBlank", "isNotEmpty", "isNullOrBlank", "isNullOrEmpty", "iterator", "Lkotlin/collections/CharIterator;", "lastIndexOf", "lastIndexOfAny", "lineSequence", "Lkotlin/sequences/Sequence;", "lines", "", "matches", "orEmpty", "padEnd", "length", "padChar", "padStart", "rangesDelimitedBy", "delimiters", "", "limit", "rangesDelimitedBy$StringsKt__StringsKt", "(Ljava/lang/CharSequence;[Ljava/lang/String;IZI)Lkotlin/sequences/Sequence;", "regionMatchesImpl", "thisOffset", "otherOffset", "removePrefix", "prefix", "removeRange", "range", "removeSuffix", "removeSurrounding", "delimiter", "replace", "transform", "Lkotlin/Function1;", "Lkotlin/text/MatchResult;", "replacement", "replaceAfter", "missingDelimiterValue", "replaceAfterLast", "replaceBefore", "replaceBeforeLast", "replaceFirst", "replaceRange", "split", "(Ljava/lang/CharSequence;[Ljava/lang/String;ZI)Ljava/util/List;", "split$StringsKt__StringsKt", "splitToSequence", "(Ljava/lang/CharSequence;[Ljava/lang/String;ZI)Lkotlin/sequences/Sequence;", "startsWith", "subSequence", "start", "end", "substring", "substringAfter", "substringAfterLast", "substringBefore", "substringBeforeLast", "trim", "predicate", "trimEnd", "trimStart", "kotlin-stdlib"},
   xs = "kotlin/text/StringsKt"
)
class StringsKt__StringsKt extends StringsKt__StringsJVMKt {
   @NotNull
   public static final CharSequence trim(@NotNull CharSequence $this$trim, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$trim = 0;
      Intrinsics.checkParameterIsNotNull($this$trim, "$this$trim");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      int startIndex = 0;
      int endIndex = $this$trim.length() - 1;
      boolean startFound = false;

      while(startIndex <= endIndex) {
         int index = !startFound ? startIndex : endIndex;
         boolean match = (Boolean)predicate.invoke($this$trim.charAt(index));
         if (!startFound) {
            if (!match) {
               startFound = true;
            } else {
               ++startIndex;
            }
         } else {
            if (!match) {
               break;
            }

            --endIndex;
         }
      }

      return $this$trim.subSequence(startIndex, endIndex + 1);
   }

   @NotNull
   public static final String trim(@NotNull String $this$trim, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$trim = 0;
      Intrinsics.checkParameterIsNotNull($this$trim, "$this$trim");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      CharSequence $this$trim$iv = (CharSequence)$this$trim;
      int $i$f$trim = false;
      int startIndex$iv = 0;
      int endIndex$iv = $this$trim$iv.length() - 1;
      boolean startFound$iv = false;

      while(startIndex$iv <= endIndex$iv) {
         int index$iv = !startFound$iv ? startIndex$iv : endIndex$iv;
         boolean match$iv = (Boolean)predicate.invoke($this$trim$iv.charAt(index$iv));
         if (!startFound$iv) {
            if (!match$iv) {
               startFound$iv = true;
            } else {
               ++startIndex$iv;
            }
         } else {
            if (!match$iv) {
               break;
            }

            --endIndex$iv;
         }
      }

      return $this$trim$iv.subSequence(startIndex$iv, endIndex$iv + 1).toString();
   }

   @NotNull
   public static final CharSequence trimStart(@NotNull CharSequence $this$trimStart, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$trimStart = 0;
      Intrinsics.checkParameterIsNotNull($this$trimStart, "$this$trimStart");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      int index = 0;

      for(int var4 = $this$trimStart.length(); index < var4; ++index) {
         if (!(Boolean)predicate.invoke($this$trimStart.charAt(index))) {
            return $this$trimStart.subSequence(index, $this$trimStart.length());
         }
      }

      return (CharSequence)"";
   }

   @NotNull
   public static final String trimStart(@NotNull String $this$trimStart, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$trimStart = 0;
      Intrinsics.checkParameterIsNotNull($this$trimStart, "$this$trimStart");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      CharSequence $this$trimStart$iv = (CharSequence)$this$trimStart;
      int $i$f$trimStart = false;
      int index$iv = 0;
      int var6 = $this$trimStart$iv.length();

      CharSequence var10000;
      while(true) {
         if (index$iv >= var6) {
            var10000 = (CharSequence)"";
            break;
         }

         if (!(Boolean)predicate.invoke($this$trimStart$iv.charAt(index$iv))) {
            var10000 = $this$trimStart$iv.subSequence(index$iv, $this$trimStart$iv.length());
            break;
         }

         ++index$iv;
      }

      return var10000.toString();
   }

   @NotNull
   public static final CharSequence trimEnd(@NotNull CharSequence $this$trimEnd, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$trimEnd = 0;
      Intrinsics.checkParameterIsNotNull($this$trimEnd, "$this$trimEnd");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      int index = $this$trimEnd.length();
      --index;

      for(boolean var4 = false; index >= 0; --index) {
         if (!(Boolean)predicate.invoke($this$trimEnd.charAt(index))) {
            return $this$trimEnd.subSequence(0, index + 1);
         }
      }

      return (CharSequence)"";
   }

   @NotNull
   public static final String trimEnd(@NotNull String $this$trimEnd, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$trimEnd = 0;
      Intrinsics.checkParameterIsNotNull($this$trimEnd, "$this$trimEnd");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      CharSequence $this$trimEnd$iv = (CharSequence)$this$trimEnd;
      int $i$f$trimEnd = false;
      int index$iv = $this$trimEnd$iv.length();
      --index$iv;
      boolean var6 = false;

      CharSequence var10000;
      while(true) {
         if (index$iv < 0) {
            var10000 = (CharSequence)"";
            break;
         }

         if (!(Boolean)predicate.invoke($this$trimEnd$iv.charAt(index$iv))) {
            var10000 = $this$trimEnd$iv.subSequence(0, index$iv + 1);
            break;
         }

         --index$iv;
      }

      return var10000.toString();
   }

   @NotNull
   public static final CharSequence trim(@NotNull CharSequence $this$trim, @NotNull char... chars) {
      Intrinsics.checkParameterIsNotNull($this$trim, "$this$trim");
      Intrinsics.checkParameterIsNotNull(chars, "chars");
      CharSequence $this$trim$iv = $this$trim;
      int $i$f$trim = false;
      int startIndex$iv = 0;
      int endIndex$iv = $this$trim.length() - 1;
      boolean startFound$iv = false;

      while(startIndex$iv <= endIndex$iv) {
         int index$iv = !startFound$iv ? startIndex$iv : endIndex$iv;
         char it = $this$trim$iv.charAt(index$iv);
         int var9 = false;
         boolean match$iv = ArraysKt.contains(chars, it);
         if (!startFound$iv) {
            if (!match$iv) {
               startFound$iv = true;
            } else {
               ++startIndex$iv;
            }
         } else {
            if (!match$iv) {
               break;
            }

            --endIndex$iv;
         }
      }

      return $this$trim$iv.subSequence(startIndex$iv, endIndex$iv + 1);
   }

   @NotNull
   public static final String trim(@NotNull String $this$trim, @NotNull char... chars) {
      Intrinsics.checkParameterIsNotNull($this$trim, "$this$trim");
      Intrinsics.checkParameterIsNotNull(chars, "chars");
      int $i$f$trim = false;
      CharSequence $this$trim$iv$iv = (CharSequence)$this$trim;
      int $i$f$trim = false;
      int startIndex$iv$iv = 0;
      int endIndex$iv$iv = $this$trim$iv$iv.length() - 1;
      boolean startFound$iv$iv = false;

      while(startIndex$iv$iv <= endIndex$iv$iv) {
         int index$iv$iv = !startFound$iv$iv ? startIndex$iv$iv : endIndex$iv$iv;
         char it = $this$trim$iv$iv.charAt(index$iv$iv);
         int var11 = false;
         boolean match$iv$iv = ArraysKt.contains(chars, it);
         if (!startFound$iv$iv) {
            if (!match$iv$iv) {
               startFound$iv$iv = true;
            } else {
               ++startIndex$iv$iv;
            }
         } else {
            if (!match$iv$iv) {
               break;
            }

            --endIndex$iv$iv;
         }
      }

      return $this$trim$iv$iv.subSequence(startIndex$iv$iv, endIndex$iv$iv + 1).toString();
   }

   @NotNull
   public static final CharSequence trimStart(@NotNull CharSequence $this$trimStart, @NotNull char... chars) {
      Intrinsics.checkParameterIsNotNull($this$trimStart, "$this$trimStart");
      Intrinsics.checkParameterIsNotNull(chars, "chars");
      CharSequence $this$trimStart$iv = $this$trimStart;
      int $i$f$trimStart = false;
      int index$iv = 0;
      int var5 = $this$trimStart.length();

      CharSequence var10000;
      while(true) {
         if (index$iv >= var5) {
            var10000 = (CharSequence)"";
            break;
         }

         char it = $this$trimStart$iv.charAt(index$iv);
         int var7 = false;
         if (!ArraysKt.contains(chars, it)) {
            var10000 = $this$trimStart$iv.subSequence(index$iv, $this$trimStart$iv.length());
            break;
         }

         ++index$iv;
      }

      return var10000;
   }

   @NotNull
   public static final String trimStart(@NotNull String $this$trimStart, @NotNull char... chars) {
      Intrinsics.checkParameterIsNotNull($this$trimStart, "$this$trimStart");
      Intrinsics.checkParameterIsNotNull(chars, "chars");
      int $i$f$trimStart = false;
      CharSequence $this$trimStart$iv$iv = (CharSequence)$this$trimStart;
      int $i$f$trimStart = false;
      int index$iv$iv = 0;
      int var7 = $this$trimStart$iv$iv.length();

      CharSequence var10000;
      while(true) {
         if (index$iv$iv >= var7) {
            var10000 = (CharSequence)"";
            break;
         }

         char it = $this$trimStart$iv$iv.charAt(index$iv$iv);
         int var9 = false;
         if (!ArraysKt.contains(chars, it)) {
            var10000 = $this$trimStart$iv$iv.subSequence(index$iv$iv, $this$trimStart$iv$iv.length());
            break;
         }

         ++index$iv$iv;
      }

      return var10000.toString();
   }

   @NotNull
   public static final CharSequence trimEnd(@NotNull CharSequence $this$trimEnd, @NotNull char... chars) {
      Intrinsics.checkParameterIsNotNull($this$trimEnd, "$this$trimEnd");
      Intrinsics.checkParameterIsNotNull(chars, "chars");
      CharSequence $this$trimEnd$iv = $this$trimEnd;
      int $i$f$trimEnd = false;
      int index$iv = $this$trimEnd.length();
      --index$iv;
      boolean var5 = false;

      CharSequence var10000;
      while(true) {
         if (index$iv < 0) {
            var10000 = (CharSequence)"";
            break;
         }

         char it = $this$trimEnd$iv.charAt(index$iv);
         int var7 = false;
         if (!ArraysKt.contains(chars, it)) {
            var10000 = $this$trimEnd$iv.subSequence(0, index$iv + 1);
            break;
         }

         --index$iv;
      }

      return var10000;
   }

   @NotNull
   public static final String trimEnd(@NotNull String $this$trimEnd, @NotNull char... chars) {
      Intrinsics.checkParameterIsNotNull($this$trimEnd, "$this$trimEnd");
      Intrinsics.checkParameterIsNotNull(chars, "chars");
      int $i$f$trimEnd = false;
      CharSequence $this$trimEnd$iv$iv = (CharSequence)$this$trimEnd;
      int $i$f$trimEnd = false;
      int index$iv$iv = $this$trimEnd$iv$iv.length();
      --index$iv$iv;
      boolean var7 = false;

      CharSequence var10000;
      while(true) {
         if (index$iv$iv < 0) {
            var10000 = (CharSequence)"";
            break;
         }

         char it = $this$trimEnd$iv$iv.charAt(index$iv$iv);
         int var9 = false;
         if (!ArraysKt.contains(chars, it)) {
            var10000 = $this$trimEnd$iv$iv.subSequence(0, index$iv$iv + 1);
            break;
         }

         --index$iv$iv;
      }

      return var10000.toString();
   }

   @NotNull
   public static final CharSequence trim(@NotNull CharSequence $this$trim) {
      Intrinsics.checkParameterIsNotNull($this$trim, "$this$trim");
      CharSequence $this$trim$iv = $this$trim;
      int $i$f$trim = false;
      int startIndex$iv = 0;
      int endIndex$iv = $this$trim.length() - 1;
      boolean startFound$iv = false;

      while(startIndex$iv <= endIndex$iv) {
         int index$iv = !startFound$iv ? startIndex$iv : endIndex$iv;
         char p1 = $this$trim$iv.charAt(index$iv);
         int var8 = false;
         boolean match$iv = CharsKt.isWhitespace(p1);
         if (!startFound$iv) {
            if (!match$iv) {
               startFound$iv = true;
            } else {
               ++startIndex$iv;
            }
         } else {
            if (!match$iv) {
               break;
            }

            --endIndex$iv;
         }
      }

      return $this$trim$iv.subSequence(startIndex$iv, endIndex$iv + 1);
   }

   @InlineOnly
   private static final String trim(@NotNull String $this$trim) {
      return StringsKt.trim((CharSequence)$this$trim).toString();
   }

   @NotNull
   public static final CharSequence trimStart(@NotNull CharSequence $this$trimStart) {
      Intrinsics.checkParameterIsNotNull($this$trimStart, "$this$trimStart");
      CharSequence $this$trimStart$iv = $this$trimStart;
      int $i$f$trimStart = false;
      int index$iv = 0;
      int var4 = $this$trimStart.length();

      CharSequence var10000;
      while(true) {
         if (index$iv >= var4) {
            var10000 = (CharSequence)"";
            break;
         }

         char p1 = $this$trimStart$iv.charAt(index$iv);
         int var6 = false;
         if (!CharsKt.isWhitespace(p1)) {
            var10000 = $this$trimStart$iv.subSequence(index$iv, $this$trimStart$iv.length());
            break;
         }

         ++index$iv;
      }

      return var10000;
   }

   @InlineOnly
   private static final String trimStart(@NotNull String $this$trimStart) {
      return StringsKt.trimStart((CharSequence)$this$trimStart).toString();
   }

   @NotNull
   public static final CharSequence trimEnd(@NotNull CharSequence $this$trimEnd) {
      Intrinsics.checkParameterIsNotNull($this$trimEnd, "$this$trimEnd");
      CharSequence $this$trimEnd$iv = $this$trimEnd;
      int $i$f$trimEnd = false;
      int index$iv = $this$trimEnd.length();
      --index$iv;
      boolean var4 = false;

      CharSequence var10000;
      while(true) {
         if (index$iv < 0) {
            var10000 = (CharSequence)"";
            break;
         }

         char p1 = $this$trimEnd$iv.charAt(index$iv);
         int var6 = false;
         if (!CharsKt.isWhitespace(p1)) {
            var10000 = $this$trimEnd$iv.subSequence(0, index$iv + 1);
            break;
         }

         --index$iv;
      }

      return var10000;
   }

   @InlineOnly
   private static final String trimEnd(@NotNull String $this$trimEnd) {
      return StringsKt.trimEnd((CharSequence)$this$trimEnd).toString();
   }

   @NotNull
   public static final CharSequence padStart(@NotNull CharSequence $this$padStart, int length, char padChar) {
      Intrinsics.checkParameterIsNotNull($this$padStart, "$this$padStart");
      if (length < 0) {
         throw (Throwable)(new IllegalArgumentException("Desired length " + length + " is less than zero."));
      } else if (length <= $this$padStart.length()) {
         return $this$padStart.subSequence(0, $this$padStart.length());
      } else {
         StringBuilder sb = new StringBuilder(length);
         int i = 1;
         int var5 = length - $this$padStart.length();
         if (i <= var5) {
            while(true) {
               sb.append(padChar);
               if (i == var5) {
                  break;
               }

               ++i;
            }
         }

         sb.append($this$padStart);
         return (CharSequence)sb;
      }
   }

   // $FF: synthetic method
   public static CharSequence padStart$default(CharSequence var0, int var1, char var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = ' ';
      }

      return StringsKt.padStart(var0, var1, var2);
   }

   @NotNull
   public static final String padStart(@NotNull String $this$padStart, int length, char padChar) {
      Intrinsics.checkParameterIsNotNull($this$padStart, "$this$padStart");
      return StringsKt.padStart((CharSequence)$this$padStart, length, padChar).toString();
   }

   // $FF: synthetic method
   public static String padStart$default(String var0, int var1, char var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = ' ';
      }

      return StringsKt.padStart(var0, var1, var2);
   }

   @NotNull
   public static final CharSequence padEnd(@NotNull CharSequence $this$padEnd, int length, char padChar) {
      Intrinsics.checkParameterIsNotNull($this$padEnd, "$this$padEnd");
      if (length < 0) {
         throw (Throwable)(new IllegalArgumentException("Desired length " + length + " is less than zero."));
      } else if (length <= $this$padEnd.length()) {
         return $this$padEnd.subSequence(0, $this$padEnd.length());
      } else {
         StringBuilder sb = new StringBuilder(length);
         sb.append($this$padEnd);
         int i = 1;
         int var5 = length - $this$padEnd.length();
         if (i <= var5) {
            while(true) {
               sb.append(padChar);
               if (i == var5) {
                  break;
               }

               ++i;
            }
         }

         return (CharSequence)sb;
      }
   }

   // $FF: synthetic method
   public static CharSequence padEnd$default(CharSequence var0, int var1, char var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = ' ';
      }

      return StringsKt.padEnd(var0, var1, var2);
   }

   @NotNull
   public static final String padEnd(@NotNull String $this$padEnd, int length, char padChar) {
      Intrinsics.checkParameterIsNotNull($this$padEnd, "$this$padEnd");
      return StringsKt.padEnd((CharSequence)$this$padEnd, length, padChar).toString();
   }

   // $FF: synthetic method
   public static String padEnd$default(String var0, int var1, char var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = ' ';
      }

      return StringsKt.padEnd(var0, var1, var2);
   }

   @InlineOnly
   private static final boolean isNullOrEmpty(@Nullable CharSequence $this$isNullOrEmpty) {
      int $i$f$isNullOrEmpty = 0;
      boolean var2 = false;
      return $this$isNullOrEmpty == null || $this$isNullOrEmpty.length() == 0;
   }

   @InlineOnly
   private static final boolean isEmpty(@NotNull CharSequence $this$isEmpty) {
      int $i$f$isEmpty = 0;
      return $this$isEmpty.length() == 0;
   }

   @InlineOnly
   private static final boolean isNotEmpty(@NotNull CharSequence $this$isNotEmpty) {
      int $i$f$isNotEmpty = 0;
      return $this$isNotEmpty.length() > 0;
   }

   @InlineOnly
   private static final boolean isNotBlank(@NotNull CharSequence $this$isNotBlank) {
      int $i$f$isNotBlank = 0;
      return !StringsKt.isBlank($this$isNotBlank);
   }

   @InlineOnly
   private static final boolean isNullOrBlank(@Nullable CharSequence $this$isNullOrBlank) {
      int $i$f$isNullOrBlank = 0;
      boolean var2 = false;
      return $this$isNullOrBlank == null || StringsKt.isBlank($this$isNullOrBlank);
   }

   @NotNull
   public static final CharIterator iterator(@NotNull final CharSequence $this$iterator) {
      Intrinsics.checkParameterIsNotNull($this$iterator, "$this$iterator");
      return (CharIterator)(new CharIterator() {
         private int index;

         public char nextChar() {
            CharSequence var10000 = $this$iterator;
            int var1;
            this.index = (var1 = this.index) + 1;
            return var10000.charAt(var1);
         }

         public boolean hasNext() {
            return this.index < $this$iterator.length();
         }
      });
   }

   @InlineOnly
   private static final String orEmpty(@Nullable String $this$orEmpty) {
      int $i$f$orEmpty = 0;
      String var10000 = $this$orEmpty;
      if ($this$orEmpty == null) {
         var10000 = "";
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final <C extends CharSequence & R, R> R ifEmpty(C $this$ifEmpty, Function0<? extends R> defaultValue) {
      int $i$f$ifEmpty = 0;
      boolean var4 = false;
      return $this$ifEmpty.length() == 0 ? defaultValue.invoke() : $this$ifEmpty;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final <C extends CharSequence & R, R> R ifBlank(C $this$ifBlank, Function0<? extends R> defaultValue) {
      int $i$f$ifBlank = 0;
      return StringsKt.isBlank($this$ifBlank) ? defaultValue.invoke() : $this$ifBlank;
   }

   @NotNull
   public static final IntRange getIndices(@NotNull CharSequence $this$indices) {
      Intrinsics.checkParameterIsNotNull($this$indices, "$this$indices");
      byte var1 = 0;
      return new IntRange(var1, $this$indices.length() - 1);
   }

   public static final int getLastIndex(@NotNull CharSequence $this$lastIndex) {
      Intrinsics.checkParameterIsNotNull($this$lastIndex, "$this$lastIndex");
      return $this$lastIndex.length() - 1;
   }

   public static final boolean hasSurrogatePairAt(@NotNull CharSequence $this$hasSurrogatePairAt, int index) {
      Intrinsics.checkParameterIsNotNull($this$hasSurrogatePairAt, "$this$hasSurrogatePairAt");
      int var10000 = $this$hasSurrogatePairAt.length() - 2;
      boolean var4;
      if (0 <= index) {
         if (var10000 >= index) {
            char var2 = $this$hasSurrogatePairAt.charAt(index);
            boolean var3 = false;
            if (Character.isHighSurrogate(var2)) {
               var2 = $this$hasSurrogatePairAt.charAt(index + 1);
               var3 = false;
               if (Character.isLowSurrogate(var2)) {
                  var4 = true;
                  return var4;
               }
            }
         }
      }

      var4 = false;
      return var4;
   }

   @NotNull
   public static final String substring(@NotNull String $this$substring, @NotNull IntRange range) {
      Intrinsics.checkParameterIsNotNull($this$substring, "$this$substring");
      Intrinsics.checkParameterIsNotNull(range, "range");
      int var3 = range.getStart();
      int var4 = range.getEndInclusive() + 1;
      boolean var5 = false;
      String var10000 = $this$substring.substring(var3, var4);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      return var10000;
   }

   @NotNull
   public static final CharSequence subSequence(@NotNull CharSequence $this$subSequence, @NotNull IntRange range) {
      Intrinsics.checkParameterIsNotNull($this$subSequence, "$this$subSequence");
      Intrinsics.checkParameterIsNotNull(range, "range");
      return $this$subSequence.subSequence(range.getStart(), range.getEndInclusive() + 1);
   }

   /** @deprecated */
   @Deprecated(
      message = "Use parameters named startIndex and endIndex.",
      replaceWith = @ReplaceWith(
   imports = {},
   expression = "subSequence(startIndex = start, endIndex = end)"
)
   )
   @InlineOnly
   private static final CharSequence subSequence(@NotNull String $this$subSequence, int start, int end) {
      int $i$f$subSequence = 0;
      return $this$subSequence.subSequence(start, end);
   }

   @InlineOnly
   private static final String substring(@NotNull CharSequence $this$substring, int startIndex, int endIndex) {
      int $i$f$substring = 0;
      return $this$substring.subSequence(startIndex, endIndex).toString();
   }

   // $FF: synthetic method
   static String substring$default(CharSequence $this$substring, int startIndex, int endIndex, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         endIndex = $this$substring.length();
      }

      int $i$f$substring = false;
      return $this$substring.subSequence(startIndex, endIndex).toString();
   }

   @NotNull
   public static final String substring(@NotNull CharSequence $this$substring, @NotNull IntRange range) {
      Intrinsics.checkParameterIsNotNull($this$substring, "$this$substring");
      Intrinsics.checkParameterIsNotNull(range, "range");
      return $this$substring.subSequence(range.getStart(), range.getEndInclusive() + 1).toString();
   }

   @NotNull
   public static final String substringBefore(@NotNull String $this$substringBefore, char delimiter, @NotNull String missingDelimiterValue) {
      Intrinsics.checkParameterIsNotNull($this$substringBefore, "$this$substringBefore");
      Intrinsics.checkParameterIsNotNull(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.indexOf$default((CharSequence)$this$substringBefore, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         byte var5 = 0;
         boolean var6 = false;
         var10000 = $this$substringBefore.substring(var5, index);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String substringBefore$default(String var0, char var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringBefore(var0, var1, var2);
   }

   @NotNull
   public static final String substringBefore(@NotNull String $this$substringBefore, @NotNull String delimiter, @NotNull String missingDelimiterValue) {
      Intrinsics.checkParameterIsNotNull($this$substringBefore, "$this$substringBefore");
      Intrinsics.checkParameterIsNotNull(delimiter, "delimiter");
      Intrinsics.checkParameterIsNotNull(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.indexOf$default((CharSequence)$this$substringBefore, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         byte var5 = 0;
         boolean var6 = false;
         var10000 = $this$substringBefore.substring(var5, index);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String substringBefore$default(String var0, String var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringBefore(var0, var1, var2);
   }

   @NotNull
   public static final String substringAfter(@NotNull String $this$substringAfter, char delimiter, @NotNull String missingDelimiterValue) {
      Intrinsics.checkParameterIsNotNull($this$substringAfter, "$this$substringAfter");
      Intrinsics.checkParameterIsNotNull(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.indexOf$default((CharSequence)$this$substringAfter, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         int var5 = index + 1;
         int var6 = $this$substringAfter.length();
         boolean var7 = false;
         var10000 = $this$substringAfter.substring(var5, var6);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String substringAfter$default(String var0, char var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringAfter(var0, var1, var2);
   }

   @NotNull
   public static final String substringAfter(@NotNull String $this$substringAfter, @NotNull String delimiter, @NotNull String missingDelimiterValue) {
      Intrinsics.checkParameterIsNotNull($this$substringAfter, "$this$substringAfter");
      Intrinsics.checkParameterIsNotNull(delimiter, "delimiter");
      Intrinsics.checkParameterIsNotNull(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.indexOf$default((CharSequence)$this$substringAfter, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         int var5 = index + delimiter.length();
         int var6 = $this$substringAfter.length();
         boolean var7 = false;
         var10000 = $this$substringAfter.substring(var5, var6);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String substringAfter$default(String var0, String var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringAfter(var0, var1, var2);
   }

   @NotNull
   public static final String substringBeforeLast(@NotNull String $this$substringBeforeLast, char delimiter, @NotNull String missingDelimiterValue) {
      Intrinsics.checkParameterIsNotNull($this$substringBeforeLast, "$this$substringBeforeLast");
      Intrinsics.checkParameterIsNotNull(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.lastIndexOf$default((CharSequence)$this$substringBeforeLast, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         byte var5 = 0;
         boolean var6 = false;
         var10000 = $this$substringBeforeLast.substring(var5, index);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String substringBeforeLast$default(String var0, char var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringBeforeLast(var0, var1, var2);
   }

   @NotNull
   public static final String substringBeforeLast(@NotNull String $this$substringBeforeLast, @NotNull String delimiter, @NotNull String missingDelimiterValue) {
      Intrinsics.checkParameterIsNotNull($this$substringBeforeLast, "$this$substringBeforeLast");
      Intrinsics.checkParameterIsNotNull(delimiter, "delimiter");
      Intrinsics.checkParameterIsNotNull(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.lastIndexOf$default((CharSequence)$this$substringBeforeLast, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         byte var5 = 0;
         boolean var6 = false;
         var10000 = $this$substringBeforeLast.substring(var5, index);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String substringBeforeLast$default(String var0, String var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringBeforeLast(var0, var1, var2);
   }

   @NotNull
   public static final String substringAfterLast(@NotNull String $this$substringAfterLast, char delimiter, @NotNull String missingDelimiterValue) {
      Intrinsics.checkParameterIsNotNull($this$substringAfterLast, "$this$substringAfterLast");
      Intrinsics.checkParameterIsNotNull(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.lastIndexOf$default((CharSequence)$this$substringAfterLast, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         int var5 = index + 1;
         int var6 = $this$substringAfterLast.length();
         boolean var7 = false;
         var10000 = $this$substringAfterLast.substring(var5, var6);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String substringAfterLast$default(String var0, char var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringAfterLast(var0, var1, var2);
   }

   @NotNull
   public static final String substringAfterLast(@NotNull String $this$substringAfterLast, @NotNull String delimiter, @NotNull String missingDelimiterValue) {
      Intrinsics.checkParameterIsNotNull($this$substringAfterLast, "$this$substringAfterLast");
      Intrinsics.checkParameterIsNotNull(delimiter, "delimiter");
      Intrinsics.checkParameterIsNotNull(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.lastIndexOf$default((CharSequence)$this$substringAfterLast, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         int var5 = index + delimiter.length();
         int var6 = $this$substringAfterLast.length();
         boolean var7 = false;
         var10000 = $this$substringAfterLast.substring(var5, var6);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String substringAfterLast$default(String var0, String var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringAfterLast(var0, var1, var2);
   }

   @NotNull
   public static final CharSequence replaceRange(@NotNull CharSequence $this$replaceRange, int startIndex, int endIndex, @NotNull CharSequence replacement) {
      Intrinsics.checkParameterIsNotNull($this$replaceRange, "$this$replaceRange");
      Intrinsics.checkParameterIsNotNull(replacement, "replacement");
      if (endIndex < startIndex) {
         throw (Throwable)(new IndexOutOfBoundsException("End index (" + endIndex + ") is less than start index (" + startIndex + ")."));
      } else {
         StringBuilder sb = new StringBuilder();
         sb.append($this$replaceRange, 0, startIndex);
         sb.append(replacement);
         sb.append($this$replaceRange, endIndex, $this$replaceRange.length());
         return (CharSequence)sb;
      }
   }

   @InlineOnly
   private static final String replaceRange(@NotNull String $this$replaceRange, int startIndex, int endIndex, CharSequence replacement) {
      return StringsKt.replaceRange((CharSequence)$this$replaceRange, startIndex, endIndex, replacement).toString();
   }

   @NotNull
   public static final CharSequence replaceRange(@NotNull CharSequence $this$replaceRange, @NotNull IntRange range, @NotNull CharSequence replacement) {
      Intrinsics.checkParameterIsNotNull($this$replaceRange, "$this$replaceRange");
      Intrinsics.checkParameterIsNotNull(range, "range");
      Intrinsics.checkParameterIsNotNull(replacement, "replacement");
      return StringsKt.replaceRange($this$replaceRange, range.getStart(), range.getEndInclusive() + 1, replacement);
   }

   @InlineOnly
   private static final String replaceRange(@NotNull String $this$replaceRange, IntRange range, CharSequence replacement) {
      return StringsKt.replaceRange((CharSequence)$this$replaceRange, range, replacement).toString();
   }

   @NotNull
   public static final CharSequence removeRange(@NotNull CharSequence $this$removeRange, int startIndex, int endIndex) {
      Intrinsics.checkParameterIsNotNull($this$removeRange, "$this$removeRange");
      if (endIndex < startIndex) {
         throw (Throwable)(new IndexOutOfBoundsException("End index (" + endIndex + ") is less than start index (" + startIndex + ")."));
      } else if (endIndex == startIndex) {
         return $this$removeRange.subSequence(0, $this$removeRange.length());
      } else {
         StringBuilder sb = new StringBuilder($this$removeRange.length() - (endIndex - startIndex));
         sb.append($this$removeRange, 0, startIndex);
         sb.append($this$removeRange, endIndex, $this$removeRange.length());
         return (CharSequence)sb;
      }
   }

   @InlineOnly
   private static final String removeRange(@NotNull String $this$removeRange, int startIndex, int endIndex) {
      return StringsKt.removeRange((CharSequence)$this$removeRange, startIndex, endIndex).toString();
   }

   @NotNull
   public static final CharSequence removeRange(@NotNull CharSequence $this$removeRange, @NotNull IntRange range) {
      Intrinsics.checkParameterIsNotNull($this$removeRange, "$this$removeRange");
      Intrinsics.checkParameterIsNotNull(range, "range");
      return StringsKt.removeRange($this$removeRange, range.getStart(), range.getEndInclusive() + 1);
   }

   @InlineOnly
   private static final String removeRange(@NotNull String $this$removeRange, IntRange range) {
      return StringsKt.removeRange((CharSequence)$this$removeRange, range).toString();
   }

   @NotNull
   public static final CharSequence removePrefix(@NotNull CharSequence $this$removePrefix, @NotNull CharSequence prefix) {
      Intrinsics.checkParameterIsNotNull($this$removePrefix, "$this$removePrefix");
      Intrinsics.checkParameterIsNotNull(prefix, "prefix");
      return StringsKt.startsWith$default($this$removePrefix, prefix, false, 2, (Object)null) ? $this$removePrefix.subSequence(prefix.length(), $this$removePrefix.length()) : $this$removePrefix.subSequence(0, $this$removePrefix.length());
   }

   @NotNull
   public static final String removePrefix(@NotNull String $this$removePrefix, @NotNull CharSequence prefix) {
      Intrinsics.checkParameterIsNotNull($this$removePrefix, "$this$removePrefix");
      Intrinsics.checkParameterIsNotNull(prefix, "prefix");
      if (StringsKt.startsWith$default((CharSequence)$this$removePrefix, prefix, false, 2, (Object)null)) {
         int var3 = prefix.length();
         boolean var4 = false;
         String var10000 = $this$removePrefix.substring(var3);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).substring(startIndex)");
         return var10000;
      } else {
         return $this$removePrefix;
      }
   }

   @NotNull
   public static final CharSequence removeSuffix(@NotNull CharSequence $this$removeSuffix, @NotNull CharSequence suffix) {
      Intrinsics.checkParameterIsNotNull($this$removeSuffix, "$this$removeSuffix");
      Intrinsics.checkParameterIsNotNull(suffix, "suffix");
      return StringsKt.endsWith$default($this$removeSuffix, suffix, false, 2, (Object)null) ? $this$removeSuffix.subSequence(0, $this$removeSuffix.length() - suffix.length()) : $this$removeSuffix.subSequence(0, $this$removeSuffix.length());
   }

   @NotNull
   public static final String removeSuffix(@NotNull String $this$removeSuffix, @NotNull CharSequence suffix) {
      Intrinsics.checkParameterIsNotNull($this$removeSuffix, "$this$removeSuffix");
      Intrinsics.checkParameterIsNotNull(suffix, "suffix");
      if (StringsKt.endsWith$default((CharSequence)$this$removeSuffix, suffix, false, 2, (Object)null)) {
         byte var3 = 0;
         int var4 = $this$removeSuffix.length() - suffix.length();
         boolean var5 = false;
         String var10000 = $this$removeSuffix.substring(var3, var4);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
         return var10000;
      } else {
         return $this$removeSuffix;
      }
   }

   @NotNull
   public static final CharSequence removeSurrounding(@NotNull CharSequence $this$removeSurrounding, @NotNull CharSequence prefix, @NotNull CharSequence suffix) {
      Intrinsics.checkParameterIsNotNull($this$removeSurrounding, "$this$removeSurrounding");
      Intrinsics.checkParameterIsNotNull(prefix, "prefix");
      Intrinsics.checkParameterIsNotNull(suffix, "suffix");
      return $this$removeSurrounding.length() >= prefix.length() + suffix.length() && StringsKt.startsWith$default($this$removeSurrounding, prefix, false, 2, (Object)null) && StringsKt.endsWith$default($this$removeSurrounding, suffix, false, 2, (Object)null) ? $this$removeSurrounding.subSequence(prefix.length(), $this$removeSurrounding.length() - suffix.length()) : $this$removeSurrounding.subSequence(0, $this$removeSurrounding.length());
   }

   @NotNull
   public static final String removeSurrounding(@NotNull String $this$removeSurrounding, @NotNull CharSequence prefix, @NotNull CharSequence suffix) {
      Intrinsics.checkParameterIsNotNull($this$removeSurrounding, "$this$removeSurrounding");
      Intrinsics.checkParameterIsNotNull(prefix, "prefix");
      Intrinsics.checkParameterIsNotNull(suffix, "suffix");
      if ($this$removeSurrounding.length() >= prefix.length() + suffix.length() && StringsKt.startsWith$default((CharSequence)$this$removeSurrounding, prefix, false, 2, (Object)null) && StringsKt.endsWith$default((CharSequence)$this$removeSurrounding, suffix, false, 2, (Object)null)) {
         int var4 = prefix.length();
         int var5 = $this$removeSurrounding.length() - suffix.length();
         boolean var6 = false;
         String var10000 = $this$removeSurrounding.substring(var4, var5);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
         return var10000;
      } else {
         return $this$removeSurrounding;
      }
   }

   @NotNull
   public static final CharSequence removeSurrounding(@NotNull CharSequence $this$removeSurrounding, @NotNull CharSequence delimiter) {
      Intrinsics.checkParameterIsNotNull($this$removeSurrounding, "$this$removeSurrounding");
      Intrinsics.checkParameterIsNotNull(delimiter, "delimiter");
      return StringsKt.removeSurrounding($this$removeSurrounding, delimiter, delimiter);
   }

   @NotNull
   public static final String removeSurrounding(@NotNull String $this$removeSurrounding, @NotNull CharSequence delimiter) {
      Intrinsics.checkParameterIsNotNull($this$removeSurrounding, "$this$removeSurrounding");
      Intrinsics.checkParameterIsNotNull(delimiter, "delimiter");
      return StringsKt.removeSurrounding($this$removeSurrounding, delimiter, delimiter);
   }

   @NotNull
   public static final String replaceBefore(@NotNull String $this$replaceBefore, char delimiter, @NotNull String replacement, @NotNull String missingDelimiterValue) {
      Intrinsics.checkParameterIsNotNull($this$replaceBefore, "$this$replaceBefore");
      Intrinsics.checkParameterIsNotNull(replacement, "replacement");
      Intrinsics.checkParameterIsNotNull(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.indexOf$default((CharSequence)$this$replaceBefore, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         byte var6 = 0;
         boolean var7 = false;
         var10000 = StringsKt.replaceRange((CharSequence)$this$replaceBefore, var6, index, (CharSequence)replacement).toString();
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String replaceBefore$default(String var0, char var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceBefore(var0, var1, var2, var3);
   }

   @NotNull
   public static final String replaceBefore(@NotNull String $this$replaceBefore, @NotNull String delimiter, @NotNull String replacement, @NotNull String missingDelimiterValue) {
      Intrinsics.checkParameterIsNotNull($this$replaceBefore, "$this$replaceBefore");
      Intrinsics.checkParameterIsNotNull(delimiter, "delimiter");
      Intrinsics.checkParameterIsNotNull(replacement, "replacement");
      Intrinsics.checkParameterIsNotNull(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.indexOf$default((CharSequence)$this$replaceBefore, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         byte var6 = 0;
         boolean var7 = false;
         var10000 = StringsKt.replaceRange((CharSequence)$this$replaceBefore, var6, index, (CharSequence)replacement).toString();
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String replaceBefore$default(String var0, String var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceBefore(var0, var1, var2, var3);
   }

   @NotNull
   public static final String replaceAfter(@NotNull String $this$replaceAfter, char delimiter, @NotNull String replacement, @NotNull String missingDelimiterValue) {
      Intrinsics.checkParameterIsNotNull($this$replaceAfter, "$this$replaceAfter");
      Intrinsics.checkParameterIsNotNull(replacement, "replacement");
      Intrinsics.checkParameterIsNotNull(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.indexOf$default((CharSequence)$this$replaceAfter, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         int var6 = index + 1;
         int var7 = $this$replaceAfter.length();
         boolean var8 = false;
         var10000 = StringsKt.replaceRange((CharSequence)$this$replaceAfter, var6, var7, (CharSequence)replacement).toString();
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String replaceAfter$default(String var0, char var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceAfter(var0, var1, var2, var3);
   }

   @NotNull
   public static final String replaceAfter(@NotNull String $this$replaceAfter, @NotNull String delimiter, @NotNull String replacement, @NotNull String missingDelimiterValue) {
      Intrinsics.checkParameterIsNotNull($this$replaceAfter, "$this$replaceAfter");
      Intrinsics.checkParameterIsNotNull(delimiter, "delimiter");
      Intrinsics.checkParameterIsNotNull(replacement, "replacement");
      Intrinsics.checkParameterIsNotNull(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.indexOf$default((CharSequence)$this$replaceAfter, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         int var6 = index + delimiter.length();
         int var7 = $this$replaceAfter.length();
         boolean var8 = false;
         var10000 = StringsKt.replaceRange((CharSequence)$this$replaceAfter, var6, var7, (CharSequence)replacement).toString();
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String replaceAfter$default(String var0, String var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceAfter(var0, var1, var2, var3);
   }

   @NotNull
   public static final String replaceAfterLast(@NotNull String $this$replaceAfterLast, @NotNull String delimiter, @NotNull String replacement, @NotNull String missingDelimiterValue) {
      Intrinsics.checkParameterIsNotNull($this$replaceAfterLast, "$this$replaceAfterLast");
      Intrinsics.checkParameterIsNotNull(delimiter, "delimiter");
      Intrinsics.checkParameterIsNotNull(replacement, "replacement");
      Intrinsics.checkParameterIsNotNull(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.lastIndexOf$default((CharSequence)$this$replaceAfterLast, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         int var6 = index + delimiter.length();
         int var7 = $this$replaceAfterLast.length();
         boolean var8 = false;
         var10000 = StringsKt.replaceRange((CharSequence)$this$replaceAfterLast, var6, var7, (CharSequence)replacement).toString();
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String replaceAfterLast$default(String var0, String var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceAfterLast(var0, var1, var2, var3);
   }

   @NotNull
   public static final String replaceAfterLast(@NotNull String $this$replaceAfterLast, char delimiter, @NotNull String replacement, @NotNull String missingDelimiterValue) {
      Intrinsics.checkParameterIsNotNull($this$replaceAfterLast, "$this$replaceAfterLast");
      Intrinsics.checkParameterIsNotNull(replacement, "replacement");
      Intrinsics.checkParameterIsNotNull(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.lastIndexOf$default((CharSequence)$this$replaceAfterLast, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         int var6 = index + 1;
         int var7 = $this$replaceAfterLast.length();
         boolean var8 = false;
         var10000 = StringsKt.replaceRange((CharSequence)$this$replaceAfterLast, var6, var7, (CharSequence)replacement).toString();
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String replaceAfterLast$default(String var0, char var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceAfterLast(var0, var1, var2, var3);
   }

   @NotNull
   public static final String replaceBeforeLast(@NotNull String $this$replaceBeforeLast, char delimiter, @NotNull String replacement, @NotNull String missingDelimiterValue) {
      Intrinsics.checkParameterIsNotNull($this$replaceBeforeLast, "$this$replaceBeforeLast");
      Intrinsics.checkParameterIsNotNull(replacement, "replacement");
      Intrinsics.checkParameterIsNotNull(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.lastIndexOf$default((CharSequence)$this$replaceBeforeLast, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         byte var6 = 0;
         boolean var7 = false;
         var10000 = StringsKt.replaceRange((CharSequence)$this$replaceBeforeLast, var6, index, (CharSequence)replacement).toString();
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String replaceBeforeLast$default(String var0, char var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceBeforeLast(var0, var1, var2, var3);
   }

   @NotNull
   public static final String replaceBeforeLast(@NotNull String $this$replaceBeforeLast, @NotNull String delimiter, @NotNull String replacement, @NotNull String missingDelimiterValue) {
      Intrinsics.checkParameterIsNotNull($this$replaceBeforeLast, "$this$replaceBeforeLast");
      Intrinsics.checkParameterIsNotNull(delimiter, "delimiter");
      Intrinsics.checkParameterIsNotNull(replacement, "replacement");
      Intrinsics.checkParameterIsNotNull(missingDelimiterValue, "missingDelimiterValue");
      int index = StringsKt.lastIndexOf$default((CharSequence)$this$replaceBeforeLast, delimiter, 0, false, 6, (Object)null);
      String var10000;
      if (index == -1) {
         var10000 = missingDelimiterValue;
      } else {
         byte var6 = 0;
         boolean var7 = false;
         var10000 = StringsKt.replaceRange((CharSequence)$this$replaceBeforeLast, var6, index, (CharSequence)replacement).toString();
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String replaceBeforeLast$default(String var0, String var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceBeforeLast(var0, var1, var2, var3);
   }

   @InlineOnly
   private static final String replace(@NotNull CharSequence $this$replace, Regex regex, String replacement) {
      int $i$f$replace = 0;
      return regex.replace($this$replace, replacement);
   }

   @InlineOnly
   private static final String replace(@NotNull CharSequence $this$replace, Regex regex, Function1<? super MatchResult, ? extends CharSequence> transform) {
      int $i$f$replace = 0;
      return regex.replace($this$replace, transform);
   }

   @InlineOnly
   private static final String replaceFirst(@NotNull CharSequence $this$replaceFirst, Regex regex, String replacement) {
      int $i$f$replaceFirst = 0;
      return regex.replaceFirst($this$replaceFirst, replacement);
   }

   @InlineOnly
   private static final boolean matches(@NotNull CharSequence $this$matches, Regex regex) {
      int $i$f$matches = 0;
      return regex.matches($this$matches);
   }

   public static final boolean regionMatchesImpl(@NotNull CharSequence $this$regionMatchesImpl, int thisOffset, @NotNull CharSequence other, int otherOffset, int length, boolean ignoreCase) {
      Intrinsics.checkParameterIsNotNull($this$regionMatchesImpl, "$this$regionMatchesImpl");
      Intrinsics.checkParameterIsNotNull(other, "other");
      if (otherOffset >= 0 && thisOffset >= 0 && thisOffset <= $this$regionMatchesImpl.length() - length && otherOffset <= other.length() - length) {
         int index = 0;

         for(int var7 = length; index < var7; ++index) {
            if (!CharsKt.equals($this$regionMatchesImpl.charAt(thisOffset + index), other.charAt(otherOffset + index), ignoreCase)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public static final boolean startsWith(@NotNull CharSequence $this$startsWith, char var1, boolean ignoreCase) {
      Intrinsics.checkParameterIsNotNull($this$startsWith, "$this$startsWith");
      return $this$startsWith.length() > 0 && CharsKt.equals($this$startsWith.charAt(0), var1, ignoreCase);
   }

   // $FF: synthetic method
   public static boolean startsWith$default(CharSequence var0, char var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.startsWith(var0, var1, var2);
   }

   public static final boolean endsWith(@NotNull CharSequence $this$endsWith, char var1, boolean ignoreCase) {
      Intrinsics.checkParameterIsNotNull($this$endsWith, "$this$endsWith");
      return $this$endsWith.length() > 0 && CharsKt.equals($this$endsWith.charAt(StringsKt.getLastIndex($this$endsWith)), var1, ignoreCase);
   }

   // $FF: synthetic method
   public static boolean endsWith$default(CharSequence var0, char var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.endsWith(var0, var1, var2);
   }

   public static final boolean startsWith(@NotNull CharSequence $this$startsWith, @NotNull CharSequence prefix, boolean ignoreCase) {
      Intrinsics.checkParameterIsNotNull($this$startsWith, "$this$startsWith");
      Intrinsics.checkParameterIsNotNull(prefix, "prefix");
      return !ignoreCase && $this$startsWith instanceof String && prefix instanceof String ? StringsKt.startsWith$default((String)$this$startsWith, (String)prefix, false, 2, (Object)null) : StringsKt.regionMatchesImpl($this$startsWith, 0, prefix, 0, prefix.length(), ignoreCase);
   }

   // $FF: synthetic method
   public static boolean startsWith$default(CharSequence var0, CharSequence var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.startsWith(var0, var1, var2);
   }

   public static final boolean startsWith(@NotNull CharSequence $this$startsWith, @NotNull CharSequence prefix, int startIndex, boolean ignoreCase) {
      Intrinsics.checkParameterIsNotNull($this$startsWith, "$this$startsWith");
      Intrinsics.checkParameterIsNotNull(prefix, "prefix");
      return !ignoreCase && $this$startsWith instanceof String && prefix instanceof String ? StringsKt.startsWith$default((String)$this$startsWith, (String)prefix, startIndex, false, 4, (Object)null) : StringsKt.regionMatchesImpl($this$startsWith, startIndex, prefix, 0, prefix.length(), ignoreCase);
   }

   // $FF: synthetic method
   public static boolean startsWith$default(CharSequence var0, CharSequence var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.startsWith(var0, var1, var2, var3);
   }

   public static final boolean endsWith(@NotNull CharSequence $this$endsWith, @NotNull CharSequence suffix, boolean ignoreCase) {
      Intrinsics.checkParameterIsNotNull($this$endsWith, "$this$endsWith");
      Intrinsics.checkParameterIsNotNull(suffix, "suffix");
      return !ignoreCase && $this$endsWith instanceof String && suffix instanceof String ? StringsKt.endsWith$default((String)$this$endsWith, (String)suffix, false, 2, (Object)null) : StringsKt.regionMatchesImpl($this$endsWith, $this$endsWith.length() - suffix.length(), suffix, 0, suffix.length(), ignoreCase);
   }

   // $FF: synthetic method
   public static boolean endsWith$default(CharSequence var0, CharSequence var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.endsWith(var0, var1, var2);
   }

   @NotNull
   public static final String commonPrefixWith(@NotNull CharSequence $this$commonPrefixWith, @NotNull CharSequence other, boolean ignoreCase) {
      Intrinsics.checkParameterIsNotNull($this$commonPrefixWith, "$this$commonPrefixWith");
      Intrinsics.checkParameterIsNotNull(other, "other");
      int i = $this$commonPrefixWith.length();
      int var5 = other.length();
      boolean var6 = false;
      int shortestLength = Math.min(i, var5);

      for(i = 0; i < shortestLength && CharsKt.equals($this$commonPrefixWith.charAt(i), other.charAt(i), ignoreCase); ++i) {
      }

      if (StringsKt.hasSurrogatePairAt($this$commonPrefixWith, i - 1) || StringsKt.hasSurrogatePairAt(other, i - 1)) {
         --i;
      }

      return $this$commonPrefixWith.subSequence(0, i).toString();
   }

   // $FF: synthetic method
   public static String commonPrefixWith$default(CharSequence var0, CharSequence var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.commonPrefixWith(var0, var1, var2);
   }

   @NotNull
   public static final String commonSuffixWith(@NotNull CharSequence $this$commonSuffixWith, @NotNull CharSequence other, boolean ignoreCase) {
      Intrinsics.checkParameterIsNotNull($this$commonSuffixWith, "$this$commonSuffixWith");
      Intrinsics.checkParameterIsNotNull(other, "other");
      int thisLength = $this$commonSuffixWith.length();
      int otherLength = other.length();
      boolean var6 = false;
      int shortestLength = Math.min(thisLength, otherLength);

      int i;
      for(i = 0; i < shortestLength && CharsKt.equals($this$commonSuffixWith.charAt(thisLength - i - 1), other.charAt(otherLength - i - 1), ignoreCase); ++i) {
      }

      if (StringsKt.hasSurrogatePairAt($this$commonSuffixWith, thisLength - i - 1) || StringsKt.hasSurrogatePairAt(other, otherLength - i - 1)) {
         --i;
      }

      return $this$commonSuffixWith.subSequence(thisLength - i, thisLength).toString();
   }

   // $FF: synthetic method
   public static String commonSuffixWith$default(CharSequence var0, CharSequence var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.commonSuffixWith(var0, var1, var2);
   }

   public static final int indexOfAny(@NotNull CharSequence $this$indexOfAny, @NotNull char[] chars, int startIndex, boolean ignoreCase) {
      Intrinsics.checkParameterIsNotNull($this$indexOfAny, "$this$indexOfAny");
      Intrinsics.checkParameterIsNotNull(chars, "chars");
      if (!ignoreCase && chars.length == 1 && $this$indexOfAny instanceof String) {
         char var15 = ArraysKt.single(chars);
         String var16 = (String)$this$indexOfAny;
         boolean var17 = false;
         return var16.indexOf(var15, startIndex);
      } else {
         int index = RangesKt.coerceAtLeast(startIndex, 0);
         int var5 = StringsKt.getLastIndex($this$indexOfAny);
         if (index <= var5) {
            while(true) {
               char charAtIndex = $this$indexOfAny.charAt(index);
               int $i$f$any = false;
               char[] var9 = chars;
               int var10 = chars.length;
               int var11 = 0;

               boolean var10000;
               while(true) {
                  if (var11 >= var10) {
                     var10000 = false;
                     break;
                  }

                  char element$iv = var9[var11];
                  int var14 = false;
                  if (CharsKt.equals(element$iv, charAtIndex, ignoreCase)) {
                     var10000 = true;
                     break;
                  }

                  ++var11;
               }

               if (var10000) {
                  return index;
               }

               if (index == var5) {
                  break;
               }

               ++index;
            }
         }

         return -1;
      }
   }

   // $FF: synthetic method
   public static int indexOfAny$default(CharSequence var0, char[] var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.indexOfAny(var0, var1, var2, var3);
   }

   public static final int lastIndexOfAny(@NotNull CharSequence $this$lastIndexOfAny, @NotNull char[] chars, int startIndex, boolean ignoreCase) {
      Intrinsics.checkParameterIsNotNull($this$lastIndexOfAny, "$this$lastIndexOfAny");
      Intrinsics.checkParameterIsNotNull(chars, "chars");
      if (!ignoreCase && chars.length == 1 && $this$lastIndexOfAny instanceof String) {
         char var15 = ArraysKt.single(chars);
         String var16 = (String)$this$lastIndexOfAny;
         boolean var17 = false;
         return var16.lastIndexOf(var15, startIndex);
      } else {
         int index = RangesKt.coerceAtMost(startIndex, StringsKt.getLastIndex($this$lastIndexOfAny));

         for(boolean var5 = false; index >= 0; --index) {
            char charAtIndex = $this$lastIndexOfAny.charAt(index);
            int $i$f$any = false;
            char[] var9 = chars;
            int var10 = chars.length;
            int var11 = 0;

            boolean var10000;
            while(true) {
               if (var11 >= var10) {
                  var10000 = false;
                  break;
               }

               char element$iv = var9[var11];
               int var14 = false;
               if (CharsKt.equals(element$iv, charAtIndex, ignoreCase)) {
                  var10000 = true;
                  break;
               }

               ++var11;
            }

            if (var10000) {
               return index;
            }
         }

         return -1;
      }
   }

   // $FF: synthetic method
   public static int lastIndexOfAny$default(CharSequence var0, char[] var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = StringsKt.getLastIndex(var0);
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.lastIndexOfAny(var0, var1, var2, var3);
   }

   private static final int indexOf$StringsKt__StringsKt(@NotNull CharSequence $this$indexOf, CharSequence other, int startIndex, int endIndex, boolean ignoreCase, boolean last) {
      IntProgression var10000;
      int index;
      if (!last) {
         index = RangesKt.coerceAtLeast(startIndex, 0);
         var10000 = (IntProgression)(new IntRange(index, RangesKt.coerceAtMost(endIndex, $this$indexOf.length())));
      } else {
         var10000 = RangesKt.downTo(RangesKt.coerceAtMost(startIndex, StringsKt.getLastIndex($this$indexOf)), RangesKt.coerceAtLeast(endIndex, 0));
      }

      IntProgression indices = var10000;
      int var8;
      int var9;
      if ($this$indexOf instanceof String && other instanceof String) {
         index = indices.getFirst();
         var8 = indices.getLast();
         var9 = indices.getStep();
         if (var9 >= 0) {
            if (index > var8) {
               return -1;
            }
         } else if (index < var8) {
            return -1;
         }

         while(true) {
            if (StringsKt.regionMatches((String)other, 0, (String)$this$indexOf, index, other.length(), ignoreCase)) {
               return index;
            }

            if (index == var8) {
               break;
            }

            index += var9;
         }
      } else {
         index = indices.getFirst();
         var8 = indices.getLast();
         var9 = indices.getStep();
         if (var9 >= 0) {
            if (index > var8) {
               return -1;
            }
         } else if (index < var8) {
            return -1;
         }

         while(true) {
            if (StringsKt.regionMatchesImpl(other, 0, $this$indexOf, index, other.length(), ignoreCase)) {
               return index;
            }

            if (index == var8) {
               break;
            }

            index += var9;
         }
      }

      return -1;
   }

   // $FF: synthetic method
   static int indexOf$StringsKt__StringsKt$default(CharSequence var0, CharSequence var1, int var2, int var3, boolean var4, boolean var5, int var6, Object var7) {
      if ((var6 & 16) != 0) {
         var5 = false;
      }

      return indexOf$StringsKt__StringsKt(var0, var1, var2, var3, var4, var5);
   }

   private static final Pair<Integer, String> findAnyOf$StringsKt__StringsKt(@NotNull CharSequence $this$findAnyOf, Collection<String> strings, int startIndex, boolean ignoreCase, boolean last) {
      int index;
      if (!ignoreCase && strings.size() == 1) {
         String string = (String)CollectionsKt.single((Iterable)strings);
         index = !last ? StringsKt.indexOf$default($this$findAnyOf, string, startIndex, false, 4, (Object)null) : StringsKt.lastIndexOf$default($this$findAnyOf, string, startIndex, false, 4, (Object)null);
         return index < 0 ? null : TuplesKt.to(index, string);
      } else {
         IntProgression var10000;
         if (!last) {
            index = RangesKt.coerceAtLeast(startIndex, 0);
            var10000 = (IntProgression)(new IntRange(index, $this$findAnyOf.length()));
         } else {
            var10000 = RangesKt.downTo(RangesKt.coerceAtMost(startIndex, StringsKt.getLastIndex($this$findAnyOf)), 0);
         }

         IntProgression indices = var10000;
         int var7;
         int var8;
         String matchingString;
         Iterable $this$firstOrNull$iv;
         boolean $i$f$firstOrNull;
         Iterator var12;
         Object element$iv;
         String it;
         boolean var15;
         Object var17;
         if ($this$findAnyOf instanceof String) {
            index = indices.getFirst();
            var7 = indices.getLast();
            var8 = indices.getStep();
            if (var8 >= 0) {
               if (index > var7) {
                  return null;
               }
            } else if (index < var7) {
               return null;
            }

            while(true) {
               $this$firstOrNull$iv = (Iterable)strings;
               $i$f$firstOrNull = false;
               var12 = $this$firstOrNull$iv.iterator();

               while(true) {
                  if (!var12.hasNext()) {
                     var17 = null;
                     break;
                  }

                  element$iv = var12.next();
                  it = (String)element$iv;
                  var15 = false;
                  if (StringsKt.regionMatches(it, 0, (String)$this$findAnyOf, index, it.length(), ignoreCase)) {
                     var17 = element$iv;
                     break;
                  }
               }

               matchingString = (String)var17;
               if (matchingString != null) {
                  return TuplesKt.to(index, matchingString);
               }

               if (index == var7) {
                  break;
               }

               index += var8;
            }
         } else {
            index = indices.getFirst();
            var7 = indices.getLast();
            var8 = indices.getStep();
            if (var8 >= 0) {
               if (index > var7) {
                  return null;
               }
            } else if (index < var7) {
               return null;
            }

            while(true) {
               $this$firstOrNull$iv = (Iterable)strings;
               $i$f$firstOrNull = false;
               var12 = $this$firstOrNull$iv.iterator();

               while(true) {
                  if (!var12.hasNext()) {
                     var17 = null;
                     break;
                  }

                  element$iv = var12.next();
                  it = (String)element$iv;
                  var15 = false;
                  if (StringsKt.regionMatchesImpl((CharSequence)it, 0, $this$findAnyOf, index, it.length(), ignoreCase)) {
                     var17 = element$iv;
                     break;
                  }
               }

               matchingString = (String)var17;
               if (matchingString != null) {
                  return TuplesKt.to(index, matchingString);
               }

               if (index == var7) {
                  break;
               }

               index += var8;
            }
         }

         return null;
      }
   }

   @Nullable
   public static final Pair<Integer, String> findAnyOf(@NotNull CharSequence $this$findAnyOf, @NotNull Collection<String> strings, int startIndex, boolean ignoreCase) {
      Intrinsics.checkParameterIsNotNull($this$findAnyOf, "$this$findAnyOf");
      Intrinsics.checkParameterIsNotNull(strings, "strings");
      return findAnyOf$StringsKt__StringsKt($this$findAnyOf, strings, startIndex, ignoreCase, false);
   }

   // $FF: synthetic method
   public static Pair findAnyOf$default(CharSequence var0, Collection var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.findAnyOf(var0, var1, var2, var3);
   }

   @Nullable
   public static final Pair<Integer, String> findLastAnyOf(@NotNull CharSequence $this$findLastAnyOf, @NotNull Collection<String> strings, int startIndex, boolean ignoreCase) {
      Intrinsics.checkParameterIsNotNull($this$findLastAnyOf, "$this$findLastAnyOf");
      Intrinsics.checkParameterIsNotNull(strings, "strings");
      return findAnyOf$StringsKt__StringsKt($this$findLastAnyOf, strings, startIndex, ignoreCase, true);
   }

   // $FF: synthetic method
   public static Pair findLastAnyOf$default(CharSequence var0, Collection var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = StringsKt.getLastIndex(var0);
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.findLastAnyOf(var0, var1, var2, var3);
   }

   public static final int indexOfAny(@NotNull CharSequence $this$indexOfAny, @NotNull Collection<String> strings, int startIndex, boolean ignoreCase) {
      Intrinsics.checkParameterIsNotNull($this$indexOfAny, "$this$indexOfAny");
      Intrinsics.checkParameterIsNotNull(strings, "strings");
      Pair var10000 = findAnyOf$StringsKt__StringsKt($this$indexOfAny, strings, startIndex, ignoreCase, false);
      int var5;
      if (var10000 != null) {
         Integer var4 = (Integer)var10000.getFirst();
         if (var4 != null) {
            var5 = var4;
            return var5;
         }
      }

      var5 = -1;
      return var5;
   }

   // $FF: synthetic method
   public static int indexOfAny$default(CharSequence var0, Collection var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.indexOfAny(var0, var1, var2, var3);
   }

   public static final int lastIndexOfAny(@NotNull CharSequence $this$lastIndexOfAny, @NotNull Collection<String> strings, int startIndex, boolean ignoreCase) {
      Intrinsics.checkParameterIsNotNull($this$lastIndexOfAny, "$this$lastIndexOfAny");
      Intrinsics.checkParameterIsNotNull(strings, "strings");
      Pair var10000 = findAnyOf$StringsKt__StringsKt($this$lastIndexOfAny, strings, startIndex, ignoreCase, true);
      int var5;
      if (var10000 != null) {
         Integer var4 = (Integer)var10000.getFirst();
         if (var4 != null) {
            var5 = var4;
            return var5;
         }
      }

      var5 = -1;
      return var5;
   }

   // $FF: synthetic method
   public static int lastIndexOfAny$default(CharSequence var0, Collection var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = StringsKt.getLastIndex(var0);
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.lastIndexOfAny(var0, var1, var2, var3);
   }

   public static final int indexOf(@NotNull CharSequence $this$indexOf, char var1, int startIndex, boolean ignoreCase) {
      Intrinsics.checkParameterIsNotNull($this$indexOf, "$this$indexOf");
      int var10000;
      if (!ignoreCase && $this$indexOf instanceof String) {
         String var4 = (String)$this$indexOf;
         boolean var5 = false;
         var10000 = var4.indexOf(var1, startIndex);
      } else {
         var10000 = StringsKt.indexOfAny($this$indexOf, new char[]{var1}, startIndex, ignoreCase);
      }

      return var10000;
   }

   // $FF: synthetic method
   public static int indexOf$default(CharSequence var0, char var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.indexOf(var0, var1, var2, var3);
   }

   public static final int indexOf(@NotNull CharSequence $this$indexOf, @NotNull String string, int startIndex, boolean ignoreCase) {
      Intrinsics.checkParameterIsNotNull($this$indexOf, "$this$indexOf");
      Intrinsics.checkParameterIsNotNull(string, "string");
      int var10000;
      if (!ignoreCase && $this$indexOf instanceof String) {
         String var4 = (String)$this$indexOf;
         boolean var5 = false;
         var10000 = var4.indexOf(string, startIndex);
      } else {
         var10000 = indexOf$StringsKt__StringsKt$default($this$indexOf, (CharSequence)string, startIndex, $this$indexOf.length(), ignoreCase, false, 16, (Object)null);
      }

      return var10000;
   }

   // $FF: synthetic method
   public static int indexOf$default(CharSequence var0, String var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.indexOf(var0, var1, var2, var3);
   }

   public static final int lastIndexOf(@NotNull CharSequence $this$lastIndexOf, char var1, int startIndex, boolean ignoreCase) {
      Intrinsics.checkParameterIsNotNull($this$lastIndexOf, "$this$lastIndexOf");
      int var10000;
      if (!ignoreCase && $this$lastIndexOf instanceof String) {
         String var4 = (String)$this$lastIndexOf;
         boolean var5 = false;
         var10000 = var4.lastIndexOf(var1, startIndex);
      } else {
         var10000 = StringsKt.lastIndexOfAny($this$lastIndexOf, new char[]{var1}, startIndex, ignoreCase);
      }

      return var10000;
   }

   // $FF: synthetic method
   public static int lastIndexOf$default(CharSequence var0, char var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = StringsKt.getLastIndex(var0);
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.lastIndexOf(var0, var1, var2, var3);
   }

   public static final int lastIndexOf(@NotNull CharSequence $this$lastIndexOf, @NotNull String string, int startIndex, boolean ignoreCase) {
      Intrinsics.checkParameterIsNotNull($this$lastIndexOf, "$this$lastIndexOf");
      Intrinsics.checkParameterIsNotNull(string, "string");
      int var10000;
      if (!ignoreCase && $this$lastIndexOf instanceof String) {
         String var4 = (String)$this$lastIndexOf;
         boolean var5 = false;
         var10000 = var4.lastIndexOf(string, startIndex);
      } else {
         var10000 = indexOf$StringsKt__StringsKt($this$lastIndexOf, (CharSequence)string, startIndex, 0, ignoreCase, true);
      }

      return var10000;
   }

   // $FF: synthetic method
   public static int lastIndexOf$default(CharSequence var0, String var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = StringsKt.getLastIndex(var0);
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.lastIndexOf(var0, var1, var2, var3);
   }

   public static final boolean contains(@NotNull CharSequence $this$contains, @NotNull CharSequence other, boolean ignoreCase) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      Intrinsics.checkParameterIsNotNull(other, "other");
      return other instanceof String ? StringsKt.indexOf$default($this$contains, (String)other, 0, ignoreCase, 2, (Object)null) >= 0 : indexOf$StringsKt__StringsKt$default($this$contains, other, 0, $this$contains.length(), ignoreCase, false, 16, (Object)null) >= 0;
   }

   // $FF: synthetic method
   public static boolean contains$default(CharSequence var0, CharSequence var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.contains(var0, var1, var2);
   }

   public static final boolean contains(@NotNull CharSequence $this$contains, char var1, boolean ignoreCase) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      return StringsKt.indexOf$default($this$contains, var1, 0, ignoreCase, 2, (Object)null) >= 0;
   }

   // $FF: synthetic method
   public static boolean contains$default(CharSequence var0, char var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.contains(var0, var1, var2);
   }

   @InlineOnly
   private static final boolean contains(@NotNull CharSequence $this$contains, Regex regex) {
      int $i$f$contains = 0;
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      return regex.containsMatchIn($this$contains);
   }

   private static final Sequence<IntRange> rangesDelimitedBy$StringsKt__StringsKt(@NotNull CharSequence $this$rangesDelimitedBy, final char[] delimiters, int startIndex, final boolean ignoreCase, int limit) {
      boolean var5 = limit >= 0;
      boolean var6 = false;
      boolean var7 = false;
      if (!var5) {
         int var8 = false;
         String var9 = "Limit must be non-negative, but was " + limit + '.';
         throw (Throwable)(new IllegalArgumentException(var9.toString()));
      } else {
         return (Sequence)(new DelimitedRangesSequence($this$rangesDelimitedBy, startIndex, limit, (Function2)(new Function2<CharSequence, Integer, Pair<? extends Integer, ? extends Integer>>() {
            @Nullable
            public final Pair<Integer, Integer> invoke(@NotNull CharSequence $receiver, int currentIndex) {
               Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
               int var3 = StringsKt.indexOfAny($receiver, delimiters, currentIndex, ignoreCase);
               boolean var4 = false;
               boolean var5 = false;
               int var7 = false;
               return var3 < 0 ? null : TuplesKt.to(var3, 1);
            }
         })));
      }
   }

   // $FF: synthetic method
   static Sequence rangesDelimitedBy$StringsKt__StringsKt$default(CharSequence var0, char[] var1, int var2, boolean var3, int var4, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         var2 = 0;
      }

      if ((var5 & 4) != 0) {
         var3 = false;
      }

      if ((var5 & 8) != 0) {
         var4 = 0;
      }

      return rangesDelimitedBy$StringsKt__StringsKt(var0, var1, var2, var3, var4);
   }

   private static final Sequence<IntRange> rangesDelimitedBy$StringsKt__StringsKt(@NotNull CharSequence $this$rangesDelimitedBy, String[] delimiters, int startIndex, final boolean ignoreCase, int limit) {
      boolean var5 = limit >= 0;
      boolean var6 = false;
      boolean var7 = false;
      if (!var5) {
         int var8 = false;
         String var10 = "Limit must be non-negative, but was " + limit + '.';
         throw (Throwable)(new IllegalArgumentException(var10.toString()));
      } else {
         final List delimitersList = ArraysKt.asList(delimiters);
         return (Sequence)(new DelimitedRangesSequence($this$rangesDelimitedBy, startIndex, limit, (Function2)(new Function2<CharSequence, Integer, Pair<? extends Integer, ? extends Integer>>() {
            @Nullable
            public final Pair<Integer, Integer> invoke(@NotNull CharSequence $receiver, int currentIndex) {
               Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
               Pair var10000 = StringsKt__StringsKt.findAnyOf$StringsKt__StringsKt($receiver, (Collection)delimitersList, currentIndex, ignoreCase, false);
               if (var10000 != null) {
                  Pair var3 = var10000;
                  boolean var4 = false;
                  boolean var5 = false;
                  int var7 = false;
                  var10000 = TuplesKt.to(var3.getFirst(), ((String)var3.getSecond()).length());
               } else {
                  var10000 = null;
               }

               return var10000;
            }
         })));
      }
   }

   // $FF: synthetic method
   static Sequence rangesDelimitedBy$StringsKt__StringsKt$default(CharSequence var0, String[] var1, int var2, boolean var3, int var4, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         var2 = 0;
      }

      if ((var5 & 4) != 0) {
         var3 = false;
      }

      if ((var5 & 8) != 0) {
         var4 = 0;
      }

      return rangesDelimitedBy$StringsKt__StringsKt(var0, var1, var2, var3, var4);
   }

   @NotNull
   public static final Sequence<String> splitToSequence(@NotNull final CharSequence $this$splitToSequence, @NotNull String[] delimiters, boolean ignoreCase, int limit) {
      Intrinsics.checkParameterIsNotNull($this$splitToSequence, "$this$splitToSequence");
      Intrinsics.checkParameterIsNotNull(delimiters, "delimiters");
      return SequencesKt.map(rangesDelimitedBy$StringsKt__StringsKt$default($this$splitToSequence, (String[])delimiters, 0, ignoreCase, limit, 2, (Object)null), (Function1)(new Function1<IntRange, String>() {
         @NotNull
         public final String invoke(@NotNull IntRange it) {
            Intrinsics.checkParameterIsNotNull(it, "it");
            return StringsKt.substring($this$splitToSequence, it);
         }
      }));
   }

   // $FF: synthetic method
   public static Sequence splitToSequence$default(CharSequence var0, String[] var1, boolean var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = false;
      }

      if ((var4 & 4) != 0) {
         var3 = 0;
      }

      return StringsKt.splitToSequence(var0, var1, var2, var3);
   }

   @NotNull
   public static final List<String> split(@NotNull CharSequence $this$split, @NotNull String[] delimiters, boolean ignoreCase, int limit) {
      Intrinsics.checkParameterIsNotNull($this$split, "$this$split");
      Intrinsics.checkParameterIsNotNull(delimiters, "delimiters");
      if (delimiters.length == 1) {
         String delimiter = delimiters[0];
         CharSequence var5 = (CharSequence)delimiter;
         boolean var6 = false;
         if (var5.length() != 0) {
            return split$StringsKt__StringsKt($this$split, delimiter, ignoreCase, limit);
         }
      }

      Iterable $this$map$iv = SequencesKt.asIterable(rangesDelimitedBy$StringsKt__StringsKt$default($this$split, (String[])delimiters, 0, ignoreCase, limit, 2, (Object)null));
      int $i$f$map = false;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
      int $i$f$mapTo = false;
      Iterator var9 = $this$map$iv.iterator();

      while(var9.hasNext()) {
         Object item$iv$iv = var9.next();
         IntRange it = (IntRange)item$iv$iv;
         int var12 = false;
         String var14 = StringsKt.substring($this$split, it);
         destination$iv$iv.add(var14);
      }

      return (List)destination$iv$iv;
   }

   // $FF: synthetic method
   public static List split$default(CharSequence var0, String[] var1, boolean var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = false;
      }

      if ((var4 & 4) != 0) {
         var3 = 0;
      }

      return StringsKt.split(var0, var1, var2, var3);
   }

   @NotNull
   public static final Sequence<String> splitToSequence(@NotNull final CharSequence $this$splitToSequence, @NotNull char[] delimiters, boolean ignoreCase, int limit) {
      Intrinsics.checkParameterIsNotNull($this$splitToSequence, "$this$splitToSequence");
      Intrinsics.checkParameterIsNotNull(delimiters, "delimiters");
      return SequencesKt.map(rangesDelimitedBy$StringsKt__StringsKt$default($this$splitToSequence, (char[])delimiters, 0, ignoreCase, limit, 2, (Object)null), (Function1)(new Function1<IntRange, String>() {
         @NotNull
         public final String invoke(@NotNull IntRange it) {
            Intrinsics.checkParameterIsNotNull(it, "it");
            return StringsKt.substring($this$splitToSequence, it);
         }
      }));
   }

   // $FF: synthetic method
   public static Sequence splitToSequence$default(CharSequence var0, char[] var1, boolean var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = false;
      }

      if ((var4 & 4) != 0) {
         var3 = 0;
      }

      return StringsKt.splitToSequence(var0, var1, var2, var3);
   }

   @NotNull
   public static final List<String> split(@NotNull CharSequence $this$split, @NotNull char[] delimiters, boolean ignoreCase, int limit) {
      Intrinsics.checkParameterIsNotNull($this$split, "$this$split");
      Intrinsics.checkParameterIsNotNull(delimiters, "delimiters");
      if (delimiters.length == 1) {
         return split$StringsKt__StringsKt($this$split, String.valueOf(delimiters[0]), ignoreCase, limit);
      } else {
         Iterable $this$map$iv = SequencesKt.asIterable(rangesDelimitedBy$StringsKt__StringsKt$default($this$split, (char[])delimiters, 0, ignoreCase, limit, 2, (Object)null));
         int $i$f$map = false;
         Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
         int $i$f$mapTo = false;
         Iterator var9 = $this$map$iv.iterator();

         while(var9.hasNext()) {
            Object item$iv$iv = var9.next();
            IntRange it = (IntRange)item$iv$iv;
            int var12 = false;
            String var14 = StringsKt.substring($this$split, it);
            destination$iv$iv.add(var14);
         }

         return (List)destination$iv$iv;
      }
   }

   // $FF: synthetic method
   public static List split$default(CharSequence var0, char[] var1, boolean var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = false;
      }

      if ((var4 & 4) != 0) {
         var3 = 0;
      }

      return StringsKt.split(var0, var1, var2, var3);
   }

   private static final List<String> split$StringsKt__StringsKt(@NotNull CharSequence $this$split, String delimiter, boolean ignoreCase, int limit) {
      boolean var4 = limit >= 0;
      boolean var5 = false;
      boolean isLimited = false;
      if (!var4) {
         int var16 = false;
         String var15 = "Limit must be non-negative, but was " + limit + '.';
         throw (Throwable)(new IllegalArgumentException(var15.toString()));
      } else {
         int currentOffset = 0;
         int nextIndex = StringsKt.indexOf($this$split, delimiter, currentOffset, ignoreCase);
         if (nextIndex != -1 && limit != 1) {
            isLimited = limit > 0;
            ArrayList result = new ArrayList(isLimited ? RangesKt.coerceAtMost(limit, 10) : 10);

            String var12;
            do {
               boolean var9 = false;
               var12 = $this$split.subSequence(currentOffset, nextIndex).toString();
               result.add(var12);
               currentOffset = nextIndex + delimiter.length();
               if (isLimited && result.size() == limit - 1) {
                  break;
               }

               nextIndex = StringsKt.indexOf($this$split, delimiter, currentOffset, ignoreCase);
            } while(nextIndex != -1);

            int var17 = $this$split.length();
            boolean var10 = false;
            var12 = $this$split.subSequence(currentOffset, var17).toString();
            result.add(var12);
            return (List)result;
         } else {
            return CollectionsKt.listOf($this$split.toString());
         }
      }
   }

   @InlineOnly
   private static final List<String> split(@NotNull CharSequence $this$split, Regex regex, int limit) {
      int $i$f$split = 0;
      return regex.split($this$split, limit);
   }

   // $FF: synthetic method
   static List split$default(CharSequence $this$split, Regex regex, int limit, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         limit = 0;
      }

      int $i$f$split = false;
      return regex.split($this$split, limit);
   }

   @NotNull
   public static final Sequence<String> lineSequence(@NotNull CharSequence $this$lineSequence) {
      Intrinsics.checkParameterIsNotNull($this$lineSequence, "$this$lineSequence");
      return StringsKt.splitToSequence$default($this$lineSequence, new String[]{"\r\n", "\n", "\r"}, false, 0, 6, (Object)null);
   }

   @NotNull
   public static final List<String> lines(@NotNull CharSequence $this$lines) {
      Intrinsics.checkParameterIsNotNull($this$lines, "$this$lines");
      return SequencesKt.toList(StringsKt.lineSequence($this$lines));
   }

   public StringsKt__StringsKt() {
   }
}
