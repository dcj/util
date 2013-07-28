(ns util.introspect
  "Utilities for inspecting objects and namespaces"
  (:require [clojure.repl :as repl]))

(defn ns-docs
  "Prints docs for all public symbols in given namespace
   http://blog.twonegatives.com/post/42435179639/ns-docs
   https://gist.github.com/timvisher/4728530"
  [ns-symbol]
  (dorun 
   (map (comp #'repl/print-doc meta)
        (->> ns-symbol 
             ns-publics 
             sort 
             vals))))

(defn scaffold
  "Given an interface, returns a 'hollow' body suitable for use with `deftype`.
  https://github.com/clojurebook/ClojureProgramming/blob/master/ch06-datatypes-repl-interactions.clj"
  [interface]
  (doseq [[iface methods] (->> interface
                            .getMethods
                            (map #(vector (.getName (.getDeclaringClass %))
                                    (symbol (.getName %))
                                    (count (.getParameterTypes %))))
                            (group-by first))]
    (println (str "  " iface))
    (doseq [[_ name argcount] methods]
      (println
        (str "    "
          (list name (into '[this] (take argcount (repeatedly gensym)))))))))





