(ns lein-autoreload.plugin
  (:require
    [leiningen.repl]
    [leiningen.core.eval :as eval]
    [ns-tracker.core :as tracker]
    [robert.hooke]))

(defn reload [project track]
  (try
    (if (> (count (track)) 0)
      (eval/eval-in (assoc project :eval-in :nrepl)
        '(do
          (when-not (if-let [v (resolve 'my-aliases)] (bound? v) false)
            (def my-aliases (atom (ns-aliases *ns*))))
          (reset! my-aliases (merge @my-aliases (ns-aliases *ns*)))
          (require 'clojure.tools.namespace.repl)
          (clojure.tools.namespace.repl/refresh
            :after 'clojure.main/repl-prompt)
          (doseq [aliased @my-aliases]
            (let [sym (first aliased)
                  target (second aliased)]
              (ns-unalias *ns* sym)
              (alias sym (symbol (.toString target))))))))
    (catch Throwable e (println (.getStackTrace e))))
  (Thread/sleep 1000))

(defn start-reloader [project]
  (let [track (tracker/ns-tracker ["./src" "./test"])]
    (doto
      (Thread.
        #(while true (reload project track)))
      (.setDaemon true)
      (.start))))

(defn repl-hook [task project & args]
  (if (empty? args) (start-reloader project))
  (apply task project args))

(defn hooks []
  (robert.hooke/add-hook #'leiningen.repl/repl #'repl-hook))

(defn middleware [project]
  (update-in project [:dependencies]
    conj ['org.clojure/tools.namespace "0.2.3"]))
