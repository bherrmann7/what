package what
actions {
    action(id: 'saveAndExitCommand',
            name: 'Save (CTRL-S)',
            closure: { controller.saveAndExitCommand() }
    )
    action(id: 'publish',
            name: 'Publish',
            closure: { controller.publish() }
    )

}

def ta

application(title: 'What are you doing?', pack: true, locationByPlatform: true) {

    menuBar() {
        menu(text: "File", mnemonic: 'F') {
            menuItem(text: "Exit", mnemonic: 'X', actionPerformed: {dispose() })
        }
        hglue()
        menuItem(text: "Tally", mnemonic: 't', actionPerformed: {
            controller.makeTally()
        })
        menu(text: "Help") {
            menuItem(text: "About", mnemonic: 'A', actionPerformed: {about.show()})
        }
    }

    panel(border: emptyBorder(6)) {
        borderLayout()

        panel(constraints: NORTH) {
            comboBox(items: model.customers, selectedItem: model.currentCustomer, actionPerformed: {controller.switchCustomer(it.source.selectedItem)})
            /*
            buttonGroup().with { group ->
                model.customers.each { customer ->
                    text: customer,
                                                buttonGroup: group, selected: (model.currentCustomer == customer),
                                                actionPerformed: {controller.switchCustomer(customer)}
                }
            }
            */
        }

        scrollPane(constraints: CENTER) {
            textArea(
                    lineWrap: true, id: 'ta', columns: 60, rows: 30,
                    text: bind { model.text }
            )
        }

        panel(constraints: SOUTH) {
            button(saveAndExitCommand)
            button(publish)
        }
    }
}

about = dialog(
        title: "About What", pack: true, resizable: true,
        locationByPlatform: true)
        {
            panel(border: emptyBorder(3)) {
                borderLayout()
                label(constraints: CENTER, icon: imageIcon("TheBobs.jpg"))
                panel(constraints: SOUTH) {
                    button(text: "Ok", actionPerformed: {dispose()})
                }
            }
        }

tally = dialog(
        title: "Tally", pack: true, resizable: true,
        locationByPlatform: true)
        {
            panel(border: emptyBorder(3)) {
                borderLayout()
                scrollPane(constraints: CENTER) {
                    ta = textArea(
                            id: 'tallyText', columns: 60, rows: 15
                    )
                }
                panel(constraints: SOUTH) {
                    button(text: "Ok", actionPerformed: {dispose()})
                }
            }
        }


