(ns count.core)
(import '(java.io File))
(use '[clojure.java.io :only (reader)])

(defn non-blank? [line]
  (if (re-find #"\S" line) true false))

(defn non-svn? [file]
  (not (.contains (.toString file) ".svn")))

(defn clojure-src? [file]
  (.endsWith (.toString file) ".clj"))

(defn count-lines [file]
  (with-open [rdr (reader file)]
    (count (filter non-blank? (line-seq rdr)))))

(defn clojure-loc [base-file]
  (reduce
    +
    (for [file (file-seq base-file)
          :when (and (clojure-src? file) (non-svn? file))]
      (count-lines file))))

(clojure-loc (File. "/Users/jmena/t.clj"))
