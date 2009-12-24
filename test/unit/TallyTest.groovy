class TallyTest extends GroovyTestCase {

  def sampleDay = '''

Monday, December 21
 09:30AM arrive
 11:00AM emails...
 12:00PM lunch
--
 12:30PM
 01:00PM
 02:00PM status
 03:30PM finishing up final text

Tuesday, December 22
 07:00AM email... working on fixing popup text on pricing for no extras sites
 08:00AM ...
 09:00AM
 11:00AM
 12:00PM ah..  confusiion
 12:25PM lunch
--
 12:55PM verify tests are running...
 01:00PM
 01:49PM
 02:00PM sit down for stand up
 03:00PM scoot

Thursday, December 24
 07:00AM arrive read emails
 08:00AM running tests 
 09:00AM
s:burp

'''

  void testTally() {
    println new Tally().doTally(sampleDay)

  }

}
