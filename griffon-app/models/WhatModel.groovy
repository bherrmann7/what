

class WhatModel {

    def file = new File(System.properties["user.home"]+"/.doing");

    WhatModel(){
        if (file.exists())
            text = file.text
        text += '\n '+new java.text.SimpleDateFormat("hh:mma ").format(new Date())
    }


    String text

    def save = {
        file.text = text;
        println "Saved $text"
    }
}