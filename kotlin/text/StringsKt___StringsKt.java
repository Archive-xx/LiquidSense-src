package kotlin.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.SinceKotlin;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CharIterator;
import kotlin.collections.CollectionsKt;
import kotlin.collections.Grouping;
import kotlin.collections.IndexedValue;
import kotlin.collections.IndexingIterable;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.collections.SlidingWindowKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
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
   d1 = {"\u0000Ü\u0001\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u001f\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0010\u000f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a!\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\n\u0010\u0006\u001a\u00020\u0001*\u00020\u0002\u001a!\u0010\u0006\u001a\u00020\u0001*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0010\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\b*\u00020\u0002\u001a\u0010\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\n*\u00020\u0002\u001aE\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\f\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e*\u00020\u00022\u001e\u0010\u000f\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\u00100\u0004H\u0086\b\u001a3\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u00020\u00050\f\"\u0004\b\u0000\u0010\r*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0086\b\u001aM\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\f\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0086\b\u001aN\u0010\u0014\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u0018\b\u0001\u0010\u0015*\u0012\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\u0006\b\u0000\u0012\u00020\u00050\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0086\b¢\u0006\u0002\u0010\u0018\u001ah\u0010\u0014\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e\"\u0018\b\u0002\u0010\u0015*\u0012\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\u0006\b\u0000\u0012\u0002H\u000e0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0086\b¢\u0006\u0002\u0010\u0019\u001a`\u0010\u001a\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e\"\u0018\b\u0002\u0010\u0015*\u0012\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\u0006\b\u0000\u0012\u0002H\u000e0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u001e\u0010\u000f\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\u00100\u0004H\u0086\b¢\u0006\u0002\u0010\u0018\u001a3\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\f\"\u0004\b\u0000\u0010\u000e*\u00020\u00022\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0087\b\u001aN\u0010\u001d\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\u000e\"\u0018\b\u0001\u0010\u0015*\u0012\u0012\u0006\b\u0000\u0012\u00020\u0005\u0012\u0006\b\u0000\u0012\u0002H\u000e0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0087\b¢\u0006\u0002\u0010\u0018\u001a\u001a\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001f*\u00020\u00022\u0006\u0010!\u001a\u00020\"H\u0007\u001a4\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H#0\u0004H\u0007\u001a\u001a\u0010$\u001a\b\u0012\u0004\u0012\u00020 0\n*\u00020\u00022\u0006\u0010!\u001a\u00020\"H\u0007\u001a4\u0010$\u001a\b\u0012\u0004\u0012\u0002H#0\n\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H#0\u0004H\u0007\u001a\r\u0010%\u001a\u00020\"*\u00020\u0002H\u0087\b\u001a!\u0010%\u001a\u00020\"*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0012\u0010&\u001a\u00020\u0002*\u00020\u00022\u0006\u0010'\u001a\u00020\"\u001a\u0012\u0010&\u001a\u00020 *\u00020 2\u0006\u0010'\u001a\u00020\"\u001a\u0012\u0010(\u001a\u00020\u0002*\u00020\u00022\u0006\u0010'\u001a\u00020\"\u001a\u0012\u0010(\u001a\u00020 *\u00020 2\u0006\u0010'\u001a\u00020\"\u001a!\u0010)\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010)\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010*\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010*\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a)\u0010+\u001a\u00020\u0005*\u00020\u00022\u0006\u0010,\u001a\u00020\"2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\u00050\u0004H\u0087\b\u001a\u001c\u0010.\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0006\u0010,\u001a\u00020\"H\u0087\b¢\u0006\u0002\u0010/\u001a!\u00100\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u00100\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a6\u00101\u001a\u00020\u0002*\u00020\u00022'\u0010\u0003\u001a#\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000102H\u0086\b\u001a6\u00101\u001a\u00020 *\u00020 2'\u0010\u0003\u001a#\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000102H\u0086\b\u001aQ\u00105\u001a\u0002H6\"\f\b\u0000\u00106*\u000607j\u0002`8*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62'\u0010\u0003\u001a#\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000102H\u0086\b¢\u0006\u0002\u00109\u001a!\u0010:\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010:\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a<\u0010;\u001a\u0002H6\"\f\b\u0000\u00106*\u000607j\u0002`8*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b¢\u0006\u0002\u0010<\u001a<\u0010=\u001a\u0002H6\"\f\b\u0000\u00106*\u000607j\u0002`8*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b¢\u0006\u0002\u0010<\u001a(\u0010>\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0087\b¢\u0006\u0002\u0010?\u001a(\u0010@\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0087\b¢\u0006\u0002\u0010?\u001a\n\u0010A\u001a\u00020\u0005*\u00020\u0002\u001a!\u0010A\u001a\u00020\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0011\u0010B\u001a\u0004\u0018\u00010\u0005*\u00020\u0002¢\u0006\u0002\u0010C\u001a(\u0010B\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b¢\u0006\u0002\u0010?\u001a3\u0010D\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u00022\u0018\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u0002H#0\b0\u0004H\u0086\b\u001aL\u0010E\u001a\u0002H6\"\u0004\b\u0000\u0010#\"\u0010\b\u0001\u00106*\n\u0012\u0006\b\u0000\u0012\u0002H#0F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62\u0018\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u0002H#0\b0\u0004H\u0086\b¢\u0006\u0002\u0010G\u001aI\u0010H\u001a\u0002H#\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010I\u001a\u0002H#2'\u0010J\u001a#\u0012\u0013\u0012\u0011H#¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#02H\u0086\b¢\u0006\u0002\u0010L\u001a^\u0010M\u001a\u0002H#\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010I\u001a\u0002H#2<\u0010J\u001a8\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0013\u0012\u0011H#¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0NH\u0086\b¢\u0006\u0002\u0010O\u001aI\u0010P\u001a\u0002H#\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010I\u001a\u0002H#2'\u0010J\u001a#\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u0011H#¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u0002H#02H\u0086\b¢\u0006\u0002\u0010L\u001a^\u0010Q\u001a\u0002H#\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010I\u001a\u0002H#2<\u0010J\u001a8\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u0011H#¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u0002H#0NH\u0086\b¢\u0006\u0002\u0010O\u001a!\u0010R\u001a\u00020S*\u00020\u00022\u0012\u0010T\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020S0\u0004H\u0086\b\u001a6\u0010U\u001a\u00020S*\u00020\u00022'\u0010T\u001a#\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020S02H\u0086\b\u001a)\u0010V\u001a\u00020\u0005*\u00020\u00022\u0006\u0010,\u001a\u00020\"2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\u00050\u0004H\u0087\b\u001a\u0019\u0010W\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0006\u0010,\u001a\u00020\"¢\u0006\u0002\u0010/\u001a9\u0010X\u001a\u0014\u0012\u0004\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u001f0\f\"\u0004\b\u0000\u0010\r*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0086\b\u001aS\u0010X\u001a\u0014\u0012\u0004\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\u001f0\f\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0086\b\u001aR\u0010Y\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u001c\b\u0001\u0010\u0015*\u0016\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050Z0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0086\b¢\u0006\u0002\u0010\u0018\u001al\u0010Y\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e\"\u001c\b\u0002\u0010\u0015*\u0016\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0Z0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0086\b¢\u0006\u0002\u0010\u0019\u001a5\u0010[\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\\\"\u0004\b\u0000\u0010\r*\u00020\u00022\u0014\b\u0004\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0087\b\u001a!\u0010]\u001a\u00020\"*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010^\u001a\u00020\"*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\n\u0010_\u001a\u00020\u0005*\u00020\u0002\u001a!\u0010_\u001a\u00020\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0011\u0010`\u001a\u0004\u0018\u00010\u0005*\u00020\u0002¢\u0006\u0002\u0010C\u001a(\u0010`\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b¢\u0006\u0002\u0010?\u001a-\u0010a\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u00022\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0\u0004H\u0086\b\u001aB\u0010b\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u00022'\u0010\u000f\u001a#\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#02H\u0086\b\u001aH\u0010c\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\b\b\u0000\u0010#*\u00020d*\u00020\u00022)\u0010\u000f\u001a%\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H#02H\u0086\b\u001aa\u0010e\u001a\u0002H6\"\b\b\u0000\u0010#*\u00020d\"\u0010\b\u0001\u00106*\n\u0012\u0006\b\u0000\u0012\u0002H#0F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62)\u0010\u000f\u001a%\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H#02H\u0086\b¢\u0006\u0002\u0010f\u001a[\u0010g\u001a\u0002H6\"\u0004\b\u0000\u0010#\"\u0010\b\u0001\u00106*\n\u0012\u0006\b\u0000\u0012\u0002H#0F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62'\u0010\u000f\u001a#\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#02H\u0086\b¢\u0006\u0002\u0010f\u001a3\u0010h\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\b\b\u0000\u0010#*\u00020d*\u00020\u00022\u0014\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H#0\u0004H\u0086\b\u001aL\u0010i\u001a\u0002H6\"\b\b\u0000\u0010#*\u00020d\"\u0010\b\u0001\u00106*\n\u0012\u0006\b\u0000\u0012\u0002H#0F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62\u0014\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H#0\u0004H\u0086\b¢\u0006\u0002\u0010G\u001aF\u0010j\u001a\u0002H6\"\u0004\b\u0000\u0010#\"\u0010\b\u0001\u00106*\n\u0012\u0006\b\u0000\u0012\u0002H#0F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0\u0004H\u0086\b¢\u0006\u0002\u0010G\u001a\u0011\u0010k\u001a\u0004\u0018\u00010\u0005*\u00020\u0002¢\u0006\u0002\u0010C\u001a8\u0010l\u001a\u0004\u0018\u00010\u0005\"\u000e\b\u0000\u0010#*\b\u0012\u0004\u0012\u0002H#0m*\u00020\u00022\u0012\u0010n\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0\u0004H\u0086\b¢\u0006\u0002\u0010?\u001a-\u0010o\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u001a\u0010p\u001a\u0016\u0012\u0006\b\u0000\u0012\u00020\u00050qj\n\u0012\u0006\b\u0000\u0012\u00020\u0005`r¢\u0006\u0002\u0010s\u001a\u0011\u0010t\u001a\u0004\u0018\u00010\u0005*\u00020\u0002¢\u0006\u0002\u0010C\u001a8\u0010u\u001a\u0004\u0018\u00010\u0005\"\u000e\b\u0000\u0010#*\b\u0012\u0004\u0012\u0002H#0m*\u00020\u00022\u0012\u0010n\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0\u0004H\u0086\b¢\u0006\u0002\u0010?\u001a-\u0010v\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u001a\u0010p\u001a\u0016\u0012\u0006\b\u0000\u0012\u00020\u00050qj\n\u0012\u0006\b\u0000\u0012\u00020\u0005`r¢\u0006\u0002\u0010s\u001a\n\u0010w\u001a\u00020\u0001*\u00020\u0002\u001a!\u0010w\u001a\u00020\u0001*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a0\u0010x\u001a\u0002Hy\"\b\b\u0000\u0010y*\u00020\u0002*\u0002Hy2\u0012\u0010T\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020S0\u0004H\u0087\b¢\u0006\u0002\u0010z\u001a-\u0010{\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0010*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a-\u0010{\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020 0\u0010*\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\r\u0010|\u001a\u00020\u0005*\u00020\u0002H\u0087\b\u001a\u0014\u0010|\u001a\u00020\u0005*\u00020\u00022\u0006\u0010|\u001a\u00020}H\u0007\u001a6\u0010~\u001a\u00020\u0005*\u00020\u00022'\u0010J\u001a#\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000502H\u0086\b\u001aK\u0010\u007f\u001a\u00020\u0005*\u00020\u00022<\u0010J\u001a8\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050NH\u0086\b\u001a7\u0010\u0080\u0001\u001a\u00020\u0005*\u00020\u00022'\u0010J\u001a#\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u000502H\u0086\b\u001aL\u0010\u0081\u0001\u001a\u00020\u0005*\u00020\u00022<\u0010J\u001a8\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u00050NH\u0086\b\u001a\u000b\u0010\u0082\u0001\u001a\u00020\u0002*\u00020\u0002\u001a\u000e\u0010\u0082\u0001\u001a\u00020 *\u00020 H\u0087\b\u001a\u000b\u0010\u0083\u0001\u001a\u00020\u0005*\u00020\u0002\u001a\"\u0010\u0083\u0001\u001a\u00020\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0012\u0010\u0084\u0001\u001a\u0004\u0018\u00010\u0005*\u00020\u0002¢\u0006\u0002\u0010C\u001a)\u0010\u0084\u0001\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b¢\u0006\u0002\u0010?\u001a\u001a\u0010\u0085\u0001\u001a\u00020\u0002*\u00020\u00022\r\u0010\u0086\u0001\u001a\b\u0012\u0004\u0012\u00020\"0\b\u001a\u0015\u0010\u0085\u0001\u001a\u00020\u0002*\u00020\u00022\b\u0010\u0086\u0001\u001a\u00030\u0087\u0001\u001a\u001d\u0010\u0085\u0001\u001a\u00020 *\u00020 2\r\u0010\u0086\u0001\u001a\b\u0012\u0004\u0012\u00020\"0\bH\u0087\b\u001a\u0015\u0010\u0085\u0001\u001a\u00020 *\u00020 2\b\u0010\u0086\u0001\u001a\u00030\u0087\u0001\u001a\"\u0010\u0088\u0001\u001a\u00020\"*\u00020\u00022\u0012\u0010n\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\"0\u0004H\u0086\b\u001a$\u0010\u0089\u0001\u001a\u00030\u008a\u0001*\u00020\u00022\u0013\u0010n\u001a\u000f\u0012\u0004\u0012\u00020\u0005\u0012\u0005\u0012\u00030\u008a\u00010\u0004H\u0086\b\u001a\u0013\u0010\u008b\u0001\u001a\u00020\u0002*\u00020\u00022\u0006\u0010'\u001a\u00020\"\u001a\u0013\u0010\u008b\u0001\u001a\u00020 *\u00020 2\u0006\u0010'\u001a\u00020\"\u001a\u0013\u0010\u008c\u0001\u001a\u00020\u0002*\u00020\u00022\u0006\u0010'\u001a\u00020\"\u001a\u0013\u0010\u008c\u0001\u001a\u00020 *\u00020 2\u0006\u0010'\u001a\u00020\"\u001a\"\u0010\u008d\u0001\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\"\u0010\u008d\u0001\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\"\u0010\u008e\u0001\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\"\u0010\u008e\u0001\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a+\u0010\u008f\u0001\u001a\u0002H6\"\u0010\b\u0000\u00106*\n\u0012\u0006\b\u0000\u0012\u00020\u00050F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H6¢\u0006\u0003\u0010\u0090\u0001\u001a\u001d\u0010\u0091\u0001\u001a\u0014\u0012\u0004\u0012\u00020\u00050\u0092\u0001j\t\u0012\u0004\u0012\u00020\u0005`\u0093\u0001*\u00020\u0002\u001a\u0011\u0010\u0094\u0001\u001a\b\u0012\u0004\u0012\u00020\u00050\u001f*\u00020\u0002\u001a\u0011\u0010\u0095\u0001\u001a\b\u0012\u0004\u0012\u00020\u00050Z*\u00020\u0002\u001a\u0012\u0010\u0096\u0001\u001a\t\u0012\u0004\u0012\u00020\u00050\u0097\u0001*\u00020\u0002\u001a1\u0010\u0098\u0001\u001a\b\u0012\u0004\u0012\u00020 0\u001f*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\t\b\u0002\u0010\u0099\u0001\u001a\u00020\"2\t\b\u0002\u0010\u009a\u0001\u001a\u00020\u0001H\u0007\u001aK\u0010\u0098\u0001\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\t\b\u0002\u0010\u0099\u0001\u001a\u00020\"2\t\b\u0002\u0010\u009a\u0001\u001a\u00020\u00012\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H#0\u0004H\u0007\u001a1\u0010\u009b\u0001\u001a\b\u0012\u0004\u0012\u00020 0\n*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\t\b\u0002\u0010\u0099\u0001\u001a\u00020\"2\t\b\u0002\u0010\u009a\u0001\u001a\u00020\u0001H\u0007\u001aK\u0010\u009b\u0001\u001a\b\u0012\u0004\u0012\u0002H#0\n\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\t\b\u0002\u0010\u0099\u0001\u001a\u00020\"2\t\b\u0002\u0010\u009a\u0001\u001a\u00020\u00012\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H#0\u0004H\u0007\u001a\u0018\u0010\u009c\u0001\u001a\u000f\u0012\u000b\u0012\t\u0012\u0004\u0012\u00020\u00050\u009d\u00010\b*\u00020\u0002\u001a)\u0010\u009e\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00100\u001f*\u00020\u00022\u0007\u0010\u009f\u0001\u001a\u00020\u0002H\u0086\u0004\u001a]\u0010\u009e\u0001\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u001f\"\u0004\b\u0000\u0010\u000e*\u00020\u00022\u0007\u0010\u009f\u0001\u001a\u00020\u000228\u0010\u000f\u001a4\u0012\u0014\u0012\u00120\u0005¢\u0006\r\b3\u0012\t\b4\u0012\u0005\b\b( \u0001\u0012\u0014\u0012\u00120\u0005¢\u0006\r\b3\u0012\t\b4\u0012\u0005\b\b(¡\u0001\u0012\u0004\u0012\u0002H\u000e02H\u0086\b\u001a\u001f\u0010¢\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00100\u001f*\u00020\u0002H\u0007\u001aT\u0010¢\u0001\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u000228\u0010\u000f\u001a4\u0012\u0014\u0012\u00120\u0005¢\u0006\r\b3\u0012\t\b4\u0012\u0005\b\b( \u0001\u0012\u0014\u0012\u00120\u0005¢\u0006\r\b3\u0012\t\b4\u0012\u0005\b\b(¡\u0001\u0012\u0004\u0012\u0002H#02H\u0087\b¨\u0006£\u0001"},
   d2 = {"all", "", "", "predicate", "Lkotlin/Function1;", "", "any", "asIterable", "", "asSequence", "Lkotlin/sequences/Sequence;", "associate", "", "K", "V", "transform", "Lkotlin/Pair;", "associateBy", "keySelector", "valueTransform", "associateByTo", "M", "", "destination", "(Ljava/lang/CharSequence;Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "(Ljava/lang/CharSequence;Ljava/util/Map;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "associateTo", "associateWith", "valueSelector", "associateWithTo", "chunked", "", "", "size", "", "R", "chunkedSequence", "count", "drop", "n", "dropLast", "dropLastWhile", "dropWhile", "elementAtOrElse", "index", "defaultValue", "elementAtOrNull", "(Ljava/lang/CharSequence;I)Ljava/lang/Character;", "filter", "filterIndexed", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "filterIndexedTo", "C", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "(Ljava/lang/CharSequence;Ljava/lang/Appendable;Lkotlin/jvm/functions/Function2;)Ljava/lang/Appendable;", "filterNot", "filterNotTo", "(Ljava/lang/CharSequence;Ljava/lang/Appendable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Appendable;", "filterTo", "find", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function1;)Ljava/lang/Character;", "findLast", "first", "firstOrNull", "(Ljava/lang/CharSequence;)Ljava/lang/Character;", "flatMap", "flatMapTo", "", "(Ljava/lang/CharSequence;Ljava/util/Collection;Lkotlin/jvm/functions/Function1;)Ljava/util/Collection;", "fold", "initial", "operation", "acc", "(Ljava/lang/CharSequence;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "foldIndexed", "Lkotlin/Function3;", "(Ljava/lang/CharSequence;Ljava/lang/Object;Lkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "foldRight", "foldRightIndexed", "forEach", "", "action", "forEachIndexed", "getOrElse", "getOrNull", "groupBy", "groupByTo", "", "groupingBy", "Lkotlin/collections/Grouping;", "indexOfFirst", "indexOfLast", "last", "lastOrNull", "map", "mapIndexed", "mapIndexedNotNull", "", "mapIndexedNotNullTo", "(Ljava/lang/CharSequence;Ljava/util/Collection;Lkotlin/jvm/functions/Function2;)Ljava/util/Collection;", "mapIndexedTo", "mapNotNull", "mapNotNullTo", "mapTo", "max", "maxBy", "", "selector", "maxWith", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/lang/CharSequence;Ljava/util/Comparator;)Ljava/lang/Character;", "min", "minBy", "minWith", "none", "onEach", "S", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function1;)Ljava/lang/CharSequence;", "partition", "random", "Lkotlin/random/Random;", "reduce", "reduceIndexed", "reduceRight", "reduceRightIndexed", "reversed", "single", "singleOrNull", "slice", "indices", "Lkotlin/ranges/IntRange;", "sumBy", "sumByDouble", "", "take", "takeLast", "takeLastWhile", "takeWhile", "toCollection", "(Ljava/lang/CharSequence;Ljava/util/Collection;)Ljava/util/Collection;", "toHashSet", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "toList", "toMutableList", "toSet", "", "windowed", "step", "partialWindows", "windowedSequence", "withIndex", "Lkotlin/collections/IndexedValue;", "zip", "other", "a", "b", "zipWithNext", "kotlin-stdlib"},
   xs = "kotlin/text/StringsKt"
)
class StringsKt___StringsKt extends StringsKt___StringsJvmKt {
   @InlineOnly
   private static final char elementAtOrElse(@NotNull CharSequence $this$elementAtOrElse, int index, Function1<? super Integer, Character> defaultValue) {
      int $i$f$elementAtOrElse = 0;
      return index >= 0 && index <= StringsKt.getLastIndex($this$elementAtOrElse) ? $this$elementAtOrElse.charAt(index) : (Character)defaultValue.invoke(index);
   }

   @InlineOnly
   private static final Character elementAtOrNull(@NotNull CharSequence $this$elementAtOrNull, int index) {
      int $i$f$elementAtOrNull = 0;
      return StringsKt.getOrNull($this$elementAtOrNull, index);
   }

   @InlineOnly
   private static final Character find(@NotNull CharSequence $this$find, Function1<? super Character, Boolean> predicate) {
      int $i$f$find = 0;
      int $i$f$firstOrNull = false;
      CharSequence var5 = $this$find;
      int var6 = 0;

      Character var10000;
      while(true) {
         if (var6 >= var5.length()) {
            var10000 = null;
            break;
         }

         char element$iv = var5.charAt(var6);
         if ((Boolean)predicate.invoke(element$iv)) {
            var10000 = element$iv;
            break;
         }

         ++var6;
      }

      return var10000;
   }

   @InlineOnly
   private static final Character findLast(@NotNull CharSequence $this$findLast, Function1<? super Character, Boolean> predicate) {
      int $i$f$findLast = 0;
      CharSequence $this$lastOrNull$iv = $this$findLast;
      int $i$f$lastOrNull = false;
      int index$iv = $this$findLast.length();
      --index$iv;
      boolean var6 = false;

      Character var10000;
      while(true) {
         if (index$iv < 0) {
            var10000 = null;
            break;
         }

         char element$iv = $this$lastOrNull$iv.charAt(index$iv);
         if ((Boolean)predicate.invoke(element$iv)) {
            var10000 = element$iv;
            break;
         }

         --index$iv;
      }

      return var10000;
   }

   public static final char first(@NotNull CharSequence $this$first) {
      Intrinsics.checkParameterIsNotNull($this$first, "$this$first");
      boolean var2 = false;
      if ($this$first.length() == 0) {
         throw (Throwable)(new NoSuchElementException("Char sequence is empty."));
      } else {
         return $this$first.charAt(0);
      }
   }

   public static final char first(@NotNull CharSequence $this$first, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$first = 0;
      Intrinsics.checkParameterIsNotNull($this$first, "$this$first");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      CharSequence var5 = $this$first;

      for(int var4 = 0; var4 < var5.length(); ++var4) {
         char element = var5.charAt(var4);
         if ((Boolean)predicate.invoke(element)) {
            return element;
         }
      }

      throw (Throwable)(new NoSuchElementException("Char sequence contains no character matching the predicate."));
   }

   @Nullable
   public static final Character firstOrNull(@NotNull CharSequence $this$firstOrNull) {
      Intrinsics.checkParameterIsNotNull($this$firstOrNull, "$this$firstOrNull");
      boolean var2 = false;
      return $this$firstOrNull.length() == 0 ? null : $this$firstOrNull.charAt(0);
   }

   @Nullable
   public static final Character firstOrNull(@NotNull CharSequence $this$firstOrNull, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$firstOrNull = 0;
      Intrinsics.checkParameterIsNotNull($this$firstOrNull, "$this$firstOrNull");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      CharSequence var5 = $this$firstOrNull;

      for(int var4 = 0; var4 < var5.length(); ++var4) {
         char element = var5.charAt(var4);
         if ((Boolean)predicate.invoke(element)) {
            return element;
         }
      }

      return null;
   }

   @InlineOnly
   private static final char getOrElse(@NotNull CharSequence $this$getOrElse, int index, Function1<? super Integer, Character> defaultValue) {
      int $i$f$getOrElse = 0;
      return index >= 0 && index <= StringsKt.getLastIndex($this$getOrElse) ? $this$getOrElse.charAt(index) : (Character)defaultValue.invoke(index);
   }

   @Nullable
   public static final Character getOrNull(@NotNull CharSequence $this$getOrNull, int index) {
      Intrinsics.checkParameterIsNotNull($this$getOrNull, "$this$getOrNull");
      return index >= 0 && index <= StringsKt.getLastIndex($this$getOrNull) ? $this$getOrNull.charAt(index) : null;
   }

   public static final int indexOfFirst(@NotNull CharSequence $this$indexOfFirst, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$indexOfFirst = 0;
      Intrinsics.checkParameterIsNotNull($this$indexOfFirst, "$this$indexOfFirst");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      int index = 0;

      for(int var4 = $this$indexOfFirst.length(); index < var4; ++index) {
         if ((Boolean)predicate.invoke($this$indexOfFirst.charAt(index))) {
            return index;
         }
      }

      return -1;
   }

   public static final int indexOfLast(@NotNull CharSequence $this$indexOfLast, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$indexOfLast = 0;
      Intrinsics.checkParameterIsNotNull($this$indexOfLast, "$this$indexOfLast");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      int index = $this$indexOfLast.length();
      --index;

      for(boolean var4 = false; index >= 0; --index) {
         if ((Boolean)predicate.invoke($this$indexOfLast.charAt(index))) {
            return index;
         }
      }

      return -1;
   }

   public static final char last(@NotNull CharSequence $this$last) {
      Intrinsics.checkParameterIsNotNull($this$last, "$this$last");
      boolean var2 = false;
      if ($this$last.length() == 0) {
         throw (Throwable)(new NoSuchElementException("Char sequence is empty."));
      } else {
         return $this$last.charAt(StringsKt.getLastIndex($this$last));
      }
   }

   public static final char last(@NotNull CharSequence $this$last, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$last = 0;
      Intrinsics.checkParameterIsNotNull($this$last, "$this$last");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      int index = $this$last.length();
      --index;

      for(boolean var4 = false; index >= 0; --index) {
         char element = $this$last.charAt(index);
         if ((Boolean)predicate.invoke(element)) {
            return element;
         }
      }

      throw (Throwable)(new NoSuchElementException("Char sequence contains no character matching the predicate."));
   }

   @Nullable
   public static final Character lastOrNull(@NotNull CharSequence $this$lastOrNull) {
      Intrinsics.checkParameterIsNotNull($this$lastOrNull, "$this$lastOrNull");
      boolean var2 = false;
      return $this$lastOrNull.length() == 0 ? null : $this$lastOrNull.charAt($this$lastOrNull.length() - 1);
   }

   @Nullable
   public static final Character lastOrNull(@NotNull CharSequence $this$lastOrNull, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$lastOrNull = 0;
      Intrinsics.checkParameterIsNotNull($this$lastOrNull, "$this$lastOrNull");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      int index = $this$lastOrNull.length();
      --index;

      for(boolean var4 = false; index >= 0; --index) {
         char element = $this$lastOrNull.charAt(index);
         if ((Boolean)predicate.invoke(element)) {
            return element;
         }
      }

      return null;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final char random(@NotNull CharSequence $this$random) {
      int $i$f$random = 0;
      return StringsKt.random($this$random, (Random)Random.Default);
   }

   @SinceKotlin(
      version = "1.3"
   )
   public static final char random(@NotNull CharSequence $this$random, @NotNull Random random) {
      Intrinsics.checkParameterIsNotNull($this$random, "$this$random");
      Intrinsics.checkParameterIsNotNull(random, "random");
      boolean var3 = false;
      if ($this$random.length() == 0) {
         throw (Throwable)(new NoSuchElementException("Char sequence is empty."));
      } else {
         return $this$random.charAt(random.nextInt($this$random.length()));
      }
   }

   public static final char single(@NotNull CharSequence $this$single) {
      Intrinsics.checkParameterIsNotNull($this$single, "$this$single");
      switch($this$single.length()) {
      case 0:
         throw (Throwable)(new NoSuchElementException("Char sequence is empty."));
      case 1:
         return $this$single.charAt(0);
      default:
         throw (Throwable)(new IllegalArgumentException("Char sequence has more than one element."));
      }
   }

   public static final char single(@NotNull CharSequence $this$single, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$single = 0;
      Intrinsics.checkParameterIsNotNull($this$single, "$this$single");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      Character single = (Character)null;
      boolean found = false;
      CharSequence var7 = $this$single;

      for(int var6 = 0; var6 < var7.length(); ++var6) {
         char element = var7.charAt(var6);
         if ((Boolean)predicate.invoke(element)) {
            if (found) {
               throw (Throwable)(new IllegalArgumentException("Char sequence contains more than one matching element."));
            }

            single = element;
            found = true;
         }
      }

      if (!found) {
         throw (Throwable)(new NoSuchElementException("Char sequence contains no character matching the predicate."));
      } else if (single == null) {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.Char");
      } else {
         return single;
      }
   }

   @Nullable
   public static final Character singleOrNull(@NotNull CharSequence $this$singleOrNull) {
      Intrinsics.checkParameterIsNotNull($this$singleOrNull, "$this$singleOrNull");
      return $this$singleOrNull.length() == 1 ? $this$singleOrNull.charAt(0) : null;
   }

   @Nullable
   public static final Character singleOrNull(@NotNull CharSequence $this$singleOrNull, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$singleOrNull = 0;
      Intrinsics.checkParameterIsNotNull($this$singleOrNull, "$this$singleOrNull");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      Character single = (Character)null;
      boolean found = false;
      CharSequence var7 = $this$singleOrNull;

      for(int var6 = 0; var6 < var7.length(); ++var6) {
         char element = var7.charAt(var6);
         if ((Boolean)predicate.invoke(element)) {
            if (found) {
               return null;
            }

            single = element;
            found = true;
         }
      }

      if (!found) {
         return null;
      } else {
         return single;
      }
   }

   @NotNull
   public static final CharSequence drop(@NotNull CharSequence $this$drop, int n) {
      Intrinsics.checkParameterIsNotNull($this$drop, "$this$drop");
      boolean var2 = n >= 0;
      boolean var3 = false;
      boolean var4 = false;
      if (!var2) {
         int var5 = false;
         String var6 = "Requested character count " + n + " is less than zero.";
         throw (Throwable)(new IllegalArgumentException(var6.toString()));
      } else {
         return $this$drop.subSequence(RangesKt.coerceAtMost(n, $this$drop.length()), $this$drop.length());
      }
   }

   @NotNull
   public static final String drop(@NotNull String $this$drop, int n) {
      Intrinsics.checkParameterIsNotNull($this$drop, "$this$drop");
      boolean var2 = n >= 0;
      boolean var3 = false;
      boolean var4 = false;
      if (!var2) {
         int var5 = false;
         String var7 = "Requested character count " + n + " is less than zero.";
         throw (Throwable)(new IllegalArgumentException(var7.toString()));
      } else {
         int var6 = RangesKt.coerceAtMost(n, $this$drop.length());
         var4 = false;
         String var10000 = $this$drop.substring(var6);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).substring(startIndex)");
         return var10000;
      }
   }

   @NotNull
   public static final CharSequence dropLast(@NotNull CharSequence $this$dropLast, int n) {
      Intrinsics.checkParameterIsNotNull($this$dropLast, "$this$dropLast");
      boolean var2 = n >= 0;
      boolean var3 = false;
      boolean var4 = false;
      if (!var2) {
         int var5 = false;
         String var6 = "Requested character count " + n + " is less than zero.";
         throw (Throwable)(new IllegalArgumentException(var6.toString()));
      } else {
         return StringsKt.take($this$dropLast, RangesKt.coerceAtLeast($this$dropLast.length() - n, 0));
      }
   }

   @NotNull
   public static final String dropLast(@NotNull String $this$dropLast, int n) {
      Intrinsics.checkParameterIsNotNull($this$dropLast, "$this$dropLast");
      boolean var2 = n >= 0;
      boolean var3 = false;
      boolean var4 = false;
      if (!var2) {
         int var5 = false;
         String var6 = "Requested character count " + n + " is less than zero.";
         throw (Throwable)(new IllegalArgumentException(var6.toString()));
      } else {
         return StringsKt.take($this$dropLast, RangesKt.coerceAtLeast($this$dropLast.length() - n, 0));
      }
   }

   @NotNull
   public static final CharSequence dropLastWhile(@NotNull CharSequence $this$dropLastWhile, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$dropLastWhile = 0;
      Intrinsics.checkParameterIsNotNull($this$dropLastWhile, "$this$dropLastWhile");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      int index = StringsKt.getLastIndex($this$dropLastWhile);

      for(boolean var4 = false; index >= 0; --index) {
         if (!(Boolean)predicate.invoke($this$dropLastWhile.charAt(index))) {
            return $this$dropLastWhile.subSequence(0, index + 1);
         }
      }

      return (CharSequence)"";
   }

   @NotNull
   public static final String dropLastWhile(@NotNull String $this$dropLastWhile, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$dropLastWhile = 0;
      Intrinsics.checkParameterIsNotNull($this$dropLastWhile, "$this$dropLastWhile");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      int index = StringsKt.getLastIndex((CharSequence)$this$dropLastWhile);

      for(boolean var4 = false; index >= 0; --index) {
         if (!(Boolean)predicate.invoke($this$dropLastWhile.charAt(index))) {
            byte var6 = 0;
            int var7 = index + 1;
            boolean var8 = false;
            String var10000 = $this$dropLastWhile.substring(var6, var7);
            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            return var10000;
         }
      }

      return "";
   }

   @NotNull
   public static final CharSequence dropWhile(@NotNull CharSequence $this$dropWhile, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$dropWhile = 0;
      Intrinsics.checkParameterIsNotNull($this$dropWhile, "$this$dropWhile");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      int index = 0;

      for(int var4 = $this$dropWhile.length(); index < var4; ++index) {
         if (!(Boolean)predicate.invoke($this$dropWhile.charAt(index))) {
            return $this$dropWhile.subSequence(index, $this$dropWhile.length());
         }
      }

      return (CharSequence)"";
   }

   @NotNull
   public static final String dropWhile(@NotNull String $this$dropWhile, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$dropWhile = 0;
      Intrinsics.checkParameterIsNotNull($this$dropWhile, "$this$dropWhile");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      int index = 0;

      for(int var4 = ((CharSequence)$this$dropWhile).length(); index < var4; ++index) {
         if (!(Boolean)predicate.invoke($this$dropWhile.charAt(index))) {
            boolean var6 = false;
            String var10000 = $this$dropWhile.substring(index);
            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).substring(startIndex)");
            return var10000;
         }
      }

      return "";
   }

   @NotNull
   public static final CharSequence filter(@NotNull CharSequence $this$filter, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$filter = 0;
      Intrinsics.checkParameterIsNotNull($this$filter, "$this$filter");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      CharSequence $this$filterTo$iv = $this$filter;
      Appendable destination$iv = (Appendable)(new StringBuilder());
      int $i$f$filterTo = false;
      int index$iv = 0;

      for(int var7 = $this$filter.length(); index$iv < var7; ++index$iv) {
         char element$iv = $this$filterTo$iv.charAt(index$iv);
         if ((Boolean)predicate.invoke(element$iv)) {
            destination$iv.append(element$iv);
         }
      }

      return (CharSequence)destination$iv;
   }

   @NotNull
   public static final String filter(@NotNull String $this$filter, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$filter = 0;
      Intrinsics.checkParameterIsNotNull($this$filter, "$this$filter");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      CharSequence $this$filterTo$iv = (CharSequence)$this$filter;
      Appendable destination$iv = (Appendable)(new StringBuilder());
      int $i$f$filterTo = false;
      int index$iv = 0;

      for(int var7 = $this$filterTo$iv.length(); index$iv < var7; ++index$iv) {
         char element$iv = $this$filterTo$iv.charAt(index$iv);
         if ((Boolean)predicate.invoke(element$iv)) {
            destination$iv.append(element$iv);
         }
      }

      String var10000 = ((StringBuilder)destination$iv).toString();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "filterTo(StringBuilder(), predicate).toString()");
      return var10000;
   }

   @NotNull
   public static final CharSequence filterIndexed(@NotNull CharSequence $this$filterIndexed, @NotNull Function2<? super Integer, ? super Character, Boolean> predicate) {
      int $i$f$filterIndexed = 0;
      Intrinsics.checkParameterIsNotNull($this$filterIndexed, "$this$filterIndexed");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      Appendable destination$iv = (Appendable)(new StringBuilder());
      int $i$f$filterIndexedTo = false;
      int $i$f$forEachIndexed = false;
      int index$iv$iv = 0;
      CharSequence var9 = $this$filterIndexed;

      for(int var10 = 0; var10 < var9.length(); ++var10) {
         char item$iv$iv = var9.charAt(var10);
         int index$iv = index$iv$iv++;
         int var14 = false;
         if ((Boolean)predicate.invoke(index$iv, item$iv$iv)) {
            destination$iv.append(item$iv$iv);
         }
      }

      return (CharSequence)destination$iv;
   }

   @NotNull
   public static final String filterIndexed(@NotNull String $this$filterIndexed, @NotNull Function2<? super Integer, ? super Character, Boolean> predicate) {
      int $i$f$filterIndexed = 0;
      Intrinsics.checkParameterIsNotNull($this$filterIndexed, "$this$filterIndexed");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      CharSequence $this$filterIndexedTo$iv = (CharSequence)$this$filterIndexed;
      Appendable destination$iv = (Appendable)(new StringBuilder());
      int $i$f$filterIndexedTo = false;
      int $i$f$forEachIndexed = false;
      int index$iv$iv = 0;
      CharSequence var9 = $this$filterIndexedTo$iv;

      for(int var10 = 0; var10 < var9.length(); ++var10) {
         char item$iv$iv = var9.charAt(var10);
         int index$iv = index$iv$iv++;
         int var14 = false;
         if ((Boolean)predicate.invoke(index$iv, item$iv$iv)) {
            destination$iv.append(item$iv$iv);
         }
      }

      String var15 = ((StringBuilder)destination$iv).toString();
      Intrinsics.checkExpressionValueIsNotNull(var15, "filterIndexedTo(StringBu…(), predicate).toString()");
      return var15;
   }

   @NotNull
   public static final <C extends Appendable> C filterIndexedTo(@NotNull CharSequence $this$filterIndexedTo, @NotNull C destination, @NotNull Function2<? super Integer, ? super Character, Boolean> predicate) {
      int $i$f$filterIndexedTo = 0;
      Intrinsics.checkParameterIsNotNull($this$filterIndexedTo, "$this$filterIndexedTo");
      Intrinsics.checkParameterIsNotNull(destination, "destination");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      int $i$f$forEachIndexed = false;
      int index$iv = 0;
      CharSequence var7 = $this$filterIndexedTo;

      for(int var8 = 0; var8 < var7.length(); ++var8) {
         char item$iv = var7.charAt(var8);
         int index = index$iv++;
         int var12 = false;
         if ((Boolean)predicate.invoke(index, item$iv)) {
            destination.append(item$iv);
         }
      }

      return destination;
   }

   @NotNull
   public static final CharSequence filterNot(@NotNull CharSequence $this$filterNot, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$filterNot = 0;
      Intrinsics.checkParameterIsNotNull($this$filterNot, "$this$filterNot");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      Appendable destination$iv = (Appendable)(new StringBuilder());
      int $i$f$filterNotTo = false;
      CharSequence var6 = $this$filterNot;

      for(int var7 = 0; var7 < var6.length(); ++var7) {
         char element$iv = var6.charAt(var7);
         if (!(Boolean)predicate.invoke(element$iv)) {
            destination$iv.append(element$iv);
         }
      }

      return (CharSequence)destination$iv;
   }

   @NotNull
   public static final String filterNot(@NotNull String $this$filterNot, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$filterNot = 0;
      Intrinsics.checkParameterIsNotNull($this$filterNot, "$this$filterNot");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      CharSequence $this$filterNotTo$iv = (CharSequence)$this$filterNot;
      Appendable destination$iv = (Appendable)(new StringBuilder());
      int $i$f$filterNotTo = false;
      CharSequence var6 = $this$filterNotTo$iv;

      for(int var7 = 0; var7 < var6.length(); ++var7) {
         char element$iv = var6.charAt(var7);
         if (!(Boolean)predicate.invoke(element$iv)) {
            destination$iv.append(element$iv);
         }
      }

      String var10000 = ((StringBuilder)destination$iv).toString();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "filterNotTo(StringBuilder(), predicate).toString()");
      return var10000;
   }

   @NotNull
   public static final <C extends Appendable> C filterNotTo(@NotNull CharSequence $this$filterNotTo, @NotNull C destination, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$filterNotTo = 0;
      Intrinsics.checkParameterIsNotNull($this$filterNotTo, "$this$filterNotTo");
      Intrinsics.checkParameterIsNotNull(destination, "destination");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      CharSequence var6 = $this$filterNotTo;

      for(int var5 = 0; var5 < var6.length(); ++var5) {
         char element = var6.charAt(var5);
         if (!(Boolean)predicate.invoke(element)) {
            destination.append(element);
         }
      }

      return destination;
   }

   @NotNull
   public static final <C extends Appendable> C filterTo(@NotNull CharSequence $this$filterTo, @NotNull C destination, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$filterTo = 0;
      Intrinsics.checkParameterIsNotNull($this$filterTo, "$this$filterTo");
      Intrinsics.checkParameterIsNotNull(destination, "destination");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      int index = 0;

      for(int var5 = $this$filterTo.length(); index < var5; ++index) {
         char element = $this$filterTo.charAt(index);
         if ((Boolean)predicate.invoke(element)) {
            destination.append(element);
         }
      }

      return destination;
   }

   @NotNull
   public static final CharSequence slice(@NotNull CharSequence $this$slice, @NotNull IntRange indices) {
      Intrinsics.checkParameterIsNotNull($this$slice, "$this$slice");
      Intrinsics.checkParameterIsNotNull(indices, "indices");
      return indices.isEmpty() ? (CharSequence)"" : StringsKt.subSequence($this$slice, indices);
   }

   @NotNull
   public static final String slice(@NotNull String $this$slice, @NotNull IntRange indices) {
      Intrinsics.checkParameterIsNotNull($this$slice, "$this$slice");
      Intrinsics.checkParameterIsNotNull(indices, "indices");
      return indices.isEmpty() ? "" : StringsKt.substring($this$slice, indices);
   }

   @NotNull
   public static final CharSequence slice(@NotNull CharSequence $this$slice, @NotNull Iterable<Integer> indices) {
      Intrinsics.checkParameterIsNotNull($this$slice, "$this$slice");
      Intrinsics.checkParameterIsNotNull(indices, "indices");
      int size = CollectionsKt.collectionSizeOrDefault(indices, 10);
      if (size == 0) {
         return (CharSequence)"";
      } else {
         StringBuilder result = new StringBuilder(size);
         Iterator var5 = indices.iterator();

         while(var5.hasNext()) {
            int i = ((Number)var5.next()).intValue();
            result.append($this$slice.charAt(i));
         }

         return (CharSequence)result;
      }
   }

   @InlineOnly
   private static final String slice(@NotNull String $this$slice, Iterable<Integer> indices) {
      return StringsKt.slice((CharSequence)$this$slice, indices).toString();
   }

   @NotNull
   public static final CharSequence take(@NotNull CharSequence $this$take, int n) {
      Intrinsics.checkParameterIsNotNull($this$take, "$this$take");
      boolean var2 = n >= 0;
      boolean var3 = false;
      boolean var4 = false;
      if (!var2) {
         int var5 = false;
         String var6 = "Requested character count " + n + " is less than zero.";
         throw (Throwable)(new IllegalArgumentException(var6.toString()));
      } else {
         return $this$take.subSequence(0, RangesKt.coerceAtMost(n, $this$take.length()));
      }
   }

   @NotNull
   public static final String take(@NotNull String $this$take, int n) {
      Intrinsics.checkParameterIsNotNull($this$take, "$this$take");
      boolean var2 = n >= 0;
      boolean var3 = false;
      boolean var4 = false;
      boolean var5;
      if (!var2) {
         var5 = false;
         String var8 = "Requested character count " + n + " is less than zero.";
         throw (Throwable)(new IllegalArgumentException(var8.toString()));
      } else {
         byte var6 = 0;
         int var7 = RangesKt.coerceAtMost(n, $this$take.length());
         var5 = false;
         String var10000 = $this$take.substring(var6, var7);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
         return var10000;
      }
   }

   @NotNull
   public static final CharSequence takeLast(@NotNull CharSequence $this$takeLast, int n) {
      Intrinsics.checkParameterIsNotNull($this$takeLast, "$this$takeLast");
      boolean var2 = n >= 0;
      boolean var3 = false;
      boolean var4 = false;
      if (!var2) {
         int var5 = false;
         String var7 = "Requested character count " + n + " is less than zero.";
         throw (Throwable)(new IllegalArgumentException(var7.toString()));
      } else {
         int length = $this$takeLast.length();
         return $this$takeLast.subSequence(length - RangesKt.coerceAtMost(n, length), length);
      }
   }

   @NotNull
   public static final String takeLast(@NotNull String $this$takeLast, int n) {
      Intrinsics.checkParameterIsNotNull($this$takeLast, "$this$takeLast");
      boolean var2 = n >= 0;
      boolean var3 = false;
      boolean var4 = false;
      boolean var5;
      if (!var2) {
         var5 = false;
         String var8 = "Requested character count " + n + " is less than zero.";
         throw (Throwable)(new IllegalArgumentException(var8.toString()));
      } else {
         int length = $this$takeLast.length();
         int var7 = length - RangesKt.coerceAtMost(n, length);
         var5 = false;
         String var10000 = $this$takeLast.substring(var7);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).substring(startIndex)");
         return var10000;
      }
   }

   @NotNull
   public static final CharSequence takeLastWhile(@NotNull CharSequence $this$takeLastWhile, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$takeLastWhile = 0;
      Intrinsics.checkParameterIsNotNull($this$takeLastWhile, "$this$takeLastWhile");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      int index = StringsKt.getLastIndex($this$takeLastWhile);

      for(boolean var4 = false; index >= 0; --index) {
         if (!(Boolean)predicate.invoke($this$takeLastWhile.charAt(index))) {
            return $this$takeLastWhile.subSequence(index + 1, $this$takeLastWhile.length());
         }
      }

      return $this$takeLastWhile.subSequence(0, $this$takeLastWhile.length());
   }

   @NotNull
   public static final String takeLastWhile(@NotNull String $this$takeLastWhile, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$takeLastWhile = 0;
      Intrinsics.checkParameterIsNotNull($this$takeLastWhile, "$this$takeLastWhile");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      int index = StringsKt.getLastIndex((CharSequence)$this$takeLastWhile);

      for(boolean var4 = false; index >= 0; --index) {
         if (!(Boolean)predicate.invoke($this$takeLastWhile.charAt(index))) {
            int var6 = index + 1;
            boolean var7 = false;
            String var10000 = $this$takeLastWhile.substring(var6);
            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).substring(startIndex)");
            return var10000;
         }
      }

      return $this$takeLastWhile;
   }

   @NotNull
   public static final CharSequence takeWhile(@NotNull CharSequence $this$takeWhile, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$takeWhile = 0;
      Intrinsics.checkParameterIsNotNull($this$takeWhile, "$this$takeWhile");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      int index = 0;

      for(int var4 = $this$takeWhile.length(); index < var4; ++index) {
         if (!(Boolean)predicate.invoke($this$takeWhile.charAt(index))) {
            return $this$takeWhile.subSequence(0, index);
         }
      }

      return $this$takeWhile.subSequence(0, $this$takeWhile.length());
   }

   @NotNull
   public static final String takeWhile(@NotNull String $this$takeWhile, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$takeWhile = 0;
      Intrinsics.checkParameterIsNotNull($this$takeWhile, "$this$takeWhile");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      int index = 0;

      for(int var4 = $this$takeWhile.length(); index < var4; ++index) {
         if (!(Boolean)predicate.invoke($this$takeWhile.charAt(index))) {
            byte var6 = 0;
            boolean var7 = false;
            String var10000 = $this$takeWhile.substring(var6, index);
            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            return var10000;
         }
      }

      return $this$takeWhile;
   }

   @NotNull
   public static final CharSequence reversed(@NotNull CharSequence $this$reversed) {
      Intrinsics.checkParameterIsNotNull($this$reversed, "$this$reversed");
      StringBuilder var10000 = (new StringBuilder($this$reversed)).reverse();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "StringBuilder(this).reverse()");
      return (CharSequence)var10000;
   }

   @InlineOnly
   private static final String reversed(@NotNull String $this$reversed) {
      return StringsKt.reversed((CharSequence)$this$reversed).toString();
   }

   @NotNull
   public static final <K, V> Map<K, V> associate(@NotNull CharSequence $this$associate, @NotNull Function1<? super Character, ? extends Pair<? extends K, ? extends V>> transform) {
      int $i$f$associate = 0;
      Intrinsics.checkParameterIsNotNull($this$associate, "$this$associate");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      int capacity = RangesKt.coerceAtLeast(MapsKt.mapCapacity($this$associate.length()), 16);
      Map destination$iv = (Map)(new LinkedHashMap(capacity));
      int $i$f$associateTo = false;
      CharSequence var7 = $this$associate;

      for(int var8 = 0; var8 < var7.length(); ++var8) {
         char element$iv = var7.charAt(var8);
         Pair var11 = (Pair)transform.invoke(element$iv);
         boolean var12 = false;
         destination$iv.put(var11.getFirst(), var11.getSecond());
      }

      return destination$iv;
   }

   @NotNull
   public static final <K> Map<K, Character> associateBy(@NotNull CharSequence $this$associateBy, @NotNull Function1<? super Character, ? extends K> keySelector) {
      int $i$f$associateBy = 0;
      Intrinsics.checkParameterIsNotNull($this$associateBy, "$this$associateBy");
      Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
      int capacity = RangesKt.coerceAtLeast(MapsKt.mapCapacity($this$associateBy.length()), 16);
      Map destination$iv = (Map)(new LinkedHashMap(capacity));
      int $i$f$associateByTo = false;
      CharSequence var7 = $this$associateBy;

      for(int var8 = 0; var8 < var7.length(); ++var8) {
         char element$iv = var7.charAt(var8);
         destination$iv.put(keySelector.invoke(element$iv), element$iv);
      }

      return destination$iv;
   }

   @NotNull
   public static final <K, V> Map<K, V> associateBy(@NotNull CharSequence $this$associateBy, @NotNull Function1<? super Character, ? extends K> keySelector, @NotNull Function1<? super Character, ? extends V> valueTransform) {
      int $i$f$associateBy = 0;
      Intrinsics.checkParameterIsNotNull($this$associateBy, "$this$associateBy");
      Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
      Intrinsics.checkParameterIsNotNull(valueTransform, "valueTransform");
      int capacity = RangesKt.coerceAtLeast(MapsKt.mapCapacity($this$associateBy.length()), 16);
      Map destination$iv = (Map)(new LinkedHashMap(capacity));
      int $i$f$associateByTo = false;
      CharSequence var8 = $this$associateBy;

      for(int var9 = 0; var9 < var8.length(); ++var9) {
         char element$iv = var8.charAt(var9);
         destination$iv.put(keySelector.invoke(element$iv), valueTransform.invoke(element$iv));
      }

      return destination$iv;
   }

   @NotNull
   public static final <K, M extends Map<? super K, ? super Character>> M associateByTo(@NotNull CharSequence $this$associateByTo, @NotNull M destination, @NotNull Function1<? super Character, ? extends K> keySelector) {
      int $i$f$associateByTo = 0;
      Intrinsics.checkParameterIsNotNull($this$associateByTo, "$this$associateByTo");
      Intrinsics.checkParameterIsNotNull(destination, "destination");
      Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
      CharSequence var6 = $this$associateByTo;

      for(int var5 = 0; var5 < var6.length(); ++var5) {
         char element = var6.charAt(var5);
         destination.put(keySelector.invoke(element), element);
      }

      return destination;
   }

   @NotNull
   public static final <K, V, M extends Map<? super K, ? super V>> M associateByTo(@NotNull CharSequence $this$associateByTo, @NotNull M destination, @NotNull Function1<? super Character, ? extends K> keySelector, @NotNull Function1<? super Character, ? extends V> valueTransform) {
      int $i$f$associateByTo = 0;
      Intrinsics.checkParameterIsNotNull($this$associateByTo, "$this$associateByTo");
      Intrinsics.checkParameterIsNotNull(destination, "destination");
      Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
      Intrinsics.checkParameterIsNotNull(valueTransform, "valueTransform");
      CharSequence var7 = $this$associateByTo;

      for(int var6 = 0; var6 < var7.length(); ++var6) {
         char element = var7.charAt(var6);
         destination.put(keySelector.invoke(element), valueTransform.invoke(element));
      }

      return destination;
   }

   @NotNull
   public static final <K, V, M extends Map<? super K, ? super V>> M associateTo(@NotNull CharSequence $this$associateTo, @NotNull M destination, @NotNull Function1<? super Character, ? extends Pair<? extends K, ? extends V>> transform) {
      int $i$f$associateTo = 0;
      Intrinsics.checkParameterIsNotNull($this$associateTo, "$this$associateTo");
      Intrinsics.checkParameterIsNotNull(destination, "destination");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      CharSequence var6 = $this$associateTo;

      for(int var5 = 0; var5 < var6.length(); ++var5) {
         char element = var6.charAt(var5);
         Pair var8 = (Pair)transform.invoke(element);
         boolean var9 = false;
         destination.put(var8.getFirst(), var8.getSecond());
      }

      return destination;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final <V> Map<Character, V> associateWith(@NotNull CharSequence $this$associateWith, @NotNull Function1<? super Character, ? extends V> valueSelector) {
      int $i$f$associateWith = 0;
      Intrinsics.checkParameterIsNotNull($this$associateWith, "$this$associateWith");
      Intrinsics.checkParameterIsNotNull(valueSelector, "valueSelector");
      LinkedHashMap result = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity($this$associateWith.length()), 16));
      int $i$f$associateWithTo = false;
      CharSequence var6 = $this$associateWith;

      for(int var7 = 0; var7 < var6.length(); ++var7) {
         char element$iv = var6.charAt(var7);
         ((Map)result).put(element$iv, valueSelector.invoke(element$iv));
      }

      return (Map)result;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final <V, M extends Map<? super Character, ? super V>> M associateWithTo(@NotNull CharSequence $this$associateWithTo, @NotNull M destination, @NotNull Function1<? super Character, ? extends V> valueSelector) {
      int $i$f$associateWithTo = 0;
      Intrinsics.checkParameterIsNotNull($this$associateWithTo, "$this$associateWithTo");
      Intrinsics.checkParameterIsNotNull(destination, "destination");
      Intrinsics.checkParameterIsNotNull(valueSelector, "valueSelector");
      CharSequence var6 = $this$associateWithTo;

      for(int var5 = 0; var5 < var6.length(); ++var5) {
         char element = var6.charAt(var5);
         destination.put(element, valueSelector.invoke(element));
      }

      return destination;
   }

   @NotNull
   public static final <C extends Collection<? super Character>> C toCollection(@NotNull CharSequence $this$toCollection, @NotNull C destination) {
      Intrinsics.checkParameterIsNotNull($this$toCollection, "$this$toCollection");
      Intrinsics.checkParameterIsNotNull(destination, "destination");
      CharSequence var4 = $this$toCollection;

      for(int var3 = 0; var3 < var4.length(); ++var3) {
         char item = var4.charAt(var3);
         destination.add(item);
      }

      return destination;
   }

   @NotNull
   public static final HashSet<Character> toHashSet(@NotNull CharSequence $this$toHashSet) {
      Intrinsics.checkParameterIsNotNull($this$toHashSet, "$this$toHashSet");
      return (HashSet)StringsKt.toCollection($this$toHashSet, (Collection)(new HashSet(MapsKt.mapCapacity($this$toHashSet.length()))));
   }

   @NotNull
   public static final List<Character> toList(@NotNull CharSequence $this$toList) {
      Intrinsics.checkParameterIsNotNull($this$toList, "$this$toList");
      List var10000;
      switch($this$toList.length()) {
      case 0:
         var10000 = CollectionsKt.emptyList();
         break;
      case 1:
         var10000 = CollectionsKt.listOf($this$toList.charAt(0));
         break;
      default:
         var10000 = StringsKt.toMutableList($this$toList);
      }

      return var10000;
   }

   @NotNull
   public static final List<Character> toMutableList(@NotNull CharSequence $this$toMutableList) {
      Intrinsics.checkParameterIsNotNull($this$toMutableList, "$this$toMutableList");
      return (List)StringsKt.toCollection($this$toMutableList, (Collection)(new ArrayList($this$toMutableList.length())));
   }

   @NotNull
   public static final Set<Character> toSet(@NotNull CharSequence $this$toSet) {
      Intrinsics.checkParameterIsNotNull($this$toSet, "$this$toSet");
      Set var10000;
      switch($this$toSet.length()) {
      case 0:
         var10000 = SetsKt.emptySet();
         break;
      case 1:
         var10000 = SetsKt.setOf($this$toSet.charAt(0));
         break;
      default:
         var10000 = (Set)StringsKt.toCollection($this$toSet, (Collection)(new LinkedHashSet(MapsKt.mapCapacity($this$toSet.length()))));
      }

      return var10000;
   }

   @NotNull
   public static final <R> List<R> flatMap(@NotNull CharSequence $this$flatMap, @NotNull Function1<? super Character, ? extends Iterable<? extends R>> transform) {
      int $i$f$flatMap = 0;
      Intrinsics.checkParameterIsNotNull($this$flatMap, "$this$flatMap");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      Collection destination$iv = (Collection)(new ArrayList());
      int $i$f$flatMapTo = false;
      CharSequence var6 = $this$flatMap;

      for(int var7 = 0; var7 < var6.length(); ++var7) {
         char element$iv = var6.charAt(var7);
         Iterable list$iv = (Iterable)transform.invoke(element$iv);
         CollectionsKt.addAll(destination$iv, list$iv);
      }

      return (List)destination$iv;
   }

   @NotNull
   public static final <R, C extends Collection<? super R>> C flatMapTo(@NotNull CharSequence $this$flatMapTo, @NotNull C destination, @NotNull Function1<? super Character, ? extends Iterable<? extends R>> transform) {
      int $i$f$flatMapTo = 0;
      Intrinsics.checkParameterIsNotNull($this$flatMapTo, "$this$flatMapTo");
      Intrinsics.checkParameterIsNotNull(destination, "destination");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      CharSequence var6 = $this$flatMapTo;

      for(int var5 = 0; var5 < var6.length(); ++var5) {
         char element = var6.charAt(var5);
         Iterable list = (Iterable)transform.invoke(element);
         CollectionsKt.addAll(destination, list);
      }

      return destination;
   }

   @NotNull
   public static final <K> Map<K, List<Character>> groupBy(@NotNull CharSequence $this$groupBy, @NotNull Function1<? super Character, ? extends K> keySelector) {
      int $i$f$groupBy = 0;
      Intrinsics.checkParameterIsNotNull($this$groupBy, "$this$groupBy");
      Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
      Map destination$iv = (Map)(new LinkedHashMap());
      int $i$f$groupByTo = false;
      CharSequence var6 = $this$groupBy;

      for(int var7 = 0; var7 < var6.length(); ++var7) {
         char element$iv = var6.charAt(var7);
         Object key$iv = keySelector.invoke(element$iv);
         int $i$f$getOrPut = false;
         Object value$iv$iv = destination$iv.get(key$iv);
         Object var10000;
         if (value$iv$iv == null) {
            int var13 = false;
            Object answer$iv$iv = new ArrayList();
            destination$iv.put(key$iv, answer$iv$iv);
            var10000 = answer$iv$iv;
         } else {
            var10000 = value$iv$iv;
         }

         List list$iv = (List)var10000;
         list$iv.add(element$iv);
      }

      return destination$iv;
   }

   @NotNull
   public static final <K, V> Map<K, List<V>> groupBy(@NotNull CharSequence $this$groupBy, @NotNull Function1<? super Character, ? extends K> keySelector, @NotNull Function1<? super Character, ? extends V> valueTransform) {
      int $i$f$groupBy = 0;
      Intrinsics.checkParameterIsNotNull($this$groupBy, "$this$groupBy");
      Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
      Intrinsics.checkParameterIsNotNull(valueTransform, "valueTransform");
      Map destination$iv = (Map)(new LinkedHashMap());
      int $i$f$groupByTo = false;
      CharSequence var7 = $this$groupBy;

      for(int var8 = 0; var8 < var7.length(); ++var8) {
         char element$iv = var7.charAt(var8);
         Object key$iv = keySelector.invoke(element$iv);
         int $i$f$getOrPut = false;
         Object value$iv$iv = destination$iv.get(key$iv);
         Object var10000;
         if (value$iv$iv == null) {
            int var14 = false;
            Object answer$iv$iv = new ArrayList();
            destination$iv.put(key$iv, answer$iv$iv);
            var10000 = answer$iv$iv;
         } else {
            var10000 = value$iv$iv;
         }

         List list$iv = (List)var10000;
         list$iv.add(valueTransform.invoke(element$iv));
      }

      return destination$iv;
   }

   @NotNull
   public static final <K, M extends Map<? super K, List<Character>>> M groupByTo(@NotNull CharSequence $this$groupByTo, @NotNull M destination, @NotNull Function1<? super Character, ? extends K> keySelector) {
      int $i$f$groupByTo = 0;
      Intrinsics.checkParameterIsNotNull($this$groupByTo, "$this$groupByTo");
      Intrinsics.checkParameterIsNotNull(destination, "destination");
      Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
      CharSequence var6 = $this$groupByTo;

      for(int var5 = 0; var5 < var6.length(); ++var5) {
         char element = var6.charAt(var5);
         Object key = keySelector.invoke(element);
         int $i$f$getOrPut = false;
         Object value$iv = destination.get(key);
         Object var10000;
         if (value$iv == null) {
            int var12 = false;
            Object answer$iv = new ArrayList();
            destination.put(key, answer$iv);
            var10000 = answer$iv;
         } else {
            var10000 = value$iv;
         }

         List list = (List)var10000;
         list.add(element);
      }

      return destination;
   }

   @NotNull
   public static final <K, V, M extends Map<? super K, List<V>>> M groupByTo(@NotNull CharSequence $this$groupByTo, @NotNull M destination, @NotNull Function1<? super Character, ? extends K> keySelector, @NotNull Function1<? super Character, ? extends V> valueTransform) {
      int $i$f$groupByTo = 0;
      Intrinsics.checkParameterIsNotNull($this$groupByTo, "$this$groupByTo");
      Intrinsics.checkParameterIsNotNull(destination, "destination");
      Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
      Intrinsics.checkParameterIsNotNull(valueTransform, "valueTransform");
      CharSequence var7 = $this$groupByTo;

      for(int var6 = 0; var6 < var7.length(); ++var6) {
         char element = var7.charAt(var6);
         Object key = keySelector.invoke(element);
         int $i$f$getOrPut = false;
         Object value$iv = destination.get(key);
         Object var10000;
         if (value$iv == null) {
            int var13 = false;
            Object answer$iv = new ArrayList();
            destination.put(key, answer$iv);
            var10000 = answer$iv;
         } else {
            var10000 = value$iv;
         }

         List list = (List)var10000;
         list.add(valueTransform.invoke(element));
      }

      return destination;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final <K> Grouping<Character, K> groupingBy(@NotNull final CharSequence $this$groupingBy, @NotNull final Function1<? super Character, ? extends K> keySelector) {
      int $i$f$groupingBy = 0;
      Intrinsics.checkParameterIsNotNull($this$groupingBy, "$this$groupingBy");
      Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
      return (Grouping)(new Grouping<Character, K>() {
         @NotNull
         public Iterator<Character> sourceIterator() {
            return (Iterator)StringsKt.iterator($this$groupingBy);
         }

         public K keyOf(char element) {
            return keySelector.invoke(element);
         }
      });
   }

   @NotNull
   public static final <R> List<R> map(@NotNull CharSequence $this$map, @NotNull Function1<? super Character, ? extends R> transform) {
      int $i$f$map = 0;
      Intrinsics.checkParameterIsNotNull($this$map, "$this$map");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      Collection destination$iv = (Collection)(new ArrayList($this$map.length()));
      int $i$f$mapTo = false;
      CharSequence var6 = $this$map;

      for(int var7 = 0; var7 < var6.length(); ++var7) {
         char item$iv = var6.charAt(var7);
         destination$iv.add(transform.invoke(item$iv));
      }

      return (List)destination$iv;
   }

   @NotNull
   public static final <R> List<R> mapIndexed(@NotNull CharSequence $this$mapIndexed, @NotNull Function2<? super Integer, ? super Character, ? extends R> transform) {
      int $i$f$mapIndexed = 0;
      Intrinsics.checkParameterIsNotNull($this$mapIndexed, "$this$mapIndexed");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      Collection destination$iv = (Collection)(new ArrayList($this$mapIndexed.length()));
      int $i$f$mapIndexedTo = false;
      int index$iv = 0;
      CharSequence var7 = $this$mapIndexed;

      for(int var8 = 0; var8 < var7.length(); ++var8) {
         char item$iv = var7.charAt(var8);
         Integer var10002 = index$iv;
         ++index$iv;
         destination$iv.add(transform.invoke(var10002, item$iv));
      }

      return (List)destination$iv;
   }

   @NotNull
   public static final <R> List<R> mapIndexedNotNull(@NotNull CharSequence $this$mapIndexedNotNull, @NotNull Function2<? super Integer, ? super Character, ? extends R> transform) {
      int $i$f$mapIndexedNotNull = 0;
      Intrinsics.checkParameterIsNotNull($this$mapIndexedNotNull, "$this$mapIndexedNotNull");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      Collection destination$iv = (Collection)(new ArrayList());
      int $i$f$mapIndexedNotNullTo = false;
      int $i$f$forEachIndexed = false;
      int index$iv$iv = 0;
      CharSequence var9 = $this$mapIndexedNotNull;

      for(int var10 = 0; var10 < var9.length(); ++var10) {
         char item$iv$iv = var9.charAt(var10);
         int index$iv = index$iv$iv++;
         int var14 = false;
         Object var20 = transform.invoke(index$iv, item$iv$iv);
         if (var20 != null) {
            Object var15 = var20;
            boolean var16 = false;
            boolean var17 = false;
            int var19 = false;
            destination$iv.add(var15);
         }
      }

      return (List)destination$iv;
   }

   @NotNull
   public static final <R, C extends Collection<? super R>> C mapIndexedNotNullTo(@NotNull CharSequence $this$mapIndexedNotNullTo, @NotNull C destination, @NotNull Function2<? super Integer, ? super Character, ? extends R> transform) {
      int $i$f$mapIndexedNotNullTo = 0;
      Intrinsics.checkParameterIsNotNull($this$mapIndexedNotNullTo, "$this$mapIndexedNotNullTo");
      Intrinsics.checkParameterIsNotNull(destination, "destination");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      int $i$f$forEachIndexed = false;
      int index$iv = 0;
      CharSequence var7 = $this$mapIndexedNotNullTo;

      for(int var8 = 0; var8 < var7.length(); ++var8) {
         char item$iv = var7.charAt(var8);
         int index = index$iv++;
         int var12 = false;
         Object var18 = transform.invoke(index, item$iv);
         if (var18 != null) {
            Object var13 = var18;
            boolean var14 = false;
            boolean var15 = false;
            int var17 = false;
            destination.add(var13);
         }
      }

      return destination;
   }

   @NotNull
   public static final <R, C extends Collection<? super R>> C mapIndexedTo(@NotNull CharSequence $this$mapIndexedTo, @NotNull C destination, @NotNull Function2<? super Integer, ? super Character, ? extends R> transform) {
      int $i$f$mapIndexedTo = 0;
      Intrinsics.checkParameterIsNotNull($this$mapIndexedTo, "$this$mapIndexedTo");
      Intrinsics.checkParameterIsNotNull(destination, "destination");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      int index = 0;
      CharSequence var7 = $this$mapIndexedTo;

      for(int var6 = 0; var6 < var7.length(); ++var6) {
         char item = var7.charAt(var6);
         Integer var10002 = index;
         ++index;
         destination.add(transform.invoke(var10002, item));
      }

      return destination;
   }

   @NotNull
   public static final <R> List<R> mapNotNull(@NotNull CharSequence $this$mapNotNull, @NotNull Function1<? super Character, ? extends R> transform) {
      int $i$f$mapNotNull = 0;
      Intrinsics.checkParameterIsNotNull($this$mapNotNull, "$this$mapNotNull");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      Collection destination$iv = (Collection)(new ArrayList());
      int $i$f$mapNotNullTo = false;
      int $i$f$forEach = false;
      CharSequence var8 = $this$mapNotNull;

      for(int var9 = 0; var9 < var8.length(); ++var9) {
         char element$iv$iv = var8.charAt(var9);
         int var12 = false;
         Object var10000 = transform.invoke(element$iv$iv);
         if (var10000 != null) {
            Object var13 = var10000;
            boolean var14 = false;
            boolean var15 = false;
            int var17 = false;
            destination$iv.add(var13);
         }
      }

      return (List)destination$iv;
   }

   @NotNull
   public static final <R, C extends Collection<? super R>> C mapNotNullTo(@NotNull CharSequence $this$mapNotNullTo, @NotNull C destination, @NotNull Function1<? super Character, ? extends R> transform) {
      int $i$f$mapNotNullTo = 0;
      Intrinsics.checkParameterIsNotNull($this$mapNotNullTo, "$this$mapNotNullTo");
      Intrinsics.checkParameterIsNotNull(destination, "destination");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      int $i$f$forEach = false;
      CharSequence var6 = $this$mapNotNullTo;

      for(int var7 = 0; var7 < var6.length(); ++var7) {
         char element$iv = var6.charAt(var7);
         int var10 = false;
         Object var10000 = transform.invoke(element$iv);
         if (var10000 != null) {
            Object var11 = var10000;
            boolean var12 = false;
            boolean var13 = false;
            int var15 = false;
            destination.add(var11);
         }
      }

      return destination;
   }

   @NotNull
   public static final <R, C extends Collection<? super R>> C mapTo(@NotNull CharSequence $this$mapTo, @NotNull C destination, @NotNull Function1<? super Character, ? extends R> transform) {
      int $i$f$mapTo = 0;
      Intrinsics.checkParameterIsNotNull($this$mapTo, "$this$mapTo");
      Intrinsics.checkParameterIsNotNull(destination, "destination");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      CharSequence var6 = $this$mapTo;

      for(int var5 = 0; var5 < var6.length(); ++var5) {
         char item = var6.charAt(var5);
         destination.add(transform.invoke(item));
      }

      return destination;
   }

   @NotNull
   public static final Iterable<IndexedValue<Character>> withIndex(@NotNull final CharSequence $this$withIndex) {
      Intrinsics.checkParameterIsNotNull($this$withIndex, "$this$withIndex");
      return (Iterable)(new IndexingIterable((Function0)(new Function0<CharIterator>() {
         @NotNull
         public final CharIterator invoke() {
            return StringsKt.iterator($this$withIndex);
         }
      })));
   }

   public static final boolean all(@NotNull CharSequence $this$all, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$all = 0;
      Intrinsics.checkParameterIsNotNull($this$all, "$this$all");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      CharSequence var5 = $this$all;

      for(int var4 = 0; var4 < var5.length(); ++var4) {
         char element = var5.charAt(var4);
         if (!(Boolean)predicate.invoke(element)) {
            return false;
         }
      }

      return true;
   }

   public static final boolean any(@NotNull CharSequence $this$any) {
      Intrinsics.checkParameterIsNotNull($this$any, "$this$any");
      boolean var2 = false;
      return $this$any.length() != 0;
   }

   public static final boolean any(@NotNull CharSequence $this$any, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$any = 0;
      Intrinsics.checkParameterIsNotNull($this$any, "$this$any");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      CharSequence var5 = $this$any;

      for(int var4 = 0; var4 < var5.length(); ++var4) {
         char element = var5.charAt(var4);
         if ((Boolean)predicate.invoke(element)) {
            return true;
         }
      }

      return false;
   }

   @InlineOnly
   private static final int count(@NotNull CharSequence $this$count) {
      int $i$f$count = 0;
      return $this$count.length();
   }

   public static final int count(@NotNull CharSequence $this$count, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$count = 0;
      Intrinsics.checkParameterIsNotNull($this$count, "$this$count");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      int count = 0;
      CharSequence var6 = $this$count;

      for(int var5 = 0; var5 < var6.length(); ++var5) {
         char element = var6.charAt(var5);
         if ((Boolean)predicate.invoke(element)) {
            ++count;
         }
      }

      return count;
   }

   public static final <R> R fold(@NotNull CharSequence $this$fold, R initial, @NotNull Function2<? super R, ? super Character, ? extends R> operation) {
      int $i$f$fold = 0;
      Intrinsics.checkParameterIsNotNull($this$fold, "$this$fold");
      Intrinsics.checkParameterIsNotNull(operation, "operation");
      Object accumulator = initial;
      CharSequence var7 = $this$fold;

      for(int var6 = 0; var6 < var7.length(); ++var6) {
         char element = var7.charAt(var6);
         accumulator = operation.invoke(accumulator, element);
      }

      return accumulator;
   }

   public static final <R> R foldIndexed(@NotNull CharSequence $this$foldIndexed, R initial, @NotNull Function3<? super Integer, ? super R, ? super Character, ? extends R> operation) {
      int $i$f$foldIndexed = 0;
      Intrinsics.checkParameterIsNotNull($this$foldIndexed, "$this$foldIndexed");
      Intrinsics.checkParameterIsNotNull(operation, "operation");
      int index = 0;
      Object accumulator = initial;
      CharSequence var8 = $this$foldIndexed;

      for(int var7 = 0; var7 < var8.length(); ++var7) {
         char element = var8.charAt(var7);
         Integer var10001 = index;
         ++index;
         accumulator = operation.invoke(var10001, accumulator, element);
      }

      return accumulator;
   }

   public static final <R> R foldRight(@NotNull CharSequence $this$foldRight, R initial, @NotNull Function2<? super Character, ? super R, ? extends R> operation) {
      int $i$f$foldRight = 0;
      Intrinsics.checkParameterIsNotNull($this$foldRight, "$this$foldRight");
      Intrinsics.checkParameterIsNotNull(operation, "operation");
      int index = StringsKt.getLastIndex($this$foldRight);

      Object accumulator;
      for(accumulator = initial; index >= 0; accumulator = operation.invoke($this$foldRight.charAt(index--), accumulator)) {
      }

      return accumulator;
   }

   public static final <R> R foldRightIndexed(@NotNull CharSequence $this$foldRightIndexed, R initial, @NotNull Function3<? super Integer, ? super Character, ? super R, ? extends R> operation) {
      int $i$f$foldRightIndexed = 0;
      Intrinsics.checkParameterIsNotNull($this$foldRightIndexed, "$this$foldRightIndexed");
      Intrinsics.checkParameterIsNotNull(operation, "operation");
      int index = StringsKt.getLastIndex($this$foldRightIndexed);

      Object accumulator;
      for(accumulator = initial; index >= 0; --index) {
         accumulator = operation.invoke(index, $this$foldRightIndexed.charAt(index), accumulator);
      }

      return accumulator;
   }

   public static final void forEach(@NotNull CharSequence $this$forEach, @NotNull Function1<? super Character, Unit> action) {
      int $i$f$forEach = 0;
      Intrinsics.checkParameterIsNotNull($this$forEach, "$this$forEach");
      Intrinsics.checkParameterIsNotNull(action, "action");
      CharSequence var5 = $this$forEach;

      for(int var4 = 0; var4 < var5.length(); ++var4) {
         char element = var5.charAt(var4);
         action.invoke(element);
      }

   }

   public static final void forEachIndexed(@NotNull CharSequence $this$forEachIndexed, @NotNull Function2<? super Integer, ? super Character, Unit> action) {
      int $i$f$forEachIndexed = 0;
      Intrinsics.checkParameterIsNotNull($this$forEachIndexed, "$this$forEachIndexed");
      Intrinsics.checkParameterIsNotNull(action, "action");
      int index = 0;
      CharSequence var6 = $this$forEachIndexed;

      for(int var5 = 0; var5 < var6.length(); ++var5) {
         char item = var6.charAt(var5);
         Integer var10001 = index;
         ++index;
         action.invoke(var10001, item);
      }

   }

   @Nullable
   public static final Character max(@NotNull CharSequence $this$max) {
      Intrinsics.checkParameterIsNotNull($this$max, "$this$max");
      boolean var2 = false;
      if ($this$max.length() == 0) {
         return null;
      } else {
         char max = $this$max.charAt(0);
         int i = 1;
         int var3 = StringsKt.getLastIndex($this$max);
         if (i <= var3) {
            while(true) {
               char e = $this$max.charAt(i);
               if (max < e) {
                  max = e;
               }

               if (i == var3) {
                  break;
               }

               ++i;
            }
         }

         return max;
      }
   }

   @Nullable
   public static final <R extends Comparable<? super R>> Character maxBy(@NotNull CharSequence $this$maxBy, @NotNull Function1<? super Character, ? extends R> selector) {
      int $i$f$maxBy = 0;
      Intrinsics.checkParameterIsNotNull($this$maxBy, "$this$maxBy");
      Intrinsics.checkParameterIsNotNull(selector, "selector");
      boolean var4 = false;
      if ($this$maxBy.length() == 0) {
         return null;
      } else {
         char maxElem = $this$maxBy.charAt(0);
         int lastIndex = StringsKt.getLastIndex($this$maxBy);
         if (lastIndex == 0) {
            return maxElem;
         } else {
            Comparable maxValue = (Comparable)selector.invoke(maxElem);
            int i = 1;
            int var7 = lastIndex;
            if (i <= lastIndex) {
               while(true) {
                  char e = $this$maxBy.charAt(i);
                  Comparable v = (Comparable)selector.invoke(e);
                  if (maxValue.compareTo(v) < 0) {
                     maxElem = e;
                     maxValue = v;
                  }

                  if (i == var7) {
                     break;
                  }

                  ++i;
               }
            }

            return maxElem;
         }
      }
   }

   @Nullable
   public static final Character maxWith(@NotNull CharSequence $this$maxWith, @NotNull Comparator<? super Character> comparator) {
      Intrinsics.checkParameterIsNotNull($this$maxWith, "$this$maxWith");
      Intrinsics.checkParameterIsNotNull(comparator, "comparator");
      boolean var3 = false;
      if ($this$maxWith.length() == 0) {
         return null;
      } else {
         char max = $this$maxWith.charAt(0);
         int i = 1;
         int var4 = StringsKt.getLastIndex($this$maxWith);
         if (i <= var4) {
            while(true) {
               char e = $this$maxWith.charAt(i);
               if (comparator.compare(max, e) < 0) {
                  max = e;
               }

               if (i == var4) {
                  break;
               }

               ++i;
            }
         }

         return max;
      }
   }

   @Nullable
   public static final Character min(@NotNull CharSequence $this$min) {
      Intrinsics.checkParameterIsNotNull($this$min, "$this$min");
      boolean var2 = false;
      if ($this$min.length() == 0) {
         return null;
      } else {
         char min = $this$min.charAt(0);
         int i = 1;
         int var3 = StringsKt.getLastIndex($this$min);
         if (i <= var3) {
            while(true) {
               char e = $this$min.charAt(i);
               if (min > e) {
                  min = e;
               }

               if (i == var3) {
                  break;
               }

               ++i;
            }
         }

         return min;
      }
   }

   @Nullable
   public static final <R extends Comparable<? super R>> Character minBy(@NotNull CharSequence $this$minBy, @NotNull Function1<? super Character, ? extends R> selector) {
      int $i$f$minBy = 0;
      Intrinsics.checkParameterIsNotNull($this$minBy, "$this$minBy");
      Intrinsics.checkParameterIsNotNull(selector, "selector");
      boolean var4 = false;
      if ($this$minBy.length() == 0) {
         return null;
      } else {
         char minElem = $this$minBy.charAt(0);
         int lastIndex = StringsKt.getLastIndex($this$minBy);
         if (lastIndex == 0) {
            return minElem;
         } else {
            Comparable minValue = (Comparable)selector.invoke(minElem);
            int i = 1;
            int var7 = lastIndex;
            if (i <= lastIndex) {
               while(true) {
                  char e = $this$minBy.charAt(i);
                  Comparable v = (Comparable)selector.invoke(e);
                  if (minValue.compareTo(v) > 0) {
                     minElem = e;
                     minValue = v;
                  }

                  if (i == var7) {
                     break;
                  }

                  ++i;
               }
            }

            return minElem;
         }
      }
   }

   @Nullable
   public static final Character minWith(@NotNull CharSequence $this$minWith, @NotNull Comparator<? super Character> comparator) {
      Intrinsics.checkParameterIsNotNull($this$minWith, "$this$minWith");
      Intrinsics.checkParameterIsNotNull(comparator, "comparator");
      boolean var3 = false;
      if ($this$minWith.length() == 0) {
         return null;
      } else {
         char min = $this$minWith.charAt(0);
         int i = 1;
         int var4 = StringsKt.getLastIndex($this$minWith);
         if (i <= var4) {
            while(true) {
               char e = $this$minWith.charAt(i);
               if (comparator.compare(min, e) > 0) {
                  min = e;
               }

               if (i == var4) {
                  break;
               }

               ++i;
            }
         }

         return min;
      }
   }

   public static final boolean none(@NotNull CharSequence $this$none) {
      Intrinsics.checkParameterIsNotNull($this$none, "$this$none");
      boolean var2 = false;
      return $this$none.length() == 0;
   }

   public static final boolean none(@NotNull CharSequence $this$none, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$none = 0;
      Intrinsics.checkParameterIsNotNull($this$none, "$this$none");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      CharSequence var5 = $this$none;

      for(int var4 = 0; var4 < var5.length(); ++var4) {
         char element = var5.charAt(var4);
         if ((Boolean)predicate.invoke(element)) {
            return false;
         }
      }

      return true;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final <S extends CharSequence> S onEach(@NotNull S $this$onEach, @NotNull Function1<? super Character, Unit> action) {
      int $i$f$onEach = 0;
      Intrinsics.checkParameterIsNotNull($this$onEach, "$this$onEach");
      Intrinsics.checkParameterIsNotNull(action, "action");
      boolean var4 = false;
      boolean var5 = false;
      int var7 = false;
      CharSequence var8 = $this$onEach;

      for(int var9 = 0; var9 < var8.length(); ++var9) {
         char element = var8.charAt(var9);
         action.invoke(element);
      }

      return $this$onEach;
   }

   public static final char reduce(@NotNull CharSequence $this$reduce, @NotNull Function2<? super Character, ? super Character, Character> operation) {
      int $i$f$reduce = 0;
      Intrinsics.checkParameterIsNotNull($this$reduce, "$this$reduce");
      Intrinsics.checkParameterIsNotNull(operation, "operation");
      boolean var4 = false;
      if ($this$reduce.length() == 0) {
         throw (Throwable)(new UnsupportedOperationException("Empty char sequence can't be reduced."));
      } else {
         char accumulator = $this$reduce.charAt(0);
         int index = 1;
         int var5 = StringsKt.getLastIndex($this$reduce);
         if (index <= var5) {
            while(true) {
               accumulator = (Character)operation.invoke(accumulator, $this$reduce.charAt(index));
               if (index == var5) {
                  break;
               }

               ++index;
            }
         }

         return accumulator;
      }
   }

   public static final char reduceIndexed(@NotNull CharSequence $this$reduceIndexed, @NotNull Function3<? super Integer, ? super Character, ? super Character, Character> operation) {
      int $i$f$reduceIndexed = 0;
      Intrinsics.checkParameterIsNotNull($this$reduceIndexed, "$this$reduceIndexed");
      Intrinsics.checkParameterIsNotNull(operation, "operation");
      boolean var4 = false;
      if ($this$reduceIndexed.length() == 0) {
         throw (Throwable)(new UnsupportedOperationException("Empty char sequence can't be reduced."));
      } else {
         char accumulator = $this$reduceIndexed.charAt(0);
         int index = 1;
         int var5 = StringsKt.getLastIndex($this$reduceIndexed);
         if (index <= var5) {
            while(true) {
               accumulator = (Character)operation.invoke(index, accumulator, $this$reduceIndexed.charAt(index));
               if (index == var5) {
                  break;
               }

               ++index;
            }
         }

         return accumulator;
      }
   }

   public static final char reduceRight(@NotNull CharSequence $this$reduceRight, @NotNull Function2<? super Character, ? super Character, Character> operation) {
      int $i$f$reduceRight = 0;
      Intrinsics.checkParameterIsNotNull($this$reduceRight, "$this$reduceRight");
      Intrinsics.checkParameterIsNotNull(operation, "operation");
      int index = StringsKt.getLastIndex($this$reduceRight);
      if (index < 0) {
         throw (Throwable)(new UnsupportedOperationException("Empty char sequence can't be reduced."));
      } else {
         char accumulator;
         for(accumulator = $this$reduceRight.charAt(index--); index >= 0; accumulator = (Character)operation.invoke($this$reduceRight.charAt(index--), accumulator)) {
         }

         return accumulator;
      }
   }

   public static final char reduceRightIndexed(@NotNull CharSequence $this$reduceRightIndexed, @NotNull Function3<? super Integer, ? super Character, ? super Character, Character> operation) {
      int $i$f$reduceRightIndexed = 0;
      Intrinsics.checkParameterIsNotNull($this$reduceRightIndexed, "$this$reduceRightIndexed");
      Intrinsics.checkParameterIsNotNull(operation, "operation");
      int index = StringsKt.getLastIndex($this$reduceRightIndexed);
      if (index < 0) {
         throw (Throwable)(new UnsupportedOperationException("Empty char sequence can't be reduced."));
      } else {
         char accumulator;
         for(accumulator = $this$reduceRightIndexed.charAt(index--); index >= 0; --index) {
            accumulator = (Character)operation.invoke(index, $this$reduceRightIndexed.charAt(index), accumulator);
         }

         return accumulator;
      }
   }

   public static final int sumBy(@NotNull CharSequence $this$sumBy, @NotNull Function1<? super Character, Integer> selector) {
      int $i$f$sumBy = 0;
      Intrinsics.checkParameterIsNotNull($this$sumBy, "$this$sumBy");
      Intrinsics.checkParameterIsNotNull(selector, "selector");
      int sum = 0;
      CharSequence var6 = $this$sumBy;

      for(int var5 = 0; var5 < var6.length(); ++var5) {
         char element = var6.charAt(var5);
         sum += ((Number)selector.invoke(element)).intValue();
      }

      return sum;
   }

   public static final double sumByDouble(@NotNull CharSequence $this$sumByDouble, @NotNull Function1<? super Character, Double> selector) {
      int $i$f$sumByDouble = 0;
      Intrinsics.checkParameterIsNotNull($this$sumByDouble, "$this$sumByDouble");
      Intrinsics.checkParameterIsNotNull(selector, "selector");
      double sum = 0.0D;
      CharSequence var7 = $this$sumByDouble;

      for(int var6 = 0; var6 < var7.length(); ++var6) {
         char element = var7.charAt(var6);
         sum += ((Number)selector.invoke(element)).doubleValue();
      }

      return sum;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final List<String> chunked(@NotNull CharSequence $this$chunked, int size) {
      Intrinsics.checkParameterIsNotNull($this$chunked, "$this$chunked");
      return StringsKt.windowed($this$chunked, size, size, true);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final <R> List<R> chunked(@NotNull CharSequence $this$chunked, int size, @NotNull Function1<? super CharSequence, ? extends R> transform) {
      Intrinsics.checkParameterIsNotNull($this$chunked, "$this$chunked");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      return StringsKt.windowed($this$chunked, size, size, true, transform);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final Sequence<String> chunkedSequence(@NotNull CharSequence $this$chunkedSequence, int size) {
      Intrinsics.checkParameterIsNotNull($this$chunkedSequence, "$this$chunkedSequence");
      return StringsKt.chunkedSequence($this$chunkedSequence, size, (Function1)null.INSTANCE);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final <R> Sequence<R> chunkedSequence(@NotNull CharSequence $this$chunkedSequence, int size, @NotNull Function1<? super CharSequence, ? extends R> transform) {
      Intrinsics.checkParameterIsNotNull($this$chunkedSequence, "$this$chunkedSequence");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      return StringsKt.windowedSequence($this$chunkedSequence, size, size, true, transform);
   }

   @NotNull
   public static final Pair<CharSequence, CharSequence> partition(@NotNull CharSequence $this$partition, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$partition = 0;
      Intrinsics.checkParameterIsNotNull($this$partition, "$this$partition");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      StringBuilder first = new StringBuilder();
      StringBuilder second = new StringBuilder();
      CharSequence var7 = $this$partition;

      for(int var6 = 0; var6 < var7.length(); ++var6) {
         char element = var7.charAt(var6);
         if ((Boolean)predicate.invoke(element)) {
            first.append(element);
         } else {
            second.append(element);
         }
      }

      return new Pair(first, second);
   }

   @NotNull
   public static final Pair<String, String> partition(@NotNull String $this$partition, @NotNull Function1<? super Character, Boolean> predicate) {
      int $i$f$partition = 0;
      Intrinsics.checkParameterIsNotNull($this$partition, "$this$partition");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      StringBuilder first = new StringBuilder();
      StringBuilder second = new StringBuilder();
      String var7 = $this$partition;
      int var8 = $this$partition.length();

      for(int var6 = 0; var6 < var8; ++var6) {
         char element = var7.charAt(var6);
         if ((Boolean)predicate.invoke(element)) {
            first.append(element);
         } else {
            second.append(element);
         }
      }

      return new Pair(first.toString(), second.toString());
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final List<String> windowed(@NotNull CharSequence $this$windowed, int size, int step, boolean partialWindows) {
      Intrinsics.checkParameterIsNotNull($this$windowed, "$this$windowed");
      return StringsKt.windowed($this$windowed, size, step, partialWindows, (Function1)null.INSTANCE);
   }

   // $FF: synthetic method
   public static List windowed$default(CharSequence var0, int var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 1;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.windowed(var0, var1, var2, var3);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final <R> List<R> windowed(@NotNull CharSequence $this$windowed, int size, int step, boolean partialWindows, @NotNull Function1<? super CharSequence, ? extends R> transform) {
      Intrinsics.checkParameterIsNotNull($this$windowed, "$this$windowed");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      SlidingWindowKt.checkWindowSizeStep(size, step);
      int thisSize = $this$windowed.length();
      int resultCapacity = thisSize / step + (thisSize % step == 0 ? 0 : 1);
      ArrayList result = new ArrayList(resultCapacity);
      int index = 0;

      while(true) {
         if (0 > index) {
            break;
         }

         if (thisSize <= index) {
            break;
         }

         int end = index + size;
         int var10000;
         if (end >= 0 && end <= thisSize) {
            var10000 = end;
         } else {
            if (!partialWindows) {
               break;
            }

            var10000 = thisSize;
         }

         int coercedEnd = var10000;
         result.add(transform.invoke($this$windowed.subSequence(index, coercedEnd)));
         index += step;
      }

      return (List)result;
   }

   // $FF: synthetic method
   public static List windowed$default(CharSequence var0, int var1, int var2, boolean var3, Function1 var4, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         var2 = 1;
      }

      if ((var5 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.windowed(var0, var1, var2, var3, var4);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final Sequence<String> windowedSequence(@NotNull CharSequence $this$windowedSequence, int size, int step, boolean partialWindows) {
      Intrinsics.checkParameterIsNotNull($this$windowedSequence, "$this$windowedSequence");
      return StringsKt.windowedSequence($this$windowedSequence, size, step, partialWindows, (Function1)null.INSTANCE);
   }

   // $FF: synthetic method
   public static Sequence windowedSequence$default(CharSequence var0, int var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 1;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.windowedSequence(var0, var1, var2, var3);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final <R> Sequence<R> windowedSequence(@NotNull final CharSequence $this$windowedSequence, final int size, int step, boolean partialWindows, @NotNull final Function1<? super CharSequence, ? extends R> transform) {
      Intrinsics.checkParameterIsNotNull($this$windowedSequence, "$this$windowedSequence");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      SlidingWindowKt.checkWindowSizeStep(size, step);
      IntProgression windows = RangesKt.step((IntProgression)(partialWindows ? StringsKt.getIndices($this$windowedSequence) : RangesKt.until(0, $this$windowedSequence.length() - size + 1)), step);
      return SequencesKt.map(CollectionsKt.asSequence((Iterable)windows), (Function1)(new Function1<Integer, R>() {
         public final R invoke(int index) {
            int end = index + size;
            int coercedEnd = end >= 0 && end <= $this$windowedSequence.length() ? end : $this$windowedSequence.length();
            return transform.invoke($this$windowedSequence.subSequence(index, coercedEnd));
         }
      }));
   }

   // $FF: synthetic method
   public static Sequence windowedSequence$default(CharSequence var0, int var1, int var2, boolean var3, Function1 var4, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         var2 = 1;
      }

      if ((var5 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.windowedSequence(var0, var1, var2, var3, var4);
   }

   @NotNull
   public static final List<Pair<Character, Character>> zip(@NotNull CharSequence $this$zip, @NotNull CharSequence other) {
      Intrinsics.checkParameterIsNotNull($this$zip, "$this$zip");
      Intrinsics.checkParameterIsNotNull(other, "other");
      CharSequence $this$zip$iv = $this$zip;
      int $i$f$zip = false;
      int var4 = $this$zip.length();
      int i$iv = other.length();
      boolean var6 = false;
      int length$iv = Math.min(var4, i$iv);
      ArrayList list$iv = new ArrayList(length$iv);
      i$iv = 0;

      for(int var14 = length$iv; i$iv < var14; ++i$iv) {
         char var10001 = $this$zip$iv.charAt(i$iv);
         char c2 = other.charAt(i$iv);
         char c1 = var10001;
         int var10 = false;
         Pair var12 = TuplesKt.to(c1, c2);
         list$iv.add(var12);
      }

      return (List)list$iv;
   }

   @NotNull
   public static final <V> List<V> zip(@NotNull CharSequence $this$zip, @NotNull CharSequence other, @NotNull Function2<? super Character, ? super Character, ? extends V> transform) {
      int $i$f$zip = 0;
      Intrinsics.checkParameterIsNotNull($this$zip, "$this$zip");
      Intrinsics.checkParameterIsNotNull(other, "other");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      int var5 = $this$zip.length();
      int i = other.length();
      boolean var7 = false;
      int length = Math.min(var5, i);
      ArrayList list = new ArrayList(length);
      i = 0;

      for(int var9 = length; i < var9; ++i) {
         list.add(transform.invoke($this$zip.charAt(i), other.charAt(i)));
      }

      return (List)list;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final List<Pair<Character, Character>> zipWithNext(@NotNull CharSequence $this$zipWithNext) {
      Intrinsics.checkParameterIsNotNull($this$zipWithNext, "$this$zipWithNext");
      CharSequence $this$zipWithNext$iv = $this$zipWithNext;
      int $i$f$zipWithNext = false;
      int size$iv = $this$zipWithNext.length() - 1;
      List var10000;
      if (size$iv < 1) {
         var10000 = CollectionsKt.emptyList();
      } else {
         ArrayList result$iv = new ArrayList(size$iv);
         int index$iv = 0;

         for(int var6 = size$iv; index$iv < var6; ++index$iv) {
            char var10001 = $this$zipWithNext$iv.charAt(index$iv);
            char b = $this$zipWithNext$iv.charAt(index$iv + 1);
            char a = var10001;
            int var9 = false;
            Pair var11 = TuplesKt.to(a, b);
            result$iv.add(var11);
         }

         var10000 = (List)result$iv;
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final <R> List<R> zipWithNext(@NotNull CharSequence $this$zipWithNext, @NotNull Function2<? super Character, ? super Character, ? extends R> transform) {
      int $i$f$zipWithNext = 0;
      Intrinsics.checkParameterIsNotNull($this$zipWithNext, "$this$zipWithNext");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      int size = $this$zipWithNext.length() - 1;
      if (size < 1) {
         return CollectionsKt.emptyList();
      } else {
         ArrayList result = new ArrayList(size);
         int index = 0;

         for(int var6 = size; index < var6; ++index) {
            result.add(transform.invoke($this$zipWithNext.charAt(index), $this$zipWithNext.charAt(index + 1)));
         }

         return (List)result;
      }
   }

   @NotNull
   public static final Iterable<Character> asIterable(@NotNull CharSequence $this$asIterable) {
      Intrinsics.checkParameterIsNotNull($this$asIterable, "$this$asIterable");
      if ($this$asIterable instanceof String) {
         boolean var2 = false;
         if ($this$asIterable.length() == 0) {
            return (Iterable)CollectionsKt.emptyList();
         }
      }

      boolean var1 = false;
      return (Iterable)(new StringsKt___StringsKt$asIterable$$inlined$Iterable$1($this$asIterable));
   }

   @NotNull
   public static final Sequence<Character> asSequence(@NotNull CharSequence $this$asSequence) {
      Intrinsics.checkParameterIsNotNull($this$asSequence, "$this$asSequence");
      if ($this$asSequence instanceof String) {
         boolean var2 = false;
         if ($this$asSequence.length() == 0) {
            return SequencesKt.emptySequence();
         }
      }

      boolean var1 = false;
      return (Sequence)(new StringsKt___StringsKt$asSequence$$inlined$Sequence$1($this$asSequence));
   }

   public StringsKt___StringsKt() {
   }
}
