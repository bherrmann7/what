class Tally {
    String doTally(file) {
        StringBuilder sb = new StringBuilder()
        def println = {
            sb.append(it)
            sb.append('\n')
        }
        def total = 0

        def minSinceMidnight = {time ->
            def min = Integer.parseInt(time[0..1]) * 60 + Integer.parseInt(time[3..4])
            if (time[0..1] != '12' && time[5] == 'P')
                min += 12 * 60
            min
        }

//return minSinceMidnight("01:00PM")

        int start = -1
        def lastTime

        def entries = [:]
        def currentDay
        def matcher

        file.eachLine {line ->
            matcher = line =~ /^ (\d\d:\d\d[AP]M)/
            if (matcher) {
//       println '** '+line
                def time = matcher[0][1]
                lastTime = time
                if (start == -1) {
                    //println time
                    start = minSinceMidnight(time)
                }
            }
            else {
                if (start != -1) {
                    def endWith =' '
                    if (line.startsWith('s:'))
                        endWith = line.substring(2).trim()
                    def min = (minSinceMidnight(lastTime) - start)
                    def minStr = Integer.toString(min % 60)
                    if (minStr.size()==1)
                      minStr = '0'+minStr
                    println currentDay+'\t' + (int) (min / 60) + ':' + minStr +'\t'+ endWith
                    total += min
                }
//       println '-- '+line
                start = -1
            }

            matcher = line =~ /^\w+,\s\w+\s\w+/
            if (matcher) {
                currentDay = line
            }
        }
        println "total ${total / 60} hours"
        sb.toString()
    }
}
