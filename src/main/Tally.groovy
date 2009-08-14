

class Tally {
    String doTally (file) {
        StringBuilder sb = new StringBuilder()
        def println = {
            sb.append(it)
            sb.append('\n')
        }
        def total=0

        def minSinceMidnight = { time ->
            def min = Integer.parseInt(time[0..1])*60 + Integer.parseInt(time[3..4])
            if(time[5]=='P')
               min += 12*60
            min
        }

//return minSinceMidnight("01:00PM")

        int start = -1
        def lastTime

        def entries = [ : ]
        def currentDay
        def matcher

        file.eachLine { line ->
            matcher = line =~ /^ (\d\d:\d\d[AP]M)/
            if(matcher) {
//       println '** '+line
               def time = matcher[0][1]
               lastTime = time
               if(start == -1 ){
                //println time
                start = minSinceMidnight(time)
               }
            }
            else {
               if(start!=-1){
                 def min = (minSinceMidnight(lastTime) - start)
                println currentDay.padRight(25,' ')+(int)(min/60)+':'+min%60
                 total += min
               }
//       println '-- '+line
               start = -1
            }

            matcher = line =~ /^\w+,\s\w+\s\w+/
            if(matcher) {
                currentDay = line
            }
        }
        println "total ${total/60} hours"
        sb.toString()
    }
}
