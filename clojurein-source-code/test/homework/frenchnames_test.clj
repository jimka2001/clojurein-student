(ns homework.frenchnames-test
  (:require [homework.frenchnames :as sut]
            [common.util :refer [member testing-with-timeout is]]
            [clojure.pprint :refer [cl-format]]
            [clojure.test :refer [deftest]]))

(deftest t-elide
  (testing-with-timeout "elide"
    (is (= "jerome"
           (sut/elide-string "jèrome")))
    (is (= "jerome"
           (sut/elide-string "jèrôme")))
    (is (= "come"
           (sut/elide-string "côme")))))

(deftest t-renomes-rare
  (testing-with-timeout "prenoms rares"
    (is (= {}
           (sut/baby-names-per-year "_prenoms_rares" "M")))
    (is (= {}
           (sut/baby-names-per-year "_prenoms_rares" "F")))))

(deftest t-baby-names-per-year
  (testing-with-timeout "baby names per year"
    (let [aaron (sut/baby-names-per-year "aaron" "M")]
      (is (= (get aaron 2019) 2443))
      (is (= (get aaron 2018) 2248))
      (is (= (get aaron 2017) 2383))
      (is (= (get aaron 2000) 118)))
    (let [fabienne (sut/baby-names-per-year "fabienne""F")]
      (is (= (fabienne 1999) 23))
      (is (= (fabienne 1998) 32))
      (is (= (fabienne 2010) 7))
      (is (= (fabienne 2011) 7))
      (is (= (fabienne 1962) 4739)))))

(deftest t-count-names-0
  (testing-with-timeout "count names in year range without elide"
    (is (= (sut/count-names-in-year-range "aaron"  "M"  1900  2000  false)
           723))))

(deftest t-count-names-1
  (testing-with-timeout "count names in year range without elide"
    (is (= (sut/count-names-in-year-range "fabienne"  "M"  1900  2000  false)
           0))
    (is (= (sut/count-names-in-year-range "fabienne"  "F"  1900  2000  false)
           105177))
    (is (= (sut/count-names-in-year-range "fabienne"  "F"  1000  3000  false)
           105326))
    (is (= (sut/count-names-in-year-range "fabienne"  "F"  1962  1962  false)
           4739))
    (is (= (sut/count-names-in-year-range "jeremy"  "M"  1900  2000  false)
           106063))
    (is (= (sut/count-names-in-year-range "jerémy"  "M"  1900  2000  false)
           99))
    (is (= (sut/count-names-in-year-range "agnes"  "F"  1900  2050  false)
           158))
    (is (= (sut/count-names-in-year-range "agnés"  "F"  1900  2050  false)
           0))
    (is (= (sut/count-names-in-year-range "agnès"  "F"  1900  2050  false)
           112773))))

(deftest t-count-names-elide
  (testing-with-timeout "count names in year range with elide"

    (is (= (sut/count-names-in-year-range "jeremy"  "M"  1900  2000  true)
           121220))
    (is (= (sut/count-names-in-year-range "jerome"  "M"  1900  2000  true)
           206081))
    (is (= (sut/count-names-in-year-range "zumra"  "F"  2000  2050  true)
           287))
    (is (= (sut/count-names-in-year-range "agnes"  "F"  1900  2050  true)
           112931))))

(deftest t-count-names-2-elide
  (testing-with-timeout "count names with elide"
    ;; jerome vs jérôme vs jérome

    (is (= {} (sut/baby-names-per-year "jérôme" "M"  true)))
    (is (= {} (sut/baby-names-per-year "jerôme" "M"  true)))
    (is (= {} (sut/baby-names-per-year "jérome" "M"  true)))
    (is (= {} (sut/baby-names-per-year "jéröme" "M"  true)))


    (let [j1 (sut/baby-names-per-year "jerome" "M"  false)
          j2 (sut/baby-names-per-year "jérôme" "M"  false)
          j3 (sut/baby-names-per-year "jérome" "M"  false)
          j4 (sut/baby-names-per-year "jéröme" "M"  false)
          j5 (sut/baby-names-per-year "jerôme" "M"  false)
          j6 (sut/baby-names-per-year "jerome" "M"  true)]
      (is (= (j1 1997) 525))
      (is (= (j1 1956) 320))
      (is (= (j1 1939) 73))
      
      (is (= (j2 1932) 3))
      (is (= (j2 1947) 12))
      (is (= (j2 1984) 763))
      (is (= (j2 1979) 1174))

      (is (= (j3 1981) 94))
      (is (= (j3 1972) 87))
      (is (= (j3 2013) 4))
      (is (= (j3 1966) 29))
      (is (= (j3 1998) 25))

      (is (= (j4 1985) 4))
      (is (= (j4 1975) 4))
      (is (= (j4 1983) 3))
      (is (= (j4 1974) 3))
      (is (= (j4 1977) 7))

      (is (= (j5 1968) 14))
      (is (= (j5 1964) 13))
      (is (= (j5 1965) 10))
      (is (= (j5 1997) 11))

      (is (not (= j6 j1)))

      (doseq [year (range 1900 2031)]
        (is (= (get j6 year 0)
               (+ (get j1 year 0)
                  (get j2 year 0)
                  (get j3 year 0)
                  (get j4 year 0)
                  (get j5 year 0)))
            (cl-format nil
                       "year=~A ~A + ~A + ~A + ~A + ~A != ~A"
                       year (get j1 year 0)
                       (get j2 year 0)
                       (get j3 year 0)
                       (get j4 year 0)
                       (get j5 year 0)
                       (get j6 year 0)
                       ))))))

(deftest t-hyphenated-0a
  (testing-with-timeout "hyphenated names 0a"
    (is (not (member "jean" (sut/hyphenated-names "jean")))
        "jean-jean only ppaears in a year XXXX which should be ignored")))

(deftest t-hyphenated-0b
  (testing-with-timeout "hyphenated names 0b"
    (is (not (member "cyrille" (sut/hyphenated-names "cyrille"))))))

(deftest t-hyphenated-0c
  (testing-with-timeout "hyphenated names 0c"
    (is (member "marie" (sut/hyphenated-names "marie")))))

(deftest t-hyphenated-1
  (testing-with-timeout "hyphenated names"


    (let [jean  (sut/hyphenated-names "jean")]
      (doseq [n ["mario"  "michael"  "cyrille"  "simon" 
                 ;; "sully"  ;; only in year XXXX
                 "cyril"  "yannick"  "lou"  "mael" 
                 "lucien"  "eudes" 
                 ;; "constant" ;; only in year XXXX
                 ]]
        (is (member n jean)))
      (doseq [n ["sully"  "constant"]]
        (is (not (member n jean))
            (cl-format nil "failed to ignore year XXXX  thus found jean-~A" n))))
    (is (= #{"sainte"  "anne"  "marie"}
           (sut/hyphenated-names "catherine")))
    (let [el  (sut/hyphenated-names "el")
          ;; TODO need to test marie, how?
          marie (sut/hyphenated-names "marie")]
      (is (= #{"habib"  "hadi"  "amine"  "kal"  "hadji" 
               "krim"  "nour"  "rahman"  "hadj"  "houda"  "mehdi" 
               "abd"  "kader"  "yamine"  "mohamed"}
             el))
      (doseq [name el]
        (is (member "el" (sut/hyphenated-names name)))))))
