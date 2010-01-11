class Tally {

  def html = true

  int minSinceMidnight(time) {
    def min = Integer.parseInt(time[0..1]) * 60 + Integer.parseInt(time[3..4])
    if (time[0..1] != '12' && time[5] == 'P')
      min += 12 * 60
    min
  }

  StringBuilder sb = new StringBuilder()
  def addLine = {
    sb.append(it)
    sb.append('\n')
  }
  def total = 0
  def day = 0

  String doTally(file) {

    int start = -1
    def lastTime

    def currentDay
    def matcher
    def endWith = ' '

    file.eachLine {line ->
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
            if(html){
                  addLine "<tr><td>$currentDay</td><td>${day / 60}<td>$endWith</td></tr>"
            } else {
                  addLine currentDay + '\t' + day / 60  +'\t' + endWith
            }
        }
        day = 0
        currentDay = line
      }
      
    }
    if (currentDay) {
        if(html){
          addLine "<tr><td>$currentDay</td><td>${day / 60}<td>$endWith</td></tr>"
            } else {
              addLine currentDay + '\t' + day / 60  +'\t' + endWith
        }
    }
    
    addLine "total ${total / 60} hours"
    sb.toString()
  }
}
