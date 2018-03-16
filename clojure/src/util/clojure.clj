(ns util.clojure
  "Misc functions to assist with Clojure development"
  (:use [clojure.tools.macro :only (name-with-attributes)]))

;; https://gist.github.com/4134522
;; https://twitter.com/Baranosky/status/271894356418498560

;; Example:

;; (doseq-indexed idx [name names]
;;   (println (str idx ". " name)

(defmacro doseq-indexed
  [index-sym [item-sym coll] & body]
  `(let [idx-atom# (atom 0)]
     (doseq [~item-sym ~coll]
       (let [~index-sym (deref idx-atom#)]
         ~@body
         (swap! idx-atom# inc)))))

;; https://www.refheap.com/paste/6936
;; https://twitter.com/borkdude/status/272078638097248259

;; (my-filter odd? (range 10)) ;;=> (1 3 5 7 9)||

(defn my-filter
  [p coll]
  ((reduce (fn [k x]
             (if (p x)
               (comp k (partial cons x))
               k))
           identity
           coll)
   (empty coll)))

;; http://blog.zololabs.com/2012/11/26/clojure-utility-functions-part-ii/

;; (defn handle-batch [batch]
;;  (blah blah...))

;; (->> coll
;;      (partition-all n)
;;      (pmapcat handle-batch))

(defn pmapcat
  [f batches]
  (->> batches
       (pmap f)
       (apply concat)
       doall))

;; http://blog.zololabs.com/2012/11/21/clojure-utility-functions-part-i/

(defn domap
  [function coll]
  (->> coll (map function) doall))

(defn doeach
  [function coll]
  (doseq [c coll]
    (function c)))


;; From Fogus
;; git://gist.github.com/2008056.git

(defn kwargify
  "Takes a function that expects a map and returns a function that 
   accepts keyword arguments on its behalf."
  [f]
  (fn [& kwargs]
    (f (apply hash-map kwargs))))
 
;; (defrecord R [a b])
 
;; (map->R {:a 42})

;; (def *->R (kwargify map->R))

;; (*->R :a 108 :b 9 :c 99)
;; ;=>  #cljs.user.R{:a 108, :b 9, :c 99}

(defn apply-kw
  "Like apply, but f takes keyword arguments and the last argument is
  not a seq but a map with the arguments for f"
  [f & args]
  {:pre [(map? (last args))]}
  (apply f (apply concat
                  (butlast args) (last args))))

; defnk by Meikel Brandmeyer:
(defmacro defnk
 "Define a function accepting keyword arguments. Symbols up to the first
 keyword in the parameter list are taken as positional arguments.  Then
 an alternating sequence of keywords and defaults values is expected. The
 values of the keyword arguments are available in the function body by
 virtue of the symbol corresponding to the keyword (cf. :keys destructuring).
 defnk accepts an optional docstring as well as an optional metadata map."
 [fn-name & fn-tail]
 (let [[fn-name [args & body]] (name-with-attributes fn-name fn-tail)
       [pos kw-vals]           (split-with symbol? args)
       syms                    (map #(-> % name symbol) (take-nth 2 kw-vals))
       values                  (take-nth 2 (rest kw-vals))
       sym-vals                (apply hash-map (interleave syms values))
       de-map                  {:keys (vec syms)
                                :or   sym-vals}]
   `(defn ~fn-name
      [~@pos & options#]
      (let [~de-map (apply hash-map options#)]
        ~@body))))



(defmacro defalias
  "Defines an alias for a var: a new var with the same root binding (if
  any) and similar metadata. The metadata of the alias is its initial
  metadata (as provided by def) merged into the metadata of the original."
  ([name orig]
     `(do
        (alter-meta!
         (if (.hasRoot (var ~orig))
           (def ~name (.getRawRoot (var ~orig)))
           (def ~name))
         ;; When copying metadata, disregard {:macro false}.
         ;; Workaround for http://www.assembla.com/spaces/clojure/tickets/273
         #(conj (dissoc % :macro)
                (apply dissoc (meta (var ~orig)) (remove #{:macro} (keys %)))))
        (var ~name)))
  ([name orig doc]
     (list `defalias (with-meta name (assoc (meta name) :doc doc)) orig)))


;; http://stackoverflow.com/questions/4830900/how-do-i-find-the-index-of-an-item-in-a-vector

(defn indexed
  "Returns a lazy sequence of [index, item] pairs, where items come
  from 's' and indexes count up from zero.

  (indexed '(a b c d))  =>  ([0 a] [1 b] [2 c] [3 d])"
  [s]
  ;; (map vector (iterate inc 0) s))
  (map vector (range) s))

(defn positions
  "Returns a lazy sequence containing the positions at which pred
   is true for items in coll."
  [pred coll]
  (for [[idx elt] (indexed coll) :when (pred elt)] idx))


;; http://blog.juxt.pro/posts/condas.html

(defmacro condas->
  "A mixture of cond-> and as-> allowing more flexibility in the test and step forms"
  [expr name & clauses]
  (assert (even? (count clauses)))
  (let [pstep (fn [[test step]] `(if ~test ~step ~name))]
    `(let [~name ~expr
           ~@(interleave (repeat name) (map pstep (partition 2 clauses)))]
       ~name)))

(defmacro condp->
 "Takes an expression and a set of predicate/form pairs. Threads expr (via ->)
 through each form for which the corresponding predicate is true of expr.
 Note that, unlike cond branching, condp-> threading does not short circuit
 after the first true test expression."
 [expr & clauses]
 (assert (even? (count clauses)))
 (let [g (gensym)
       pstep (fn [[pred step]] `(if (~pred ~g) (-> ~g ~step) ~g))]
   `(let [~g ~expr
          ~@(interleave (repeat g) (map pstep (partition 2 clauses)))]
      ~g)))

(defmacro condp->>
  "Takes an expression and a set of predicate/form pairs. Threads expr (via ->>)
  through each form for which the corresponding predicate is true of expr.
  Note that, unlike cond branching, condp->> threading does not short circuit
  after the first true test expression."
  [expr & clauses]
  (assert (even? (count clauses)))
  (let [g (gensym)
        pstep (fn [[pred step]] `(if (~pred ~g) (->> ~g ~step) ~g))]
    `(let [~g ~expr
           ~@(interleave (repeat g) (map pstep (partition 2 clauses)))]
       ~g)))
