package com.abhishekbharti.lounge.login

class CountryCodeUtils {

    fun getCountriesList(): ArrayList<CountryDetails> {
        val countries: MutableList<CountryDetails> = ArrayList()
        //top countries
        countries.add(CountryDetails("in", "91", "India"))
        countries.add(CountryDetails("us", "1", "United States"))
        countries.add(
            CountryDetails(
                "ae",
                "971",
                "United Arab Emirates (UAE)"
            )
        )

        //other countries
        countries.add(CountryDetails("ad", "376", "Andorra"))
        countries.add(
            CountryDetails(
                "ae",
                "971",
                "United Arab Emirates (UAE)"
            )
        )
        countries.add(CountryDetails("af", "93", "Afghanistan"))
        countries.add(CountryDetails("ag", "1", "Antigua and Barbuda"))
        countries.add(CountryDetails("ai", "1", "Anguilla"))
        countries.add(CountryDetails("al", "355", "Albania"))
        countries.add(CountryDetails("am", "374", "Armenia"))
        countries.add(CountryDetails("ao", "244", "Angola"))
        countries.add(CountryDetails("aq", "672", "Antarctica"))
        countries.add(CountryDetails("ar", "54", "Argentina"))
        countries.add(CountryDetails("as", "1", "American Samoa"))
        countries.add(CountryDetails("at", "43", "Austria"))
        countries.add(CountryDetails("au", "61", "Australia"))
        countries.add(CountryDetails("aw", "297", "Aruba"))
        countries.add(CountryDetails("ax", "358", "Åland Islands"))
        countries.add(CountryDetails("az", "994", "Azerbaijan"))
        countries.add(
            CountryDetails(
                "ba",
                "387",
                "Bosnia And Herzegovina"
            )
        )
        countries.add(CountryDetails("bb", "1", "Barbados"))
        countries.add(CountryDetails("bd", "880", "Bangladesh"))
        countries.add(CountryDetails("be", "32", "Belgium"))
        countries.add(CountryDetails("bf", "226", "Burkina Faso"))
        countries.add(CountryDetails("bg", "359", "Bulgaria"))
        countries.add(CountryDetails("bh", "973", "Bahrain"))
        countries.add(CountryDetails("bi", "257", "Burundi"))
        countries.add(CountryDetails("bj", "229", "Benin"))
        countries.add(CountryDetails("bl", "590", "Saint Barthélemy"))
        countries.add(CountryDetails("bm", "1", "Bermuda"))
        countries.add(CountryDetails("bn", "673", "Brunei Darussalam"))
        countries.add(
            CountryDetails(
                "bo",
                "591",
                "Bolivia, Plurinational State Of"
            )
        )
        countries.add(CountryDetails("br", "55", "Brazil"))
        countries.add(CountryDetails("bs", "1", "Bahamas"))
        countries.add(CountryDetails("bt", "975", "Bhutan"))
        countries.add(CountryDetails("bw", "267", "Botswana"))
        countries.add(CountryDetails("by", "375", "Belarus"))
        countries.add(CountryDetails("bz", "501", "Belize"))
        countries.add(CountryDetails("ca", "1", "Canada"))
        countries.add(
            CountryDetails(
                "cc",
                "61",
                "Cocos (keeling) Islands"
            )
        )
        countries.add(
            CountryDetails(
                "cd",
                "243",
                "Congo, The Democratic Republic Of The"
            )
        )
        countries.add(
            CountryDetails(
                "cf",
                "236",
                "Central African Republic"
            )
        )
        countries.add(CountryDetails("cg", "242", "Congo"))
        countries.add(CountryDetails("ch", "41", "Switzerland"))
        countries.add(CountryDetails("ci", "225", "Côte D'ivoire"))
        countries.add(CountryDetails("ck", "682", "Cook Islands"))
        countries.add(CountryDetails("cl", "56", "Chile"))
        countries.add(CountryDetails("cm", "237", "Cameroon"))
        countries.add(CountryDetails("cn", "86", "China"))
        countries.add(CountryDetails("co", "57", "Colombia"))
        countries.add(CountryDetails("cr", "506", "Costa Rica"))
        countries.add(CountryDetails("cu", "53", "Cuba"))
        countries.add(CountryDetails("cv", "238", "Cape Verde"))
        countries.add(CountryDetails("cw", "599", "Curaçao"))
        countries.add(CountryDetails("cx", "61", "Christmas Island"))
        countries.add(CountryDetails("cy", "357", "Cyprus"))
        countries.add(CountryDetails("cz", "420", "Czech Republic"))
        countries.add(CountryDetails("de", "49", "Germany"))
        countries.add(CountryDetails("dj", "253", "Djibouti"))
        countries.add(CountryDetails("dk", "45", "Denmark"))
        countries.add(CountryDetails("dm", "1", "Dominica"))
        countries.add(CountryDetails("do", "1", "Dominican Republic"))
        countries.add(CountryDetails("dz", "213", "Algeria"))
        countries.add(CountryDetails("ec", "593", "Ecuador"))
        countries.add(CountryDetails("ee", "372", "Estonia"))
        countries.add(CountryDetails("eg", "20", "Egypt"))
        countries.add(CountryDetails("er", "291", "Eritrea"))
        countries.add(CountryDetails("es", "34", "Spain"))
        countries.add(CountryDetails("et", "251", "Ethiopia"))
        countries.add(CountryDetails("fi", "358", "Finland"))
        countries.add(CountryDetails("fj", "679", "Fiji"))
        countries.add(
            CountryDetails(
                "fk",
                "500",
                "Falkland Islands (malvinas)"
            )
        )
        countries.add(
            CountryDetails(
                "fm",
                "691",
                "Micronesia, Federated States Of"
            )
        )
        countries.add(CountryDetails("fo", "298", "Faroe Islands"))
        countries.add(CountryDetails("fr", "33", "France"))
        countries.add(CountryDetails("ga", "241", "Gabon"))
        countries.add(CountryDetails("gb", "44", "United Kingdom"))
        countries.add(CountryDetails("gd", "1", "Grenada"))
        countries.add(CountryDetails("ge", "995", "Georgia"))
        countries.add(CountryDetails("gf", "594", "French Guyana"))
        countries.add(CountryDetails("gh", "233", "Ghana"))
        countries.add(CountryDetails("gi", "350", "Gibraltar"))
        countries.add(CountryDetails("gl", "299", "Greenland"))
        countries.add(CountryDetails("gm", "220", "Gambia"))
        countries.add(CountryDetails("gn", "224", "Guinea"))
        countries.add(CountryDetails("gp", "450", "Guadeloupe"))
        countries.add(CountryDetails("gq", "240", "Equatorial Guinea"))
        countries.add(CountryDetails("gr", "30", "Greece"))
        countries.add(CountryDetails("gt", "502", "Guatemala"))
        countries.add(CountryDetails("gu", "1", "Guam"))
        countries.add(CountryDetails("gw", "245", "Guinea-bissau"))
        countries.add(CountryDetails("gy", "592", "Guyana"))
        countries.add(CountryDetails("hk", "852", "Hong Kong"))
        countries.add(CountryDetails("hn", "504", "Honduras"))
        countries.add(CountryDetails("hr", "385", "Croatia"))
        countries.add(CountryDetails("ht", "509", "Haiti"))
        countries.add(CountryDetails("hu", "36", "Hungary"))
        countries.add(CountryDetails("id", "62", "Indonesia"))
        countries.add(CountryDetails("ie", "353", "Ireland"))
        countries.add(CountryDetails("il", "972", "Israel"))
        countries.add(CountryDetails("im", "44", "Isle Of Man"))
        countries.add(CountryDetails("is", "354", "Iceland"))
        countries.add(CountryDetails("in", "91", "India"))
        countries.add(
            CountryDetails(
                "io",
                "246",
                "British Indian Ocean Territory"
            )
        )
        countries.add(CountryDetails("iq", "964", "Iraq"))
        countries.add(
            CountryDetails(
                "ir",
                "98",
                "Iran, Islamic Republic Of"
            )
        )
        countries.add(CountryDetails("it", "39", "Italy"))
        countries.add(CountryDetails("je", "44", "Jersey "))
        countries.add(CountryDetails("jm", "1", "Jamaica"))
        countries.add(CountryDetails("jo", "962", "Jordan"))
        countries.add(CountryDetails("jp", "81", "Japan"))
        countries.add(CountryDetails("ke", "254", "Kenya"))
        countries.add(CountryDetails("kg", "996", "Kyrgyzstan"))
        countries.add(CountryDetails("kh", "855", "Cambodia"))
        countries.add(CountryDetails("ki", "686", "Kiribati"))
        countries.add(CountryDetails("km", "269", "Comoros"))
        countries.add(CountryDetails("kn", "1", "Saint Kitts and Nevis"))
        countries.add(CountryDetails("kp", "850", "North Korea"))
        countries.add(CountryDetails("kr", "82", "South Korea"))
        countries.add(CountryDetails("kw", "965", "Kuwait"))
        countries.add(CountryDetails("ky", "1", "Cayman Islands"))
        countries.add(CountryDetails("kz", "7", "Kazakhstan"))
        countries.add(
            CountryDetails(
                "la",
                "856",
                "Lao People's Democratic Republic"
            )
        )
        countries.add(CountryDetails("lb", "961", "Lebanon"))
        countries.add(CountryDetails("lc", "1", "Saint Lucia"))
        countries.add(CountryDetails("li", "423", "Liechtenstein"))
        countries.add(CountryDetails("lk", "94", "Sri Lanka"))
        countries.add(CountryDetails("lr", "231", "Liberia"))
        countries.add(CountryDetails("ls", "266", "Lesotho"))
        countries.add(CountryDetails("lt", "370", "Lithuania"))
        countries.add(CountryDetails("lu", "352", "Luxembourg"))
        countries.add(CountryDetails("lv", "371", "Latvia"))
        countries.add(CountryDetails("ly", "218", "Libya"))
        countries.add(CountryDetails("ma", "212", "Morocco"))
        countries.add(CountryDetails("mc", "377", "Monaco"))
        countries.add(CountryDetails("md", "373", "Moldova, Republic Of"))
        countries.add(CountryDetails("me", "382", "Montenegro"))
        countries.add(CountryDetails("mf", "590", "Saint Martin"))
        countries.add(CountryDetails("mg", "261", "Madagascar"))
        countries.add(CountryDetails("mh", "692", "Marshall Islands"))
        countries.add(CountryDetails("mk", "389", "Macedonia (FYROM)"))
        countries.add(CountryDetails("ml", "223", "Mali"))
        countries.add(CountryDetails("mm", "95", "Myanmar"))
        countries.add(CountryDetails("mn", "976", "Mongolia"))
        countries.add(CountryDetails("mo", "853", "Macau"))
        countries.add(
            CountryDetails(
                "mp",
                "1",
                "Northern Mariana Islands"
            )
        )
        countries.add(CountryDetails("mq", "596", "Martinique"))
        countries.add(CountryDetails("mr", "222", "Mauritania"))
        countries.add(CountryDetails("ms", "1", "Montserrat"))
        countries.add(CountryDetails("mt", "356", "Malta"))
        countries.add(CountryDetails("mu", "230", "Mauritius"))
        countries.add(CountryDetails("mv", "960", "Maldives"))
        countries.add(CountryDetails("mw", "265", "Malawi"))
        countries.add(CountryDetails("mx", "52", "Mexico"))
        countries.add(CountryDetails("my", "60", "Malaysia"))
        countries.add(CountryDetails("mz", "258", "Mozambique"))
        countries.add(CountryDetails("na", "264", "Namibia"))
        countries.add(CountryDetails("nc", "687", "New Caledonia"))
        countries.add(CountryDetails("ne", "227", "Niger"))
        countries.add(CountryDetails("nf", "672", "Norfolk Islands"))
        countries.add(CountryDetails("ng", "234", "Nigeria"))
        countries.add(CountryDetails("ni", "505", "Nicaragua"))
        countries.add(CountryDetails("nl", "31", "Netherlands"))
        countries.add(CountryDetails("no", "47", "Norway"))
        countries.add(CountryDetails("np", "977", "Nepal"))
        countries.add(CountryDetails("nr", "674", "Nauru"))
        countries.add(CountryDetails("nu", "683", "Niue"))
        countries.add(CountryDetails("nz", "64", "New Zealand"))
        countries.add(CountryDetails("om", "968", "Oman"))
        countries.add(CountryDetails("pa", "507", "Panama"))
        countries.add(CountryDetails("pe", "51", "Peru"))
        countries.add(CountryDetails("pf", "689", "French Polynesia"))
        countries.add(CountryDetails("pg", "675", "Papua New Guinea"))
        countries.add(CountryDetails("ph", "63", "Philippines"))
        countries.add(CountryDetails("pk", "92", "Pakistan"))
        countries.add(CountryDetails("pl", "48", "Poland"))
        countries.add(
            CountryDetails(
                "pm",
                "508",
                "Saint Pierre And Miquelon"
            )
        )
        countries.add(CountryDetails("pn", "870", "Pitcairn Islands"))
        countries.add(CountryDetails("pr", "1", "Puerto Rico"))
        countries.add(CountryDetails("ps", "970", "Palestine"))
        countries.add(CountryDetails("pt", "351", "Portugal"))
        countries.add(CountryDetails("pw", "680", "Palau"))
        countries.add(CountryDetails("py", "595", "Paraguay"))
        countries.add(CountryDetails("qa", "974", "Qatar"))
        countries.add(CountryDetails("re", "262", "Réunion"))
        countries.add(CountryDetails("ro", "40", "Romania"))
        countries.add(CountryDetails("rs", "381", "Serbia"))
        countries.add(CountryDetails("ru", "7", "Russian Federation"))
        countries.add(CountryDetails("rw", "250", "Rwanda"))
        countries.add(CountryDetails("sa", "966", "Saudi Arabia"))
        countries.add(CountryDetails("sb", "677", "Solomon Islands"))
        countries.add(CountryDetails("sc", "248", "Seychelles"))
        countries.add(CountryDetails("sd", "249", "Sudan"))
        countries.add(CountryDetails("se", "46", "Sweden"))
        countries.add(CountryDetails("sg", "65", "Singapore"))
        countries.add(
            CountryDetails(
                "sh",
                "290",
                "Saint Helena, Ascension And Tristan Da Cunha"
            )
        )
        countries.add(CountryDetails("si", "386", "Slovenia"))
        countries.add(CountryDetails("sk", "421", "Slovakia"))
        countries.add(CountryDetails("sl", "232", "Sierra Leone"))
        countries.add(CountryDetails("sm", "378", "San Marino"))
        countries.add(CountryDetails("sn", "221", "Senegal"))
        countries.add(CountryDetails("so", "252", "Somalia"))
        countries.add(CountryDetails("sr", "597", "Suriname"))
        countries.add(CountryDetails("ss", "211", "South Sudan"))
        countries.add(CountryDetails("st", "239", "Sao Tome And Principe"))
        countries.add(CountryDetails("sv", "503", "El Salvador"))
        countries.add(CountryDetails("sx", "1", "Sint Maarten"))
        countries.add(CountryDetails("sy", "963", "Syrian Arab Republic"))
        countries.add(CountryDetails("sz", "268", "Swaziland"))
        countries.add(
            CountryDetails(
                "tc",
                "1",
                "Turks and Caicos Islands"
            )
        )
        countries.add(CountryDetails("td", "235", "Chad"))
        countries.add(CountryDetails("tg", "228", "Togo"))
        countries.add(CountryDetails("th", "66", "Thailand"))
        countries.add(CountryDetails("tj", "992", "Tajikistan"))
        countries.add(CountryDetails("tk", "690", "Tokelau"))
        countries.add(CountryDetails("tl", "670", "Timor-leste"))
        countries.add(CountryDetails("tm", "993", "Turkmenistan"))
        countries.add(CountryDetails("tn", "216", "Tunisia"))
        countries.add(CountryDetails("to", "676", "Tonga"))
        countries.add(CountryDetails("tr", "90", "Turkey"))
        countries.add(CountryDetails("tt", "1", "Trinidad &amp; Tobago"))
        countries.add(CountryDetails("tv", "688", "Tuvalu"))
        countries.add(CountryDetails("tw", "886", "Taiwan"))
        countries.add(
            CountryDetails(
                "tz",
                "255",
                "Tanzania, United Republic Of"
            )
        )
        countries.add(CountryDetails("ua", "380", "Ukraine"))
        countries.add(CountryDetails("ug", "256", "Uganda"))
        countries.add(CountryDetails("us", "1", "United States"))
        countries.add(CountryDetails("uy", "598", "Uruguay"))
        countries.add(CountryDetails("uz", "998", "Uzbekistan"))
        countries.add(
            CountryDetails(
                "va",
                "379",
                "Holy See (vatican City State)"
            )
        )
        countries.add(
            CountryDetails(
                "vc",
                "1",
                "Saint Vincent &amp; The Grenadines"
            )
        )
        countries.add(
            CountryDetails(
                "ve",
                "58",
                "Venezuela, Bolivarian Republic Of"
            )
        )
        countries.add(CountryDetails("vg", "1", "British Virgin Islands"))
        countries.add(CountryDetails("vi", "1", "US Virgin Islands"))
        countries.add(CountryDetails("vn", "84", "Vietnam"))
        countries.add(CountryDetails("vu", "678", "Vanuatu"))
        countries.add(CountryDetails("wf", "681", "Wallis And Futuna"))
        countries.add(CountryDetails("ws", "685", "Samoa"))
        countries.add(CountryDetails("xk", "383", "Kosovo"))
        countries.add(CountryDetails("ye", "967", "Yemen"))
        countries.add(CountryDetails("yt", "262", "Mayotte"))
        countries.add(CountryDetails("za", "27", "South Africa"))
        countries.add(CountryDetails("zm", "260", "Zambia"))
        countries.add(CountryDetails("zw", "263", "Zimbabwe"))
        return countries as ArrayList<CountryDetails>
    }

    fun getCountryDetailsFromISO(iso: String): CountryDetails{
        val countriesList = getCountriesList()
        for(countryDetails in countriesList){
            if (countryDetails.nameCode.lowercase().equals(iso.lowercase())){
                return countryDetails
            }
        }
        return CountryDetails("in", "91", "India")
    }

    fun getCountryDetailsFromPhoneCode(phoneCode: String): CountryDetails{
        val countriesList = getCountriesList()
        for(countryDetails in countriesList){
            if(phoneCode == "1"){
                return CountryDetails("us", "1", "United States")
            } else if (countryDetails.phoneCode.lowercase().equals(phoneCode.lowercase())){
                return countryDetails
            }
        }
        return CountryDetails("in", "91", "India")
    }
}