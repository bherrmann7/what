class WhatController {

    // these will be injected by Griffon
  def model
  def view

   WhatController(){
       new MyEventQueue( saveAndExitCommand )      
   }


    def saveAndExitCommand = {  it ->
        if(it) {
            model.text = view.ta.text
            model.save()
        }
        System.exit(0)
    }

    def makeTally(){
      // extra new lines make sure last day is counted
        view.tallyText.text  = new Tally().doTally(view.ta.text+'\n\n')
        view.tally.show()
    }
  
}
