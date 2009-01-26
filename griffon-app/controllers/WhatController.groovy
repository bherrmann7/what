class WhatController {

    // these will be injected by Griffon
  def model
  def view

   WhatController(){
       new MyEventQueue( saveAndExitCommand )      
   }


    def saveAndExitCommand = {
        model.text = view.ta.text
        model.save()

        System.exit(0)
    }

  
}
