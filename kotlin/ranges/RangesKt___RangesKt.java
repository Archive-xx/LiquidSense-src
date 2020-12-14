package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.random.RandomKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000n\n\u0002\b\u0002\n\u0002\u0010\u000f\n\u0002\b\u0002\n\u0002\u0010\u0005\n\u0002\u0010\u0006\n\u0002\u0010\u0007\n\u0002\u0010\b\n\u0002\u0010\t\n\u0002\u0010\n\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\f\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0016\u001a'\u0010\u0000\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\u0006\u0010\u0003\u001a\u0002H\u0001¢\u0006\u0002\u0010\u0004\u001a\u0012\u0010\u0000\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0005\u001a\u0012\u0010\u0000\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0006\u001a\u0012\u0010\u0000\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0007\u001a\u0012\u0010\u0000\u001a\u00020\b*\u00020\b2\u0006\u0010\u0003\u001a\u00020\b\u001a\u0012\u0010\u0000\u001a\u00020\t*\u00020\t2\u0006\u0010\u0003\u001a\u00020\t\u001a\u0012\u0010\u0000\u001a\u00020\n*\u00020\n2\u0006\u0010\u0003\u001a\u00020\n\u001a'\u0010\u000b\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\u0006\u0010\f\u001a\u0002H\u0001¢\u0006\u0002\u0010\u0004\u001a\u0012\u0010\u000b\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005\u001a\u0012\u0010\u000b\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006\u001a\u0012\u0010\u000b\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\f\u001a\u00020\u0007\u001a\u0012\u0010\u000b\u001a\u00020\b*\u00020\b2\u0006\u0010\f\u001a\u00020\b\u001a\u0012\u0010\u000b\u001a\u00020\t*\u00020\t2\u0006\u0010\f\u001a\u00020\t\u001a\u0012\u0010\u000b\u001a\u00020\n*\u00020\n2\u0006\u0010\f\u001a\u00020\n\u001a3\u0010\r\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\b\u0010\u0003\u001a\u0004\u0018\u0001H\u00012\b\u0010\f\u001a\u0004\u0018\u0001H\u0001¢\u0006\u0002\u0010\u000e\u001a/\u0010\r\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0010H\u0007¢\u0006\u0002\u0010\u0011\u001a-\u0010\r\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0012¢\u0006\u0002\u0010\u0013\u001a\u001a\u0010\r\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005\u001a\u001a\u0010\r\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006\u001a\u001a\u0010\r\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u0007\u001a\u001a\u0010\r\u001a\u00020\b*\u00020\b2\u0006\u0010\u0003\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\b\u001a\u0018\u0010\r\u001a\u00020\b*\u00020\b2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\b0\u0012\u001a\u001a\u0010\r\u001a\u00020\t*\u00020\t2\u0006\u0010\u0003\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\t\u001a\u0018\u0010\r\u001a\u00020\t*\u00020\t2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\t0\u0012\u001a\u001a\u0010\r\u001a\u00020\n*\u00020\n2\u0006\u0010\u0003\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n\u001a\u001c\u0010\u0014\u001a\u00020\u0015*\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0087\n¢\u0006\u0002\u0010\u0019\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b \u001a\u001c\u0010\u0014\u001a\u00020\u0015*\u00020!2\b\u0010\u0017\u001a\u0004\u0018\u00010\bH\u0087\n¢\u0006\u0002\u0010\"\u001a\u001c\u0010\u0014\u001a\u00020\u0015*\u00020#2\b\u0010\u0017\u001a\u0004\u0018\u00010\tH\u0087\n¢\u0006\u0002\u0010$\u001a\u0015\u0010%\u001a\u00020&*\u00020\u00052\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\u00052\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\u00052\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\u00052\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020)*\u00020\u00182\u0006\u0010'\u001a\u00020\u0018H\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\b2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\b2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\b2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\b2\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\t2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\t2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\t2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\t2\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\n2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\n2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\n2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\n2\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\r\u0010*\u001a\u00020\u0018*\u00020\u0016H\u0087\b\u001a\u0014\u0010*\u001a\u00020\u0018*\u00020\u00162\u0006\u0010*\u001a\u00020+H\u0007\u001a\r\u0010*\u001a\u00020\b*\u00020!H\u0087\b\u001a\u0014\u0010*\u001a\u00020\b*\u00020!2\u0006\u0010*\u001a\u00020+H\u0007\u001a\r\u0010*\u001a\u00020\t*\u00020#H\u0087\b\u001a\u0014\u0010*\u001a\u00020\t*\u00020#2\u0006\u0010*\u001a\u00020+H\u0007\u001a\n\u0010,\u001a\u00020)*\u00020)\u001a\n\u0010,\u001a\u00020&*\u00020&\u001a\n\u0010,\u001a\u00020(*\u00020(\u001a\u0015\u0010-\u001a\u00020)*\u00020)2\u0006\u0010-\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010-\u001a\u00020&*\u00020&2\u0006\u0010-\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010-\u001a\u00020(*\u00020(2\u0006\u0010-\u001a\u00020\tH\u0086\u0004\u001a\u0013\u0010.\u001a\u0004\u0018\u00010\u0005*\u00020\u0006H\u0000¢\u0006\u0002\u0010/\u001a\u0013\u0010.\u001a\u0004\u0018\u00010\u0005*\u00020\u0007H\u0000¢\u0006\u0002\u00100\u001a\u0013\u0010.\u001a\u0004\u0018\u00010\u0005*\u00020\bH\u0000¢\u0006\u0002\u00101\u001a\u0013\u0010.\u001a\u0004\u0018\u00010\u0005*\u00020\tH\u0000¢\u0006\u0002\u00102\u001a\u0013\u0010.\u001a\u0004\u0018\u00010\u0005*\u00020\nH\u0000¢\u0006\u0002\u00103\u001a\u0013\u00104\u001a\u0004\u0018\u00010\b*\u00020\u0006H\u0000¢\u0006\u0002\u00105\u001a\u0013\u00104\u001a\u0004\u0018\u00010\b*\u00020\u0007H\u0000¢\u0006\u0002\u00106\u001a\u0013\u00104\u001a\u0004\u0018\u00010\b*\u00020\tH\u0000¢\u0006\u0002\u00107\u001a\u0013\u00108\u001a\u0004\u0018\u00010\t*\u00020\u0006H\u0000¢\u0006\u0002\u00109\u001a\u0013\u00108\u001a\u0004\u0018\u00010\t*\u00020\u0007H\u0000¢\u0006\u0002\u0010:\u001a\u0013\u0010;\u001a\u0004\u0018\u00010\n*\u00020\u0006H\u0000¢\u0006\u0002\u0010<\u001a\u0013\u0010;\u001a\u0004\u0018\u00010\n*\u00020\u0007H\u0000¢\u0006\u0002\u0010=\u001a\u0013\u0010;\u001a\u0004\u0018\u00010\n*\u00020\bH\u0000¢\u0006\u0002\u0010>\u001a\u0013\u0010;\u001a\u0004\u0018\u00010\n*\u00020\tH\u0000¢\u0006\u0002\u0010?\u001a\u0015\u0010@\u001a\u00020!*\u00020\u00052\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\u00052\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020#*\u00020\u00052\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\u00052\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020\u0016*\u00020\u00182\u0006\u0010'\u001a\u00020\u0018H\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\b2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\b2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020#*\u00020\b2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\b2\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020#*\u00020\t2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010@\u001a\u00020#*\u00020\t2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020#*\u00020\t2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020#*\u00020\t2\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\n2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\n2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020#*\u00020\n2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\n2\u0006\u0010'\u001a\u00020\nH\u0086\u0004¨\u0006A"},
   d2 = {"coerceAtLeast", "T", "", "minimumValue", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "", "", "", "", "", "", "coerceAtMost", "maximumValue", "coerceIn", "(Ljava/lang/Comparable;Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "range", "Lkotlin/ranges/ClosedFloatingPointRange;", "(Ljava/lang/Comparable;Lkotlin/ranges/ClosedFloatingPointRange;)Ljava/lang/Comparable;", "Lkotlin/ranges/ClosedRange;", "(Ljava/lang/Comparable;Lkotlin/ranges/ClosedRange;)Ljava/lang/Comparable;", "contains", "", "Lkotlin/ranges/CharRange;", "element", "", "(Lkotlin/ranges/CharRange;Ljava/lang/Character;)Z", "value", "byteRangeContains", "doubleRangeContains", "floatRangeContains", "intRangeContains", "longRangeContains", "shortRangeContains", "Lkotlin/ranges/IntRange;", "(Lkotlin/ranges/IntRange;Ljava/lang/Integer;)Z", "Lkotlin/ranges/LongRange;", "(Lkotlin/ranges/LongRange;Ljava/lang/Long;)Z", "downTo", "Lkotlin/ranges/IntProgression;", "to", "Lkotlin/ranges/LongProgression;", "Lkotlin/ranges/CharProgression;", "random", "Lkotlin/random/Random;", "reversed", "step", "toByteExactOrNull", "(D)Ljava/lang/Byte;", "(F)Ljava/lang/Byte;", "(I)Ljava/lang/Byte;", "(J)Ljava/lang/Byte;", "(S)Ljava/lang/Byte;", "toIntExactOrNull", "(D)Ljava/lang/Integer;", "(F)Ljava/lang/Integer;", "(J)Ljava/lang/Integer;", "toLongExactOrNull", "(D)Ljava/lang/Long;", "(F)Ljava/lang/Long;", "toShortExactOrNull", "(D)Ljava/lang/Short;", "(F)Ljava/lang/Short;", "(I)Ljava/lang/Short;", "(J)Ljava/lang/Short;", "until", "kotlin-stdlib"},
   xs = "kotlin/ranges/RangesKt"
)
class RangesKt___RangesKt extends RangesKt__RangesKt {
   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final int random(@NotNull IntRange $this$random) {
      int $i$f$random = 0;
      return RangesKt.random($this$random, (Random)Random.Default);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final long random(@NotNull LongRange $this$random) {
      int $i$f$random = 0;
      return RangesKt.random($this$random, (Random)Random.Default);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final char random(@NotNull CharRange $this$random) {
      int $i$f$random = 0;
      return RangesKt.random($this$random, (Random)Random.Default);
   }

   @SinceKotlin(
      version = "1.3"
   )
   public static final int random(@NotNull IntRange $this$random, @NotNull Random random) {
      Intrinsics.checkParameterIsNotNull($this$random, "$this$random");
      Intrinsics.checkParameterIsNotNull(random, "random");

      try {
         return RandomKt.nextInt(random, $this$random);
      } catch (IllegalArgumentException var3) {
         throw (Throwable)(new NoSuchElementException(var3.getMessage()));
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   public static final long random(@NotNull LongRange $this$random, @NotNull Random random) {
      Intrinsics.checkParameterIsNotNull($this$random, "$this$random");
      Intrinsics.checkParameterIsNotNull(random, "random");

      try {
         return RandomKt.nextLong(random, $this$random);
      } catch (IllegalArgumentException var3) {
         throw (Throwable)(new NoSuchElementException(var3.getMessage()));
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   public static final char random(@NotNull CharRange $this$random, @NotNull Random random) {
      Intrinsics.checkParameterIsNotNull($this$random, "$this$random");
      Intrinsics.checkParameterIsNotNull(random, "random");

      try {
         return (char)random.nextInt($this$random.getFirst(), $this$random.getLast() + 1);
      } catch (IllegalArgumentException var3) {
         throw (Throwable)(new NoSuchElementException(var3.getMessage()));
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final boolean contains(@NotNull IntRange $this$contains, Integer element) {
      int $i$f$contains = 0;
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      return element != null && $this$contains.contains(element);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final boolean contains(@NotNull LongRange $this$contains, Long element) {
      int $i$f$contains = 0;
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      return element != null && $this$contains.contains(element);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final boolean contains(@NotNull CharRange $this$contains, Character element) {
      int $i$f$contains = 0;
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      return element != null && $this$contains.contains(element);
   }

   @JvmName(
      name = "intRangeContains"
   )
   public static final boolean intRangeContains(@NotNull ClosedRange<Integer> $this$contains, byte value) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      return $this$contains.contains((Comparable)Integer.valueOf(value));
   }

   @JvmName(
      name = "longRangeContains"
   )
   public static final boolean longRangeContains(@NotNull ClosedRange<Long> $this$contains, byte value) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      return $this$contains.contains((Comparable)(long)value);
   }

   @JvmName(
      name = "shortRangeContains"
   )
   public static final boolean shortRangeContains(@NotNull ClosedRange<Short> $this$contains, byte value) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      return $this$contains.contains((Comparable)(short)value);
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "doubleRangeContains"
   )
   public static final boolean doubleRangeContains(@NotNull ClosedRange<Double> $this$contains, byte value) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      return $this$contains.contains((Comparable)(double)value);
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "floatRangeContains"
   )
   public static final boolean floatRangeContains(@NotNull ClosedRange<Float> $this$contains, byte value) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      return $this$contains.contains((Comparable)(float)value);
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "intRangeContains"
   )
   public static final boolean intRangeContains(@NotNull ClosedRange<Integer> $this$contains, double value) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      Integer var3 = RangesKt.toIntExactOrNull(value);
      boolean var4 = false;
      boolean var5 = false;
      int var7 = false;
      return var3 != null ? $this$contains.contains((Comparable)var3) : false;
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "longRangeContains"
   )
   public static final boolean longRangeContains(@NotNull ClosedRange<Long> $this$contains, double value) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      Long var3 = RangesKt.toLongExactOrNull(value);
      boolean var4 = false;
      boolean var5 = false;
      int var7 = false;
      return var3 != null ? $this$contains.contains((Comparable)var3) : false;
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "byteRangeContains"
   )
   public static final boolean byteRangeContains(@NotNull ClosedRange<Byte> $this$contains, double value) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      Byte var3 = RangesKt.toByteExactOrNull(value);
      boolean var4 = false;
      boolean var5 = false;
      int var7 = false;
      return var3 != null ? $this$contains.contains((Comparable)var3) : false;
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "shortRangeContains"
   )
   public static final boolean shortRangeContains(@NotNull ClosedRange<Short> $this$contains, double value) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      Short var3 = RangesKt.toShortExactOrNull(value);
      boolean var4 = false;
      boolean var5 = false;
      int var7 = false;
      return var3 != null ? $this$contains.contains((Comparable)var3) : false;
   }

   @JvmName(
      name = "floatRangeContains"
   )
   public static final boolean floatRangeContains(@NotNull ClosedRange<Float> $this$contains, double value) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      return $this$contains.contains((Comparable)(float)value);
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "intRangeContains"
   )
   public static final boolean intRangeContains(@NotNull ClosedRange<Integer> $this$contains, float value) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      Integer var2 = RangesKt.toIntExactOrNull(value);
      boolean var3 = false;
      boolean var4 = false;
      int var6 = false;
      return var2 != null ? $this$contains.contains((Comparable)var2) : false;
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "longRangeContains"
   )
   public static final boolean longRangeContains(@NotNull ClosedRange<Long> $this$contains, float value) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      Long var2 = RangesKt.toLongExactOrNull(value);
      boolean var3 = false;
      boolean var4 = false;
      int var6 = false;
      return var2 != null ? $this$contains.contains((Comparable)var2) : false;
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "byteRangeContains"
   )
   public static final boolean byteRangeContains(@NotNull ClosedRange<Byte> $this$contains, float value) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      Byte var2 = RangesKt.toByteExactOrNull(value);
      boolean var3 = false;
      boolean var4 = false;
      int var6 = false;
      return var2 != null ? $this$contains.contains((Comparable)var2) : false;
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "shortRangeContains"
   )
   public static final boolean shortRangeContains(@NotNull ClosedRange<Short> $this$contains, float value) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      Short var2 = RangesKt.toShortExactOrNull(value);
      boolean var3 = false;
      boolean var4 = false;
      int var6 = false;
      return var2 != null ? $this$contains.contains((Comparable)var2) : false;
   }

   @JvmName(
      name = "doubleRangeContains"
   )
   public static final boolean doubleRangeContains(@NotNull ClosedRange<Double> $this$contains, float value) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      return $this$contains.contains((Comparable)(double)value);
   }

   @JvmName(
      name = "longRangeContains"
   )
   public static final boolean longRangeContains(@NotNull ClosedRange<Long> $this$contains, int value) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      return $this$contains.contains((Comparable)(long)value);
   }

   @JvmName(
      name = "byteRangeContains"
   )
   public static final boolean byteRangeContains(@NotNull ClosedRange<Byte> $this$contains, int value) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      Byte var2 = RangesKt.toByteExactOrNull(value);
      boolean var3 = false;
      boolean var4 = false;
      int var6 = false;
      return var2 != null ? $this$contains.contains((Comparable)var2) : false;
   }

   @JvmName(
      name = "shortRangeContains"
   )
   public static final boolean shortRangeContains(@NotNull ClosedRange<Short> $this$contains, int value) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      Short var2 = RangesKt.toShortExactOrNull(value);
      boolean var3 = false;
      boolean var4 = false;
      int var6 = false;
      return var2 != null ? $this$contains.contains((Comparable)var2) : false;
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "doubleRangeContains"
   )
   public static final boolean doubleRangeContains(@NotNull ClosedRange<Double> $this$contains, int value) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      return $this$contains.contains((Comparable)(double)value);
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "floatRangeContains"
   )
   public static final boolean floatRangeContains(@NotNull ClosedRange<Float> $this$contains, int value) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      return $this$contains.contains((Comparable)(float)value);
   }

   @JvmName(
      name = "intRangeContains"
   )
   public static final boolean intRangeContains(@NotNull ClosedRange<Integer> $this$contains, long value) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      Integer var3 = RangesKt.toIntExactOrNull(value);
      boolean var4 = false;
      boolean var5 = false;
      int var7 = false;
      return var3 != null ? $this$contains.contains((Comparable)var3) : false;
   }

   @JvmName(
      name = "byteRangeContains"
   )
   public static final boolean byteRangeContains(@NotNull ClosedRange<Byte> $this$contains, long value) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      Byte var3 = RangesKt.toByteExactOrNull(value);
      boolean var4 = false;
      boolean var5 = false;
      int var7 = false;
      return var3 != null ? $this$contains.contains((Comparable)var3) : false;
   }

   @JvmName(
      name = "shortRangeContains"
   )
   public static final boolean shortRangeContains(@NotNull ClosedRange<Short> $this$contains, long value) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      Short var3 = RangesKt.toShortExactOrNull(value);
      boolean var4 = false;
      boolean var5 = false;
      int var7 = false;
      return var3 != null ? $this$contains.contains((Comparable)var3) : false;
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "doubleRangeContains"
   )
   public static final boolean doubleRangeContains(@NotNull ClosedRange<Double> $this$contains, long value) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      return $this$contains.contains((Comparable)(double)value);
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "floatRangeContains"
   )
   public static final boolean floatRangeContains(@NotNull ClosedRange<Float> $this$contains, long value) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      return $this$contains.contains((Comparable)(float)value);
   }

   @JvmName(
      name = "intRangeContains"
   )
   public static final boolean intRangeContains(@NotNull ClosedRange<Integer> $this$contains, short value) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      return $this$contains.contains((Comparable)Integer.valueOf(value));
   }

   @JvmName(
      name = "longRangeContains"
   )
   public static final boolean longRangeContains(@NotNull ClosedRange<Long> $this$contains, short value) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      return $this$contains.contains((Comparable)(long)value);
   }

   @JvmName(
      name = "byteRangeContains"
   )
   public static final boolean byteRangeContains(@NotNull ClosedRange<Byte> $this$contains, short value) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      Byte var2 = RangesKt.toByteExactOrNull(value);
      boolean var3 = false;
      boolean var4 = false;
      int var6 = false;
      return var2 != null ? $this$contains.contains((Comparable)var2) : false;
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "doubleRangeContains"
   )
   public static final boolean doubleRangeContains(@NotNull ClosedRange<Double> $this$contains, short value) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      return $this$contains.contains((Comparable)(double)value);
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "floatRangeContains"
   )
   public static final boolean floatRangeContains(@NotNull ClosedRange<Float> $this$contains, short value) {
      Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
      return $this$contains.contains((Comparable)(float)value);
   }

   @NotNull
   public static final IntProgression downTo(int $this$downTo, byte to) {
      return IntProgression.Companion.fromClosedRange($this$downTo, to, -1);
   }

   @NotNull
   public static final LongProgression downTo(long $this$downTo, byte to) {
      return LongProgression.Companion.fromClosedRange($this$downTo, (long)to, -1L);
   }

   @NotNull
   public static final IntProgression downTo(byte $this$downTo, byte to) {
      return IntProgression.Companion.fromClosedRange($this$downTo, to, -1);
   }

   @NotNull
   public static final IntProgression downTo(short $this$downTo, byte to) {
      return IntProgression.Companion.fromClosedRange($this$downTo, to, -1);
   }

   @NotNull
   public static final CharProgression downTo(char $this$downTo, char to) {
      return CharProgression.Companion.fromClosedRange($this$downTo, to, -1);
   }

   @NotNull
   public static final IntProgression downTo(int $this$downTo, int to) {
      return IntProgression.Companion.fromClosedRange($this$downTo, to, -1);
   }

   @NotNull
   public static final LongProgression downTo(long $this$downTo, int to) {
      return LongProgression.Companion.fromClosedRange($this$downTo, (long)to, -1L);
   }

   @NotNull
   public static final IntProgression downTo(byte $this$downTo, int to) {
      return IntProgression.Companion.fromClosedRange($this$downTo, to, -1);
   }

   @NotNull
   public static final IntProgression downTo(short $this$downTo, int to) {
      return IntProgression.Companion.fromClosedRange($this$downTo, to, -1);
   }

   @NotNull
   public static final LongProgression downTo(int $this$downTo, long to) {
      return LongProgression.Companion.fromClosedRange((long)$this$downTo, to, -1L);
   }

   @NotNull
   public static final LongProgression downTo(long $this$downTo, long to) {
      return LongProgression.Companion.fromClosedRange($this$downTo, to, -1L);
   }

   @NotNull
   public static final LongProgression downTo(byte $this$downTo, long to) {
      return LongProgression.Companion.fromClosedRange((long)$this$downTo, to, -1L);
   }

   @NotNull
   public static final LongProgression downTo(short $this$downTo, long to) {
      return LongProgression.Companion.fromClosedRange((long)$this$downTo, to, -1L);
   }

   @NotNull
   public static final IntProgression downTo(int $this$downTo, short to) {
      return IntProgression.Companion.fromClosedRange($this$downTo, to, -1);
   }

   @NotNull
   public static final LongProgression downTo(long $this$downTo, short to) {
      return LongProgression.Companion.fromClosedRange($this$downTo, (long)to, -1L);
   }

   @NotNull
   public static final IntProgression downTo(byte $this$downTo, short to) {
      return IntProgression.Companion.fromClosedRange($this$downTo, to, -1);
   }

   @NotNull
   public static final IntProgression downTo(short $this$downTo, short to) {
      return IntProgression.Companion.fromClosedRange($this$downTo, to, -1);
   }

   @NotNull
   public static final IntProgression reversed(@NotNull IntProgression $this$reversed) {
      Intrinsics.checkParameterIsNotNull($this$reversed, "$this$reversed");
      return IntProgression.Companion.fromClosedRange($this$reversed.getLast(), $this$reversed.getFirst(), -$this$reversed.getStep());
   }

   @NotNull
   public static final LongProgression reversed(@NotNull LongProgression $this$reversed) {
      Intrinsics.checkParameterIsNotNull($this$reversed, "$this$reversed");
      return LongProgression.Companion.fromClosedRange($this$reversed.getLast(), $this$reversed.getFirst(), -$this$reversed.getStep());
   }

   @NotNull
   public static final CharProgression reversed(@NotNull CharProgression $this$reversed) {
      Intrinsics.checkParameterIsNotNull($this$reversed, "$this$reversed");
      return CharProgression.Companion.fromClosedRange($this$reversed.getLast(), $this$reversed.getFirst(), -$this$reversed.getStep());
   }

   @NotNull
   public static final IntProgression step(@NotNull IntProgression $this$step, int step) {
      Intrinsics.checkParameterIsNotNull($this$step, "$this$step");
      RangesKt.checkStepIsPositive(step > 0, (Number)step);
      return IntProgression.Companion.fromClosedRange($this$step.getFirst(), $this$step.getLast(), $this$step.getStep() > 0 ? step : -step);
   }

   @NotNull
   public static final LongProgression step(@NotNull LongProgression $this$step, long step) {
      Intrinsics.checkParameterIsNotNull($this$step, "$this$step");
      RangesKt.checkStepIsPositive(step > 0L, (Number)step);
      return LongProgression.Companion.fromClosedRange($this$step.getFirst(), $this$step.getLast(), $this$step.getStep() > 0L ? step : -step);
   }

   @NotNull
   public static final CharProgression step(@NotNull CharProgression $this$step, int step) {
      Intrinsics.checkParameterIsNotNull($this$step, "$this$step");
      RangesKt.checkStepIsPositive(step > 0, (Number)step);
      return CharProgression.Companion.fromClosedRange($this$step.getFirst(), $this$step.getLast(), $this$step.getStep() > 0 ? step : -step);
   }

   @Nullable
   public static final Byte toByteExactOrNull(int $this$toByteExactOrNull) {
      Byte var10000;
      if (-128 <= $this$toByteExactOrNull) {
         if (127 >= $this$toByteExactOrNull) {
            var10000 = (byte)$this$toByteExactOrNull;
            return var10000;
         }
      }

      var10000 = null;
      return var10000;
   }

   @Nullable
   public static final Byte toByteExactOrNull(long $this$toByteExactOrNull) {
      long var10000 = (long)-128;
      long var10002 = (long)127;
      Byte var4;
      if (var10000 <= $this$toByteExactOrNull) {
         if (var10002 >= $this$toByteExactOrNull) {
            var4 = (byte)((int)$this$toByteExactOrNull);
            return var4;
         }
      }

      var4 = null;
      return var4;
   }

   @Nullable
   public static final Byte toByteExactOrNull(short $this$toByteExactOrNull) {
      short var10000 = (short)-128;
      short var10002 = (short)127;
      Byte var2;
      if (var10000 <= $this$toByteExactOrNull) {
         if (var10002 >= $this$toByteExactOrNull) {
            var2 = (byte)$this$toByteExactOrNull;
            return var2;
         }
      }

      var2 = null;
      return var2;
   }

   @Nullable
   public static final Byte toByteExactOrNull(double $this$toByteExactOrNull) {
      double var4 = (double)-128;
      double var6 = (double)127;
      return $this$toByteExactOrNull >= var4 && $this$toByteExactOrNull <= var6 ? (byte)((int)$this$toByteExactOrNull) : null;
   }

   @Nullable
   public static final Byte toByteExactOrNull(float $this$toByteExactOrNull) {
      float var2 = (float)-128;
      float var3 = (float)127;
      return $this$toByteExactOrNull >= var2 && $this$toByteExactOrNull <= var3 ? (byte)((int)$this$toByteExactOrNull) : null;
   }

   @Nullable
   public static final Integer toIntExactOrNull(long $this$toIntExactOrNull) {
      long var10000 = (long)Integer.MIN_VALUE;
      long var10002 = (long)Integer.MAX_VALUE;
      Integer var4;
      if (var10000 <= $this$toIntExactOrNull) {
         if (var10002 >= $this$toIntExactOrNull) {
            var4 = (int)$this$toIntExactOrNull;
            return var4;
         }
      }

      var4 = null;
      return var4;
   }

   @Nullable
   public static final Integer toIntExactOrNull(double $this$toIntExactOrNull) {
      double var4 = (double)Integer.MIN_VALUE;
      double var6 = (double)Integer.MAX_VALUE;
      return $this$toIntExactOrNull >= var4 && $this$toIntExactOrNull <= var6 ? (int)$this$toIntExactOrNull : null;
   }

   @Nullable
   public static final Integer toIntExactOrNull(float $this$toIntExactOrNull) {
      float var2 = (float)Integer.MIN_VALUE;
      float var3 = (float)Integer.MAX_VALUE;
      return $this$toIntExactOrNull >= var2 && $this$toIntExactOrNull <= var3 ? (int)$this$toIntExactOrNull : null;
   }

   @Nullable
   public static final Long toLongExactOrNull(double $this$toLongExactOrNull) {
      double var4 = (double)Long.MIN_VALUE;
      double var6 = (double)Long.MAX_VALUE;
      return $this$toLongExactOrNull >= var4 && $this$toLongExactOrNull <= var6 ? (long)$this$toLongExactOrNull : null;
   }

   @Nullable
   public static final Long toLongExactOrNull(float $this$toLongExactOrNull) {
      float var2 = (float)Long.MIN_VALUE;
      float var3 = (float)Long.MAX_VALUE;
      return $this$toLongExactOrNull >= var2 && $this$toLongExactOrNull <= var3 ? (long)$this$toLongExactOrNull : null;
   }

   @Nullable
   public static final Short toShortExactOrNull(int $this$toShortExactOrNull) {
      Short var10000;
      if (-32768 <= $this$toShortExactOrNull) {
         if (32767 >= $this$toShortExactOrNull) {
            var10000 = (short)$this$toShortExactOrNull;
            return var10000;
         }
      }

      var10000 = null;
      return var10000;
   }

   @Nullable
   public static final Short toShortExactOrNull(long $this$toShortExactOrNull) {
      long var10000 = (long)-32768;
      long var10002 = (long)32767;
      Short var4;
      if (var10000 <= $this$toShortExactOrNull) {
         if (var10002 >= $this$toShortExactOrNull) {
            var4 = (short)((int)$this$toShortExactOrNull);
            return var4;
         }
      }

      var4 = null;
      return var4;
   }

   @Nullable
   public static final Short toShortExactOrNull(double $this$toShortExactOrNull) {
      double var4 = (double)-32768;
      double var6 = (double)32767;
      return $this$toShortExactOrNull >= var4 && $this$toShortExactOrNull <= var6 ? (short)((int)$this$toShortExactOrNull) : null;
   }

   @Nullable
   public static final Short toShortExactOrNull(float $this$toShortExactOrNull) {
      float var2 = (float)-32768;
      float var3 = (float)32767;
      return $this$toShortExactOrNull >= var2 && $this$toShortExactOrNull <= var3 ? (short)((int)$this$toShortExactOrNull) : null;
   }

   @NotNull
   public static final IntRange until(int $this$until, byte to) {
      return new IntRange($this$until, to - 1);
   }

   @NotNull
   public static final LongRange until(long $this$until, byte to) {
      return new LongRange($this$until, (long)to - 1L);
   }

   @NotNull
   public static final IntRange until(byte $this$until, byte to) {
      return new IntRange($this$until, to - 1);
   }

   @NotNull
   public static final IntRange until(short $this$until, byte to) {
      return new IntRange($this$until, to - 1);
   }

   @NotNull
   public static final CharRange until(char $this$until, char to) {
      return to <= 0 ? CharRange.Companion.getEMPTY() : new CharRange($this$until, (char)(to - 1));
   }

   @NotNull
   public static final IntRange until(int $this$until, int to) {
      return to <= Integer.MIN_VALUE ? IntRange.Companion.getEMPTY() : new IntRange($this$until, to - 1);
   }

   @NotNull
   public static final LongRange until(long $this$until, int to) {
      return new LongRange($this$until, (long)to - 1L);
   }

   @NotNull
   public static final IntRange until(byte $this$until, int to) {
      return to <= Integer.MIN_VALUE ? IntRange.Companion.getEMPTY() : new IntRange($this$until, to - 1);
   }

   @NotNull
   public static final IntRange until(short $this$until, int to) {
      return to <= Integer.MIN_VALUE ? IntRange.Companion.getEMPTY() : new IntRange($this$until, to - 1);
   }

   @NotNull
   public static final LongRange until(int $this$until, long to) {
      if (to <= Long.MIN_VALUE) {
         return LongRange.Companion.getEMPTY();
      } else {
         long var3 = (long)$this$until;
         return new LongRange(var3, to - 1L);
      }
   }

   @NotNull
   public static final LongRange until(long $this$until, long to) {
      return to <= Long.MIN_VALUE ? LongRange.Companion.getEMPTY() : new LongRange($this$until, to - 1L);
   }

   @NotNull
   public static final LongRange until(byte $this$until, long to) {
      if (to <= Long.MIN_VALUE) {
         return LongRange.Companion.getEMPTY();
      } else {
         long var3 = (long)$this$until;
         return new LongRange(var3, to - 1L);
      }
   }

   @NotNull
   public static final LongRange until(short $this$until, long to) {
      if (to <= Long.MIN_VALUE) {
         return LongRange.Companion.getEMPTY();
      } else {
         long var3 = (long)$this$until;
         return new LongRange(var3, to - 1L);
      }
   }

   @NotNull
   public static final IntRange until(int $this$until, short to) {
      return new IntRange($this$until, to - 1);
   }

   @NotNull
   public static final LongRange until(long $this$until, short to) {
      return new LongRange($this$until, (long)to - 1L);
   }

   @NotNull
   public static final IntRange until(byte $this$until, short to) {
      return new IntRange($this$until, to - 1);
   }

   @NotNull
   public static final IntRange until(short $this$until, short to) {
      return new IntRange($this$until, to - 1);
   }

   @NotNull
   public static final <T extends Comparable<? super T>> T coerceAtLeast(@NotNull T $this$coerceAtLeast, @NotNull T minimumValue) {
      Intrinsics.checkParameterIsNotNull($this$coerceAtLeast, "$this$coerceAtLeast");
      Intrinsics.checkParameterIsNotNull(minimumValue, "minimumValue");
      return $this$coerceAtLeast.compareTo(minimumValue) < 0 ? minimumValue : $this$coerceAtLeast;
   }

   public static final byte coerceAtLeast(byte $this$coerceAtLeast, byte minimumValue) {
      return $this$coerceAtLeast < minimumValue ? minimumValue : $this$coerceAtLeast;
   }

   public static final short coerceAtLeast(short $this$coerceAtLeast, short minimumValue) {
      return $this$coerceAtLeast < minimumValue ? minimumValue : $this$coerceAtLeast;
   }

   public static final int coerceAtLeast(int $this$coerceAtLeast, int minimumValue) {
      return $this$coerceAtLeast < minimumValue ? minimumValue : $this$coerceAtLeast;
   }

   public static final long coerceAtLeast(long $this$coerceAtLeast, long minimumValue) {
      return $this$coerceAtLeast < minimumValue ? minimumValue : $this$coerceAtLeast;
   }

   public static final float coerceAtLeast(float $this$coerceAtLeast, float minimumValue) {
      return $this$coerceAtLeast < minimumValue ? minimumValue : $this$coerceAtLeast;
   }

   public static final double coerceAtLeast(double $this$coerceAtLeast, double minimumValue) {
      return $this$coerceAtLeast < minimumValue ? minimumValue : $this$coerceAtLeast;
   }

   @NotNull
   public static final <T extends Comparable<? super T>> T coerceAtMost(@NotNull T $this$coerceAtMost, @NotNull T maximumValue) {
      Intrinsics.checkParameterIsNotNull($this$coerceAtMost, "$this$coerceAtMost");
      Intrinsics.checkParameterIsNotNull(maximumValue, "maximumValue");
      return $this$coerceAtMost.compareTo(maximumValue) > 0 ? maximumValue : $this$coerceAtMost;
   }

   public static final byte coerceAtMost(byte $this$coerceAtMost, byte maximumValue) {
      return $this$coerceAtMost > maximumValue ? maximumValue : $this$coerceAtMost;
   }

   public static final short coerceAtMost(short $this$coerceAtMost, short maximumValue) {
      return $this$coerceAtMost > maximumValue ? maximumValue : $this$coerceAtMost;
   }

   public static final int coerceAtMost(int $this$coerceAtMost, int maximumValue) {
      return $this$coerceAtMost > maximumValue ? maximumValue : $this$coerceAtMost;
   }

   public static final long coerceAtMost(long $this$coerceAtMost, long maximumValue) {
      return $this$coerceAtMost > maximumValue ? maximumValue : $this$coerceAtMost;
   }

   public static final float coerceAtMost(float $this$coerceAtMost, float maximumValue) {
      return $this$coerceAtMost > maximumValue ? maximumValue : $this$coerceAtMost;
   }

   public static final double coerceAtMost(double $this$coerceAtMost, double maximumValue) {
      return $this$coerceAtMost > maximumValue ? maximumValue : $this$coerceAtMost;
   }

   @NotNull
   public static final <T extends Comparable<? super T>> T coerceIn(@NotNull T $this$coerceIn, @Nullable T minimumValue, @Nullable T maximumValue) {
      Intrinsics.checkParameterIsNotNull($this$coerceIn, "$this$coerceIn");
      if (minimumValue != null && maximumValue != null) {
         if (minimumValue.compareTo(maximumValue) > 0) {
            throw (Throwable)(new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.'));
         }

         if ($this$coerceIn.compareTo(minimumValue) < 0) {
            return minimumValue;
         }

         if ($this$coerceIn.compareTo(maximumValue) > 0) {
            return maximumValue;
         }
      } else {
         if (minimumValue != null && $this$coerceIn.compareTo(minimumValue) < 0) {
            return minimumValue;
         }

         if (maximumValue != null && $this$coerceIn.compareTo(maximumValue) > 0) {
            return maximumValue;
         }
      }

      return $this$coerceIn;
   }

   public static final byte coerceIn(byte $this$coerceIn, byte minimumValue, byte maximumValue) {
      if (minimumValue > maximumValue) {
         throw (Throwable)(new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.'));
      } else if ($this$coerceIn < minimumValue) {
         return minimumValue;
      } else {
         return $this$coerceIn > maximumValue ? maximumValue : $this$coerceIn;
      }
   }

   public static final short coerceIn(short $this$coerceIn, short minimumValue, short maximumValue) {
      if (minimumValue > maximumValue) {
         throw (Throwable)(new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.'));
      } else if ($this$coerceIn < minimumValue) {
         return minimumValue;
      } else {
         return $this$coerceIn > maximumValue ? maximumValue : $this$coerceIn;
      }
   }

   public static final int coerceIn(int $this$coerceIn, int minimumValue, int maximumValue) {
      if (minimumValue > maximumValue) {
         throw (Throwable)(new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.'));
      } else if ($this$coerceIn < minimumValue) {
         return minimumValue;
      } else {
         return $this$coerceIn > maximumValue ? maximumValue : $this$coerceIn;
      }
   }

   public static final long coerceIn(long $this$coerceIn, long minimumValue, long maximumValue) {
      if (minimumValue > maximumValue) {
         throw (Throwable)(new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.'));
      } else if ($this$coerceIn < minimumValue) {
         return minimumValue;
      } else {
         return $this$coerceIn > maximumValue ? maximumValue : $this$coerceIn;
      }
   }

   public static final float coerceIn(float $this$coerceIn, float minimumValue, float maximumValue) {
      if (minimumValue > maximumValue) {
         throw (Throwable)(new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.'));
      } else if ($this$coerceIn < minimumValue) {
         return minimumValue;
      } else {
         return $this$coerceIn > maximumValue ? maximumValue : $this$coerceIn;
      }
   }

   public static final double coerceIn(double $this$coerceIn, double minimumValue, double maximumValue) {
      if (minimumValue > maximumValue) {
         throw (Throwable)(new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.'));
      } else if ($this$coerceIn < minimumValue) {
         return minimumValue;
      } else {
         return $this$coerceIn > maximumValue ? maximumValue : $this$coerceIn;
      }
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final <T extends Comparable<? super T>> T coerceIn(@NotNull T $this$coerceIn, @NotNull ClosedFloatingPointRange<T> range) {
      Intrinsics.checkParameterIsNotNull($this$coerceIn, "$this$coerceIn");
      Intrinsics.checkParameterIsNotNull(range, "range");
      if (range.isEmpty()) {
         throw (Throwable)(new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.'));
      } else {
         return range.lessThanOrEquals($this$coerceIn, range.getStart()) && !range.lessThanOrEquals(range.getStart(), $this$coerceIn) ? range.getStart() : (range.lessThanOrEquals(range.getEndInclusive(), $this$coerceIn) && !range.lessThanOrEquals($this$coerceIn, range.getEndInclusive()) ? range.getEndInclusive() : $this$coerceIn);
      }
   }

   @NotNull
   public static final <T extends Comparable<? super T>> T coerceIn(@NotNull T $this$coerceIn, @NotNull ClosedRange<T> range) {
      Intrinsics.checkParameterIsNotNull($this$coerceIn, "$this$coerceIn");
      Intrinsics.checkParameterIsNotNull(range, "range");
      if (range instanceof ClosedFloatingPointRange) {
         return RangesKt.coerceIn($this$coerceIn, (ClosedFloatingPointRange)range);
      } else if (range.isEmpty()) {
         throw (Throwable)(new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.'));
      } else {
         return $this$coerceIn.compareTo(range.getStart()) < 0 ? range.getStart() : ($this$coerceIn.compareTo(range.getEndInclusive()) > 0 ? range.getEndInclusive() : $this$coerceIn);
      }
   }

   public static final int coerceIn(int $this$coerceIn, @NotNull ClosedRange<Integer> range) {
      Intrinsics.checkParameterIsNotNull(range, "range");
      if (range instanceof ClosedFloatingPointRange) {
         return ((Number)RangesKt.coerceIn((Comparable)$this$coerceIn, (ClosedFloatingPointRange)range)).intValue();
      } else if (range.isEmpty()) {
         throw (Throwable)(new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.'));
      } else {
         return $this$coerceIn < ((Number)range.getStart()).intValue() ? ((Number)range.getStart()).intValue() : ($this$coerceIn > ((Number)range.getEndInclusive()).intValue() ? ((Number)range.getEndInclusive()).intValue() : $this$coerceIn);
      }
   }

   public static final long coerceIn(long $this$coerceIn, @NotNull ClosedRange<Long> range) {
      Intrinsics.checkParameterIsNotNull(range, "range");
      if (range instanceof ClosedFloatingPointRange) {
         return ((Number)RangesKt.coerceIn((Comparable)$this$coerceIn, (ClosedFloatingPointRange)range)).longValue();
      } else if (range.isEmpty()) {
         throw (Throwable)(new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.'));
      } else {
         return $this$coerceIn < ((Number)range.getStart()).longValue() ? ((Number)range.getStart()).longValue() : ($this$coerceIn > ((Number)range.getEndInclusive()).longValue() ? ((Number)range.getEndInclusive()).longValue() : $this$coerceIn);
      }
   }

   public RangesKt___RangesKt() {
   }
}
