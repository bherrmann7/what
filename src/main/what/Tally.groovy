package what

class Tally {

    def html = true

    int rate = new File(System.properties["user.home"] + "/.rate").text.toInteger();

    int minSinceMidnight(time) {
        def min = Integer.parseInt(time[0..1]) * 60 + Integer.parseInt(time[3..4])
        if (time[0..1] != '12' && time[5] == 'P')
            min += 12 * 60
        min
    }

    def days = []

    def addDay = { date, hours, summary ->
        days << [date: date, hours: hours, summary: summary]
    }

    def total = 0
    def day = 0

    String doTally(file, surpressText = false) {

        int start = -1
        def lastTime

        def currentDay
        def matcher
        def endWith = ' '

        boolean stopMarker

        file.eachLine {line ->
            if (stopMarker)
                return
            if (line.trim().equals('-')) {
                stopMarker = true;
                return;
            }

            matcher = line =~ /^ (\d\d:\d\d[AP]M)/
            if (matcher) {
                def time = matcher[0][1]
                lastTime = time
                if (start == -1) {
                    //addLine time
                    start = minSinceMidnight(time)
                }
            }
            else {
                if (start != -1) {
                    if (line.startsWith('s:'))
                        endWith = line.substring(2).trim()
                    def min = (minSinceMidnight(lastTime) - start)
                    def minStr = Integer.toString(min % 60)
                    if (minStr.size() == 1)
                        minStr = '0' + minStr
                    total += min
                    day += min
                }
                start = -1
            }

            matcher = line =~ /^\w+,\s\w+\s\w+/
            if (matcher) {
                if (currentDay) {
                    addDay currentDay, day / 60, endWith
                }
                day = 0
                currentDay = line
            }

        }
        if (currentDay) {
            addDay currentDay, day / 60, endWith
        }

        StringBuilder sb = new StringBuilder()

        def weeklyTotal = 0

        // text version
        if (!surpressText) {
            days.each { day ->
                if (day.date.startsWith("Mon")) {
                    sb.append("weekly total: ${weeklyTotal}\n\n")
                    weeklyTotal = 0
                }
                weeklyTotal += day.hours
                sb.append day.date.padRight(30) + day.hours.toString().padRight(4) + '   ' + day.summary + '\n'
            }
            if (weeklyTotal) {
                sb.append("weekly total (so far): ${weeklyTotal}\n")
            }
        }
        sb.append('\n')

        // html version
        days.each { day ->
            sb.append "<tr><td>$day.date</td><td>$day.hours</td><td>$day.summary</td></tr>\n"
        }

        sb.append "</table><br/><br/>\n"
        sb.append "     Total = ${total / 60} hours at \$${rate}/hr = \$${total / 60 * rate}"


        sb.toString()
    }
}
