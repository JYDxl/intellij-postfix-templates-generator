package de.endrullis.idea.postfixtemplatesgenerator

import cn.hutool.aop.ProxyUtil
import cn.hutool.bloomfilter.BloomFilterUtil
import cn.hutool.cache.CacheUtil
import cn.hutool.captcha.CaptchaUtil
import cn.hutool.core.annotation.AnnotationUtil
import com.google.common.collect.Maps
import com.google.common.io.Resources
import resource._

import java.io.{File, PrintStream}
import java.lang.reflect.Modifier
import java.util.Optional
import java.util.stream.Collectors
import scala.io.Source

/**
 * Template file generator for the Intellij IDEA plugin "Custom Postfix Templates".
 *
 * @author Stefan Endrullis
 */
object PostfixTemplateGenerator {

	val templateDir = new File("templates")

	val utilsCollections = List(
		UtilsCollection("hutool", "Hutool是一个小而全的Java工具类库，通过静态方法封装，降低相关API的学习成本，提高工作效率，使Java拥有函数式语言般的优雅，让Java语言也可以“甜甜的”。",
			classOf[ProxyUtil],
			classOf[BloomFilterUtil],
			classOf[CacheUtil],
			classOf[CaptchaUtil],
			classOf[AnnotationUtil]),

		UtilsCollection("guava", "Guava is a suite of core and expanded libraries that include utility classes, Google's collections, I/O classes, and much more.",
			classOf[Resources],
			classOf[Maps]),

		UtilsCollection("jdk", "JDK",
			classOf[Optional[_]],
			classOf[Collectors]),
	)
	val langs = List(
		Lang("java", "ARRAY", _.getCanonicalName),
//		Lang("kotlin", "ARRAY", _.getCanonicalName),TODO
		Lang("scala", "scala.Array", _.getCanonicalName.replaceFirst(".*\\.", ""))
	)

	def main(args: Array[String]): Unit = {
		for (lang ← langs; utilsCollection ← utilsCollections) {
			val dir = new File(templateDir, lang.name)
			generateTemplateFile(dir, utilsCollection, lang)
		}
	}

	def generateTemplateFile(dir: File, utilsCollection: UtilsCollection, lang: Lang): Unit = {
		dir.mkdirs()
		
		val file = new File(dir, utilsCollection.name + ".postfixTemplates")
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
			out.println("." + name + " : " + name)

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
