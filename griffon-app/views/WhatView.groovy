actions {
    action(id: 'saveAndExitCommand',
            name: 'Save (CTRL-S)',
            closure: { controller.saveAndExitCommand() }
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
            ta.text = model.tally
            tally.show()
        })
        menu(text: "Help") {
            menuItem(text: "About", mnemonic: 'A', actionPerformed: {about.show()})
        }
    }

    panel(border: emptyBorder(6)) {
        borderLayout()

        scrollPane(constraints: CENTER) {
            textArea(
                    id: 'ta', columns: 60, rows: 30,
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
                gridLayout(cols: 1, rows: 2)
                label(icon: imageIcon("TheBobs.jpg"))
                panel {
                    button(text: "Ok")
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
                            id: 'tallyText', columns: 60, rows: 30
                    )
                }
                panel(constraints: SOUTH) {
                    button(text: "Ok")
                }
            }
        }

