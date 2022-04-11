package de.endrullis.idea.postfixtemplatesgenerator;

import lombok.*;
import static cn.hutool.core.util.ClassUtil.*;
import static com.google.common.collect.ImmutableSortedSet.*;
import static java.lang.System.*;
import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;
import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;
import static java.util.stream.Stream.*;

public class Main {
  public static void main(String[] args) {
    scan("cn.hutool","com.google.common");
  }

  private static void scan(String... packages) {
    for (String pack : packages) {
      val classes = scanPackage(pack, Main::filter);
      val result  = copyOf(comparing(Class::getTypeName), classes);
      result.forEach(Main::println);
      out.println();
    }
  }

  private static boolean filter(Class<?> clazz) {
    if (clazz.isEnum()
      || clazz.isAnnotation()
      || clazz.isAnnotationPresent(Deprecated.class)
      || !isPublic(clazz.getModifiers())
      || isStatic(clazz.getModifiers())) return false;
    val publicMethods = getPublicMethods(clazz, method -> isStatic(method.getModifiers()));
    return !publicMethods.isEmpty();
  }

  private static void println(Class<?> clazz) {
    val length = clazz.getTypeParameters().length;
    if (length == 0) {
      out.println("classOf[" + clazz.getTypeName() + "],");
    } else {
      val generic = generate(() -> "_").limit(length).collect(joining(",", "[", "]"));
      out.println("classOf[" + clazz.getTypeName() + generic + "],");
    }
  }
}
