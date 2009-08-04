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

  
}
