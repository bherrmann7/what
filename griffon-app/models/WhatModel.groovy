import java.text.SimpleDateFormat

class WhatModel {

  File file = new File(System.properties["user.home"] + "/.doing");
  SimpleDateFormat sdf = new java.text.SimpleDateFormat("hh:mma ")

  WhatModel() {
    if (file.exists())
      text = file.text

    // if new day, insert new day header
    // detect new day by seeing if now is less than last time.
    def lines = text.split('\n')
    if(lines[-1].size()>sdf.toPattern().size() + 1){
    def lastTime = lines[-1][1..sdf.toPattern().size() + 1]
    println "lastTime is " + lastTime
    try {
      Date last = sdf.parse(lastTime)
      Date now = sdf.parse(sdf.format(new Date()))
      if (last.after(now)) {
        text += '\n\n'+new SimpleDateFormat('EEEE, MMMM d').format(new Date())        
      }
    } catch (e) {
      // just means last line wasn't a timestamp... no worries.
    }
    }

    text += '\n ' + sdf.format(new Date())


  }


  String text = ''

  def save = {
    file.text = text;
    println "Saved $text"
  }

  String getTally() {
    println "Doing tally on " + text
    new Tally().doTally(text+'\n\n');
  }
}