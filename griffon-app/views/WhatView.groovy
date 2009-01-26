actions {
    action(id: 'saveAndExitCommand',
            name: 'Save (CTRL-S)',
            closure: { controller.saveAndExitCommand() }
    )
 
}

application(title: 'What are you doing?', pack: true, locationByPlatform: true) {
    panel(border: emptyBorder(6)) {
        borderLayout()        

        scrollPane(constraints: CENTER) {
            textArea(
                    id:'ta', columns: 60, rows: 30,
                    text: bind { model.text }

            )
        }

        panel(constraints: SOUTH) {
            button(saveAndExitCommand)
        }
    }
}

