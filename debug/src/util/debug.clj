(ns util.debug
  "Tools to assist with Clojure development")

;; http://clojure101.blogspot.com/2009/04/destructuring-binding-support-in-def.html

(defmacro def+
  "def with binding (def+ [{:keys [a b d]} {:a 1 :b 2 :d 3}])"
  [bindings]
  (let [let-expr (macroexpand `(let ~bindings))
        vars (filter #(not (.contains (str %) "__"))
               (map first (partition 2 (second let-expr))))
        def-vars (map (fn [v] `(def ~v ~v)) vars)]
    (concat let-expr def-vars)))

;; ;Example usage:
;; (def+ [a 1 b 2])
;; (def+ [{:keys [a b]} {:a 1 :b 2}])
;; (def+ [z 1
;;           {:keys [a b]} {:a 1 :b 2}])

;; http://www.learningclojure.com/2010/09/astonishing-macro-of-narayan-singhal.html

(defmacro def-let
  "like let, but binds the expressions globally."
  [bindings & more]
  (let [let-expr (macroexpand `(let ~bindings))
        names-values (partition 2 (second let-expr))
        defs   (map #(cons 'def %) names-values)]
    (concat (list 'do) defs more)))



