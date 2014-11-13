(ns util.phonenumber.e164
  "Functions for manipulating e.164 telephone numbers, taken from Richard Newman")

;;; Based on some of my (Richard Newman's) Common Lisp code for extracting country codes
;;; from strings.

(defmacro ^{:private true} match-e164
  "Generate a huge nested condp expression. v1-v3 should indicate integers."
  [[v1 v2 v3 str] expr]
  `(condp = ~v1
     ~@(mapcat
        (fn [x]
          (if (number? x)
            [x [x 1]]
            (if (list? x)
              [(first x)
               (if (= :any (second x))
                 [`(+ ~(* 10 (first x))
                      ~v2)
                  2]
                 `(condp = ~v2
                    ~@(mapcat
                       (fn [y]
                         (if (number? y)
                           [y [(+ (* 10 (first x))
                                  y)
                               2]]
                           (if (list? y)
                             [(first y)
                              (if (= :any (second y))
                                [`(+ ~(+ (* 100 (first x))
                                         (* 10 (first y)))
                                     ~v3)
                                 3]
                                `(condp = ~v3
                                   ~@(mapcat (fn [z]
                                               (if (number? z)
                                                 [z [(+ (* 100 (first x))
                                                        (* 10 (first y))
                                                        z)
                                                     3]]
                                                 `(error "Third level cannot nest.")))
                                             (rest y))))
                              ])))
                       (rest x))))])))
        expr)))


(defn str->cc
  "Extracts a CC from s. Returns [cc, offset-of-remainder]."
  [#^String s]
  (let [len (.length s)
        number-0 (Character/digit (.charAt s 0) 10)]
    
    (if (== number-0 1)
      [1 1])
    (let [number-1 (when (and number-0
                              (> len 1))
                     (Character/digit (.charAt s 1) 10))
        
          number-2 (when (and number-1
                              (> len 2))
                     (Character/digit (.charAt s 2) 10))]
    
      (when number-1
        (match-e164 (number-0 number-1 number-2 e164)
                  
                    ;; This is an encoding of the E164 country code table.
                    ;; It contains three tiers.
                    ;; Notable ignored prefixes: 388-3, a subpart of 388 for 
                    ;; Europe-wide services; US territories, Bermuda, Caribbean
                    ;; nations, Guam, East Timor, American Samoa (appear as +1;
                    ;; check area code); Turkish Republic of Northern Cyprus.
                    (0
                     1
                     (2 0
                        (1 2 3 6 8)
                        (2 :any)
                        (3 :any)
                        (4 :any)
                        (5 0 1 2 3 4 5 6 7 8)
                        (6 :any)
                        7
                        (9 0 1 7 8 9))
                     (3 0 1 2 3 4
                        (5 :any)
                        6
                        (7 :any)
                        (8 0 1 2 5 6 7 8 9)
                        9)
                     (4 0 1 (2 0 1 3) 3 4 5 6 7 8 9)
                     (5 (0 :any)
                        1 2 3 4 5 6 7 8
                        (9 :any))
                     (6 0 1 2 3 4 5 6 
                        (7 0 2 3 4 5 6 7 8 9)
                        (8 0 1 2 3 5 6 7 8 9)
                        (9 0 1 2))
                     7
                     (8 (0 0 8)
                        1 2 4 
                        (5 0 2 3 5 6)
                        6
                        (7 0 1 2 3 4 8)
                        (8 0 1 2 3 6 8))
                     (9 0 1 2 3 4 5
                        (6 0 1 2 3 4 5 6 7 8)
                        (7 0 1 2 3 4 5 6 7 9)
                        8
                        (9 1 2 3 4 5 6 8))))))))

(def itu-code->primary-country-name
     {  1   "United States of America"
       20   "Egypt"
      212   "Morocco"
      213   "Algeria"
      216   "Tunisia"
      218   "Libya"
      220   "Gambia"
      221   "Senegal"
      222   "Mauritania"
      223   "Mali"
      224   "Guinea"
      225   "Côte d'Ivoire"
      ;225   "Ivory Coast"
      226   "Burkina Faso"
      227   "Niger"
      228   "Togo"
      229   "Benin"
      230   "Mauritius"
      231   "Liberia"
      232   "Sierra Leone"
      233   "Ghana"
      234   "Nigeria"
      235   "Chad"
      236   "Central African Republic"
      237   "Cameroon"
      238   "Cape Verde"
      239   "São Tomé and Príncipe"
      240   "Equatorial Guinea"
      241   "Gabon"
      242   "Republic of the Congo"
      243   "Democratic Republic of the Congo"
      ;243   "Congo"
      244   "Angola"
      245   "Guinea-Bissau"
      246   "Diego Garcia"
      247   "Ascension Island"
      248   "Seychelles"
      249   "Sudan"
      250   "Rwanda"
      251   "Ethiopia"
      252   "Somalia"
      253   "Djibouti"
      254   "Kenya"
      255   "Tanzania"
      256   "Uganda"
      257   "Burundi"
      258   "Mozambique"
      260   "Zambia"
      261   "Madagascar"
      262   "Réunion"
      263   "Zimbabwe"
      264   "Namibia"
      265   "Malawi"
      266   "Lesotho"
      267   "Botswana"
      268   "Swaziland"
      269   "Comoros"
       27   "South Africa"
      290   "Saint Helena"
      291   "Eritrea"
      297   "Aruba"
      298   "Faroe Islands"
      299   "Greenland"
       30   "Greece"
       31   "The Netherlands"
       32   "Belgium"
       33   "France"
       34   "Spain"
      350   "Gibraltar"
      351   "Portugal"
      352   "Luxembourg"
      353   "Ireland"
      354   "Iceland"
      355   "Albania"
      356   "Malta"
      357   "Cyprus"
      358   "Finland"
      359   "Bulgaria"
       36   "Hungary"
      370   "Lithuania"
      371   "Latvia"
      372   "Estonia"
      373   "Moldova"
      374   "Armenia"
      375   "Belarus"
      376   "Andorra"
      377   "Monaco"
      378   "San Marino"
      380   "Ukraine"
      381   "Serbia"
      382   "Montenegro"
      385   "Croatia"
      386   "Slovenia"
      387   "Bosnia and Herzegovina"
      389   "Republic of Macedonia"
      39    "Italy"
       40   "Romania"
       41   "Switzerland"
      420   "Czech Republic"
      421   "Slovakia"
      423   "Liechtenstein"
       43   "Austria"
       44   "United Kingdom"
       45   "Denmark"
       46   "Sweden"
       47   "Norway"
       48   "Poland"
       49   "Germany"
      500   "Falkland Islands"
      501   "Belize"
      502   "Guatemala"
      503   "El Salvador"
      504   "Honduras"
      505   "Nicaragua"
      506   "Costa Rica"
      507   "Panama"
      508   "Saint-Pierre"
      ;508   "Miquelon"
      509   "Haiti"
       51   "Peru"
       52   "Mexico"
       53   "Cuba"
       54   "Argentina"
       55   "Brazil"
       56   "Chile"
       57   "Colombia"
       58   "Venezuela"
      590   "Guadeloupe"
      591   "Bolivia"
      592   "Guyana"
      593   "Ecuador"
      594   "French Guiana"
      595   "Paraguay"
      596   "Martinique"
      597   "Suriname"
      598   "Uruguay"
      599   "Netherlands Antilles"
       60   "Malaysia"
       61   "Australia"
       62   "Indonesia"
       63   "Philippines"
       64   "New Zealand"
       65   "Singapore"
       66   "Thailand"
      670   "East Timor"
      672   "Australian Territories"
      673   "Brunei"
      674   "Nauru"
      675   "Papua New Guinea"
      676   "Tonga"
      677   "Solomon Islands"
      678   "Vanuatu"
      679   "Fiji"
      680   "Palau"
      681   "Wallis and Futuna"
      682   "Cook Islands"
      683   "Niue Island"
      685   "Samoa"
      686   "Kiribati"
      687   "New Caledonia"
      688   "Tuvalu"
      689   "French Polynesia"
      690   "Tokelau"
      691   "Federated States of Micronesia"
      692   "Marshall Islands"
        7   "Russia"
       81   "Japan"
       82   "South Korea"
       84   "Vietnam"
      850   "North Korea"
      852   "Hong Kong"
      853   "Macau"
      855   "Cambodia"
      856   "Laos"
       86   "Mainland China"
      880   "Bangladesh"
      886   "Taiwan"
       90   "Turkey"
       91   "India"
       92   "Pakistan"
       93   "Afghanistan"
       94   "Sri Lanka"
       95   "Burma"
      960   "Maldives"
      961   "Lebanon"
      962   "Jordan"
      963   "Syria"
      964   "Iraq"
      965   "Kuwait"
      966   "Saudi Arabia"
      967   "Yemen"
      968   "Oman"
      971   "United Arab Emirates"
      972   "Israel"
      973   "Bahrain"
      974   "Qatar"
      975   "Bhutan"
      976   "Mongolia"
      977   "Nepal"
       98   "Iran"
      992   "Tajikistan"
      993   "Turkmenistan"
      994   "Azerbaijan"
      995   "Georgia"
      996   "Kyrgyzstan"
      998   "Uzbekistan"})

(def country-name->itu-code
     {"Netherlands Antilles" 599,
      "Australia" 61,
      "Moldova" 373,
      "Cambodia" 855,
      "Israel" 972,
      "Mauritania" 222,
      "The Netherlands" 31,
      "Slovenia" 386,
      "Algeria" 213,
      "Monaco" 377,
      "Philippines" 63,
      "New Zealand" 64,
      "Costa Rica" 506,
      "French Polynesia" 689,
      "Slovakia" 421,
      "Hungary" 36,
      "Fiji" 679,
      "Falkland Islands" 500,
      "Togo" 228,
      "Aruba" 297,
      "Macau" 853,
      "Bangladesh" 880,
      "Belgium" 32,
      "Switzerland" 41,
      "United Arab Emirates" 971,
      "Jordan" 962,
      "Syria" 963,
      "Papua New Guinea" 675,
      "Bolivia" 591,
      "Indonesia" 62,
      "Suriname" 597,
      "New Caledonia" 687,
      "Singapore" 65,
      "Nepal" 977,
      "Afghanistan" 93,
      "Haiti" 509,
      "Malta" 356,
      "Macedonia" 389,
      "Nauru" 674,
      "Cook Islands" 682,
      "Miquelon" 508,
      "Paraguay" 595,
      "Lesotho" 266,
      "Ascension Island" 247,
      "Luxembourg" 352,
      "Japan" 81,
      "Ireland" 353,
      "Burkina Faso" 226,
      "Seychelles" 248,
      "Argentina" 54,
      "Gambia" 220,
      "Thailand" 66,
      "Gabon" 241,
      "Maldives" 960,
      "Taiwan" 886,
      "Gibraltar" 350,
      "Chad" 235,
      "Comoros" 269,
      "Czech Republic" 420,
      "Kiribati" 686,
      "Ethiopia" 251,
      "Guyana" 592,
      "Andorra" 376,
      "Spain" 34,
      "Lithuania" 370,
      "Senegal" 221,
      "Botswana" 267,
      "Turks and Caicos Islands" 1,
      "Poland" 48,
      "Congo" 243,
      "Angola" 244,
      "Niue Island" 683,
      "Liberia" 231,
      "Yemen" 967,
      "Tokelau" 690,
      "Belize" 501,
      "Nicaragua" 505,
      "Solomon Islands" 677,
      "Croatia" 385,
      "Oman" 968,
      "Faroe Islands" 298,
      "Mauritius" 230,
      "Namibia" 264,
      "Sweden" 46,
      "Honduras" 504,
      "San Marino" 378,
      "São Tomé and Príncipe" 239,
      "Madagascar" 261,
      "Marshall Islands" 692,
      "Brazil" 55,
      "Libya" 218,
      "South Africa" 27,
      "Greece" 30,
      "Tonga" 676,
      "Brunei" 673,
      "Guatemala" 502,
      "Greenland" 299,
      "Bosnia Herzegovina" 387,
      "Rwanda" 250,
      "Abkhazia" 7,
      "North Korea" 850,
      "Sudan" 249,
      "Palau" 680,
      "Latvia" 371,
      "Guinea" 224,
      "Tunisia" 216,
      "Morocco" 212,
      "Mongolia" 976,
      "Benin" 229,
      "Vanuatu" 678,
      "Montenegro" 382,
      "Cuba" 53,
      "Samoa" 685,
      "Mali" 223,
      "Nigeria" 234,
      "Cameroon" 237,
      "Bhutan" 975,
      "Kyrgyzstan" 996,
      "Azerbaijan" 994,
      "Burma" 95,
      "Ghana" 233,
      "Zimbabwe" 263,
      "Martinique" 596,
      "Central African Republic" 236,
      "Germany" 49,
      "Uzbekistan" 998,
      "Zambia" 260,
      "Somalia" 252,
      "Lebanon" 961,
      "Mexico" 52,
      "Austria" 43,
      "Qatar" 974,
      "Tuvalu" 688,
      "Romania" 40,
      "Finland" 358,
      "Republic of the Congo" 242,
      "Cyprus" 357,
      "Nagorno-Karabakh" 374,
      "Mozambique" 258,
      "Iceland" 354,
      "Eritrea" 291,
      "Iran" 98,
      "South Korea" 82,
      "Federated States of Micronesia" 691,
      "El Salvador" 503,
      "India" 91,
      "China" 86,
      "Swaziland" 268,
      "Bulgaria" 359,
      "Cape Verde" 238,
      "Panama" 507,
      "Herm" 44,
      "Tajikistan" 992,
      "Belarus" 375,
      "East Timor" 670,
      "Wallis and Futuna" 681,
      "Peru" 51,
      "Vietnam" 84,
      "Liechtenstein" 423,
      "Guinea-Bissau" 245,
      "Tristan da Cunha" 290,
      "Kosovo" 381,
      "Laos" 856,
      "Venezuela" 58,
      "Sri Lanka" 94,
      "Iraq" 964,
      "Turkish Republic of Northern Cyprus" 90,
      "Niger" 227,
      "Denmark" 45,
      "Saint Martin" 590,
      "Equatorial Guinea" 240,
      "Djibouti" 253,
      "France" 33,
      "Egypt" 20,
      "Turkmenistan" 993,
      "Diego Garcia" 246,
      "Mayotte" 262,
      "Ukraine" 380,
      "Vatican City" 39,
      "Malawi" 265,
      "Malaysia" 60,
      "Kenya" 254,
      "Colombia" 57,
      "Norfolk Island" 672,
      "Uganda" 256,
      "Albania" 355,
      "Chile" 56,
      "Hong Kong" 852,
      "Ecuador" 593,
      "Estonia" 372,
      "Pakistan" 92,
      "Bahrain" 973,
      "Burundi" 257,
      "Georgia" 995,
      "Saudi Arabia" 966,
      "Portugal" 351,
      "Tanzania" 255,
      "Norway" 47,
      "Uruguay" 598,
      "Kuwait" 965,
      "Ivory Coast" 225,
      "Sierra Leone" 232,
      "French Guiana" 594})
