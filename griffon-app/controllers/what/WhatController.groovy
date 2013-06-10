package what

import javax.swing.JOptionPane


class WhatController {

    // these will be injected by Griffon
    def model
    def view

    WhatController() {
        new MyEventQueue(saveAndExitCommand)
    }

    def saveAndExitCommand = { surpressSave ->

        if (!surpressSave) {
            model.text = view.ta.text
            model.save()
        }

        System.exit(0)
    }


    def publish = {
        model.text = view.ta.text
        model.save()
        String number
        String date
        String content
        view.ta.text.eachLine {
            if (it.startsWith('i:')) {
                println "got invoice marker: $it"
                def sections = it.split(':')
                number = sections[1]
                date = sections[2]
            }
        }
        if(!number || !date) {
            JOptionPane.showMessageDialog(null, "Missing 'i:num:date' line", "error", JOptionPane.ERROR_MESSAGE);
            return
        }
        content = new Tally().doTally(view.ta.text + '\n\n', true)
        String template = new File("/home/bob/work/inno/invoices/inv.template").text
        template = template.replaceAll('@number@', number)
        template = template.replaceAll('@date@', date)
        int spos = template.indexOf('@content@')
        template = template[0..spos-1] + content + template[(spos+('@content@'.size())+1)..-1]
        template = template.replaceAll('@content@', content)
        new File("/home/bob/work/inno/invoices/inv.tout").text = template
        // executes the template and displays the invoice in a pdf viewer
        "groovy /home/bob/work/inno/invoices/inv.tout".execute()
    }


    def makeTally() {
        // extra new lines make sure last day is counted
        try {
            view.tallyText.text = new Tally().doTally(view.ta.text + '\n\n')
            view.tally.show()
        } catch (Throwable t) {
            JOptionPane.showMessageDialog(null, t.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
        }
    }

    def switchCustomer(String customer) {
        model.text = view.ta.text
        if (model.text != model.textAsLoaded) {
            //println "Saved changes to $customer"
            model.save()
        }

        model.setCurrentCustomer(customer);
        view.ta.text = model.text
    }

}
