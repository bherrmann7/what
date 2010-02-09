import java.text.SimpleDateFormat

class WhatModel {

  File file = new File(System.properties["user.home"] + "/.doing");
  SimpleDateFormat sdf = new java.text.SimpleDateFormat("hh:mma ")

  WhatModel() {
    if (file.exists())
      text = file.text
    else {
      text = '''
Welcome!  Thanks for trying What.
"What" is a simple program for keeping track of "What it is you say you do around here?" I use What for generating weekly status reports, and for generating invoices.

When "What" starts up, it always loads $HOME/.doing into the textarea, and adds a time stamp.
An initial run might look like this,

 03:00PM

You then usually supply a brief description of what you are doing,

 03:00PM Editing _what_ wiki home page to have some basic description of what _what_ does.

After entering some text, type CTRL-S to save.   Or to just dismiss what, hit ESC.

I run What out of cron at the top of every hour.   After 15 minutes of no mouse or keyboard activity What exits (so you dont get 48 copies over the weekend.)
Here is a typical day,

Monday, September 15
07:00AM coffe
09:00AM coded flim flam sorting
03:30PM scoot
s: flim flam sorting

At the beginning of a new day, What adds a new "DDDD, MM NN" heading.    If you have any questions, email bob@jadn.com
'''
    }

    // if new day, insert new day header
    // detect new day by seeing if now is less than last time.
    def lines = text.split('\n')
    if (lines[-1].size() > sdf.toPattern().size() + 1) {
      def lastTime = lines[-1][1..sdf.toPattern().size() + 1]
      println "lastTime is " + lastTime
      try {
        Date last = sdf.parse(lastTime)
        Date now = sdf.parse(sdf.format(new Date()))
        if (last.after(now)) {
          text += '\n\n' + new SimpleDateFormat('EEEE, MMMM d').format(new Date())
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
    new Tally().doTally(text + '\n\n');
  }
}