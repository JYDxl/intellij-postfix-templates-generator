package de.endrullis.idea.postfixtemplatesgenerator

import cn.hutool.aop.ProxyUtil
import cn.hutool.bloomfilter.BloomFilterUtil
import cn.hutool.cache.CacheUtil
import cn.hutool.captcha.CaptchaUtil
import cn.hutool.core.annotation.AnnotationUtil
import org.github.util.tree.TreeUtil
import org.github.util.{BitUtil, FuncUtil}
import resource._

import java.io.{File, PrintStream}
import java.lang.reflect.Modifier
import java.util
import java.util.stream.Collectors
import java.util.{Collections, Optional}
import scala.io.Source

/**
 * Template file generator for the Intellij IDEA plugin "Custom Postfix Templates".
 *
 * @author Stefan Endrullis
 */
object PostfixTemplateGenerator {

	val templateDir = new File("templates")

	val utilsCollections = List(
		UtilsCollection("jydxl", "JYD_XL Custom Postfix Templates",
			classOf[BitUtil],
			classOf[FuncUtil],
			classOf[TreeUtil],

			classOf[ProxyUtil],
			classOf[BloomFilterUtil],
			classOf[CacheUtil],
			classOf[CaptchaUtil],
			classOf[AnnotationUtil],

			classOf[com.google.common.base.Ascii],
			classOf[com.google.common.base.CharMatcher],
			classOf[com.google.common.base.Converter[_,_]],
			classOf[com.google.common.base.Defaults],
			classOf[com.google.common.base.Enums],
			classOf[com.google.common.base.Equivalence[_]],
			classOf[com.google.common.base.FinalizablePhantomReference[_]],
			classOf[com.google.common.base.FinalizableSoftReference[_]],
			classOf[com.google.common.base.FinalizableWeakReference[_]],
			classOf[com.google.common.base.Functions],
			classOf[com.google.common.base.Joiner],
			classOf[com.google.common.base.MoreObjects],
			classOf[com.google.common.base.Objects],
			classOf[com.google.common.base.Optional[_]],
			classOf[com.google.common.base.Preconditions],
			classOf[com.google.common.base.Predicates],
			classOf[com.google.common.base.Splitter],
			classOf[com.google.common.base.Stopwatch],
			classOf[com.google.common.base.Strings],
			classOf[com.google.common.base.Suppliers],
			classOf[com.google.common.base.Throwables],
			classOf[com.google.common.base.Ticker],
			classOf[com.google.common.base.Utf8],
			classOf[com.google.common.base.Verify],
			classOf[com.google.common.base.internal.Finalizer],
			classOf[com.google.common.cache.CacheBuilder[_,_]],
			classOf[com.google.common.cache.CacheBuilderSpec],
			classOf[com.google.common.cache.CacheLoader[_,_]],
			classOf[com.google.common.cache.RemovalListeners],
			classOf[com.google.common.cache.RemovalNotification[_,_]],
			classOf[com.google.common.collect.ArrayListMultimap[_,_]],
			classOf[com.google.common.collect.ArrayTable[_,_,_]],
			classOf[com.google.common.collect.Collections2],
			classOf[com.google.common.collect.Comparators],
			classOf[com.google.common.collect.ComparisonChain],
			classOf[com.google.common.collect.ConcurrentHashMultiset[_]],
			classOf[com.google.common.collect.ContiguousSet[_]],
			classOf[com.google.common.collect.DiscreteDomain[_]],
			classOf[com.google.common.collect.EnumBiMap[_,_]],
			classOf[com.google.common.collect.EnumHashBiMap[_,_]],
			classOf[com.google.common.collect.EnumMultiset[_]],
			classOf[com.google.common.collect.EvictingQueue[_]],
			classOf[com.google.common.collect.FluentIterable[_]],
			classOf[com.google.common.collect.HashBasedTable[_,_,_]],
			classOf[com.google.common.collect.HashBiMap[_,_]],
			classOf[com.google.common.collect.HashMultimap[_,_]],
			classOf[com.google.common.collect.HashMultiset[_]],
			classOf[com.google.common.collect.ImmutableBiMap[_,_]],
			classOf[com.google.common.collect.ImmutableClassToInstanceMap[_]],
			classOf[com.google.common.collect.ImmutableList[_]],
			classOf[com.google.common.collect.ImmutableListMultimap[_,_]],
			classOf[com.google.common.collect.ImmutableMap[_,_]],
			classOf[com.google.common.collect.ImmutableMultimap[_,_]],
			classOf[com.google.common.collect.ImmutableMultiset[_]],
			classOf[com.google.common.collect.ImmutableRangeMap[_,_]],
			classOf[com.google.common.collect.ImmutableRangeSet[_]],
			classOf[com.google.common.collect.ImmutableSet[_]],
			classOf[com.google.common.collect.ImmutableSetMultimap[_,_]],
			classOf[com.google.common.collect.ImmutableSortedMap[_,_]],
			classOf[com.google.common.collect.ImmutableSortedMultiset[_]],
			classOf[com.google.common.collect.ImmutableSortedSet[_]],
			classOf[com.google.common.collect.ImmutableTable[_,_,_]],
			classOf[com.google.common.collect.Interners],
			classOf[com.google.common.collect.Iterables],
			classOf[com.google.common.collect.Iterators],
			classOf[com.google.common.collect.LinkedHashMultimap[_,_]],
			classOf[com.google.common.collect.LinkedHashMultiset[_]],
			classOf[com.google.common.collect.LinkedListMultimap[_,_]],
			classOf[com.google.common.collect.Lists],
			classOf[com.google.common.collect.Maps],
			classOf[com.google.common.collect.MinMaxPriorityQueue[_]],
			classOf[com.google.common.collect.MoreCollectors],
			classOf[com.google.common.collect.MultimapBuilder[_,_]],
			classOf[com.google.common.collect.Multimaps],
			classOf[com.google.common.collect.Multisets],
			classOf[com.google.common.collect.MutableClassToInstanceMap[_]],
			classOf[com.google.common.collect.ObjectArrays],
			classOf[com.google.common.collect.Ordering[_]],
			classOf[com.google.common.collect.Queues],
			classOf[com.google.common.collect.Range[_]],
			classOf[com.google.common.collect.Sets],
			classOf[com.google.common.collect.Streams],
			classOf[com.google.common.collect.Tables],
			classOf[com.google.common.collect.TreeBasedTable[_,_,_]],
			classOf[com.google.common.collect.TreeMultimap[_,_]],
			classOf[com.google.common.collect.TreeMultiset[_]],
			classOf[com.google.common.collect.TreeRangeMap[_,_]],
			classOf[com.google.common.collect.TreeRangeSet[_]],
			classOf[com.google.common.escape.ArrayBasedEscaperMap],
			classOf[com.google.common.escape.Escapers],
			classOf[com.google.common.graph.ElementOrder[_]],
			classOf[com.google.common.graph.EndpointPair[_]],
			classOf[com.google.common.graph.GraphBuilder[_]],
			classOf[com.google.common.graph.Graphs],
			classOf[com.google.common.graph.ImmutableGraph[_]],
			classOf[com.google.common.graph.ImmutableNetwork[_,_]],
			classOf[com.google.common.graph.ImmutableValueGraph[_,_]],
			classOf[com.google.common.graph.NetworkBuilder[_,_]],
			classOf[com.google.common.graph.Traverser[_]],
			classOf[com.google.common.graph.ValueGraphBuilder[_,_]],
			classOf[com.google.common.hash.BloomFilter[_]],
			classOf[com.google.common.hash.Funnels],
			classOf[com.google.common.hash.HashCode],
			classOf[com.google.common.hash.Hashing],
			classOf[com.google.common.hash.HashingInputStream],
			classOf[com.google.common.hash.HashingOutputStream],
			classOf[com.google.common.html.HtmlEscapers],
			classOf[com.google.common.io.BaseEncoding],
			classOf[com.google.common.io.ByteSource],
			classOf[com.google.common.io.ByteStreams],
			classOf[com.google.common.io.CharSource],
			classOf[com.google.common.io.CharStreams],
			classOf[com.google.common.io.Closeables],
			classOf[com.google.common.io.Closer],
			classOf[com.google.common.io.CountingInputStream],
			classOf[com.google.common.io.CountingOutputStream],
			classOf[com.google.common.io.FileBackedOutputStream],
			classOf[com.google.common.io.Files],
			classOf[com.google.common.io.Flushables],
			classOf[com.google.common.io.LittleEndianDataInputStream],
			classOf[com.google.common.io.LittleEndianDataOutputStream],
			classOf[com.google.common.io.MoreFiles],
			classOf[com.google.common.io.Resources],
			classOf[com.google.common.math.BigDecimalMath],
			classOf[com.google.common.math.BigIntegerMath],
			classOf[com.google.common.math.DoubleMath],
			classOf[com.google.common.math.IntMath],
			classOf[com.google.common.math.LinearTransformation],
			classOf[com.google.common.math.LongMath],
			classOf[com.google.common.math.PairedStats],
			classOf[com.google.common.math.Quantiles],
			classOf[com.google.common.math.Stats],
			classOf[com.google.common.net.HostAndPort],
			classOf[com.google.common.net.HostSpecifier],
			classOf[com.google.common.net.InetAddresses],
			classOf[com.google.common.net.InternetDomainName],
			classOf[com.google.common.net.MediaType],
			classOf[com.google.common.net.UrlEscapers],
			classOf[com.google.common.primitives.Booleans],
			classOf[com.google.common.primitives.Bytes],
			classOf[com.google.common.primitives.Chars],
			classOf[com.google.common.primitives.Doubles],
			classOf[com.google.common.primitives.Floats],
			classOf[com.google.common.primitives.ImmutableDoubleArray],
			classOf[com.google.common.primitives.ImmutableIntArray],
			classOf[com.google.common.primitives.ImmutableLongArray],
			classOf[com.google.common.primitives.Ints],
			classOf[com.google.common.primitives.Longs],
			classOf[com.google.common.primitives.Primitives],
			classOf[com.google.common.primitives.Shorts],
			classOf[com.google.common.primitives.SignedBytes],
			classOf[com.google.common.primitives.UnsignedBytes],
			classOf[com.google.common.primitives.UnsignedInteger],
			classOf[com.google.common.primitives.UnsignedInts],
			classOf[com.google.common.primitives.UnsignedLong],
			classOf[com.google.common.primitives.UnsignedLongs],
			classOf[com.google.common.reflect.ClassPath],
			classOf[com.google.common.reflect.ImmutableTypeToInstanceMap[_]],
			classOf[com.google.common.reflect.Invokable[_,_]],
			classOf[com.google.common.reflect.Reflection],
			classOf[com.google.common.reflect.TypeToken[_]],
			classOf[com.google.common.util.concurrent.AtomicLongMap[_]],
			classOf[com.google.common.util.concurrent.Atomics],
			classOf[com.google.common.util.concurrent.Callables],
			classOf[com.google.common.util.concurrent.ClosingFuture[_]],
			classOf[com.google.common.util.concurrent.CycleDetectingLockFactory],
			classOf[com.google.common.util.concurrent.ExecutionSequencer],
			classOf[com.google.common.util.concurrent.FluentFuture[_]],
			classOf[com.google.common.util.concurrent.Futures],
			classOf[com.google.common.util.concurrent.JdkFutureAdapters],
			classOf[com.google.common.util.concurrent.ListenableFutureTask[_]],
			classOf[com.google.common.util.concurrent.MoreExecutors],
			classOf[com.google.common.util.concurrent.RateLimiter],
			classOf[com.google.common.util.concurrent.Runnables],
			classOf[com.google.common.util.concurrent.SettableFuture[_]],
			classOf[com.google.common.util.concurrent.SimpleTimeLimiter],
			classOf[com.google.common.util.concurrent.Striped[_]],
			classOf[com.google.common.util.concurrent.UncaughtExceptionHandlers],
			classOf[com.google.common.util.concurrent.Uninterruptibles],
			classOf[com.google.common.util.concurrent.internal.InternalFutures],
			classOf[com.google.common.xml.XmlEscapers],

			classOf[cn.hutool.Hutool],
			classOf[cn.hutool.aop.ProxyUtil],
			classOf[cn.hutool.aop.proxy.CglibProxyFactory],
			classOf[cn.hutool.aop.proxy.JdkProxyFactory],
			classOf[cn.hutool.aop.proxy.ProxyFactory],
			classOf[cn.hutool.aop.proxy.SpringCglibProxyFactory],
			classOf[cn.hutool.bloomfilter.BitSetBloomFilter],
			classOf[cn.hutool.bloomfilter.BloomFilterUtil],
			classOf[cn.hutool.cache.CacheUtil],
			classOf[cn.hutool.captcha.CaptchaUtil],
			classOf[cn.hutool.core.annotation.AnnotationUtil],
			classOf[cn.hutool.core.bean.BeanPath],
			classOf[cn.hutool.core.bean.BeanUtil],
			classOf[cn.hutool.core.bean.DynaBean],
			classOf[cn.hutool.core.bean.copier.BeanCopier[_]],
			classOf[cn.hutool.core.bean.copier.CopyOptions],
			classOf[cn.hutool.core.builder.CompareToBuilder],
			classOf[cn.hutool.core.builder.EqualsBuilder],
			classOf[cn.hutool.core.builder.HashCodeBuilder],
			classOf[cn.hutool.core.codec.BCD],
			classOf[cn.hutool.core.codec.Base32],
			classOf[cn.hutool.core.codec.Base62],
			classOf[cn.hutool.core.codec.Base62Codec],
			classOf[cn.hutool.core.codec.Base64],
			classOf[cn.hutool.core.codec.Base64Decoder],
			classOf[cn.hutool.core.codec.Base64Encoder],
			classOf[cn.hutool.core.codec.Caesar],
			classOf[cn.hutool.core.codec.PunyCode],
			classOf[cn.hutool.core.codec.Rot],
			classOf[cn.hutool.core.collection.CollStreamUtil],
			classOf[cn.hutool.core.collection.CollUtil],
			classOf[cn.hutool.core.collection.CollectionUtil],
			classOf[cn.hutool.core.collection.CopiedIter[_]],
			classOf[cn.hutool.core.collection.IterUtil],
			classOf[cn.hutool.core.collection.ListUtil],
			classOf[cn.hutool.core.collection.SpliteratorUtil],
			classOf[cn.hutool.core.comparator.ComparatorChain[_]],
			classOf[cn.hutool.core.comparator.CompareUtil],
			classOf[cn.hutool.core.compiler.CompilerUtil],
			classOf[cn.hutool.core.compiler.DiagnosticUtil],
			classOf[cn.hutool.core.compiler.JavaFileObjectUtil],
			classOf[cn.hutool.core.compiler.JavaSourceCompiler],
			classOf[cn.hutool.core.compress.Deflate],
			classOf[cn.hutool.core.compress.Gzip],
			classOf[cn.hutool.core.compress.ZipReader],
			classOf[cn.hutool.core.compress.ZipWriter],
			classOf[cn.hutool.core.convert.Convert],
			classOf[cn.hutool.core.convert.ConverterRegistry],
			classOf[cn.hutool.core.convert.NumberChineseFormatter],
			classOf[cn.hutool.core.convert.NumberWordFormatter],
			classOf[cn.hutool.core.date.CalendarUtil],
			classOf[cn.hutool.core.date.DateBetween],
			classOf[cn.hutool.core.date.DateModifier],
			classOf[cn.hutool.core.date.DateTime],
			classOf[cn.hutool.core.date.DateUtil],
			classOf[cn.hutool.core.date.LocalDateTimeUtil],
			classOf[cn.hutool.core.date.StopWatch],
			classOf[cn.hutool.core.date.SystemClock],
			classOf[cn.hutool.core.date.TemporalAccessorUtil],
			classOf[cn.hutool.core.date.TemporalUtil],
			classOf[cn.hutool.core.date.Zodiac],
			classOf[cn.hutool.core.date.chinese.ChineseMonth],
			classOf[cn.hutool.core.date.chinese.GanZhi],
			classOf[cn.hutool.core.date.chinese.LunarFestival],
			classOf[cn.hutool.core.date.chinese.LunarInfo],
			classOf[cn.hutool.core.date.chinese.SolarTerms],
			classOf[cn.hutool.core.date.format.FastDateFormat],
			classOf[cn.hutool.core.date.format.GlobalCustomFormat],
			classOf[cn.hutool.core.exceptions.ExceptionUtil],
			classOf[cn.hutool.core.img.BackgroundRemoval],
			classOf[cn.hutool.core.img.FontUtil],
			classOf[cn.hutool.core.img.GraphicsUtil],
			classOf[cn.hutool.core.img.Img],
			classOf[cn.hutool.core.img.ImgUtil],
			classOf[cn.hutool.core.io.AppendableWriter],
			classOf[cn.hutool.core.io.BOMInputStream],
			classOf[cn.hutool.core.io.BomReader],
			classOf[cn.hutool.core.io.BufferUtil],
			classOf[cn.hutool.core.io.CharsetDetector],
			classOf[cn.hutool.core.io.FastByteArrayOutputStream],
			classOf[cn.hutool.core.io.FastStringWriter],
			classOf[cn.hutool.core.io.FileTypeUtil],
			classOf[cn.hutool.core.io.FileUtil],
			classOf[cn.hutool.core.io.IoUtil],
			classOf[cn.hutool.core.io.ManifestUtil],
			classOf[cn.hutool.core.io.NioUtil],
			classOf[cn.hutool.core.io.NullOutputStream],
			classOf[cn.hutool.core.io.ValidateObjectInputStream],
			classOf[cn.hutool.core.io.file.FileCopier],
			classOf[cn.hutool.core.io.file.FileNameUtil],
			classOf[cn.hutool.core.io.file.FileReader],
			classOf[cn.hutool.core.io.file.FileWriter],
			classOf[cn.hutool.core.io.file.PathUtil],
			classOf[cn.hutool.core.io.resource.ResourceUtil],
			classOf[cn.hutool.core.io.unit.DataSize],
			classOf[cn.hutool.core.io.unit.DataSizeUtil],
			classOf[cn.hutool.core.io.watch.WatchMonitor],
			classOf[cn.hutool.core.io.watch.WatchServer],
			classOf[cn.hutool.core.io.watch.WatchUtil],
			classOf[cn.hutool.core.io.watch.watchers.WatcherChain],
			classOf[cn.hutool.core.lang.Assert],
			classOf[cn.hutool.core.lang.ClassScanner],
			classOf[cn.hutool.core.lang.Console],
			classOf[cn.hutool.core.lang.ConsoleTable],
			classOf[cn.hutool.core.lang.Dict],
			classOf[cn.hutool.core.lang.Holder[_]],
			classOf[cn.hutool.core.lang.JarClassLoader],
			classOf[cn.hutool.core.lang.ObjectId],
			classOf[cn.hutool.core.lang.Opt[_]],
			classOf[cn.hutool.core.lang.Pair[_,_]],
			classOf[cn.hutool.core.lang.PatternPool],
			classOf[cn.hutool.core.lang.ResourceClassLoader[_]],
			classOf[cn.hutool.core.lang.Singleton],
			classOf[cn.hutool.core.lang.UUID],
			classOf[cn.hutool.core.lang.Validator],
			classOf[cn.hutool.core.lang.WeightRandom[_]],
			classOf[cn.hutool.core.lang.caller.CallerUtil],
			classOf[cn.hutool.core.lang.func.LambdaUtil],
			classOf[cn.hutool.core.lang.hash.CityHash],
			classOf[cn.hutool.core.lang.hash.MurmurHash],
			classOf[cn.hutool.core.lang.id.NanoId],
			classOf[cn.hutool.core.lang.intern.InternUtil],
			classOf[cn.hutool.core.lang.reflect.ActualTypeMapperPool],
			classOf[cn.hutool.core.lang.reflect.LookupFactory],
			classOf[cn.hutool.core.lang.reflect.MethodHandleUtil],
			classOf[cn.hutool.core.lang.tree.TreeBuilder[_]],
			classOf[cn.hutool.core.lang.tree.TreeUtil],
			classOf[cn.hutool.core.map.MapBuilder[_,_]],
			classOf[cn.hutool.core.map.MapProxy],
			classOf[cn.hutool.core.map.MapUtil],
			classOf[cn.hutool.core.map.TolerantMap[_,_]],
			classOf[cn.hutool.core.math.Arrangement],
			classOf[cn.hutool.core.math.BitStatusUtil],
			classOf[cn.hutool.core.math.Calculator],
			classOf[cn.hutool.core.math.Combination],
			classOf[cn.hutool.core.math.MathUtil],
			classOf[cn.hutool.core.net.Ipv4Util],
			classOf[cn.hutool.core.net.MaskBit],
			classOf[cn.hutool.core.net.NetUtil],
			classOf[cn.hutool.core.net.PassAuth],
			classOf[cn.hutool.core.net.SSLContextBuilder],
			classOf[cn.hutool.core.net.SSLUtil],
			classOf[cn.hutool.core.net.URLDecoder],
			classOf[cn.hutool.core.net.URLEncodeUtil],
			classOf[cn.hutool.core.net.URLEncoder],
			classOf[cn.hutool.core.net.UserPassAuthenticator],
			classOf[cn.hutool.core.net.multipart.MultipartRequestInputStream],
			classOf[cn.hutool.core.net.url.UrlBuilder],
			classOf[cn.hutool.core.net.url.UrlPath],
			classOf[cn.hutool.core.net.url.UrlQuery],
			classOf[cn.hutool.core.stream.CollectorUtil],
			classOf[cn.hutool.core.stream.StreamUtil],
			classOf[cn.hutool.core.swing.DesktopUtil],
			classOf[cn.hutool.core.swing.RobotUtil],
			classOf[cn.hutool.core.swing.ScreenUtil],
			classOf[cn.hutool.core.swing.clipboard.ClipboardUtil],
			classOf[cn.hutool.core.text.ASCIIStrCache],
			classOf[cn.hutool.core.text.CharSequenceUtil],
			classOf[cn.hutool.core.text.NamingCase],
			classOf[cn.hutool.core.text.PasswdStrength],
			classOf[cn.hutool.core.text.StrBuilder],
			classOf[cn.hutool.core.text.StrFormatter],
			classOf[cn.hutool.core.text.StrJoiner],
			classOf[cn.hutool.core.text.StrSplitter],
			classOf[cn.hutool.core.text.TextSimilarity],
			classOf[cn.hutool.core.text.UnicodeUtil],
			classOf[cn.hutool.core.text.csv.CsvReadConfig],
			classOf[cn.hutool.core.text.csv.CsvUtil],
			classOf[cn.hutool.core.text.csv.CsvWriteConfig],
			classOf[cn.hutool.core.thread.ExecutorBuilder],
			classOf[cn.hutool.core.thread.GlobalThreadPool],
			classOf[cn.hutool.core.thread.ThreadFactoryBuilder],
			classOf[cn.hutool.core.thread.ThreadUtil],
			classOf[cn.hutool.core.thread.lock.LockUtil],
			classOf[cn.hutool.core.thread.threadlocal.NamedInheritableThreadLocal[_]],
			classOf[cn.hutool.core.thread.threadlocal.NamedThreadLocal[_]],
			classOf[cn.hutool.core.util.ArrayUtil],
			classOf[cn.hutool.core.util.BooleanUtil],
			classOf[cn.hutool.core.util.ByteUtil],
			classOf[cn.hutool.core.util.CharUtil],
			classOf[cn.hutool.core.util.CharsetUtil],
			classOf[cn.hutool.core.util.ClassLoaderUtil],
			classOf[cn.hutool.core.util.ClassUtil],
			classOf[cn.hutool.core.util.CreditCodeUtil],
			classOf[cn.hutool.core.util.DesensitizedUtil],
			classOf[cn.hutool.core.util.EnumUtil],
			classOf[cn.hutool.core.util.EscapeUtil],
			classOf[cn.hutool.core.util.HashUtil],
			classOf[cn.hutool.core.util.HexUtil],
			classOf[cn.hutool.core.util.IdUtil],
			classOf[cn.hutool.core.util.IdcardUtil],
			classOf[cn.hutool.core.util.JAXBUtil],
			classOf[cn.hutool.core.util.JNDIUtil],
			classOf[cn.hutool.core.util.ModifierUtil],
			classOf[cn.hutool.core.util.NumberUtil],
			classOf[cn.hutool.core.util.ObjectUtil],
			classOf[cn.hutool.core.util.PageUtil],
			classOf[cn.hutool.core.util.PhoneUtil],
			classOf[cn.hutool.core.util.PrimitiveArrayUtil],
			classOf[cn.hutool.core.util.RadixUtil],
			classOf[cn.hutool.core.util.RandomUtil],
			classOf[cn.hutool.core.util.ReUtil],
			classOf[cn.hutool.core.util.ReferenceUtil],
			classOf[cn.hutool.core.util.ReflectUtil],
			classOf[cn.hutool.core.util.RuntimeUtil],
			classOf[cn.hutool.core.util.SerializeUtil],
			classOf[cn.hutool.core.util.ServiceLoaderUtil],
			classOf[cn.hutool.core.util.StrUtil],
			classOf[cn.hutool.core.util.TypeUtil],
			classOf[cn.hutool.core.util.URLUtil],
			classOf[cn.hutool.core.util.XmlUtil],
			classOf[cn.hutool.core.util.ZipUtil],
			classOf[cn.hutool.cron.CronTimer],
			classOf[cn.hutool.cron.CronUtil],
			classOf[cn.hutool.cron.pattern.CronPatternUtil],
			classOf[cn.hutool.cron.pattern.matcher.ValueMatcherBuilder],
			classOf[cn.hutool.crypto.ASN1Util],
			classOf[cn.hutool.crypto.BCUtil],
			classOf[cn.hutool.crypto.ECKeyUtil],
			classOf[cn.hutool.crypto.KeyUtil],
			classOf[cn.hutool.crypto.PemUtil],
			classOf[cn.hutool.crypto.ProviderFactory],
			classOf[cn.hutool.crypto.SecureUtil],
			classOf[cn.hutool.crypto.SmUtil],
			classOf[cn.hutool.crypto.asymmetric.RSA],
			classOf[cn.hutool.crypto.digest.BCrypt],
			classOf[cn.hutool.crypto.digest.DigestUtil],
			classOf[cn.hutool.crypto.digest.MD5],
			classOf[cn.hutool.crypto.digest.SM3],
			classOf[cn.hutool.crypto.digest.mac.MacEngineFactory],
			classOf[cn.hutool.crypto.digest.otp.HOTP],
			classOf[cn.hutool.crypto.digest.otp.TOTP],
			classOf[cn.hutool.crypto.symmetric.Vigenere],
			classOf[cn.hutool.crypto.symmetric.ZUC],
			classOf[cn.hutool.db.ActiveEntity],
			classOf[cn.hutool.db.Db],
			classOf[cn.hutool.db.DbUtil],
			classOf[cn.hutool.db.Entity],
			classOf[cn.hutool.db.GlobalDbConfig],
			classOf[cn.hutool.db.Page],
			classOf[cn.hutool.db.Session],
			classOf[cn.hutool.db.SqlConnRunner],
			classOf[cn.hutool.db.StatementUtil],
			classOf[cn.hutool.db.dialect.DialectFactory],
			classOf[cn.hutool.db.dialect.DriverUtil],
			classOf[cn.hutool.db.ds.AbstractDSFactory],
			classOf[cn.hutool.db.ds.DSFactory],
			classOf[cn.hutool.db.ds.DataSourceWrapper],
			classOf[cn.hutool.db.ds.GlobalDSFactory],
			classOf[cn.hutool.db.ds.bee.BeeDSFactory],
			classOf[cn.hutool.db.ds.c3p0.C3p0DSFactory],
			classOf[cn.hutool.db.ds.dbcp.DbcpDSFactory],
			classOf[cn.hutool.db.ds.druid.DruidDSFactory],
			classOf[cn.hutool.db.ds.hikari.HikariDSFactory],
			classOf[cn.hutool.db.ds.jndi.JndiDSFactory],
			classOf[cn.hutool.db.ds.pooled.PooledDSFactory],
			classOf[cn.hutool.db.ds.pooled.PooledDataSource],
			classOf[cn.hutool.db.ds.simple.SimpleDSFactory],
			classOf[cn.hutool.db.ds.simple.SimpleDataSource],
			classOf[cn.hutool.db.ds.tomcat.TomcatDSFactory],
			classOf[cn.hutool.db.handler.BeanHandler[_]],
			classOf[cn.hutool.db.handler.BeanListHandler[_]],
			classOf[cn.hutool.db.handler.EntityHandler],
			classOf[cn.hutool.db.handler.EntityListHandler],
			classOf[cn.hutool.db.handler.EntitySetHandler],
			classOf[cn.hutool.db.handler.HandleHelper],
			classOf[cn.hutool.db.handler.NumberHandler],
			classOf[cn.hutool.db.handler.PageResultHandler],
			classOf[cn.hutool.db.handler.StringHandler],
			classOf[cn.hutool.db.handler.ValueListHandler],
			classOf[cn.hutool.db.meta.Column],
			classOf[cn.hutool.db.meta.MetaUtil],
			classOf[cn.hutool.db.meta.Table],
			classOf[cn.hutool.db.nosql.mongo.MongoFactory],
			classOf[cn.hutool.db.nosql.redis.RedisDS],
			classOf[cn.hutool.db.sql.Condition],
			classOf[cn.hutool.db.sql.ConditionBuilder],
			classOf[cn.hutool.db.sql.Query],
			classOf[cn.hutool.db.sql.SqlBuilder],
			classOf[cn.hutool.db.sql.SqlExecutor],
			classOf[cn.hutool.db.sql.SqlFormatter],
			classOf[cn.hutool.db.sql.SqlUtil],
			classOf[cn.hutool.dfa.SensitiveUtil],
			classOf[cn.hutool.dfa.StopChar],
			classOf[cn.hutool.extra.cglib.CglibUtil],
			classOf[cn.hutool.extra.compress.CompressUtil],
			classOf[cn.hutool.extra.compress.archiver.StreamArchiver],
			classOf[cn.hutool.extra.compress.extractor.Seven7EntryInputStream],
			classOf[cn.hutool.extra.emoji.EmojiUtil],
			classOf[cn.hutool.extra.expression.ExpressionUtil],
			classOf[cn.hutool.extra.expression.engine.ExpressionFactory],
			classOf[cn.hutool.extra.ftp.FtpConfig],
			classOf[cn.hutool.extra.ftp.SimpleFtpServer],
			classOf[cn.hutool.extra.mail.InternalMailUtil],
			classOf[cn.hutool.extra.mail.Mail],
			classOf[cn.hutool.extra.mail.MailUtil],
			classOf[cn.hutool.extra.pinyin.PinyinUtil],
			classOf[cn.hutool.extra.pinyin.engine.PinyinFactory],
			classOf[cn.hutool.extra.qrcode.QrCodeUtil],
			classOf[cn.hutool.extra.qrcode.QrConfig],
			classOf[cn.hutool.extra.servlet.ServletUtil],
			classOf[cn.hutool.extra.ssh.GanymedUtil],
			classOf[cn.hutool.extra.ssh.JschUtil],
			classOf[cn.hutool.extra.template.TemplateUtil],
			classOf[cn.hutool.extra.template.engine.TemplateFactory],
			classOf[cn.hutool.extra.template.engine.beetl.BeetlTemplate],
			classOf[cn.hutool.extra.template.engine.enjoy.EnjoyTemplate],
			classOf[cn.hutool.extra.template.engine.freemarker.FreemarkerTemplate],
			classOf[cn.hutool.extra.template.engine.rythm.RythmTemplate],
			classOf[cn.hutool.extra.template.engine.thymeleaf.ThymeleafTemplate],
			classOf[cn.hutool.extra.template.engine.velocity.VelocityTemplate],
			classOf[cn.hutool.extra.template.engine.wit.WitTemplate],
			classOf[cn.hutool.extra.tokenizer.TokenizerUtil],
			classOf[cn.hutool.extra.tokenizer.engine.TokenizerFactory],
			classOf[cn.hutool.extra.validation.ValidationUtil],
			classOf[cn.hutool.http.HTMLFilter],
			classOf[cn.hutool.http.HtmlUtil],
			classOf[cn.hutool.http.HttpConnection],
			classOf[cn.hutool.http.HttpDownloader],
			classOf[cn.hutool.http.HttpGlobalConfig],
			classOf[cn.hutool.http.HttpInputStream],
			classOf[cn.hutool.http.HttpRequest],
			classOf[cn.hutool.http.HttpStatus],
			classOf[cn.hutool.http.HttpUtil],
			classOf[cn.hutool.http.body.MultipartBody],
			classOf[cn.hutool.http.cookie.GlobalCookieManager],
			classOf[cn.hutool.http.server.filter.SimpleFilter],
			classOf[cn.hutool.http.ssl.AndroidSupportSSLFactory],
			classOf[cn.hutool.http.ssl.CustomProtocolsSSLFactory],
			classOf[cn.hutool.http.ssl.DefaultSSLFactory],
			classOf[cn.hutool.http.useragent.Browser],
			classOf[cn.hutool.http.useragent.OS],
			classOf[cn.hutool.http.useragent.UserAgentParser],
			classOf[cn.hutool.http.useragent.UserAgentUtil],
			classOf[cn.hutool.http.webservice.SoapClient],
			classOf[cn.hutool.http.webservice.SoapUtil],
			classOf[cn.hutool.json.InternalJSONUtil],
			classOf[cn.hutool.json.JSONConfig],
			classOf[cn.hutool.json.JSONStrFormatter],
			classOf[cn.hutool.json.JSONUtil],
			classOf[cn.hutool.json.XML],
			classOf[cn.hutool.json.serialize.GlobalSerializeMapping],
			classOf[cn.hutool.json.serialize.JSONWriter],
			classOf[cn.hutool.json.xml.JSONXMLParser],
			classOf[cn.hutool.json.xml.JSONXMLSerializer],
			classOf[cn.hutool.jwt.JWT],
			classOf[cn.hutool.jwt.JWTUtil],
			classOf[cn.hutool.jwt.JWTValidator],
			classOf[cn.hutool.jwt.signers.AlgorithmUtil],
			classOf[cn.hutool.jwt.signers.JWTSignerUtil],
			classOf[cn.hutool.log.GlobalLogFactory],
			classOf[cn.hutool.log.Log],
			classOf[cn.hutool.log.LogFactory],
			classOf[cn.hutool.log.StaticLog],
			classOf[cn.hutool.log.dialect.commons.ApacheCommonsLogFactory],
			classOf[cn.hutool.log.dialect.console.ConsoleLog],
			classOf[cn.hutool.log.dialect.console.ConsoleLogFactory],
			classOf[cn.hutool.log.dialect.jboss.JbossLogFactory],
			classOf[cn.hutool.log.dialect.jdk.JdkLogFactory],
			classOf[cn.hutool.log.dialect.log4j.Log4jLogFactory],
			classOf[cn.hutool.log.dialect.log4j2.Log4j2LogFactory],
			classOf[cn.hutool.log.dialect.logtube.LogTubeLogFactory],
			classOf[cn.hutool.log.dialect.slf4j.Slf4jLogFactory],
			classOf[cn.hutool.log.dialect.tinylog.TinyLog2Factory],
			classOf[cn.hutool.log.dialect.tinylog.TinyLogFactory],
			classOf[cn.hutool.poi.PoiChecker],
			classOf[cn.hutool.poi.excel.ExcelDateUtil],
			classOf[cn.hutool.poi.excel.ExcelExtractorUtil],
			classOf[cn.hutool.poi.excel.ExcelFileUtil],
			classOf[cn.hutool.poi.excel.ExcelPicUtil],
			classOf[cn.hutool.poi.excel.ExcelUtil],
			classOf[cn.hutool.poi.excel.RowUtil],
			classOf[cn.hutool.poi.excel.WorkbookUtil],
			classOf[cn.hutool.poi.excel.cell.CellUtil],
			classOf[cn.hutool.poi.excel.cell.setters.CellSetterFactory],
			classOf[cn.hutool.poi.excel.sax.ExcelSaxUtil],
			classOf[cn.hutool.poi.excel.style.StyleUtil],
			classOf[cn.hutool.poi.word.DocUtil],
			classOf[cn.hutool.poi.word.TableUtil],
			classOf[cn.hutool.poi.word.WordUtil],
			classOf[cn.hutool.script.JavaScriptEngine],
			classOf[cn.hutool.script.ScriptUtil],
			classOf[cn.hutool.setting.Setting],
			classOf[cn.hutool.setting.SettingUtil],
			classOf[cn.hutool.setting.dialect.Props],
			classOf[cn.hutool.setting.dialect.PropsUtil],
			classOf[cn.hutool.setting.profile.GlobalProfile],
			classOf[cn.hutool.setting.yaml.YamlUtil],
			classOf[cn.hutool.socket.SocketUtil],
			classOf[cn.hutool.socket.nio.NioUtil],
			classOf[cn.hutool.system.SystemUtil],
			classOf[cn.hutool.system.oshi.OshiUtil],

			classOf[Optional[_]],
			classOf[Collectors],
			classOf[Collections],
			classOf[util.Arrays],
			classOf[java.lang.String]),
	)
	val langs = List(
		Lang("java", "ARRAY", _.getCanonicalName),
//		Lang("kotlin", "ARRAY", _.getCanonicalName),TODO
		Lang("scala", "scala.Array", _.getCanonicalName.replaceFirst(".*\\.", ""))
	)

	def main(args: Array[String]): Unit = {
		for (lang ← langs; utilsCollection ← utilsCollections) {
//			val dir = new File(templateDir, lang.name)
			generateTemplateFile(templateDir, utilsCollection, lang)
		}
	}

	def generateTemplateFile(dir: File, utilsCollection: UtilsCollection, lang: Lang): Unit = {
		dir.mkdirs()
		
		val file = new File(dir, lang.name + ".postfixTemplates")
		val out = new PrintStream(file)
		out.println(s"## Templates for ${utilsCollection.description} ##")

		for (utilClass ← utilsCollection.utilClasses) {
			out.println()
			out.println("## " + utilClass.getName.replaceFirst(".*\\.", ""))
			out.println()
			printTemplates(utilClass, lang, out)
		}

		out.close()
	}

	def printTemplates(utilClass: Class[_], lang: Lang, out: PrintStream): Unit = {
		val allMethods = utilClass.getMethods.toList.filter{m ⇒
			Modifier.isStatic(m.getModifiers) &&
			Modifier.isPublic(m.getModifiers) &&
			m.getParameterCount >= 0 &&
			m.getAnnotation(classOf[Deprecated]) == null &&
			!lang.classMethodExcludes.contains(lang.mapType(if (m.getParameterCount > 1) m.getParameterTypes.head else classOf[Object]), m.getName)
		}

		allMethods.groupBy(_.getName).filterNot(_._1.contains("_")).foreach{case (name, methods) ⇒
			out.println("." + name + " : " + utilClass.getCanonicalName + "." + methods.head.getName)

			methods.filter(_.getParameterCount > 0).groupBy(m ⇒ lang.mapType(m.getParameterTypes.head)).foreach{case (matchingType, ms) ⇒
				val params = ms.filter(_.getParameterCount > 1).map(_.getParameterTypes()(1)).toSet

				val className = utilClass.getCanonicalName
				val utilClassName = lang.utilClassName(utilClass)

				val leftSide = s"$matchingType [$className]"

				val rightSide = if (params.isEmpty) {
					s"$utilClassName.$name($$expr$$)"
				} else {
					s"$utilClassName.$name($$expr$$, $$arg$$)"
				}

				out.println(s"\t$leftSide  →  $rightSide")
			}
			methods.filter(_.getParameterCount == 0).groupBy(_ ⇒ lang.mapType(classOf[Object])).foreach { case (matchingType, _) ⇒
				val className     = utilClass.getCanonicalName
				val utilClassName = lang.utilClassName(utilClass)

				val leftSide = s"$matchingType [$className]"

				val rightSide = s"$utilClassName.$name()"

				out.println(s"\t$leftSide  →  $rightSide")
			}
			out.println()
		}
	}

	case class UtilsCollection(name: String, description: String, utilClasses: Class[_]*)

	case class Lang(name: String, arrayType: String, utilClassName: Class[_] ⇒ String) {
		def mapType(cls: Class[_]): String = {
			if (cls.isArray) {
				arrayType
			} else {
				cls match {
					case ShortT ⇒ "SHORT"
					case IntT ⇒ "INT"
					case LongT ⇒ "LONG"
					case FloatT ⇒ "FLOAT"
					case DoubleT ⇒ "DOUBLE"
					case CharT ⇒ "CHAR"
					case ByteT ⇒ "BYTE"
					case BooleanT ⇒ "BOOLEAN"
					case s ⇒ s.getCanonicalName
				}
			}
		}

		val classMethodExcludes: Set[(String, String)] = {
			val url = getClass.getResource(s"$name.excludes")

			if (url == null) {
				Set()
			} else {
				managed(Source.fromURL(url, "UTF-8")).acquireAndGet(_.getLines()
					.filterNot(_.isEmpty)
					.map(_.split("→") match { case Array(a, b) ⇒ (a.trim, b.trim) })
					.toSet
				)
			}
		}
	}

	private val ShortT   = classOf[Short]
	private val IntT     = classOf[Int]
	private val LongT    = classOf[Long]
	private val FloatT   = classOf[Float]
	private val DoubleT  = classOf[Double]
	private val CharT    = classOf[Char]
	private val ByteT    = classOf[Byte]
	private val BooleanT = classOf[Boolean]

}
