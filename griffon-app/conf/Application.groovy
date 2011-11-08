application {
    title = 'What'
    startupGroups = ['what']

    // Should Griffon exit when no Griffon created frames are showing?
    autoShutdown = true

    // If you want some non-standard application class, apply it here
    //frameClass = 'javax.swing.JFrame'
}
mvcGroups {
    // MVC Group for "what"
    'what' {
        model      = 'what.WhatModel'
        view       = 'what.WhatView'
        controller = 'what.WhatController'
    }

}
