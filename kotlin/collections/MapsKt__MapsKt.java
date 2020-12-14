package kotlin.collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000~\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010%\n\u0000\n\u0002\u0010&\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010(\n\u0002\u0010)\n\u0002\u0010'\n\u0002\b\n\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0016\u001a\u001e\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005\u001a1\u0010\u0006\u001a\u001e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0007j\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u0005`\b\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005H\u0087\b\u001a_\u0010\u0006\u001a\u001e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0007j\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u0005`\b\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u00052*\u0010\t\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0\n\"\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b¢\u0006\u0002\u0010\f\u001a1\u0010\r\u001a\u001e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000ej\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u0005`\u000f\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005H\u0087\b\u001a_\u0010\r\u001a\u001e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000ej\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u0005`\u000f\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u00052*\u0010\t\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0\n\"\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b¢\u0006\u0002\u0010\u0010\u001a\u0010\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u0001H\u0001\u001a!\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005H\u0087\b\u001aO\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u00052*\u0010\t\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0\n\"\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b¢\u0006\u0002\u0010\u0014\u001a!\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0016\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005H\u0087\b\u001aO\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0016\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u00052*\u0010\t\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0\n\"\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b¢\u0006\u0002\u0010\u0014\u001a*\u0010\u0017\u001a\u0002H\u0004\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018H\u0087\n¢\u0006\u0002\u0010\u0019\u001a*\u0010\u001a\u001a\u0002H\u0005\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018H\u0087\n¢\u0006\u0002\u0010\u0019\u001a9\u0010\u001b\u001a\u00020\u001c\"\t\b\u0000\u0010\u0004¢\u0006\u0002\b\u001d\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010\u001e\u001a\u0002H\u0004H\u0087\n¢\u0006\u0002\u0010\u001f\u001a1\u0010 \u001a\u00020\u001c\"\t\b\u0000\u0010\u0004¢\u0006\u0002\b\u001d*\u000e\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0002\b\u00030\u00032\u0006\u0010\u001e\u001a\u0002H\u0004H\u0087\b¢\u0006\u0002\u0010\u001f\u001a7\u0010!\u001a\u00020\u001c\"\u0004\b\u0000\u0010\u0004\"\t\b\u0001\u0010\u0005¢\u0006\u0002\b\u001d*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010\"\u001a\u0002H\u0005H\u0087\b¢\u0006\u0002\u0010\u001f\u001aS\u0010#\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u001e\u0010$\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018\u0012\u0004\u0012\u00020\u001c0%H\u0086\b\u001aG\u0010&\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u00020\u001c0%H\u0086\b\u001aS\u0010'\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u001e\u0010$\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018\u0012\u0004\u0012\u00020\u001c0%H\u0086\b\u001an\u0010(\u001a\u0002H)\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005\"\u0018\b\u0002\u0010)*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0004\u0012\u0006\b\u0000\u0012\u0002H\u00050\u0016*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010*\u001a\u0002H)2\u001e\u0010$\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018\u0012\u0004\u0012\u00020\u001c0%H\u0086\b¢\u0006\u0002\u0010+\u001an\u0010,\u001a\u0002H)\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005\"\u0018\b\u0002\u0010)*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0004\u0012\u0006\b\u0000\u0012\u0002H\u00050\u0016*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010*\u001a\u0002H)2\u001e\u0010$\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018\u0012\u0004\u0012\u00020\u001c0%H\u0086\b¢\u0006\u0002\u0010+\u001aG\u0010-\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u00020\u001c0%H\u0086\b\u001a;\u0010.\u001a\u0004\u0018\u0001H\u0005\"\t\b\u0000\u0010\u0004¢\u0006\u0002\b\u001d\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010\u001e\u001a\u0002H\u0004H\u0087\n¢\u0006\u0002\u0010/\u001a@\u00100\u001a\u0002H\u0005\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010\u001e\u001a\u0002H\u00042\f\u00101\u001a\b\u0012\u0004\u0012\u0002H\u000502H\u0087\b¢\u0006\u0002\u00103\u001a@\u00104\u001a\u0002H\u0005\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010\u001e\u001a\u0002H\u00042\f\u00101\u001a\b\u0012\u0004\u0012\u0002H\u000502H\u0080\b¢\u0006\u0002\u00103\u001a@\u00105\u001a\u0002H\u0005\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00162\u0006\u0010\u001e\u001a\u0002H\u00042\f\u00101\u001a\b\u0012\u0004\u0012\u0002H\u000502H\u0086\b¢\u0006\u0002\u00103\u001a1\u00106\u001a\u0002H\u0005\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010\u001e\u001a\u0002H\u0004H\u0007¢\u0006\u0002\u0010/\u001a<\u00107\u001a\u0002H8\"\u0014\b\u0000\u0010)*\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003*\u0002H8\"\u0004\b\u0001\u00108*\u0002H)2\f\u00101\u001a\b\u0012\u0004\u0012\u0002H802H\u0087\b¢\u0006\u0002\u00109\u001a'\u0010:\u001a\u00020\u001c\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003H\u0087\b\u001a:\u0010;\u001a\u00020\u001c\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0012\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u0005\u0018\u00010\u0003H\u0087\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a9\u0010<\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00180=\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003H\u0087\n\u001a<\u0010<\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050?0>\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0016H\u0087\n¢\u0006\u0002\b@\u001aY\u0010A\u001a\u000e\u0012\u0004\u0012\u0002H8\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005\"\u0004\b\u0002\u00108*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u001e\u0010B\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018\u0012\u0004\u0012\u0002H80%H\u0086\b\u001at\u0010C\u001a\u0002H)\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005\"\u0004\b\u0002\u00108\"\u0018\b\u0003\u0010)*\u0012\u0012\u0006\b\u0000\u0012\u0002H8\u0012\u0006\b\u0000\u0012\u0002H\u00050\u0016*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010*\u001a\u0002H)2\u001e\u0010B\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018\u0012\u0004\u0012\u0002H80%H\u0086\b¢\u0006\u0002\u0010+\u001aY\u0010D\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H80\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005\"\u0004\b\u0002\u00108*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u001e\u0010B\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018\u0012\u0004\u0012\u0002H80%H\u0086\b\u001at\u0010E\u001a\u0002H)\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005\"\u0004\b\u0002\u00108\"\u0018\b\u0003\u0010)*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0004\u0012\u0006\b\u0000\u0012\u0002H80\u0016*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010*\u001a\u0002H)2\u001e\u0010B\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018\u0012\u0004\u0012\u0002H80%H\u0086\b¢\u0006\u0002\u0010+\u001a@\u0010F\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010\u001e\u001a\u0002H\u0004H\u0087\u0002¢\u0006\u0002\u0010G\u001aH\u0010F\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u000e\u0010H\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00040\nH\u0087\u0002¢\u0006\u0002\u0010I\u001aA\u0010F\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\f\u0010H\u001a\b\u0012\u0004\u0012\u0002H\u00040JH\u0087\u0002\u001aA\u0010F\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\f\u0010H\u001a\b\u0012\u0004\u0012\u0002H\u00040KH\u0087\u0002\u001a2\u0010L\u001a\u00020M\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00162\u0006\u0010\u001e\u001a\u0002H\u0004H\u0087\n¢\u0006\u0002\u0010N\u001a:\u0010L\u001a\u00020M\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00162\u000e\u0010H\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00040\nH\u0087\n¢\u0006\u0002\u0010O\u001a3\u0010L\u001a\u00020M\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00162\f\u0010H\u001a\b\u0012\u0004\u0012\u0002H\u00040JH\u0087\n\u001a3\u0010L\u001a\u00020M\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00162\f\u0010H\u001a\b\u0012\u0004\u0012\u0002H\u00040KH\u0087\n\u001a0\u0010P\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003H\u0000\u001a3\u0010Q\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u0005\u0018\u00010\u0003H\u0087\b\u001aT\u0010R\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u001a\u0010\t\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0\nH\u0086\u0002¢\u0006\u0002\u0010S\u001aG\u0010R\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0012\u0010T\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000bH\u0086\u0002\u001aM\u0010R\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0018\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0JH\u0086\u0002\u001aI\u0010R\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0014\u0010U\u001a\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003H\u0086\u0002\u001aM\u0010R\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0018\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0KH\u0086\u0002\u001aJ\u0010V\u001a\u00020M\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0004\u0012\u0006\b\u0000\u0012\u0002H\u00050\u00162\u001a\u0010\t\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0\nH\u0087\n¢\u0006\u0002\u0010W\u001a=\u0010V\u001a\u00020M\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0004\u0012\u0006\b\u0000\u0012\u0002H\u00050\u00162\u0012\u0010T\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000bH\u0087\n\u001aC\u0010V\u001a\u00020M\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0004\u0012\u0006\b\u0000\u0012\u0002H\u00050\u00162\u0018\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0JH\u0087\n\u001a=\u0010V\u001a\u00020M\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0004\u0012\u0006\b\u0000\u0012\u0002H\u00050\u00162\u0012\u0010U\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003H\u0087\n\u001aC\u0010V\u001a\u00020M\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0004\u0012\u0006\b\u0000\u0012\u0002H\u00050\u00162\u0018\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0KH\u0087\n\u001aG\u0010X\u001a\u00020M\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0004\u0012\u0006\b\u0000\u0012\u0002H\u00050\u00162\u001a\u0010\t\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0\n¢\u0006\u0002\u0010W\u001a@\u0010X\u001a\u00020M\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0004\u0012\u0006\b\u0000\u0012\u0002H\u00050\u00162\u0018\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0J\u001a@\u0010X\u001a\u00020M\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0004\u0012\u0006\b\u0000\u0012\u0002H\u00050\u00162\u0018\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0K\u001a;\u0010Y\u001a\u0004\u0018\u0001H\u0005\"\t\b\u0000\u0010\u0004¢\u0006\u0002\b\u001d\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00162\u0006\u0010\u001e\u001a\u0002H\u0004H\u0087\b¢\u0006\u0002\u0010/\u001a:\u0010Z\u001a\u00020M\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00162\u0006\u0010\u001e\u001a\u0002H\u00042\u0006\u0010\"\u001a\u0002H\u0005H\u0087\n¢\u0006\u0002\u0010[\u001a;\u0010\\\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0\n¢\u0006\u0002\u0010\u0014\u001aQ\u0010\\\u001a\u0002H)\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005\"\u0018\b\u0002\u0010)*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0004\u0012\u0006\b\u0000\u0012\u0002H\u00050\u0016*\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0\n2\u0006\u0010*\u001a\u0002H)¢\u0006\u0002\u0010]\u001a4\u0010\\\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0J\u001aO\u0010\\\u001a\u0002H)\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005\"\u0018\b\u0002\u0010)*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0004\u0012\u0006\b\u0000\u0012\u0002H\u00050\u0016*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0J2\u0006\u0010*\u001a\u0002H)¢\u0006\u0002\u0010^\u001a2\u0010\\\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003H\u0007\u001aM\u0010\\\u001a\u0002H)\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005\"\u0018\b\u0002\u0010)*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0004\u0012\u0006\b\u0000\u0012\u0002H\u00050\u0016*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010*\u001a\u0002H)H\u0007¢\u0006\u0002\u0010_\u001a4\u0010\\\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0K\u001aO\u0010\\\u001a\u0002H)\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005\"\u0018\b\u0002\u0010)*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0004\u0012\u0006\b\u0000\u0012\u0002H\u00050\u0016*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0K2\u0006\u0010*\u001a\u0002H)¢\u0006\u0002\u0010`\u001a2\u0010a\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0016\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003H\u0007\u001a1\u0010b\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018H\u0087\b\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006c"},
   d2 = {"INT_MAX_POWER_OF_TWO", "", "emptyMap", "", "K", "V", "hashMapOf", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "pairs", "", "Lkotlin/Pair;", "([Lkotlin/Pair;)Ljava/util/HashMap;", "linkedMapOf", "Ljava/util/LinkedHashMap;", "Lkotlin/collections/LinkedHashMap;", "([Lkotlin/Pair;)Ljava/util/LinkedHashMap;", "mapCapacity", "expectedSize", "mapOf", "([Lkotlin/Pair;)Ljava/util/Map;", "mutableMapOf", "", "component1", "", "(Ljava/util/Map$Entry;)Ljava/lang/Object;", "component2", "contains", "", "Lkotlin/internal/OnlyInputTypes;", "key", "(Ljava/util/Map;Ljava/lang/Object;)Z", "containsKey", "containsValue", "value", "filter", "predicate", "Lkotlin/Function1;", "filterKeys", "filterNot", "filterNotTo", "M", "destination", "(Ljava/util/Map;Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "filterTo", "filterValues", "get", "(Ljava/util/Map;Ljava/lang/Object;)Ljava/lang/Object;", "getOrElse", "defaultValue", "Lkotlin/Function0;", "(Ljava/util/Map;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "getOrElseNullable", "getOrPut", "getValue", "ifEmpty", "R", "(Ljava/util/Map;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isNotEmpty", "isNullOrEmpty", "iterator", "", "", "", "mutableIterator", "mapKeys", "transform", "mapKeysTo", "mapValues", "mapValuesTo", "minus", "(Ljava/util/Map;Ljava/lang/Object;)Ljava/util/Map;", "keys", "(Ljava/util/Map;[Ljava/lang/Object;)Ljava/util/Map;", "", "Lkotlin/sequences/Sequence;", "minusAssign", "", "(Ljava/util/Map;Ljava/lang/Object;)V", "(Ljava/util/Map;[Ljava/lang/Object;)V", "optimizeReadOnlyMap", "orEmpty", "plus", "(Ljava/util/Map;[Lkotlin/Pair;)Ljava/util/Map;", "pair", "map", "plusAssign", "(Ljava/util/Map;[Lkotlin/Pair;)V", "putAll", "remove", "set", "(Ljava/util/Map;Ljava/lang/Object;Ljava/lang/Object;)V", "toMap", "([Lkotlin/Pair;Ljava/util/Map;)Ljava/util/Map;", "(Ljava/lang/Iterable;Ljava/util/Map;)Ljava/util/Map;", "(Ljava/util/Map;Ljava/util/Map;)Ljava/util/Map;", "(Lkotlin/sequences/Sequence;Ljava/util/Map;)Ljava/util/Map;", "toMutableMap", "toPair", "kotlin-stdlib"},
   xs = "kotlin/collections/MapsKt"
)
class MapsKt__MapsKt extends MapsKt__MapsJVMKt {
   private static final int INT_MAX_POWER_OF_TWO = 1073741824;

   @NotNull
   public static final <K, V> Map<K, V> emptyMap() {
      EmptyMap var10000 = EmptyMap.INSTANCE;
      if (var10000 == null) {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Map<K, V>");
      } else {
         return (Map)var10000;
      }
   }

   @NotNull
   public static final <K, V> Map<K, V> mapOf(@NotNull Pair<? extends K, ? extends V>... pairs) {
      Intrinsics.checkParameterIsNotNull(pairs, "pairs");
      return pairs.length > 0 ? MapsKt.toMap(pairs, (Map)(new LinkedHashMap(MapsKt.mapCapacity(pairs.length)))) : MapsKt.emptyMap();
   }

   @InlineOnly
   private static final <K, V> Map<K, V> mapOf() {
      int $i$f$mapOf = 0;
      return MapsKt.emptyMap();
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final <K, V> Map<K, V> mutableMapOf() {
      int $i$f$mutableMapOf = 0;
      return (Map)(new LinkedHashMap());
   }

   @NotNull
   public static final <K, V> Map<K, V> mutableMapOf(@NotNull Pair<? extends K, ? extends V>... pairs) {
      Intrinsics.checkParameterIsNotNull(pairs, "pairs");
      LinkedHashMap var1 = new LinkedHashMap(MapsKt.mapCapacity(pairs.length));
      boolean var2 = false;
      boolean var3 = false;
      int var5 = false;
      MapsKt.putAll((Map)var1, pairs);
      return (Map)var1;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final <K, V> HashMap<K, V> hashMapOf() {
      int $i$f$hashMapOf = 0;
      return new HashMap();
   }

   @NotNull
   public static final <K, V> HashMap<K, V> hashMapOf(@NotNull Pair<? extends K, ? extends V>... pairs) {
      Intrinsics.checkParameterIsNotNull(pairs, "pairs");
      HashMap var1 = new HashMap(MapsKt.mapCapacity(pairs.length));
      boolean var2 = false;
      boolean var3 = false;
      int var5 = false;
      MapsKt.putAll((Map)var1, pairs);
      return var1;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final <K, V> LinkedHashMap<K, V> linkedMapOf() {
      int $i$f$linkedMapOf = 0;
      return new LinkedHashMap();
   }

   @NotNull
   public static final <K, V> LinkedHashMap<K, V> linkedMapOf(@NotNull Pair<? extends K, ? extends V>... pairs) {
      Intrinsics.checkParameterIsNotNull(pairs, "pairs");
      return (LinkedHashMap)MapsKt.toMap(pairs, (Map)(new LinkedHashMap(MapsKt.mapCapacity(pairs.length))));
   }

   @PublishedApi
   public static final int mapCapacity(int expectedSize) {
      if (expectedSize < 3) {
         return expectedSize + 1;
      } else {
         return expectedSize < 1073741824 ? expectedSize + expectedSize / 3 : Integer.MAX_VALUE;
      }
   }

   @InlineOnly
   private static final <K, V> boolean isNotEmpty(@NotNull Map<? extends K, ? extends V> $this$isNotEmpty) {
      int $i$f$isNotEmpty = 0;
      return !$this$isNotEmpty.isEmpty();
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final <K, V> boolean isNullOrEmpty(@Nullable Map<? extends K, ? extends V> $this$isNullOrEmpty) {
      int $i$f$isNullOrEmpty = 0;
      boolean var2 = false;
      return $this$isNullOrEmpty == null || $this$isNullOrEmpty.isEmpty();
   }

   @InlineOnly
   private static final <K, V> Map<K, V> orEmpty(@Nullable Map<K, ? extends V> $this$orEmpty) {
      int $i$f$orEmpty = 0;
      Map var10000 = $this$orEmpty;
      if ($this$orEmpty == null) {
         var10000 = MapsKt.emptyMap();
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final <M extends Map<?, ?> & R, R> R ifEmpty(M $this$ifEmpty, Function0<? extends R> defaultValue) {
      int $i$f$ifEmpty = 0;
      return $this$ifEmpty.isEmpty() ? defaultValue.invoke() : $this$ifEmpty;
   }

   @InlineOnly
   private static final <K, V> boolean contains(@NotNull Map<? extends K, ? extends V> $this$contains, K key) {
      int $i$f$contains = 0;
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      boolean var4 = false;
      return $this$contains.containsKey(key);
   }

   @InlineOnly
   private static final <K, V> V get(@NotNull Map<? extends K, ? extends V> $this$get, K key) {
      int $i$f$get = 0;
      Intrinsics.checkParameterIsNotNull($this$get, "$this$get");
      return $this$get.get(key);
   }

   @InlineOnly
   private static final <K, V> void set(@NotNull Map<K, V> $this$set, K key, V value) {
      int $i$f$set = 0;
      Intrinsics.checkParameterIsNotNull($this$set, "$this$set");
      $this$set.put(key, value);
   }

   @InlineOnly
   private static final <K> boolean containsKey(@NotNull Map<? extends K, ?> $this$containsKey, K key) {
      return $this$containsKey.containsKey(key);
   }

   @InlineOnly
   private static final <K, V> boolean containsValue(@NotNull Map<K, ? extends V> $this$containsValue, V value) {
      int $i$f$containsValue = 0;
      return $this$containsValue.containsValue(value);
   }

   @InlineOnly
   private static final <K, V> V remove(@NotNull Map<? extends K, V> $this$remove, K key) {
      return TypeIntrinsics.asMutableMap($this$remove).remove(key);
   }

   @InlineOnly
   private static final <K, V> K component1(@NotNull Entry<? extends K, ? extends V> $this$component1) {
      int $i$f$component1 = 0;
      Intrinsics.checkParameterIsNotNull($this$component1, "$this$component1");
      return $this$component1.getKey();
   }

   @InlineOnly
   private static final <K, V> V component2(@NotNull Entry<? extends K, ? extends V> $this$component2) {
      int $i$f$component2 = 0;
      Intrinsics.checkParameterIsNotNull($this$component2, "$this$component2");
      return $this$component2.getValue();
   }

   @InlineOnly
   private static final <K, V> Pair<K, V> toPair(@NotNull Entry<? extends K, ? extends V> $this$toPair) {
      int $i$f$toPair = 0;
      return new Pair($this$toPair.getKey(), $this$toPair.getValue());
   }

   @InlineOnly
   private static final <K, V> V getOrElse(@NotNull Map<K, ? extends V> $this$getOrElse, K key, Function0<? extends V> defaultValue) {
      int $i$f$getOrElse = 0;
      Object var10000 = $this$getOrElse.get(key);
      if (var10000 == null) {
         var10000 = defaultValue.invoke();
      }

      return var10000;
   }

   public static final <K, V> V getOrElseNullable(@NotNull Map<K, ? extends V> $this$getOrElseNullable, K key, @NotNull Function0<? extends V> defaultValue) {
      int $i$f$getOrElseNullable = 0;
      Intrinsics.checkParameterIsNotNull($this$getOrElseNullable, "$this$getOrElseNullable");
      Intrinsics.checkParameterIsNotNull(defaultValue, "defaultValue");
      Object value = $this$getOrElseNullable.get(key);
      return value == null && !$this$getOrElseNullable.containsKey(key) ? defaultValue.invoke() : value;
   }

   @SinceKotlin(
      version = "1.1"
   )
   public static final <K, V> V getValue(@NotNull Map<K, ? extends V> $this$getValue, K key) {
      Intrinsics.checkParameterIsNotNull($this$getValue, "$this$getValue");
      return MapsKt.getOrImplicitDefaultNullable($this$getValue, key);
   }

   public static final <K, V> V getOrPut(@NotNull Map<K, V> $this$getOrPut, K key, @NotNull Function0<? extends V> defaultValue) {
      int $i$f$getOrPut = 0;
      Intrinsics.checkParameterIsNotNull($this$getOrPut, "$this$getOrPut");
      Intrinsics.checkParameterIsNotNull(defaultValue, "defaultValue");
      Object value = $this$getOrPut.get(key);
      Object var10000;
      if (value == null) {
         Object answer = defaultValue.invoke();
         $this$getOrPut.put(key, answer);
         var10000 = answer;
      } else {
         var10000 = value;
      }

      return var10000;
   }

   @InlineOnly
   private static final <K, V> Iterator<Entry<K, V>> iterator(@NotNull Map<? extends K, ? extends V> $this$iterator) {
      int $i$f$iterator = 0;
      Intrinsics.checkParameterIsNotNull($this$iterator, "$this$iterator");
      return $this$iterator.entrySet().iterator();
   }

   @JvmName(
      name = "mutableIterator"
   )
   @InlineOnly
   private static final <K, V> Iterator<Entry<K, V>> mutableIterator(@NotNull Map<K, V> $this$iterator) {
      int $i$f$mutableIterator = 0;
      Intrinsics.checkParameterIsNotNull($this$iterator, "$this$iterator");
      return $this$iterator.entrySet().iterator();
   }

   @NotNull
   public static final <K, V, R, M extends Map<? super K, ? super R>> M mapValuesTo(@NotNull Map<? extends K, ? extends V> $this$mapValuesTo, @NotNull M destination, @NotNull Function1<? super Entry<? extends K, ? extends V>, ? extends R> transform) {
      int $i$f$mapValuesTo = 0;
      Intrinsics.checkParameterIsNotNull($this$mapValuesTo, "$this$mapValuesTo");
      Intrinsics.checkParameterIsNotNull(destination, "destination");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      Iterable $this$associateByTo$iv = (Iterable)$this$mapValuesTo.entrySet();
      int $i$f$associateByTo = false;
      Iterator var6 = $this$associateByTo$iv.iterator();

      while(var6.hasNext()) {
         Object element$iv = var6.next();
         Entry it = (Entry)element$iv;
         int var9 = false;
         Object var11 = it.getKey();
         destination.put(var11, transform.invoke(element$iv));
      }

      return destination;
   }

   @NotNull
   public static final <K, V, R, M extends Map<? super R, ? super V>> M mapKeysTo(@NotNull Map<? extends K, ? extends V> $this$mapKeysTo, @NotNull M destination, @NotNull Function1<? super Entry<? extends K, ? extends V>, ? extends R> transform) {
      int $i$f$mapKeysTo = 0;
      Intrinsics.checkParameterIsNotNull($this$mapKeysTo, "$this$mapKeysTo");
      Intrinsics.checkParameterIsNotNull(destination, "destination");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      Iterable $this$associateByTo$iv = (Iterable)$this$mapKeysTo.entrySet();
      int $i$f$associateByTo = false;
      Iterator var6 = $this$associateByTo$iv.iterator();

      while(var6.hasNext()) {
         Object element$iv = var6.next();
         Object var10001 = transform.invoke(element$iv);
         Entry it = (Entry)element$iv;
         Object var11 = var10001;
         int var9 = false;
         Object var12 = it.getValue();
         destination.put(var11, var12);
      }

      return destination;
   }

   public static final <K, V> void putAll(@NotNull Map<? super K, ? super V> $this$putAll, @NotNull Pair<? extends K, ? extends V>[] pairs) {
      Intrinsics.checkParameterIsNotNull($this$putAll, "$this$putAll");
      Intrinsics.checkParameterIsNotNull(pairs, "pairs");
      Pair[] var4 = pairs;
      int var5 = pairs.length;

      for(int var3 = 0; var3 < var5; ++var3) {
         Pair var2 = var4[var3];
         Object key = var2.component1();
         Object value = var2.component2();
         $this$putAll.put(key, value);
      }

   }

   public static final <K, V> void putAll(@NotNull Map<? super K, ? super V> $this$putAll, @NotNull Iterable<? extends Pair<? extends K, ? extends V>> pairs) {
      Intrinsics.checkParameterIsNotNull($this$putAll, "$this$putAll");
      Intrinsics.checkParameterIsNotNull(pairs, "pairs");
      Iterator var3 = pairs.iterator();

      while(var3.hasNext()) {
         Pair var2 = (Pair)var3.next();
         Object key = var2.component1();
         Object value = var2.component2();
         $this$putAll.put(key, value);
      }

   }

   public static final <K, V> void putAll(@NotNull Map<? super K, ? super V> $this$putAll, @NotNull Sequence<? extends Pair<? extends K, ? extends V>> pairs) {
      Intrinsics.checkParameterIsNotNull($this$putAll, "$this$putAll");
      Intrinsics.checkParameterIsNotNull(pairs, "pairs");
      Iterator var3 = pairs.iterator();

      while(var3.hasNext()) {
         Pair var2 = (Pair)var3.next();
         Object key = var2.component1();
         Object value = var2.component2();
         $this$putAll.put(key, value);
      }

   }

   @NotNull
   public static final <K, V, R> Map<K, R> mapValues(@NotNull Map<? extends K, ? extends V> $this$mapValues, @NotNull Function1<? super Entry<? extends K, ? extends V>, ? extends R> transform) {
      int $i$f$mapValues = 0;
      Intrinsics.checkParameterIsNotNull($this$mapValues, "$this$mapValues");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      Map destination$iv = (Map)(new LinkedHashMap(MapsKt.mapCapacity($this$mapValues.size())));
      int $i$f$mapValuesTo = false;
      Iterable $this$associateByTo$iv$iv = (Iterable)$this$mapValues.entrySet();
      int $i$f$associateByTo = false;
      Iterator var8 = $this$associateByTo$iv$iv.iterator();

      while(var8.hasNext()) {
         Object element$iv$iv = var8.next();
         Entry it$iv = (Entry)element$iv$iv;
         int var12 = false;
         Object var13 = it$iv.getKey();
         destination$iv.put(var13, transform.invoke(element$iv$iv));
      }

      return destination$iv;
   }

   @NotNull
   public static final <K, V, R> Map<R, V> mapKeys(@NotNull Map<? extends K, ? extends V> $this$mapKeys, @NotNull Function1<? super Entry<? extends K, ? extends V>, ? extends R> transform) {
      int $i$f$mapKeys = 0;
      Intrinsics.checkParameterIsNotNull($this$mapKeys, "$this$mapKeys");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      Map destination$iv = (Map)(new LinkedHashMap(MapsKt.mapCapacity($this$mapKeys.size())));
      int $i$f$mapKeysTo = false;
      Iterable $this$associateByTo$iv$iv = (Iterable)$this$mapKeys.entrySet();
      int $i$f$associateByTo = false;
      Iterator var8 = $this$associateByTo$iv$iv.iterator();

      while(var8.hasNext()) {
         Object element$iv$iv = var8.next();
         Object var10001 = transform.invoke(element$iv$iv);
         Entry it$iv = (Entry)element$iv$iv;
         Object var11 = var10001;
         int var13 = false;
         Object var14 = it$iv.getValue();
         destination$iv.put(var11, var14);
      }

      return destination$iv;
   }

   @NotNull
   public static final <K, V> Map<K, V> filterKeys(@NotNull Map<? extends K, ? extends V> $this$filterKeys, @NotNull Function1<? super K, Boolean> predicate) {
      int $i$f$filterKeys = 0;
      Intrinsics.checkParameterIsNotNull($this$filterKeys, "$this$filterKeys");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      LinkedHashMap result = new LinkedHashMap();
      boolean var7 = false;
      Iterator var5 = $this$filterKeys.entrySet().iterator();

      while(var5.hasNext()) {
         Entry entry = (Entry)var5.next();
         if ((Boolean)predicate.invoke(entry.getKey())) {
            result.put(entry.getKey(), entry.getValue());
         }
      }

      return (Map)result;
   }

   @NotNull
   public static final <K, V> Map<K, V> filterValues(@NotNull Map<? extends K, ? extends V> $this$filterValues, @NotNull Function1<? super V, Boolean> predicate) {
      int $i$f$filterValues = 0;
      Intrinsics.checkParameterIsNotNull($this$filterValues, "$this$filterValues");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      LinkedHashMap result = new LinkedHashMap();
      boolean var7 = false;
      Iterator var5 = $this$filterValues.entrySet().iterator();

      while(var5.hasNext()) {
         Entry entry = (Entry)var5.next();
         if ((Boolean)predicate.invoke(entry.getValue())) {
            result.put(entry.getKey(), entry.getValue());
         }
      }

      return (Map)result;
   }

   @NotNull
   public static final <K, V, M extends Map<? super K, ? super V>> M filterTo(@NotNull Map<? extends K, ? extends V> $this$filterTo, @NotNull M destination, @NotNull Function1<? super Entry<? extends K, ? extends V>, Boolean> predicate) {
      int $i$f$filterTo = 0;
      Intrinsics.checkParameterIsNotNull($this$filterTo, "$this$filterTo");
      Intrinsics.checkParameterIsNotNull(destination, "destination");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      boolean var7 = false;
      Iterator var5 = $this$filterTo.entrySet().iterator();

      while(var5.hasNext()) {
         Entry element = (Entry)var5.next();
         if ((Boolean)predicate.invoke(element)) {
            destination.put(element.getKey(), element.getValue());
         }
      }

      return destination;
   }

   @NotNull
   public static final <K, V> Map<K, V> filter(@NotNull Map<? extends K, ? extends V> $this$filter, @NotNull Function1<? super Entry<? extends K, ? extends V>, Boolean> predicate) {
      int $i$f$filter = 0;
      Intrinsics.checkParameterIsNotNull($this$filter, "$this$filter");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      Map destination$iv = (Map)(new LinkedHashMap());
      int $i$f$filterTo = false;
      boolean var7 = false;
      Iterator var8 = $this$filter.entrySet().iterator();

      while(var8.hasNext()) {
         Entry element$iv = (Entry)var8.next();
         if ((Boolean)predicate.invoke(element$iv)) {
            destination$iv.put(element$iv.getKey(), element$iv.getValue());
         }
      }

      return destination$iv;
   }

   @NotNull
   public static final <K, V, M extends Map<? super K, ? super V>> M filterNotTo(@NotNull Map<? extends K, ? extends V> $this$filterNotTo, @NotNull M destination, @NotNull Function1<? super Entry<? extends K, ? extends V>, Boolean> predicate) {
      int $i$f$filterNotTo = 0;
      Intrinsics.checkParameterIsNotNull($this$filterNotTo, "$this$filterNotTo");
      Intrinsics.checkParameterIsNotNull(destination, "destination");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      boolean var7 = false;
      Iterator var5 = $this$filterNotTo.entrySet().iterator();

      while(var5.hasNext()) {
         Entry element = (Entry)var5.next();
         if (!(Boolean)predicate.invoke(element)) {
            destination.put(element.getKey(), element.getValue());
         }
      }

      return destination;
   }

   @NotNull
   public static final <K, V> Map<K, V> filterNot(@NotNull Map<? extends K, ? extends V> $this$filterNot, @NotNull Function1<? super Entry<? extends K, ? extends V>, Boolean> predicate) {
      int $i$f$filterNot = 0;
      Intrinsics.checkParameterIsNotNull($this$filterNot, "$this$filterNot");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      Map destination$iv = (Map)(new LinkedHashMap());
      int $i$f$filterNotTo = false;
      boolean var7 = false;
      Iterator var8 = $this$filterNot.entrySet().iterator();

      while(var8.hasNext()) {
         Entry element$iv = (Entry)var8.next();
         if (!(Boolean)predicate.invoke(element$iv)) {
            destination$iv.put(element$iv.getKey(), element$iv.getValue());
         }
      }

      return destination$iv;
   }

   @NotNull
   public static final <K, V> Map<K, V> toMap(@NotNull Iterable<? extends Pair<? extends K, ? extends V>> $this$toMap) {
      Intrinsics.checkParameterIsNotNull($this$toMap, "$this$toMap");
      if ($this$toMap instanceof Collection) {
         Map var10000;
         switch(((Collection)$this$toMap).size()) {
         case 0:
            var10000 = MapsKt.emptyMap();
            break;
         case 1:
            var10000 = MapsKt.mapOf($this$toMap instanceof List ? (Pair)((List)$this$toMap).get(0) : (Pair)$this$toMap.iterator().next());
            break;
         default:
            var10000 = MapsKt.toMap($this$toMap, (Map)(new LinkedHashMap(MapsKt.mapCapacity(((Collection)$this$toMap).size()))));
         }

         return var10000;
      } else {
         return MapsKt.optimizeReadOnlyMap(MapsKt.toMap($this$toMap, (Map)(new LinkedHashMap())));
      }
   }

   @NotNull
   public static final <K, V, M extends Map<? super K, ? super V>> M toMap(@NotNull Iterable<? extends Pair<? extends K, ? extends V>> $this$toMap, @NotNull M destination) {
      Intrinsics.checkParameterIsNotNull($this$toMap, "$this$toMap");
      Intrinsics.checkParameterIsNotNull(destination, "destination");
      boolean var3 = false;
      boolean var4 = false;
      int var6 = false;
      MapsKt.putAll(destination, $this$toMap);
      return destination;
   }

   @NotNull
   public static final <K, V> Map<K, V> toMap(@NotNull Pair<? extends K, ? extends V>[] $this$toMap) {
      Intrinsics.checkParameterIsNotNull($this$toMap, "$this$toMap");
      Map var10000;
      switch($this$toMap.length) {
      case 0:
         var10000 = MapsKt.emptyMap();
         break;
      case 1:
         var10000 = MapsKt.mapOf($this$toMap[0]);
         break;
      default:
         var10000 = MapsKt.toMap($this$toMap, (Map)(new LinkedHashMap(MapsKt.mapCapacity($this$toMap.length))));
      }

      return var10000;
   }

   @NotNull
   public static final <K, V, M extends Map<? super K, ? super V>> M toMap(@NotNull Pair<? extends K, ? extends V>[] $this$toMap, @NotNull M destination) {
      Intrinsics.checkParameterIsNotNull($this$toMap, "$this$toMap");
      Intrinsics.checkParameterIsNotNull(destination, "destination");
      boolean var3 = false;
      boolean var4 = false;
      int var6 = false;
      MapsKt.putAll(destination, $this$toMap);
      return destination;
   }

   @NotNull
   public static final <K, V> Map<K, V> toMap(@NotNull Sequence<? extends Pair<? extends K, ? extends V>> $this$toMap) {
      Intrinsics.checkParameterIsNotNull($this$toMap, "$this$toMap");
      return MapsKt.optimizeReadOnlyMap(MapsKt.toMap($this$toMap, (Map)(new LinkedHashMap())));
   }

   @NotNull
   public static final <K, V, M extends Map<? super K, ? super V>> M toMap(@NotNull Sequence<? extends Pair<? extends K, ? extends V>> $this$toMap, @NotNull M destination) {
      Intrinsics.checkParameterIsNotNull($this$toMap, "$this$toMap");
      Intrinsics.checkParameterIsNotNull(destination, "destination");
      boolean var3 = false;
      boolean var4 = false;
      int var6 = false;
      MapsKt.putAll(destination, $this$toMap);
      return destination;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final <K, V> Map<K, V> toMap(@NotNull Map<? extends K, ? extends V> $this$toMap) {
      Intrinsics.checkParameterIsNotNull($this$toMap, "$this$toMap");
      Map var10000;
      switch($this$toMap.size()) {
      case 0:
         var10000 = MapsKt.emptyMap();
         break;
      case 1:
         var10000 = MapsKt.toSingletonMap($this$toMap);
         break;
      default:
         var10000 = MapsKt.toMutableMap($this$toMap);
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final <K, V> Map<K, V> toMutableMap(@NotNull Map<? extends K, ? extends V> $this$toMutableMap) {
      Intrinsics.checkParameterIsNotNull($this$toMutableMap, "$this$toMutableMap");
      return (Map)(new LinkedHashMap($this$toMutableMap));
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final <K, V, M extends Map<? super K, ? super V>> M toMap(@NotNull Map<? extends K, ? extends V> $this$toMap, @NotNull M destination) {
      Intrinsics.checkParameterIsNotNull($this$toMap, "$this$toMap");
      Intrinsics.checkParameterIsNotNull(destination, "destination");
      boolean var3 = false;
      boolean var4 = false;
      int var6 = false;
      destination.putAll($this$toMap);
      return destination;
   }

   @NotNull
   public static final <K, V> Map<K, V> plus(@NotNull Map<? extends K, ? extends V> $this$plus, @NotNull Pair<? extends K, ? extends V> pair) {
      Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
      Intrinsics.checkParameterIsNotNull(pair, "pair");
      Map var10000;
      if ($this$plus.isEmpty()) {
         var10000 = MapsKt.mapOf(pair);
      } else {
         LinkedHashMap var2 = new LinkedHashMap($this$plus);
         boolean var3 = false;
         boolean var4 = false;
         int var6 = false;
         var2.put(pair.getFirst(), pair.getSecond());
         var10000 = (Map)var2;
      }

      return var10000;
   }

   @NotNull
   public static final <K, V> Map<K, V> plus(@NotNull Map<? extends K, ? extends V> $this$plus, @NotNull Iterable<? extends Pair<? extends K, ? extends V>> pairs) {
      Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
      Intrinsics.checkParameterIsNotNull(pairs, "pairs");
      Map var10000;
      if ($this$plus.isEmpty()) {
         var10000 = MapsKt.toMap(pairs);
      } else {
         LinkedHashMap var2 = new LinkedHashMap($this$plus);
         boolean var3 = false;
         boolean var4 = false;
         int var6 = false;
         MapsKt.putAll((Map)var2, pairs);
         var10000 = (Map)var2;
      }

      return var10000;
   }

   @NotNull
   public static final <K, V> Map<K, V> plus(@NotNull Map<? extends K, ? extends V> $this$plus, @NotNull Pair<? extends K, ? extends V>[] pairs) {
      Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
      Intrinsics.checkParameterIsNotNull(pairs, "pairs");
      Map var10000;
      if ($this$plus.isEmpty()) {
         var10000 = MapsKt.toMap(pairs);
      } else {
         LinkedHashMap var2 = new LinkedHashMap($this$plus);
         boolean var3 = false;
         boolean var4 = false;
         int var6 = false;
         MapsKt.putAll((Map)var2, pairs);
         var10000 = (Map)var2;
      }

      return var10000;
   }

   @NotNull
   public static final <K, V> Map<K, V> plus(@NotNull Map<? extends K, ? extends V> $this$plus, @NotNull Sequence<? extends Pair<? extends K, ? extends V>> pairs) {
      Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
      Intrinsics.checkParameterIsNotNull(pairs, "pairs");
      LinkedHashMap var2 = new LinkedHashMap($this$plus);
      boolean var3 = false;
      boolean var4 = false;
      int var6 = false;
      MapsKt.putAll((Map)var2, pairs);
      return MapsKt.optimizeReadOnlyMap((Map)var2);
   }

   @NotNull
   public static final <K, V> Map<K, V> plus(@NotNull Map<? extends K, ? extends V> $this$plus, @NotNull Map<? extends K, ? extends V> map) {
      Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
      Intrinsics.checkParameterIsNotNull(map, "map");
      LinkedHashMap var2 = new LinkedHashMap($this$plus);
      boolean var3 = false;
      boolean var4 = false;
      int var6 = false;
      var2.putAll(map);
      return (Map)var2;
   }

   @InlineOnly
   private static final <K, V> void plusAssign(@NotNull Map<? super K, ? super V> $this$plusAssign, Pair<? extends K, ? extends V> pair) {
      int $i$f$plusAssign = 0;
      Intrinsics.checkParameterIsNotNull($this$plusAssign, "$this$plusAssign");
      $this$plusAssign.put(pair.getFirst(), pair.getSecond());
   }

   @InlineOnly
   private static final <K, V> void plusAssign(@NotNull Map<? super K, ? super V> $this$plusAssign, Iterable<? extends Pair<? extends K, ? extends V>> pairs) {
      int $i$f$plusAssign = 0;
      Intrinsics.checkParameterIsNotNull($this$plusAssign, "$this$plusAssign");
      MapsKt.putAll($this$plusAssign, pairs);
   }

   @InlineOnly
   private static final <K, V> void plusAssign(@NotNull Map<? super K, ? super V> $this$plusAssign, Pair<? extends K, ? extends V>[] pairs) {
      int $i$f$plusAssign = 0;
      Intrinsics.checkParameterIsNotNull($this$plusAssign, "$this$plusAssign");
      MapsKt.putAll($this$plusAssign, pairs);
   }

   @InlineOnly
   private static final <K, V> void plusAssign(@NotNull Map<? super K, ? super V> $this$plusAssign, Sequence<? extends Pair<? extends K, ? extends V>> pairs) {
      int $i$f$plusAssign = 0;
      Intrinsics.checkParameterIsNotNull($this$plusAssign, "$this$plusAssign");
      MapsKt.putAll($this$plusAssign, pairs);
   }

   @InlineOnly
   private static final <K, V> void plusAssign(@NotNull Map<? super K, ? super V> $this$plusAssign, Map<K, ? extends V> map) {
      int $i$f$plusAssign = 0;
      Intrinsics.checkParameterIsNotNull($this$plusAssign, "$this$plusAssign");
      $this$plusAssign.putAll(map);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final <K, V> Map<K, V> minus(@NotNull Map<? extends K, ? extends V> $this$minus, K key) {
      Intrinsics.checkParameterIsNotNull($this$minus, "$this$minus");
      Map var2 = MapsKt.toMutableMap($this$minus);
      boolean var3 = false;
      boolean var4 = false;
      int var6 = false;
      boolean var9 = false;
      var2.remove(key);
      return MapsKt.optimizeReadOnlyMap(var2);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final <K, V> Map<K, V> minus(@NotNull Map<? extends K, ? extends V> $this$minus, @NotNull Iterable<? extends K> keys) {
      Intrinsics.checkParameterIsNotNull($this$minus, "$this$minus");
      Intrinsics.checkParameterIsNotNull(keys, "keys");
      Map var2 = MapsKt.toMutableMap($this$minus);
      boolean var3 = false;
      boolean var4 = false;
      int var6 = false;
      boolean var9 = false;
      CollectionsKt.removeAll((Collection)var2.keySet(), keys);
      return MapsKt.optimizeReadOnlyMap(var2);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final <K, V> Map<K, V> minus(@NotNull Map<? extends K, ? extends V> $this$minus, @NotNull K[] keys) {
      Intrinsics.checkParameterIsNotNull($this$minus, "$this$minus");
      Intrinsics.checkParameterIsNotNull(keys, "keys");
      Map var2 = MapsKt.toMutableMap($this$minus);
      boolean var3 = false;
      boolean var4 = false;
      int var6 = false;
      boolean var9 = false;
      CollectionsKt.removeAll((Collection)var2.keySet(), keys);
      return MapsKt.optimizeReadOnlyMap(var2);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final <K, V> Map<K, V> minus(@NotNull Map<? extends K, ? extends V> $this$minus, @NotNull Sequence<? extends K> keys) {
      Intrinsics.checkParameterIsNotNull($this$minus, "$this$minus");
      Intrinsics.checkParameterIsNotNull(keys, "keys");
      Map var2 = MapsKt.toMutableMap($this$minus);
      boolean var3 = false;
      boolean var4 = false;
      int var6 = false;
      boolean var9 = false;
      CollectionsKt.removeAll((Collection)var2.keySet(), keys);
      return MapsKt.optimizeReadOnlyMap(var2);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final <K, V> void minusAssign(@NotNull Map<K, V> $this$minusAssign, K key) {
      int $i$f$minusAssign = 0;
      Intrinsics.checkParameterIsNotNull($this$minusAssign, "$this$minusAssign");
      $this$minusAssign.remove(key);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final <K, V> void minusAssign(@NotNull Map<K, V> $this$minusAssign, Iterable<? extends K> keys) {
      int $i$f$minusAssign = 0;
      Intrinsics.checkParameterIsNotNull($this$minusAssign, "$this$minusAssign");
      CollectionsKt.removeAll((Collection)$this$minusAssign.keySet(), keys);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final <K, V> void minusAssign(@NotNull Map<K, V> $this$minusAssign, K[] keys) {
      int $i$f$minusAssign = 0;
      Intrinsics.checkParameterIsNotNull($this$minusAssign, "$this$minusAssign");
      CollectionsKt.removeAll((Collection)$this$minusAssign.keySet(), keys);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final <K, V> void minusAssign(@NotNull Map<K, V> $this$minusAssign, Sequence<? extends K> keys) {
      int $i$f$minusAssign = 0;
      Intrinsics.checkParameterIsNotNull($this$minusAssign, "$this$minusAssign");
      CollectionsKt.removeAll((Collection)$this$minusAssign.keySet(), keys);
   }

   @NotNull
   public static final <K, V> Map<K, V> optimizeReadOnlyMap(@NotNull Map<K, ? extends V> $this$optimizeReadOnlyMap) {
      Intrinsics.checkParameterIsNotNull($this$optimizeReadOnlyMap, "$this$optimizeReadOnlyMap");
      Map var10000;
      switch($this$optimizeReadOnlyMap.size()) {
      case 0:
         var10000 = MapsKt.emptyMap();
         break;
      case 1:
         boolean var2 = false;
         var10000 = MapsKt.toSingletonMap($this$optimizeReadOnlyMap);
         break;
      default:
         var10000 = $this$optimizeReadOnlyMap;
      }

      return var10000;
   }

   public MapsKt__MapsKt() {
   }
}
