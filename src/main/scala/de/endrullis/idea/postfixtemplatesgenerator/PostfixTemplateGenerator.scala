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
