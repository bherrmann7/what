actions {
    action(id: 'saveAndExitCommand',
            name: 'Save (CTRL-S)',
            closure: { controller.saveAndExitCommand(true) }
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
            model.customers.each { customer ->
                button(text: customer, id: "customer-$customer", enabled: (model.currentCustomer != customer),
                        actionPerformed: {controller.switchCustomer(customer)} )
            }
        }

        scrollPane(constraints: CENTER) {
            textArea(
                    lineWrap: true, id: 'ta', columns: 60, rows: 30,
                    text: bind { model.text }
            )
        }

        panel(constraints: SOUTH) {
            button(saveAndExitCommand)
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
                    button(text: "Ok",  actionPerformed: {dispose()})
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
                    button(text: "Ok",  actionPerformed: {dispose()})
                }
            }
        }

